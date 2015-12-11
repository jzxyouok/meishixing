package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class HotFirBean {

	public String type_desc;
	public String type;
	public String topic_cover;
	public String icon;
	public int noresult;

	
	public static HotFirBean getFromJson(JSONObject object){
		HotFirBean bean=new HotFirBean();
		try {
			bean.type_desc=object.getString("type_desc");
			bean.type=object.getString("type");
			bean.noresult=object.getInt("noresult");
			bean.topic_cover=object.getString("topic_cover");
			bean.icon=object.getString("icon");
			return bean;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;}
}
