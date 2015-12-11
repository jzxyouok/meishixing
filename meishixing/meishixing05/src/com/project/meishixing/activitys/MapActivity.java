package com.project.meishixing.activitys;

import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.project.meishixing.R;
import com.project.meishixing.utils.LocationHelp;
import com.project.meishixing.utils.ToasUtils;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MapActivity extends BaseActivity implements
		OnGetRoutePlanResultListener, OnCheckedChangeListener,
		android.widget.CompoundButton.OnCheckedChangeListener {

	// 起点编辑框
	private EditText startEdit;
	// 路线规划搜索对象
	private RoutePlanSearch mSearch;
	// 终点编辑框
	private EditText endEdit;

	private MapView mapView;
	private BaiduMap baiduMap;
	private String city;
	private double storLay;
	private double storLng;
	private double myLay;
	private double myLng;
	private String storName;
	private Location latAndLng;
	private TextView textView;
	private CheckBox checkBox;
	private View scView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_map);
		// 找到地图
		mapView = (MapView) findViewById(R.id.bm_map);
		mapView.showZoomControls(false);
		storLay = getIntent().getDoubleExtra("storLay", -1);
		storLng = getIntent().getDoubleExtra("storLng", -1);
		storName = getIntent().getStringExtra("storName");
		city = getIntent().getStringExtra("city");
		// 找到其他控件
		startEdit = (EditText) findViewById(R.id.et_map_from);
		endEdit = (EditText) findViewById(R.id.et_map_to);
		if (storName != null && !storName.equals("")) {
			endEdit.setText(storName);
		}
		RadioGroup rGroup = (RadioGroup) findViewById(R.id.rg_map);
		rGroup.setOnCheckedChangeListener(this);
		checkBox = (CheckBox) findViewById(R.id.cb_map);
		checkBox.setOnCheckedChangeListener(this);
		textView = (TextView) findViewById(R.id.tv_map_roatdetail);
		scView = findViewById(R.id.sv_map);
		baiduMap = mapView.getMap();
		// 获取路径规划的搜索对象
		mSearch = RoutePlanSearch.newInstance();
		// 设置路径规划结果回调对象
		mSearch.setOnGetRoutePlanResultListener(this);
		latAndLng = LocationHelp.getLatAndLng(this);
		if (latAndLng!=null) {
			myLay=latAndLng.getLatitude();
			myLng=latAndLng.getLongitude();
		}else {
			myLay=storLay;
			myLng=storLng;
		}
		//默认显示自己位置
		LatLng latLng = new LatLng(myLay, myLng);
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(latLng,
				16f);
		baiduMap.animateMapStatus(update);
		// 默认发起自驾查询
		planDringRoute();
	}

	// 单选框改变
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		/*System.out.println("onCheckedChanged 起点"
				+ startEdit.getText().toString() + "终点"
				+ endEdit.getText().toString());*/
		if (endEdit.getText().toString().equals("")
				|| startEdit.getText().toString().equals("")) {
			ToasUtils.showLToast(getApplicationContext(), "起点和终点不能为空");
			return;
		}
		switch (checkedId) {
		case R.id.rb_map_fir:// 发起公交查询
			planTransitRoute();
			break;
		case R.id.rb_map_sec:// 发起自驾查询
			planDringRoute();
			break;
		case R.id.rb_map_thre:// 发起步行查询
			planWalkRoute();
			break;
		}

	}

	// 公交路线查询
	private void planTransitRoute() {

		TransitRoutePlanOption option = new TransitRoutePlanOption();
		// 公交规划一定要设置城市名
		if (city == null) {
			city = "广州";
			option.city(city);
		} else {
			option.city(city);
		}
		PlanNode endNode = getEndNode();
		PlanNode startNode = getStarNode();
		option.from(startNode);
		option.to(endNode);
		mSearch.transitSearch(option);
	}

	// 自驾路线查询
	private void planDringRoute() {
		// 创建一个驾车路径规划的参数对象
		DrivingRoutePlanOption option = new DrivingRoutePlanOption();

		PlanNode startNode = getStarNode();
		PlanNode endNode = getEndNode();
		// 设置起点和终点
		option.from(startNode);
		option.to(endNode);

		// 向百度服务器发起驾车路径规划请求
		mSearch.drivingSearch(option);
	}

	// 步行路线查询
	private void planWalkRoute() {
		// 创建一个步行的路径规划的参数
		WalkingRoutePlanOption option = new WalkingRoutePlanOption();

		PlanNode startNode = getStarNode();
		PlanNode endNode = getEndNode();
		option.from(startNode);
		option.to(endNode);

		// 发出步行的规划请求
		mSearch.walkingSearch(option);
	}

	// 获取开始点
	private PlanNode getStarNode() {
		PlanNode startNode;
		if (latAndLng != null && startEdit.getText().toString().equals("我的位置")) {
			startNode = PlanNode.withLocation(new LatLng(myLay, myLng));
		} else {
			startNode = PlanNode.withCityNameAndPlaceName(city, startEdit
					.getText().toString());
		}
		return startNode;
	}

	// 获取结束点
	private PlanNode getEndNode() {
		PlanNode endNode;
		if (storLay != -1 && storLng != -1
				&& endEdit.getText().toString().equals(storName)) {
			endNode = PlanNode.withLocation(new LatLng(storLay, storLng));
		} else {
			endNode = PlanNode.withCityNameAndPlaceName(city, endEdit.getText()
					.toString());
		}
		return endNode;
	}

	// 路线规划公交回调
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		System.out.println("onGetTransitRouteResult");
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {// 处理有错误的情况
			ToasUtils.showLToast(getApplicationContext(), "无可用公交信息");
			return;
		}

		// 获取公交规划结果
		List<TransitRouteLine> lines = result.getRouteLines();

		// 创建公交规划覆盖物
		TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);

		baiduMap.setOnMarkerClickListener(overlay);

		overlay.setData(lines.get(0));
		overlay.addToMap();
		overlay.zoomToSpan();

		printPlanResult(lines);
	}

	// 路线规划步行回调
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {// 处理有错误的情况
			ToasUtils.showLToast(getApplicationContext(), "无可用步行信息");
			return;
		}
		List<WalkingRouteLine> lines = result.getRouteLines();
		// 创建步行路线的覆盖物
		WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
		baiduMap.setOnMarkerClickListener(overlay);
		overlay.setData(lines.get(0));
		overlay.addToMap();
		overlay.zoomToSpan();
		printPlanResult(lines);
	}

	/**
	 * 驾车路径规划结果回调
	 */
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {// 处理有错误的情况
			ToasUtils.showLToast(getApplicationContext(), "无可用驾车信息");
			return;
		}
		// 获取路径 规划的结果集合
		List<DrivingRouteLine> lines = result.getRouteLines();
		// 创建一个驾车结果的覆盖物
		DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
		// 设置路线节点点击事件，默认实现是Toast提示
		baiduMap.setOnMarkerClickListener(overlay);
		// 给覆盖物设置数据
		overlay.setData(lines.get(0));
		// 把覆盖物添加到地图上
		overlay.addToMap();
		// 缩放到合适的视野
		overlay.zoomToSpan();
		printPlanResult(lines);
	}

	//获得详细路线信息
	private void printPlanResult(
			List<? extends RouteLine<? extends RouteStep>> lines) {
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		// 打印结果
		for (RouteLine<? extends RouteStep> line : lines) {
			// 获取路径的步骤
			List<? extends RouteStep> steps = line.getAllStep();
			// 获取距离
			int distance = line.getDistance();
			// 获取耗费时间
			int duration = line.getDuration() / 60;
			String title = line.getTitle();
			buffer.append("路线" + i + ":\n");
			i++;
			buffer.append("距离：" + distance + "米\n");
			buffer.append("耗费时长：" + duration + "分钟\n");
			/*
			 * System.out.println("距离：" + distance); System.out.println("耗费时长："
			 * + duration + " 秒"); System.out.println("title = " + title);
			 */

			//添加步骤
			for (RouteStep step : steps) {
				if (step instanceof DrivingStep) {
					DrivingStep step1 = (DrivingStep) step;
					buffer.append(step1.getInstructions() + "\n\n");
					// System.out.println(step1.getInstructions());
				} else if (step instanceof WalkingStep) {
					WalkingStep step1 = (WalkingStep) step;
					buffer.append(step1.getInstructions() + "\n\n");
					// System.out.println(step1.getInstructions());
				} else if (step instanceof TransitStep) {
					TransitStep step1 = (TransitStep) step;
					buffer.append(step1.getInstructions() + "\n\n");
				}
			}
		}
		// 添加显示信息
		textView.setText(buffer);
	}

	// 选择是否显示详细信息
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (checkBox.isChecked()) {
			scView.setVisibility(View.VISIBLE);
			checkBox.setText("点击隐藏详细路线");
		} else {
			scView.setVisibility(View.GONE);
			checkBox.setText("点击显示详细路线");
		}
		checkBox.setChecked(isChecked);
	}
	
	//---------------生命周期方法-----------------------
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mapView.onPause();
	}
}
