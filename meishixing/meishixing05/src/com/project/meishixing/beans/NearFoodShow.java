package com.project.meishixing.beans;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NearFoodShow {

	public String user_image;// 使用者图片地址
	public String user_name;//mingzi
	public String picture_url;// 美食图片地址
	public String address;// 地址天河区体育东路140-148号南方证券大厦4楼,
	public String food_name;// 美食名称酸辣粉
	public String place_name;// 餐厅名字川国演义,
	public int place_id;// 地点id 87911,
	public int level_value;// 等级42,
	public int want_it_total;// 收藏数 1,
	public int user_id;// 使用者id 122586,
	public int food_id;
	public String phone;// dian hua 010-58858158
	public ArrayList<NearFoodLikeUser> likeUsers;
	public ArrayList<NearFoodComments> foodComments;
	public ArrayList<NearFoodMore > foodMores;
	public NearFoodShow(String user_image, String picture_url, String address,
			String food_name, String place_name, int place_id, int level_value,
			int want_it_total, int user_id,
			ArrayList<NearFoodLikeUser> likeUsers,
			ArrayList<NearFoodComments> foodComments,
			String user_name,String phone,
			ArrayList<NearFoodMore > foodMores,
			int food_id) {
		super();
		this.user_image = user_image;
		this.picture_url = picture_url;
		this.address = address;
		this.food_name = food_name;
		this.place_name = place_name;
		this.place_id = place_id;
		this.level_value = level_value;
		this.want_it_total = want_it_total;
		this.user_id = user_id;
		this.likeUsers = likeUsers;
		this.foodComments = foodComments;
		this.user_name=user_name;
		this.phone=phone;
		this.foodMores=foodMores;
		this.food_id=food_id;
	}
	
	public static NearFoodShow initWithJsonObject(JSONObject json){
		if (json==null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		try {
			String user_image =json.getString("user_image");
			String picture_url=json.getString("picture_url");
			String address= json.getString("address");
			String food_name=json.getString("food_name");
			String place_name= json.getString("place_name");
			int place_id =json.getInt("place_id");
			int level_value=json.getInt("level_value");
			int want_it_total=json.getInt("want_it_total");
			int user_id= json.getInt("user_id");
			
			ArrayList<NearFoodLikeUser> likeUsers=new ArrayList<NearFoodLikeUser>();
			JSONArray jsonArray1=json.getJSONArray("like_user_list");
			for (int i = 0; i < jsonArray1.length(); i++) {
				JSONObject jsonObject1 =jsonArray1.getJSONObject(i);
				NearFoodLikeUser foodLikeUser =NearFoodLikeUser.initWithJsonObject(jsonObject1);
				likeUsers.add(foodLikeUser);
			}
		//	System.out.println("ArrayList<NearFoodLikeUser> likeUsers ="+likeUsers.toString());
			ArrayList<NearFoodComments> foodComments=new ArrayList<NearFoodComments>();
			JSONArray jsonArray2 =json.getJSONArray("comments");
			for (int i = 0; i < jsonArray2.length(); i++) {
				JSONObject jsonObject2=jsonArray2.getJSONObject(i);
				NearFoodComments comments =NearFoodComments.initWithJsonObject(jsonObject2);
				foodComments.add(comments);
			}
		//	System.out.println("ArrayList<NearFoodComments> foodComments ="+foodComments);
			String user_name=json.getString("user_name");
			String phone=json.getString("phone");
			
			ArrayList<NearFoodMore > foodMores=new ArrayList<NearFoodMore>();
			JSONArray jsonArray3 =json.getJSONArray("more_sightings");
			for (int i = 0; i < jsonArray3.length(); i++) {
				JSONObject jsonObject3=jsonArray3.getJSONObject(i);
				NearFoodMore foodMore =NearFoodMore.initWithJsonObject(jsonObject3);
				foodMores.add(foodMore);
			}
			int food_id =json.getInt("food_id");
			NearFoodShow foodShow =new NearFoodShow(
					user_image, 
					picture_url, 
					address, 
					food_name, 
					place_name, 
					place_id, 
					level_value, 
					want_it_total, 
					user_id, 
					likeUsers, 
					foodComments,
					user_name,
					phone,
					foodMores,
					food_id
					);
			
			return foodShow;
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
	
		return null;	
	}

	@Override
	public String toString() {
		return "NearFoodShow [user_image=" + user_image + ", picture_url="
				+ picture_url + ", address=" + address + ", food_name="
				+ food_name + ", place_name=" + place_name + ", place_id="
				+ place_id + ", level_value=" + level_value
				+ ", want_it_total=" + want_it_total + ", user_id=" + user_id
				+ ", likeUsers=" + likeUsers + ", foodComments=" + foodComments
				+ "]";
	}
	
}
