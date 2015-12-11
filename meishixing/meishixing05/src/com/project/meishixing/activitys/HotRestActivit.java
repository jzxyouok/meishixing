package com.project.meishixing.activitys;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

import org.com.cctest.view.XListView;
import org.com.cctest.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.BaseDatas.SpannerDatas;
import com.project.meishixing.adapters.HotRestListAdaprer;
import com.project.meishixing.adapters.NearRestListAdaprer;
import com.project.meishixing.adapters.NearRestSpinerAdapter;
import com.project.meishixing.beans.HotRestListItemBean;
import com.project.meishixing.beans.NearRestListItemBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.LocationHelp;
import com.project.meishixing.utils.ToasUtils;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class HotRestActivit extends BaseActivity implements OnClickListener,
		IXListViewListener, com.android.volley.Response.ErrorListener,
		Listener<String>, OnItemClickListener {

	private View backView;
	private ArrayList<HotRestListItemBean> datas = new ArrayList<HotRestListItemBean>();
	private int page = 0;
	private double lat = 23.17671;
	private double lng = 113.349413;
	private HotRestListAdaprer adaprer;
	private HttpRequest request;
	private StringRequest srequest;
	private XListView list;
	private ProgressBar bar;
	private int cityId=5;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_hotrest);
		// 获得当前位置
		Location location = LocationHelp.getLatAndLng(this);
		if (location != null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
		}
		bar = (ProgressBar) findViewById(R.id.pb_nearrest_reflash);
		request = HttpRequest.getInstance(getApplicationContext());
		sendRequest();
		// 找到返回键
		backView = findViewById(R.id.back_nearrest);
		backView.setOnClickListener(this);
		list = (XListView) findViewById(R.id.lv_nearrest);
		// 设置上啦下拉刷新可用
		list.setPullLoadEnable(true);
		list.setPullRefreshEnable(true);
		// 设置监听
		list.setXListViewListener(this);
		list.setOnItemClickListener(this);
		adaprer = new HotRestListAdaprer(datas);
		list.setAdapter(adaprer);
	}

	//发送数据
	private void sendRequest() {
		//System.out.println("sendRequest");
		bar.setVisibility(View.VISIBLE);
		page++;
		String url=ApiHelp.getHotRestaURL(cityId, lat, lng, page);
		srequest = new StringRequest(Method.GET,
				url, this, this);
		//System.out.println("sendRequest"+url);
		request.sendRequest(srequest);
	}

	// 点击监听
	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.back_nearrest) {
			finish();
		}
	}

	// listView下拉上拉监听
	public void onRefresh() {
		page=0;
		sendRequest();
	}

	public void onLoadMore() {
		sendRequest();
	}

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		//完成刷新
		reflashFinish();
		//System.out.println(response);
		//解析数据
		if (response==null) {
			ToasUtils.showLToast(getApplicationContext(), "网络错误..");
			return;
		}
		try {
			JSONObject object=new JSONObject(response);
			int resCode=object.getInt("status");
			JSONArray results=object.getJSONArray("result");
			if (resCode!=200||results.length()==0) {
				ToasUtils.showLToast(getApplicationContext(), "没有可用数据了");
				return;
			}
			//如果是下拉刷新
			if (page==1) {
				datas.clear();
			}
			for (int i = 0; i < results.length(); i++) {
				JSONObject data=(JSONObject) results.get(i);
				HotRestListItemBean bean=new HotRestListItemBean();
				datas.add(bean.getFromJson(data));
			}
			//修改数据
			adaprer.addDatas(datas);
			//System.out.println("---------"+adaprer.getDatas().size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 网络错误回调
	@Override
	public void onErrorResponse(VolleyError error) {
		ToasUtils.showLToast(getApplicationContext(), "服务器错误..");
		reflashFinish();
	}

	//完成刷新
	private void reflashFinish() {
		bar.setVisibility(View.GONE);
		list.stopLoadMore();
		list.stopRefresh();
	}

	// item Click
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position>0) {
			position=position-1;
		}
		Intent intent=new Intent(this, RestaDetailActivity.class);
		intent.putExtra("placeID", adaprer.getDatas().get(position).place_id);
		intent.putExtra("lat", adaprer.getDatas().get(position).lat);
		intent.putExtra("lng", adaprer.getDatas().get(position).lng);
		intent.putExtra("address", adaprer.getDatas().get(position).address);
		intent.putExtra("price", adaprer.getDatas().get(position).price);
		intent.putExtra("star", adaprer.getDatas().get(position).star+"");
		intent.putExtra("resName", adaprer.getDatas().get(position).place_name);
		startActivity(intent);
	}
	
}
