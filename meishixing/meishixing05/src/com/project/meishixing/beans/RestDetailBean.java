package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RestDetailBean {
	public String open_time;
	public String city_name;
	public String url;
	public String bus;
	public String area_name;
	public String tags;
	public int view_total;
	
	public String category;
	public double star;
	public String phone;
	public int price;
	public String address;
	public String place_name;
	public double place_geo_lat;
	public double place_geo_lng;
	
	// 解析json数据
	public RestDetailBean getFromJson(JSONObject object) {
		try {
			this.category = object.getString("category");
			this.address = object.getString("address");
			this.place_name = object.getString("place_name");
			this.place_geo_lat = object.getDouble("place_geo_lat");
			this.place_geo_lng = object.getDouble("place_geo_lng");
			this.star = object.getDouble("star");
			this.price = object.getInt("price");
			this.open_time = object.getString("open_time");
			this.city_name = object.getString("city_name");
			this.url = object.getString("url");
			this.phone = object.getString("phone");
			this.bus = object.getString("bus");
			this.area_name = object.getString("area_name");
			this.tags = object.getString("tags");
			this.view_total = object.getInt("view_total");
			return this;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
}
