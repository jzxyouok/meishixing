package com.project.meishixing.activitys;

import meishixing.MeshixingApp;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.project.meishixing.R;
import com.project.meishixing.beans.RestDetailBean;
import com.project.meishixing.db.DBmessage;
import com.project.meishixing.db.OpenDBhelp;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.SetStarBG;
import com.project.meishixing.utils.ToasUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaDetailActivity extends BaseActivity implements
		com.android.volley.Response.ErrorListener, Listener<String>,
		OnMarkerClickListener, OnMapClickListener, OnInfoWindowClickListener,
		OnClickListener {

	private HttpRequest request;
	private int placeID;
	private StringRequest srequest;
	private MapView mMapView;
	private RestDetailBean bean;
	private TextView resNameV;
	private TextView priceV;
	private TextView addressV;
	private TextView openTimeV;
	private TextView looksV;
	private BaiduMap baiduMap;
	private InfoWindow infoWindow;
	private TextView phoneView;
	private View layout_check;
	private View callvView;
	private View resmesView;
	private AlertDialog alertDialog;
	private OpenDBhelp dBhelp;
	private boolean isCollec;
	private String url;
	private ImageView iv_collec;
	private Button bt_reflash;
	private ImageView starIV;
	private View v;
	private boolean isLooked;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_restdetail);
		dBhelp = OpenDBhelp.getDBhelp(getApplicationContext());
		placeID = getIntent().getIntExtra("placeID", -1);
		url = ApiHelp.getResDetailtURL(placeID);
		isCollec = dBhelp.isExistDatas(placeID + "", DBmessage.COLLECTED);
		isLooked = dBhelp.isExistDatas(placeID + "", DBmessage.LOOKDE);
		request = HttpRequest.getInstance(getApplicationContext());
		mMapView = (MapView) findViewById(R.id.bm_restdetail);
		mMapView.showZoomControls(false);
		findViews();
		sendRequest();
	}

	// 初始化控件
	private void findViews() {

		starIV = (ImageView) findViewById(R.id.iv_restdeta_star);
		resNameV = (TextView) findViewById(R.id.tv_restdeta_resname);
		priceV = (TextView) findViewById(R.id.tv_restdeta_price);
		addressV = (TextView) findViewById(R.id.tv_restdeta_address);
		openTimeV = (TextView) findViewById(R.id.tv_restdeta_opentime);
		looksV = (TextView) findViewById(R.id.tv_restdeta_looks);
		phoneView = (TextView) findViewById(R.id.tv_restdeta_phone);
		findViewById(R.id.back_restdetail).setOnClickListener(this);
		findViewById(R.id.iv_restdetail_home).setOnClickListener(this);
		callvView = findViewById(R.id.layout_restdetail_call);
		layout_check = findViewById(R.id.layout_restdetail_checkrout);
		resmesView = findViewById(R.id.layout_restdetail_resmes);
		callvView.setOnClickListener(this);
		layout_check.setOnClickListener(this);
		resmesView.setOnClickListener(this);
		iv_collec = (ImageView) findViewById(R.id.iv_resDetail_collec);
		v = findViewById(R.id.layout_restdetail_collec);
		findViewById(R.id.layout_restdetail_error).setOnClickListener(this);
		findViewById(R.id.layout_restdetail_photo).setOnClickListener(this);
		findViewById(R.id.layout_restdetail_send).setOnClickListener(this);
		bt_reflash = (Button) findViewById(R.id.bt_reflash);
		bt_reflash.setOnClickListener(this);
		if (isCollec) {
			iv_collec.setImageResource(R.drawable.tb_liked);
		} else {
			iv_collec.setImageResource(R.drawable.tb_like);
		}
	}

	// 设置百度地图
	private void baiduMapSet() {
		baiduMap = mMapView.getMap();
		LatLng latLng = new LatLng(bean.place_geo_lat, bean.place_geo_lng);
		MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(latLng,
				16f);
		baiduMap.animateMapStatus(update);
		// 创建一个覆盖物的点
		MarkerOptions options = new MarkerOptions();
		// 创建一个图标
		BitmapDescriptor curIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.da_marker_red);
		// 设置覆盖物的坐标点
		options.position(latLng);
		// 设置覆盖物的图标
		options.icon(curIcon);
		// 添加覆盖物到地图上
		baiduMap.addOverlay(options);
		// 设置Mark覆盖物的点击事件
		baiduMap.setOnMarkerClickListener(this);
		// 设置地图的单击事件监听
		baiduMap.setOnMapClickListener(this);
	}

	// 发送数据
	private void sendRequest() {
		// System.out.println("sendRequest");
		srequest = new StringRequest(Method.GET, url, this, this);
		request.sendRequest(srequest);
		bt_reflash.setVisibility(View.VISIBLE);
		bt_reflash.setText("数据加载中...");
		bt_reflash.setEnabled(false);
	}

	@Override
	public void onResponse(String response) {

		// System.out.println("onResponse"+response);
		if (response == null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			bt_reflash.setVisibility(View.VISIBLE);
			bt_reflash.setText("网络错误,点击重新加载");
			bt_reflash.setEnabled(true);
			return;
		}
		try {
			JSONObject object = new JSONObject(response);
			int resCode = object.getInt("status");
			if (resCode != 200) {
				ToasUtils.showLToast(getApplicationContext(), "服务器错误.");
				bt_reflash.setVisibility(View.VISIBLE);
				bt_reflash.setText("网络错误,点击重新加载");
				bt_reflash.setEnabled(true);
				return;
			}
			JSONObject result = object.getJSONObject("result");
			// 解析json
			bean = new RestDetailBean();
			bean = bean.getFromJson(result);
			setDatas();
			bt_reflash.setVisibility(View.GONE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 设置数据
	private void setDatas() {
		if (bean.open_time.equals("")) {
			openTimeV.setVisibility(View.GONE);
		} else {
			openTimeV.setText("营业时间:" + bean.open_time);
		}
		looksV.setText("浏览" + bean.view_total + "次");
		phoneView.setText("拨打电话:" + bean.phone);
		starIV.setImageResource(SetStarBG.setStarBG(bean.star + ""));
		priceV.setText("人均" + bean.price + "元");
		addressV.setText(bean.address);
		resNameV.setText(bean.category);
		// 设置收藏可以点击
		v.setOnClickListener(this);
		// 添加到浏览记录
		if (!isLooked) {
			dBhelp.inserData(placeID + "", bean.url, bean.category,
					bean.place_name, DBmessage.LOOKDE, 1);
		}
		// 设置百度地图
		baiduMapSet();
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// System.out.println("onErrorResponse");
		bt_reflash.setText("网络错误,点击重新加载");
		bt_reflash.setEnabled(true);
	}

	// ------------------生命周期方法----------------------------
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// --------------百度地图回调--------------------------

	/**
	 * 当地图上的某个mark类型的覆盖物被点击的时候调用
	 * 
	 * @Marker marker 被点击的那个Mark覆盖物点
	 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		// 显示店铺名字
		Button button = new Button(this);
		button.setBackgroundResource(R.drawable.magzine_bg);
		button.setText(bean.place_name);
		LatLng latLng = marker.getPosition();
		infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button),
				latLng, -90, this);

		// 弹出Infowindow
		baiduMap.showInfoWindow(infoWindow);
		return false;
	}

	/**
	 * 当地图被点击
	 */
	@Override
	public void onMapClick(LatLng arg0) {

	}

	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}

	// 点击显示信息
	@Override
	public void onInfoWindowClick() {
		// 隐藏信息
		baiduMap.hideInfoWindow();
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		if (v == bt_reflash) {
			sendRequest();
		}
		Intent intent;
		switch (v.getId()) {
		case R.id.back_restdetail:// 点击返回
			finish();
			break;
		case R.id.iv_restdetail_home:// 点击主页
			MeshixingApp.back2Home(false);
			break;
		case R.id.layout_restdetail_call:// 点击打电话
			callStor();
			break;
		case R.id.layout_restdetail_checkrout:// 点击查询路线
			getRoutPlan();
			break;
		case R.id.layout_restdetail_collec:// 点击收藏
			if (isCollec) {
				dBhelp.delDatas(placeID + "", DBmessage.COLLECTED);
				iv_collec.setImageResource(R.drawable.tb_like);
				isCollec = false;
			} else {
				dBhelp.inserData(placeID + "", bean.url, bean.place_name,
						bean.address, DBmessage.COLLECTED, 1);
				iv_collec.setImageResource(R.drawable.tb_liked);
				isCollec = true;
			}
			break;
		case R.id.layout_restdetail_error:// 点击错误反馈
			intent = new Intent(this, SugActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_restdetail_photo:// 选择图片
			showPictureDialog();
			break;
		case R.id.layout_restdetail_resmes:// 点击餐厅信息
			showStorMes();
			break;
		case R.id.layout_restdetail_send:// 点击分享
			shareMesg();
			break;
		case R.id.dialog_tk:// 图库选图
			selectImage();
			break;
		case R.id.dialog_pz:// 拍照
			getPhotoFromCam();
			break;
		}
	}

	// 显示选择图片对话框
	private void showPictureDialog() {
		// 创建dialog
		AlertDialog.Builder dialog = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_resdeta, null);
		View pz = view.findViewById(R.id.dialog_pz);
		pz.setOnClickListener(this);
		View tk = view.findViewById(R.id.dialog_tk);
		tk.setOnClickListener(this);
		dialog.setTitle("分享美食");
		dialog.setView(view);
		alertDialog = dialog.create();
		alertDialog.show();

	}

	// 相机拍照
	private void getPhotoFromCam() {
		// 用户选择拍照
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		// intent.addCategory("android.intent.category.LAUNCHER");
		startActivityForResult(intent, 2222);
	}

	// 获取图库图片
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Intent intent = new Intent(this, ShareFoodActivity.class);
		if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			intent.putExtra("flag", 1);
			intent.putExtra("uri", uri);
			startActivity(intent);
		}
		if (requestCode == 2222 && resultCode == Activity.RESULT_OK) {
			// 获得data里的图片
			Bundle bundle = data.getExtras();
			intent.putExtra("flag", 2);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}
		alertDialog.dismiss();

	}

	// 从图库选择图片
	private void selectImage() {
		// 创建隐式意图打开图库
		Intent intent = new Intent();
		// 设置intent的action
		intent.setAction("android.intent.action.GET_CONTENT");
		// 设置要发送的数据类型
		intent.setType("image/*");
		// 开启activity
		startActivityForResult(intent, 1111);
	}

	// 创建分享文本的意图
	private void shareMesg() {
		// 创建隐式意图
		Intent intent = new Intent();
		// 设置intent的action
		intent.setAction(Intent.ACTION_SEND);
		// 设置要发送的数据类型
		intent.setType("text/plain");
		// 添加数据
		intent.putExtra(
				Intent.EXTRA_TEXT,
				bean.place_name
						+ "\n"
						+ bean.address
						+ "\n"
						+ "http://meishixing.com/placenew/weixin/food?tweet_id=191562&from=groupmessage&isappinstalled=1");
		// 开启activity
		startActivity(intent);
	}

	// 显示商店信息
	private void showStorMes() {
		Intent intent;
		if (bean == null) {
			ToasUtils.showLToast(getApplicationContext(),
					"服务器没有返回数据哦,请重新加载试试..");
			return;
		}
		intent = new Intent(this, RestaMesActivity.class);
		if (bean == null) {
			bean = new RestDetailBean();
		}
		intent.putExtra("bus", bean.bus);
		// System.out.println("bus=" + bean.bus);
		intent.putExtra("tags", bean.tags);
		startActivity(intent);
	}

	// 查询路线
	private void getRoutPlan() {
		Intent intent;
		if (bean == null) {
			ToasUtils.showLToast(getApplicationContext(),
					"服务器没有返回数据哦,请重新加载试试..");
			return;
		}
		intent = new Intent(this, MapActivity.class);
		intent.putExtra("storLay", bean.place_geo_lat);
		intent.putExtra("storLng", bean.place_geo_lng);
		intent.putExtra("storName", bean.place_name);
		intent.putExtra("city", bean.city_name);
		startActivity(intent);
	}

	// 呼叫电话
	private void callStor() {
		Intent intent;
		if (bean == null) {
			ToasUtils.showLToast(getApplicationContext(),
					"服务器没有返回数据哦,请重新加载试试..");
			return;
		}
		String phone;
		if (bean.phone.contains(";")) {
			phone = bean.phone.substring(0, bean.phone.lastIndexOf(";"));
		} else {
			phone = bean.phone;
		}
		intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
		startActivity(intent);
	}
}
