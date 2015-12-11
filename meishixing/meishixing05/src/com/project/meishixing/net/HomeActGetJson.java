package com.project.meishixing.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.abs.GetHomeActJsonListener;
import com.project.meishixing.beans.HomeGridViewBean;

import android.content.Context;
import android.util.Log;

public class HomeActGetJson {
	public static final String ERROR="数据加载失败";
	public static void getHomeActJsonData(Context context,String url,int index,final  GetHomeActJsonListener jsonListener){
		Listener<String> listener=new Listener<String>() {
			@Override
			public void onResponse(String response) {
				ArrayList<HomeGridViewBean> beans=new ArrayList<HomeGridViewBean>();
				try {
					JSONObject jsonObject=new JSONObject(response);
					JSONArray jsonArray=jsonObject.getJSONArray("result");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object=jsonArray.getJSONObject(i);
						String comment=object.getString("comment");
						String user_image=object.getString("user_image");
						String city_name=null;
						try{
							city_name=object.getString("city_name");}
						catch(JSONException e){
							Log.d("TAG", "city_name没有值");
						}
						int tweet_id=object.getInt("tweet_id");
						String place_name=object.getString("place_name");
						String food_name=object.getString("food_name");
						String picture_url=object.getString("picture_url");
						String user_name=object.getString("user_name");
						int comment_count=object.getInt("comment_count");
						int food_id=object.getInt("food_id");
						int image_width=object.getInt("image_width");
						int image_height=object.getInt("image_height");
						int food_price=object.getInt("food_price");
						int pv_count=object.getInt("pv_count");
						int want_it_total=object.getInt("want_it_total");
						HomeGridViewBean bean=new HomeGridViewBean(tweet_id,comment_count,food_id, comment, 
								user_image, 
								image_height, 
								image_width, city_name, place_name, 
								food_name, picture_url, user_name, 
								food_price, want_it_total, pv_count);
						beans.add(bean);
						//TODO
					}
					jsonListener.getHomeData(beans);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		ErrorListener errorListener=new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("TAG", "JSON数据加载失败");
				jsonListener.getErrorData(ERROR);
				
			}
		};
		Request<String> request=new StringRequest(url, listener, errorListener);
		HttpRequest.getInstance(context).sendRequest(request);
	}
}
