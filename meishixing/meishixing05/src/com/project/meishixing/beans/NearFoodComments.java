package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * 评论相关
 *
 */
public class NearFoodComments {

	public String comment;// 幾錢？,   评论的话
	public String user_name;// YUKO_蚊~新浪, 名字
	public String level_title;// 食客,  等级
	public String avatar;//  头像 http://headerpicture.meishixing.com/image/picture/2013/7/17/133707q1374052947z.jpg,
	public String publish_time;// 2013-08-08 12:30:46,
	public int user_id;//使用者 id 133707,
	public NearFoodComments(String comment, String user_name,
			String level_title, String avatar, String publish_time,
			int user_id) {
		super();
		this.comment = comment;
		this.user_name = user_name;
		this.level_title = level_title;
		this.avatar = avatar;
		this.publish_time = publish_time;
		this.user_id = user_id;
	}
	
	public static NearFoodComments initWithJsonObject(JSONObject json){
		if (json==null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		try {
			String comment =json.getString("comment");
			String user_name=json.getString("user_name");
			String level_title=json.getString("level_title"); 
			String avatar =json.getString("avatar");
			String publish_time=json.getString("publish_time");
			int user_id=json.getInt("user_id");
			
			NearFoodComments foodComments =new NearFoodComments(comment, user_name, level_title, avatar, publish_time, user_id);
			return foodComments;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	@Override
	public String toString() {
		return "NearFoodComments [comment=" + comment + ", user_name="
				+ user_name + ", level_title=" + level_title + ", avatar="
				+ avatar + ", publish_time=" + publish_time + ", user_id="
				+ user_id + "]";
	}

	
	
	
}
