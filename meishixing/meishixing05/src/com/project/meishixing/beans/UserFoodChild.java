package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class UserFoodChild {

  
   
	public String user_image;// 图片网致 634w.jpg,
	public String user_name;// 严园~qq
	public int user_id;// 177892,
	
	
	

	public UserFoodChild(String user_image, String user_name, int user_id) {
		super();
		this.user_image = user_image;
		this.user_name = user_name;
		this.user_id = user_id;
	}
	public static UserFoodChild initWithJsonObject(JSONObject json) {
		if (json == null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		
		try {
			UserFoodChild child =new UserFoodChild(
					json.getString("user_image"), 
					json.getString("user_name"),	
					json.getInt("user_id")	);
				return child;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	@Override
	public String toString() {
		return "UserFoodChild [user_image=" + user_image + ", user_name="
				+ user_name + ", user_id=" + user_id + "]";
	}
	

}
