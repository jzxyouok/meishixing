package com.project.meishixing.activitys;

import java.util.ArrayList;

import org.com.cctest.view.HorizontalListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.adapters.HotFoodHListViewAdapter;
import com.project.meishixing.beans.HotFootListBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.ToasUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class HotFoodActivity extends BaseActivity implements ErrorListener,
		Listener<String>, OnClickListener, OnItemClickListener {

	private StringRequest request;
	private HttpRequest httpRequest;
	private ArrayList<HotFootListBean> data_fir = new ArrayList<HotFootListBean>();
	private ArrayList<HotFootListBean> data_sec = new ArrayList<HotFootListBean>();
	private ArrayList<HotFootListBean> data_thr = new ArrayList<HotFootListBean>();
	private HotFoodHListViewAdapter adapter_fir;
	private HotFoodHListViewAdapter adapter_thr;
	private HotFoodHListViewAdapter adapter_sec;
	private int cityId=5;
	private double lat;
	private double lng;
	private Button button;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_hotfood);
		//TODO 获得传递过来的城市id跟经纬度
		
		button = (Button) findViewById(R.id.bt_reflash);
		button.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		// 找到横向listview
		HorizontalListView lv_wh = (HorizontalListView) findViewById(R.id.hlv_hotfood_firs);
		HorizontalListView lv_wm = (HorizontalListView) findViewById(R.id.hlv_hotfood_sec);
		HorizontalListView lv_ml = (HorizontalListView) findViewById(R.id.hlv_hotfood_three);
		// 设置item点击
		lv_ml.setOnItemClickListener(this);
		lv_wm.setOnItemClickListener(this);
		lv_wh.setOnItemClickListener(this);
		adapter_fir = new HotFoodHListViewAdapter(this);
		adapter_sec = new HotFoodHListViewAdapter(this);
		adapter_thr = new HotFoodHListViewAdapter(this);
		// 查看更多监听
		findViewById(R.id.tv_hotfood_hotmon_lookmore).setOnClickListener(this);
		findViewById(R.id.tv_hotfood_hotweek_lookmore).setOnClickListener(this);
		findViewById(R.id.tv_hotfood_likemost_lookmore)
				.setOnClickListener(this);
		lv_ml.setAdapter(adapter_thr);
		lv_wh.setAdapter(adapter_fir);
		lv_wm.setAdapter(adapter_sec);

		httpRequest = HttpRequest.getInstance(getApplicationContext());
		sendRequest();
	}

	private void sendRequest() {
		button.setVisibility(View.VISIBLE);
		button.setText("数据加载中..");
		button.setClickable(false);
		request = new StringRequest(Method.GET, ApiHelp.getHotFoodURL(5,
				23.174044, 113.341), this, this);
		httpRequest.sendRequest(request);
	}

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		// System.out.println(response);
		if (response == null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			button.setVisibility(View.VISIBLE);
			button.setText("网络错误点击重试..");
			button.setClickable(true);
			return;
		}
		try {
			JSONObject object = new JSONObject(response);
			int status = object.getInt("status");
			if (status != 200) {
				ToasUtils.showLToast(getApplicationContext(), "服务器异常..");
				button.setVisibility(View.VISIBLE);
				button.setText("网络错误点击重试..");
				button.setClickable(true);
				return;
			}
			JSONObject result = object.getJSONObject("result");
			// 解析本周最热数据
			JSONArray week_best = result.getJSONArray("week_best");
			for (int i = 0; i < week_best.length(); i++) {
				JSONObject wb_data = week_best.getJSONObject(i);
				HotFootListBean firBean = HotFootListBean.getFromJson(wb_data);
				if (firBean != null) {
					data_fir.add(firBean);
				}
			}
			button.setVisibility(View.GONE);
			// 更新本周最热数据
			adapter_fir.setDatas(data_fir);

			// 解析喜欢最多数据
			JSONArray like_most = result.getJSONArray("like_most");
			for (int i = 0; i < like_most.length(); i++) {
				JSONObject lm_data = like_most.getJSONObject(i);
				HotFootListBean bean = HotFootListBean.getFromJson(lm_data);
				if (bean != null) {
					data_thr.add(bean);
				}
			}
			// 更新喜欢最多数据
			adapter_thr.setDatas(data_thr);
			// 解析本月最热数据
			JSONArray month_best = result.getJSONArray("month_best");
			for (int i = 0; i < month_best.length(); i++) {
				JSONObject mb_data = like_most.getJSONObject(i);
				HotFootListBean bean = HotFootListBean.getFromJson(mb_data);
				if (bean != null) {
					data_sec.add(bean);
				}
			}
			// 更新本月最热数据
			adapter_sec.setDatas(data_sec);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 网络错误回调
	@Override
	public void onErrorResponse(VolleyError error) {
		System.out.println("onErrorResponse");
	}

	// 点击更多
	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.back) {
			finish();
			return;
		}
		Intent intent=new Intent(this, HotMoreFoodActivity.class);
		switch (v.getId()) {
		case R.id.tv_hotfood_hotmon_lookmore://点击查看本月热门更多
			intent.putExtra("tittle", "本月最热");
			intent.putExtra("witch", ApiHelp.MOUNT_HOT);
			break;
		case R.id.tv_hotfood_hotweek_lookmore://点击查看本周热门更多
			intent.putExtra("tittle", "本周最热");
			intent.putExtra("witch", ApiHelp.WEEK_HOT);
			break;
		case R.id.tv_hotfood_likemost_lookmore://点击查看喜欢最多更多
			intent.putExtra("tittle", "喜欢最多");
			intent.putExtra("witch", ApiHelp.LIKE_MOST);
			break;
		}
		intent.putExtra("cityId", cityId);
		intent.putExtra("lat", lat);
		intent.putExtra("lng", lng);
		intent.putExtra("flag", ApiHelp.FLAG_MOREHOTFOOD);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this, NearFoodShowActivity.class);
		int tweet_id = 0;
		switch (parent.getId()) {
		case R.id.hlv_hotfood_firs:
			tweet_id=data_fir.get(position).tweet_id;
			break;
		case R.id.hlv_hotfood_sec:
			tweet_id=data_sec.get(position).tweet_id;
			break;
		case R.id.hlv_hotfood_three:
			tweet_id=data_thr.get(position).tweet_id;
			break;
		}
	//	System.out.println("tweet_id="+tweet_id);
		intent.putExtra("tweet_id", tweet_id);
		startActivity(intent);
	}

}
