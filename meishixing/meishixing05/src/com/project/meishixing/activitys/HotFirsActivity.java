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
import com.project.meishixing.adapters.HotFirsGvAdapter;
import com.project.meishixing.beans.FoodMagaBean;
import com.project.meishixing.beans.HotFirBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.ToasUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HotFirsActivity extends BaseActivity implements
		com.android.volley.Response.ErrorListener, Listener<String>, OnClickListener, OnItemClickListener {

	private int cityId = 5;
	private ArrayList<HotFirBean> datas = new ArrayList<HotFirBean>();
	private HotFirsGvAdapter adapter;
	private Button reBt;
	private StringRequest request;
	private HttpRequest httpRequest;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_hotfirs);
		// 找到Griview
		GridView gridView = (GridView) findViewById(R.id.gv_hotfirs);
		adapter = new HotFirsGvAdapter(this);
		findViewById(R.id.back).setOnClickListener(this);
		reBt = (Button) findViewById(R.id.bt_reflash);
		reBt.setOnClickListener(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		httpRequest = HttpRequest
				.getInstance(getApplicationContext());
		sendRequest();
	}

	@Override
	public void onResponse(String response) {
		//System.out.println(response);
		if (response == null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			reBt.setVisibility(View.VISIBLE);
			reBt.setText("网络错误点击刷新");
			reBt.setEnabled(true);
			return;
		}
		try {
			JSONObject object = new JSONObject(response);
			int status = object.getInt("status");
			if (status != 200) {
				ToasUtils.showLToast(getApplicationContext(), "服务器异常..");
				reBt.setVisibility(View.VISIBLE);
				reBt.setText("网络错误点击刷新");
				reBt.setEnabled(true);
				return;
			}
			reBt.setVisibility(View.GONE);
			reBt.setEnabled(true);
			JSONArray results = object.getJSONArray("result");
			if (results.length() <= 0) {
				ToasUtils.showLToast(getApplicationContext(), "已经没有数据咯...");
				return;
			}
			for (int i = 0; i < results.length(); i++) {
				JSONObject data = (JSONObject) results.get(i);
				datas.add(HotFirBean.getFromJson(data));
			}
			adapter.setDatas(datas);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		//System.out.println("onErrorResponse");
		setReflash();
	}

	//设置刷新
	private void setReflash() {
		reBt.setVisibility(View.VISIBLE);
		reBt.setText("网络错误点击刷新");
		reBt.setEnabled(true);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId()==R.id.back) {
			finish();
		}else if (arg0.getId()==R.id.bt_reflash) {
			sendRequest();
		}
	}

	//发送网络请求
	private void sendRequest() {
		request = new StringRequest(Method.GET,
				ApiHelp.getHotFirURL(cityId), this, this);
		httpRequest.sendRequest(request);
		reBt.setVisibility(View.VISIBLE);
		reBt.setText("正加载数据..");
		reBt.setEnabled(false);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("arg2="+arg2);
		switch (arg2) {
		case 0:
		case 1://热菜
			break;
		case 2:
		case 3://热店
			startActivity(new Intent(this, HotRestActivit.class));
			break;
		case 4://达人
			startActivity(new Intent(this, DaRenAcitivity.class));
			break;
		}
	}
}
