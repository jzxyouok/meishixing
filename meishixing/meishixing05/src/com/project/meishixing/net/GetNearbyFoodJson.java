package com.project.meishixing.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.abs.GetHotFoodActJsonListener;
import com.project.meishixing.abs.GetNraebyFoodJsonListener;
import com.project.meishixing.beans.HotFoodMoreBean;
import com.project.meishixing.beans.NearbyFoodBean;

import android.content.Context;
import android.util.Log;

public class GetNearbyFoodJson {
	
	public static void getJsonData(String path,Context context,final GetNraebyFoodJsonListener getlisten){
		Listener<String> listener=new Listener<String>() {

			@Override
			public void onResponse(String response) {
				List<NearbyFoodBean> beans=new ArrayList<NearbyFoodBean>();
				try {
					JSONObject jsonObject=new JSONObject(response);
					JSONArray jsonArray=jsonObject.getJSONArray("result");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object=jsonArray.getJSONObject(i);
						String comment=object.getString("comment");
						String user_image=object.getString("user_image");
						int nom_it_total=object.getInt("nom_it_total");
						int want_it_total=object.getInt("want_it_total");
						String place_name=object.getString("place_name");
						String food_name=object.getString("food_name");
						String picture_url=object.getString("picture_url");
						int tweet_id=object.getInt("tweet_id");
						int comment_count=object.getInt("comment_count");
						int pv_count=object.getInt("pv_count");
						String user_name=object.getString("user_name");
						int food_price=object.getInt("food_price");
						double distance=object.getDouble("distance");
						NearbyFoodBean bean=new NearbyFoodBean(distance,comment, user_image,
								food_name, want_it_total,
								pv_count, comment_count, 
								user_name, place_name, 
								nom_it_total, tweet_id, 
								food_price, picture_url);
						beans.add(bean);
					}
					getlisten.getJsonData(beans);
					Log.d("TAG", "数据下载成功");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		ErrorListener errorListener=new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				getlisten.getErrorData("数据下载失败");
				Log.d("TAG", "数据下载失败");
			}
		};
		
		Request<String> request=new StringRequest(path, listener, errorListener);
		HttpRequest.getInstance(context).sendRequest(request);
	}
	
	public static void getNearbyDataBySelf(final int page,final GetNraebyFoodJsonListener getlisten) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String uri =ApiHelp.getNearbyFoodPath(page);
				DefaultHttpClient client =new DefaultHttpClient();
				HttpGet get =new HttpGet(uri);
				try {
					HttpResponse response =client.execute(get);
					int code =response.getStatusLine().getStatusCode();
					if (code == 200) {
						HttpEntity entity =response.getEntity();
						String str =EntityUtils.toString(entity);
						getJsonDataSelf(str,getlisten);
					}
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				

				
			}

		
		}).start();
		
		
	}
	private static  void getJsonDataSelf(String response,
			GetNraebyFoodJsonListener getlisten) {
		List<NearbyFoodBean> beans = new ArrayList<NearbyFoodBean>();
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				String comment = object.getString("comment");
				String user_image = object.getString("user_image");
				int nom_it_total = object.getInt("nom_it_total");
				int want_it_total = object.getInt("want_it_total");
				String place_name = object.getString("place_name");
				String food_name = object.getString("food_name");
				String picture_url = object.getString("picture_url");
				int tweet_id = object.getInt("tweet_id");
				int comment_count = object.getInt("comment_count");
				int pv_count = object.getInt("pv_count");
				String user_name = object.getString("user_name");
				int food_price = object.getInt("food_price");
				double distance = object.getDouble("distance");
				NearbyFoodBean bean = new NearbyFoodBean(distance, comment,
						user_image, food_name, want_it_total, pv_count,
						comment_count, user_name, place_name, nom_it_total,
						tweet_id, food_price, picture_url);
				beans.add(bean);
			}
			getlisten.getJsonData(beans);
			Log.d("TAG", "数据下载成功");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
