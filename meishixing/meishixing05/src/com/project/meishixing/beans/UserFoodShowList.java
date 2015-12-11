package com.project.meishixing.beans;

import org.json.JSONException;
import org.json.JSONObject;

import com.project.meishixing.utils.UserFoodDataUtils;

import android.util.Log;

public class UserFoodShowList {

	public String picture_url;// 食物地址
	public String level_title;// 食客,
	public String want_it_total;// 有多少人喜欢,
	public String place_name;// 兰桂坊(沙面店),
	public String publish_time;// 2015-10-13 10:24:21,
	public int tweet_id;// 191562,
	public String food_name;

	public UserFoodShowList(String picture_url, String level_title,
			String want_it_total, String place_name, String publish_time,
			int tweet_id,
			 String food_name) {
		super();
		this.picture_url = picture_url;
		this.level_title = level_title;
		this.want_it_total = want_it_total;
		this.place_name = place_name;
		this.publish_time = publish_time;
		this.tweet_id = tweet_id;
		this.food_name = food_name;
		
	}

	public static UserFoodShowList initWithJsonObject(JSONObject json) {
		if (json == null) {
			Log.d("TAG", "json 为空");
			return null;
		}

		try {
			String picture_url = json.getString("picture_url");
			String level_title = json.getString("level_title");
			String want_it_total = json.getString("want_it_total");
			String place_name = json.getString("place_name");		
			String publish_time = UserFoodDataUtils.data(json.getString("publish_time"));		
			int tweet_id = json.getInt("tweet_id");
			String food_name=json.getString("food_name");
			UserFoodShowList foodShowList =new UserFoodShowList(picture_url, level_title, want_it_total, place_name, publish_time, tweet_id,food_name);
			return foodShowList;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return null;
	}

	@Override
	public String toString() {
		return "UserFoodShowList [picture_url=" + picture_url
				+ ", level_title=" + level_title + ", want_it_total="
				+ want_it_total + ", place_name=" + place_name
				+ ", publish_time=" + publish_time + ", tweet_id=" + tweet_id
				+ "]";
	}

}
