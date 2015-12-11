package com.project.meishixing.beans;

public class HotFoodMoreBean {
	public String comment;
	public String user_image;
	public String food_name;
	public int want_it_total;
	public int pv_count;
	public int comment_count;
	public String user_name;
	public String city_name;
	public String place_name;
	public int nom_it_total;
	public int tweet_id;
	public int food_price;
	public String picture_url;
	public HotFoodMoreBean(String picture_url,int food_price,int tweet_id,String comment, String user_image, String food_name,
			int want_it_total, int pv_count, int comment_count,
			String user_name, String city_name, String place_name,
			int nom_it_total) {
		super();
		this.picture_url=picture_url;
		this.food_price=food_price;
		this.tweet_id=tweet_id;
		this.comment = comment;
		this.user_image = user_image;
		this.food_name = food_name;
		this.want_it_total = want_it_total;
		this.pv_count = pv_count;
		this.comment_count = comment_count;
		this.user_name = user_name;
		this.city_name = city_name;
		this.place_name = place_name;
		this.nom_it_total = nom_it_total;
	}
	
}
