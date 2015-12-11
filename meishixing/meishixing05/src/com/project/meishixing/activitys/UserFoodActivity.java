package com.project.meishixing.activitys;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.activitys.MoreSightAdapter.HoldView;
import com.project.meishixing.beans.UserFoodShow;
import com.project.meishixing.beans.UserFoodShowList;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.ToasUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UserFoodActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private 	String url =null;
			
	
	private UserFoodShow foodShow;
	//个人详情控件初始化
	private ImageView user_imageView;
	private TextView nameView;
	private TextView level_titleView;
	private TextView tobelovedView;
	private CheckedTextView checkedTextView;
	private TextView picture_countTextvView;
	private TextView user_foodzine_countView;
	private TextView user_fans_countView;
	private ListView last_picture_listView;
	private TextView nofind_tvView;
	
	//头部
		private ImageView header_left_imgView;
		private TextView center_textView;
		private ImageView header_right_imgView;
		private ProgressBar header_right_loadingView;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_food_piclist);
		int uid =getIntent().getIntExtra("uid", -1);
		System.out.println("UserFoodActivity  " +uid);
		url =ApiHelp.USE_ID(uid);
		System.out.println("url ="+url);
		init();
		requestDataFromNetwork();
	}

	private void init() {
		
		//头部初始
		header_left_imgView=(ImageView) findViewById(R.id.activity_near_food_show_header_left_imgview);
		center_textView=(TextView) findViewById(R.id.activity_near_food_show_header_center_text);
		header_right_imgView=(ImageView) findViewById(R.id.activity_near_food_show_header_right_imgview);
		header_right_loadingView=(ProgressBar) findViewById(R.id.activity_near_food_show_header_right_loading);
		nofind_tvView =(TextView) findViewById(R.id.activity_user_food_piclist_nofind_tv);
		header_left_imgView.setVisibility(View.VISIBLE);
		header_left_imgView.setImageResource(R.drawable.header_menu_back);
		
		center_textView.setVisibility(View.VISIBLE);
		header_right_loadingView.setVisibility(View.VISIBLE);
		
		header_left_imgView.setOnClickListener(this);
		header_right_imgView.setOnClickListener(this);

		
		//他的足迹
		last_picture_listView=(ListView) findViewById(R.id.activity_user_foodprofile_info_foodprint_list);
		//粉丝
		user_fans_countView=(TextView) findViewById(R.id.activity_user_foodprofile_info_fans_count);
		//关注
		user_foodzine_countView=(TextView) findViewById(R.id.activity_user_foodprofile_info_follow_count);
		//美食足迹
		picture_countTextvView=(TextView) findViewById(R.id.activity_user_foodprofile_info_foodprint_count);
		//关注按钮
		checkedTextView=(CheckedTextView) findViewById(R.id.activity_user_foodprofile_info_follow);
		//被多少人喜欢
		tobelovedView=(TextView) findViewById(R.id.activity_user_foodprofile_info_favdesc);
		//使用者等级
		level_titleView=(TextView) findViewById(R.id.activity_user_foodprofile_info_level_title);
		//使用者xingm
		nameView=(TextView) findViewById(R.id.activity_user_foodprofile_info_username);
		//使用者图片
		user_imageView=(ImageView) findViewById(R.id.activity_user_foodprofile_info_userimg);	
	}
	private void requestDataFromNetwork() {
		Listener<String>listener =new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					
					JSONObject jsonObject=new JSONObject(response);
					JSONObject object =jsonObject.getJSONObject("result");
					
					foodShow=UserFoodShow.initWithJsonObject(object);
					
					upLoad();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		};
		ErrorListener errorListener =new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			
				requestDataFromNetwork();
				
			}
		};
		StringRequest request =new StringRequest(
				Method.GET, 
				url, 
				listener, 
				errorListener);
		HttpRequest.getInstance(getApplicationContext()).sendRequest(request);
		
	}

	protected void upLoad() {
		ImageLoader.ImageListener lis =ImageLoader.getImageListener(
				user_imageView, 
				R.drawable.ic_launcher, 
				R.drawable.ic_launcher);
		user_imageView.setTag(foodShow.user_image);
		HttpRequest.getInstance(getApplicationContext()).loadImage(
				foodShow.user_image, 
				lis, 
				0, 
				0);
		
		header_right_loadingView.setVisibility(View.GONE);
		header_right_imgView.setVisibility(View.VISIBLE);
		
		header_right_imgView.setImageResource(R.drawable.header_menu_home);
		center_textView.setText(foodShow.name);
		
		nameView.setText(foodShow.name);
		level_titleView.setText(foodShow.level_title);
		tobelovedView.setText(foodShow.tobeloved);
		picture_countTextvView.setText(foodShow.picture_count+"");
		user_foodzine_countView.setText(foodShow.user_foodzine_count+"");
		user_fans_countView.setText(foodShow.user_fans_count+"");
		checkedTextView.setOnClickListener(this);
		LinearLayout layout=null;
		System.out.println("foodShow.foodShowLists ="+foodShow.foodShowLists);
		if (foodShow.foodShowLists.contains(null)) {
			// layout =(LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_user_food_piclist_nofind, null);
			nofind_tvView.setVisibility(View.VISIBLE); 
			System.out.println("jin ru  null ");
		}else{
			 System.out.println("jin ru  no null ");
			layout =(LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_user_food_piclist_find, null);
			layout.setOnClickListener(this);
			last_picture_listView.addFooterView(layout);
			last_picture_listView.setVisibility(View.VISIBLE);
			last_picture_listView.setAdapter(new UserFoodShowAdapter(foodShow.foodShowLists,getApplication()));
			last_picture_listView.setOnItemClickListener(this);
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int tweet_id =foodShow.foodShowLists.get(position).tweet_id;
		Intent intent =new Intent(getApplicationContext(), NearFoodShowActivity.class);
		intent.putExtra("tweet_id", tweet_id);
		startActivity(intent);
		
	}
	boolean isChecked=false;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_user_foodprofile_info_follow:
			if (!isChecked) {
				checkedTextView.setText("已关注");
				user_fans_countView.setText(foodShow.user_fans_count+1+"");
				isChecked=true;
			}else{
				checkedTextView.setText("关注");
				user_fans_countView.setText(foodShow.user_fans_count+"");
				ToasUtils.showLToast(getApplicationContext(), "您已经取消关注了");
				isChecked=false;
			}	
			break;
		case R.id.activity_user_food_piclist_find_tv:
/**
 * 
 * 写GridView 显示 带日期的 直接写死
 * 
 * 
 * 
 */
			break;
			
		case R.id.activity_near_food_show_header_left_imgview:
			finish();
			break;
		case R.id.activity_near_food_show_header_right_imgview:
			Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
			startActivity(intent);
			break;
	
		default:
			break;
		}
		
	}


}
class UserFoodShowAdapter extends BaseAdapter{
	private ArrayList<UserFoodShowList> lists;
	private HttpRequest mHttpRequest;
	public UserFoodShowAdapter(ArrayList<UserFoodShowList> f, Context c) {
		lists=f;
		mHttpRequest=HttpRequest.getInstance(c);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public UserFoodShowList getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserFoodShowList userFoodShow= lists.get(position);
		if (convertView==null) {
			LayoutInflater inflater =LayoutInflater.from(parent.getContext());
			convertView=inflater.inflate(R.layout.activity_user_food_pivlist_item,parent , false);
			ViewHold hold= new ViewHold();
			hold.picture_urlView=(ImageView) convertView.findViewById(R.id.activity_user_food_pivlist_item_picture_url);
			hold.food_nameView=(TextView) convertView.findViewById(R.id.activity_user_food_pivlist_item_food_name);
			hold.place_nameView=(TextView) convertView.findViewById(R.id.activity_user_food_pivlist_item_place_name);
			hold.publish_timeView=(TextView) convertView.findViewById(R.id.activity_user_food_pivlist_item_publish_time);
			hold.want_it_totalView=(TextView) convertView.findViewById(R.id.activity_user_food_pivlist_item_want_it_total);
			convertView.setTag(hold);
		}
		ViewHold hold= (ViewHold) convertView.getTag();
		hold.food_nameView.setText(userFoodShow.food_name);
		hold.place_nameView.setText(userFoodShow.place_name);
		hold.publish_timeView.setText(userFoodShow.publish_time);
		hold.want_it_totalView.setText("有"+userFoodShow.want_it_total+"人喜欢");
		
		hold.picture_urlView.setTag(userFoodShow.picture_url);
		ImageLoader.ImageListener lis=ImageLoader.getImageListener(
				hold.picture_urlView, 
				R.drawable.ic_launcher, 
				R.drawable.ic_launcher);
		
		mHttpRequest.loadImage(
				userFoodShow.picture_url, 
				lis, 
				0, 
				0);
		
		
		
		
		
		
		return convertView;
	}
	class ViewHold{
		ImageView picture_urlView;
		TextView food_nameView;
		TextView place_nameView;
		TextView publish_timeView;
		TextView want_it_totalView;
	}
	
}