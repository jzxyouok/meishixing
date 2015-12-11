package com.project.meishixing.beans;

public class HomeGridViewBean {
/*	comment_count,food_id, comment, 
	user_image, 
	image_height, 
	image_width, city_name, place_name, 
	food_name, picture_url, user_name, 
	food_price, want_it_total, pv_count*/
	public int tweet_id;
	public int comment_count;
	public int food_id;
	public String comment;
	public String user_image;
	public int image_height;
	public int image_width;
	public String city_name;
	public String place_name;
	public String food_name;
	public String picture_url;
	public String user_name;
	public int food_price;
	public int want_it_total;
	public int pv_count;
	public HomeGridViewBean(int tweet_id,int comment_count, int food_id, String comment,
			String user_image, int image_height, int image_width,
			String city_name, String place_name, String food_name,
			String picture_url, String user_name, int food_price,
			int want_it_total, int pv_count) {
		super();
		this.tweet_id=tweet_id;
		this.comment_count = comment_count;
		this.food_id = food_id;
		this.comment = comment;
		this.user_image = user_image;
		this.image_height = image_height;
		this.image_width = image_width;
		this.city_name = city_name;
		this.place_name = place_name;
		this.food_name = food_name;
		this.picture_url = picture_url;
		this.user_name = user_name;
		this.food_price = food_price;
		this.want_it_total = want_it_total;
		this.pv_count = pv_count;
	}

	
	
}
