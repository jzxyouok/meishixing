package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class NearRestListItemBean {

	public String category;
	public String place_image;
	public String star;
	public String phone;
	public int price;
	public int place_id;
	public double distance;
	public int discount;
	public String address;
	public String place_name;
	public double lat;
	public double lng;
	public int category_id;
	
	//解析json数据
	public NearRestListItemBean getFromJson(JSONObject object){
		NearRestListItemBean bean=new NearRestListItemBean();
		try {
			bean.category=object.getString("category");
			bean.place_image=object.getString("place_image");
			bean.star=object.getString("star");
			//bean.phone=object.getString("phone");
			bean.address=object.getString("address");
			bean.place_name=object.getString("place_name");
			bean.price=object.getInt("price");
			bean.place_id=object.getInt("place_id");
			bean.discount=object.getInt("discount");
			bean.category_id=object.getInt("category_id");
			bean.distance=object.getDouble("distance");
			bean.lat=object.getDouble("lat");
			bean.lng=object.getDouble("lng");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
