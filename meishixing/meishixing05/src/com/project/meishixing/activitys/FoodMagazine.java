package com.project.meishixing.activitys;

import java.util.ArrayList;

import org.com.cctest.view.XListView;
import org.com.cctest.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.adapters.FoodMagAdapter;
import com.project.meishixing.beans.FoodMagaBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.ToasUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FoodMagazine extends BaseActivity implements OnItemClickListener,
		IXListViewListener, ErrorListener, Listener<String>, OnClickListener {

	private XListView listView;
	private StringRequest request;
	private HttpRequest httpRequest;
	private int cityId = 5;
	private int page;
	private ArrayList<FoodMagaBean> datas=new ArrayList<FoodMagaBean>();
	private FoodMagAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_foodmagazine);
		listView = (XListView) findViewById(R.id.xlv_foodmag);
		findViewById(R.id.back).setOnClickListener(this);
		// 设置上啦下拉刷新可用
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		// 设置监听
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(this);
		adapter = new FoodMagAdapter(this);
		listView.setAdapter(adapter);
		httpRequest = HttpRequest.getInstance(getApplicationContext());
		sendRequest();
	}

	// 发送网络请求
	private void sendRequest() {
		page++;
		request = new StringRequest(Method.GET, ApiHelp.getFoodMagaURL(cityId,
				page), this, this);
		httpRequest.sendRequest(request);
	}

	// listView item点击
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this, FoodMagDetailActivity.class);
		intent.putExtra("foodzine_id", datas.get(position).foodzine_id);
		startActivity(intent);
	}

	// 刷新
	@Override
	public void onRefresh() {
		page=0;
		sendRequest();
	}

	// 加载更多
	public void onLoadMore() {
		sendRequest();
	}

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		listView.stopLoadMore();
		listView.stopRefresh();
		if (response == null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			return;
		}
		try {
			JSONObject object = new JSONObject(response);
			int status;
			status = object.getInt("status");
			JSONArray results=object.getJSONArray("result");
			if (status != 200) {
				ToasUtils.showLToast(getApplicationContext(), "服务器异常..");
				return;
			}if (results.length()<=0) {
				ToasUtils.showLToast(getApplicationContext(), "已经没有数据咯...");
				return;
			}
			//刷新清空数据
			if (page==1) {
				datas.clear();
			}
			for (int i = 0; i < results.length(); i++) {
				JSONObject data=(JSONObject) results.get(i);
				datas.add(FoodMagaBean.getFromJson(data));
			}
			adapter.setDatas(datas);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 网络错误回调
	@Override
	public void onErrorResponse(VolleyError error) {
		listView.stopLoadMore();
		listView.stopRefresh();
		if (page>0) {
			page--;
		}
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId()==R.id.back) {
			finish();
		}
	}
}
