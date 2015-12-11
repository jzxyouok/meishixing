package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class HotRestListItemBean {

	public String category;
	public double star;
	public String phone;
	public int price;
	public int place_id;
	public String address;
	public String place_name;
	public double lat;
	public double lng;
	public int category_id;
	
	//解析json数据
	public HotRestListItemBean getFromJson(JSONObject object){
		HotRestListItemBean bean=new HotRestListItemBean();
		try {
			bean.category=object.getString("category");
			bean.star=object.getDouble("star");
			bean.address=object.getString("address");
			bean.place_name=object.getString("place_name");
			bean.price=object.getInt("price");
			bean.place_id=object.getInt("place_id");
			bean.category_id=object.getInt("category_id");
			bean.lat=object.getDouble("lat");
			bean.lng=object.getDouble("lng");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
