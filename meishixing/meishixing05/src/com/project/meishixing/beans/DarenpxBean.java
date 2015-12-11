package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class DarenpxBean {

	public String user_image;
	public String name;
	public String level_title;
	public int reputation;
	public int user_id;
	public int tobeloved;

	
	public static DarenpxBean getFromJson(JSONObject object){
		DarenpxBean bean=new DarenpxBean();
		try {
			bean.user_image=object.getString("user_image");
			bean.name=object.getString("name");
			bean.level_title=object.getString("level_title");
			bean.reputation=object.getInt("reputation");
			bean.user_id=object.getInt("user_id");
			bean.tobeloved=object.getInt("tobeloved");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;}
}
