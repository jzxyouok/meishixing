package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodMagaBean {

	public String user_image;
	public String title;
	public String user_name;
	public String picture_url;
	public String introduction;
	public String serial_number;
	public int city_id;
	public int user_id;
	public int foodzine_id;

	
	public static FoodMagaBean getFromJson(JSONObject object){
		FoodMagaBean bean=new FoodMagaBean();
		try {
			bean.user_image=object.getString("user_image");
			bean.title=object.getString("title");
			bean.city_id=object.getInt("city_id");
			bean.foodzine_id=object.getInt("foodzine_id");
			bean.user_name=object.getString("user_name");
			bean.picture_url=object.getString("picture_url");
			bean.introduction=object.getString("introduction");
			bean.serial_number=object.getString("serial_number");
			bean.user_id=object.getInt("user_id");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;}
}
