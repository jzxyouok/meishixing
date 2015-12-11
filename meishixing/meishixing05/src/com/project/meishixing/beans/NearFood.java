package com.project.meishixing.beans;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NearFood {
	public String user_image;// 使用者照片
	public String picture_url;// 食物照片
	public String place_name;// 地点名称 车田豆腐坊
	public String user_name;// 使用者姓名 蕊涵"
	public int pv_count;// 浏览量 62,
	public String distance;// 距离 1275.4393230810024,
	public int image_height;// 图片高度 482,
	public String address;// 地址 白云区沙太路163号(近天虹宾馆)
	public int level_value;// 使用者等级14
	public String nom_it_total;// 来自网页或者iphone1,
	public int city_id;// 城市 位置 5,
	public int place_id;// 地点id65903,
	public String level_title;// 使用者等级食客
	public double lng;//经度113.328842
	public double lat;//纬度
	public int want_it_total;// 点攒书100,
	public String food_name;//美食名字 水蜜桃重芝士",
	public int comment_count;//评论数
	
	public NearFood(String user_image, String picture_url, String place_name,
			String user_name, int pv_count, String distance, int image_height,
			String address, int level_value, String nom_it_total, int city_id,
			int place_id, String level_title, double lng, double lat,
			int want_it_total,String food_name, int comment_count
			) {
		super();
		this.user_image = user_image;
		this.picture_url = picture_url;
		this.place_name = place_name;
		this.user_name = user_name;
		this.pv_count = pv_count;
		this.distance = distance;
		this.image_height = image_height;
		this.address = address;
		this.level_value = level_value;
		this.nom_it_total = nom_it_total;
		this.city_id = city_id;
		this.place_id = place_id;
		this.level_title = level_title;
		this.lng = lng;
		this.lat = lat;
		this.want_it_total=want_it_total;
		this.food_name=food_name;
		this.comment_count=comment_count;
	}


	/**
	 * from ditance
	 */
	public static NearFood initWithJsonObject(JSONObject json) {
		if (json == null) {
			Log.d("TAG", "json 为空");
			return null;
		}
		try {
			String user_image =json.getString("user_image");
			String picture_url=json.getString("picture_url");
			String place_name=json.getString("place_name");
			String user_name=json.getString("user_name");
			int pv_count=json.getInt("pv_count");
			double d=json.getDouble("distance");
			
			String distance =Math.round((d/1000))+"公里";
	
			String address=json.getString("address");
			int level_value=json.getInt("level_value");
			int nom=json.getInt("nom_it_total");
			String nom_it_total =null;
			if (nom==1) {
				nom_it_total="来自网页版";
			}else{
				nom_it_total="来自iPhone版";
			}
			
			int city_id=json.getInt("city_id");
			int place_id=json.getInt("place_id");
			String level_title=json.getString("level_title");
			double lng=json.getDouble("lng");
			double lat=json.getDouble("lat");
			int want_it_total =json.getInt("want_it_total");
			String food_name =json.getString("food_name");
			int comment_count=json.getInt("comment_count");
			NearFood nearFood =new NearFood(
					user_image, 
					picture_url, 
					place_name, 
					user_name, 
					pv_count, 
					distance, 
					nom, 
					address, 
					level_value, 
					nom_it_total, 
					city_id, 
					place_id, 
					level_title, 
					lng, 
					lat,
					want_it_total,
					food_name,
					comment_count);
			return nearFood;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	@Override
	public String toString() {
		return "NearFood [user_image=" + user_image + ", picture_url="
				+ picture_url + ", place_name=" + place_name + ", user_name="
				+ user_name + ", pv_count=" + pv_count + ", distance="
				+ distance + ", image_height=" + image_height + ", address="
				+ address + ", level_value=" + level_value + ", nom_it_total="
				+ nom_it_total + ", city_id=" + city_id + ", place_id="
				+ place_id + ", level_title=" + level_title + ", lng=" + lng
				+ ", lat=" + lat + ", want_it_total=" + want_it_total
				+ ", food_name=" + food_name + ", comment_count="
				+ comment_count + "]";
	}
	
}
