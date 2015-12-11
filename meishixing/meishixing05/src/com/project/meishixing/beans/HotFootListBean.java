package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class HotFootListBean {

	public String picture_url;
	public int city_id;
	public int place_id;
	public int want_total;
	public int tweet_id;
	public int comment_count;
	public String place_name;
	public double lat;
	public double lng;
	
	public static HotFootListBean getFromJson(JSONObject object){
		HotFootListBean bean=new HotFootListBean();
		try {
			bean.picture_url=object.getString("picture_url");
			bean.place_name=object.getString("place_name");
			bean.city_id=object.getInt("city_id");
			bean.place_id=object.getInt("place_id");
			bean.tweet_id=object.getInt("tweet_id");
			bean.want_total=object.getInt("want_it_total");
			bean.comment_count=object.getInt("comment_count");
			bean.lat=object.getDouble("lat");
			bean.lng=object.getDouble("lng");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;}
}
