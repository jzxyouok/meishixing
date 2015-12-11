package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NearFoodMore {

	public String user_image;//左边人物头像
	public String picture_url;//右边图像
	public String level_title;//食客, 
	public String user_name;//李慧超儿,
	public String comment;//乳鸽，果然名不虚传",
	public int user_id;//使用者id6198,    
    public int tweet_id;//美食id 676306,
    public int picture_id;//地点id676717,
    
	public NearFoodMore(String user_image, String picture_url,
			String level_title, String user_name, String comment, int user_id,
			int tweet_id, int picture_id) {
		super();
		this.user_image = user_image;
		this.picture_url = picture_url;
		this.level_title = level_title;
		this.user_name = user_name;
		this.comment = comment;
		this.user_id = user_id;
		this.tweet_id = tweet_id;
		this.picture_id = picture_id;
	}
    
	public static NearFoodMore initWithJsonObject(JSONObject json){
		if (json==null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		try {
			NearFoodMore foodMore=new NearFoodMore(
				json.getString("user_image"), 
				json.getString("picture_url"), 
				json.getString("level_title"), 
				json.getString("user_name"), 
				json.getString("comment"), 
				json.getInt("user_id"), 
				json.getInt("tweet_id"), 
				json.getInt("picture_id"));
				
			return foodMore;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
    
	
}
