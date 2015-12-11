package com.project.meishixing.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class UserFoodShow {

	public  String user_image;//图片地址
	public  String level_title;//等级食客,
	
	public  String name;//姓名圈Oo圈Oo,
	public  String tobeloved;//被xx喜欢过112,
	public  int picture_count;//美食足迹63,
	public  int  user_fans_count;//粉丝1,
	public  int user_foodzine_count;//关注0,
	public 	ArrayList<UserFoodShowList> foodShowLists;
	
	
	
	public UserFoodShow(
			String user_image, 
			String level_title, 
			String name,
			String tobeloved, 
			int picture_count, 
			int user_fans_count,
			int user_foodzine_count, 
			ArrayList<UserFoodShowList> foodShowLists) {
		super();
		this.user_image = user_image;
		this.level_title = level_title;
		this.name = name;
		this.tobeloved = tobeloved;
		this.picture_count = picture_count;
		this.user_fans_count = user_fans_count;
		this.user_foodzine_count = user_foodzine_count;
		this.foodShowLists = foodShowLists;
	}



	public static UserFoodShow initWithJsonObject(JSONObject json) {
		if (json == null) {
			Log.d("TAG", "json 为空");
			return null;
		}
	
		try {
			String user_image=json.getString("user_image");
			String level_title=json.getString("level_title");
			String name=json.getString("name");
			String tobeloved="分享的美食被"+json.getInt("tobeloved")+"人喜欢过";
			int picture_count=json.getInt("picture_count");
			int user_fans_count=json.getInt("user_fans_count");
			int user_foodzine_count=json.getInt("user_foodzine_count");
			ArrayList<UserFoodShowList> foodShowLists=new ArrayList<UserFoodShowList>();

			if(json.isNull("last_picture_list")){
				System.out.println("---------json.isNulllast_picture_list------------");
				foodShowLists.add(null);
			}else{
				JSONArray	jsonArray=json.getJSONArray("last_picture_list");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					UserFoodShowList showList =UserFoodShowList.initWithJsonObject(jsonObject);
					foodShowLists.add(showList);
				}
			}
			
			
			
			UserFoodShow userFoodShow =new UserFoodShow(
					user_image, 
					level_title,
					name, 
					tobeloved, 
					picture_count, 
					user_fans_count, 
					user_foodzine_count, 
					foodShowLists);
			
			return userFoodShow;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;

	}



	@Override
	public String toString() {
		return "UserFoodShow [user_image=" + user_image + ", level_title="
				+ level_title + ", name=" + name + ", tobeloved=" + tobeloved
				+ ", picture_count=" + picture_count + ", user_fans_count="
				+ user_fans_count + ", user_foodzine_count="
				+ user_foodzine_count + ", foodShowLists=" + foodShowLists
				+ "]";
	}
	
	
}
