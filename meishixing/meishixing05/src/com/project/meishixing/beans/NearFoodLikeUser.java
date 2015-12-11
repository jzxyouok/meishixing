package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NearFoodLikeUser {
	public int 	user_id;
	public String user_image;
	
	
	public NearFoodLikeUser(int user_id, String user_image) {
		super();
		this.user_id = user_id;
		this.user_image = user_image;
	}


	public static NearFoodLikeUser initWithJsonObject(JSONObject json){
		if (json==null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		try {
			NearFoodLikeUser foodLikeUser=new NearFoodLikeUser(
				json.getInt("user_id"), 
				json.getString("user_image"));
			return foodLikeUser;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}


	@Override
	public String toString() {
		return "NearFoodLikeUser [user_id=" + user_id + ", user_image="
				+ user_image + "]";
	}
	
}
