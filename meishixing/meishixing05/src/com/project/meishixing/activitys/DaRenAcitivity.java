package com.project.meishixing.activitys;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.adapters.DarenAdapter;
import com.project.meishixing.beans.DarenpxBean;
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
import android.widget.ListView;

public class DaRenAcitivity extends BaseActivity implements
		com.android.volley.Response.ErrorListener, Listener<String>,
		OnClickListener, OnItemClickListener {

	private int cityId = 5;
	private ArrayList<DarenpxBean> datas = new ArrayList<DarenpxBean>();
	private DarenAdapter adapter;
	private Button button;
	private HttpRequest httpRequest;
	private StringRequest request;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_daren);
		httpRequest = HttpRequest.getInstance(getApplicationContext());
		// 找到listview
		ListView listView = (ListView) findViewById(R.id.lv_daren_list);
		listView.setOnItemClickListener(this);
		button = (Button) findViewById(R.id.bt_reflash);
		button.setOnClickListener(this);
		sendResquest();

		adapter = new DarenAdapter(httpRequest);
		listView.setAdapter(adapter);
	}

	private void sendResquest() {
		button.setText("数据加载中..");
		button.setVisibility(View.VISIBLE);
		button.setClickable(false);
		request = new StringRequest(Method.GET, ApiHelp.getDaRenURL(cityId),
				this, this);
		httpRequest.sendRequest(request);
	}

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		if (response == null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			button.setVisibility(View.VISIBLE);
			button.setText("网络错误点击重试");
			button.setClickable(true);
			return;
		}
		try {
			JSONObject object = new JSONObject(response);
			int status = object.getInt("status");
			if (status != 200) {
				ToasUtils.showLToast(getApplicationContext(), "服务器异常..");
				button.setVisibility(View.VISIBLE);
				button.setText("服务器错误点击重试");
				button.setClickable(true);
				return;
			}
			JSONObject result = object.getJSONObject("result");
			JSONArray result_city = result.getJSONArray("top_user_by_city");
			if (result_city.length() <= 0) {
				ToasUtils.showLToast(getApplicationContext(), "已经没有数据咯...");
				return;
			}
			button.setVisibility(View.GONE);
			// 获取城市徘行
			for (int i = 0; i < result_city.length(); i++) {
				JSONObject data = (JSONObject) result_city.get(i);
				datas.add(DarenpxBean.getFromJson(data));
			}
			JSONArray result_top = result.getJSONArray("top_user_all");
			// 获取全国排行
			for (int i = 0; i < result_top.length(); i++) {
				JSONObject data = (JSONObject) result_top.get(i);
				datas.add(DarenpxBean.getFromJson(data));
			}
			
			adapter.setDatas(datas);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 网络错误
	@Override
	public void onErrorResponse(VolleyError error) {
		button.setVisibility(View.VISIBLE);
		button.setText("网络错误点击重试");
		button.setClickable(true);
	}

	@Override
	public void onClick(View v) {
		sendResquest();
	}

	//listView 点击
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this, UserFoodActivity.class);
		int uid=datas.get(position).user_id;
		intent.putExtra("uid", uid);
		startActivity(intent);
	}
}
