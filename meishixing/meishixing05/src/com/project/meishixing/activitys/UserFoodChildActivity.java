package com.project.meishixing.activitys;

import java.util.ArrayList;

import org.com.cctest.view.XListView;
import org.com.cctest.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.beans.UserFoodChild;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.views.CircleImageView;

public class UserFoodChildActivity extends 	BaseActivity implements IXListViewListener, OnClickListener   {
	private int page =1;
	private XListView mListView;

	private String path;
	private UserFoodChildAdapter mAdapter;
	private ArrayList<UserFoodChild> mChilds;
	private String food_name;
	
	//头部
	private ImageView header_left_imgView;
	private TextView center_textView;
	private ImageView header_right_imgView;
	private ProgressBar header_right_loadingView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_food_all);
		int place_id=getIntent().getIntExtra("place_id", -1);
		int food_id =getIntent().getIntExtra("food_id", -1);
		food_name =getIntent().getStringExtra("food_name");
		
		path =ApiHelp.USER_FOOD_CHILD(food_id,place_id);
		
		mListView=(XListView) findViewById(R.id.activity_user_food_all_pull);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);
		
		
		//头部初始
		header_left_imgView=(ImageView) findViewById(R.id.activity_near_food_show_header_left_imgview);
		center_textView=(TextView) findViewById(R.id.activity_near_food_show_header_center_text);
		header_right_imgView=(ImageView) findViewById(R.id.activity_near_food_show_header_right_imgview);
		header_right_loadingView=(ProgressBar) findViewById(R.id.activity_near_food_show_header_right_loading);
		
		header_left_imgView.setVisibility(View.VISIBLE);
		header_left_imgView.setImageResource(R.drawable.header_menu_back);
		
		center_textView.setVisibility(View.VISIBLE);
		header_right_loadingView.setVisibility(View.VISIBLE);
		
		header_left_imgView.setOnClickListener(this);
		header_right_imgView.setOnClickListener(this);

		
		
		init();
	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
	
	
	
	
	private void init() {
		requestDataFromNetwork();
		
	}
	private void requestDataFromNetwork() {
		Listener<String> listener = new Listener<String>() {
			public void onResponse(String reString) {
		
				ArrayList<UserFoodChild> uChilds = new ArrayList<UserFoodChild>();
				try {
					JSONObject jsonObject =new JSONObject(reString);
					JSONArray array =jsonObject.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object =array.getJSONObject(i);
						UserFoodChild child =UserFoodChild.initWithJsonObject(object);
						uChilds.add(child);
					}
					
					upLoadHeader();
					
					if (page == 1) {
						mChilds = uChilds;
						if (mAdapter == null) {
							mAdapter = new UserFoodChildAdapter( mChilds,getApplicationContext(),UserFoodChildActivity.this);
							mListView.setAdapter(mAdapter);
						}else{
							mAdapter.notifyDataSetChanged();
						}
					}else{
						if (page > 1) {
							mChilds.addAll(mChilds.size(), uChilds);
							mAdapter.notifyDataSetChanged();
						}
						
					}
				
				} catch (JSONException e) {
		
					e.printStackTrace();
				}
				
				
	
			
				
			}

	

		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			public void onErrorResponse(VolleyError paramAnonymousVolleyError) {
			}
		};
		StringRequest request =new StringRequest(
				path+page, 
				listener, 
				errorListener);
		HttpRequest.getInstance(getApplicationContext()).sendRequest(request);
		
	}

	private void upLoadHeader() {
		header_right_loadingView.setVisibility(View.GONE);
		header_right_imgView.setVisibility(View.VISIBLE);
		header_right_imgView.setImageResource(R.drawable.header_menu_home);
		
		center_textView.setText("喜欢"+food_name+"的孩子们");
		
	}
	@Override
	public void onLoadMore() {
		page++;
		requestDataFromNetwork();
	}
	@Override
	public void onRefresh() {
		page=1;
		requestDataFromNetwork();
		
	}





//	@Override
//	public void onLastItemVisible() {
//		page++;
//		requestDataFromNetwork();
//	}


}
class UserFoodChildAdapter extends BaseAdapter{
	public ArrayList<UserFoodChild> mChilds;
	private HttpRequest mRequest;
	private Context mcontext;
	private UserFoodChildActivity userFoodChildActivity;
	public UserFoodChildAdapter(ArrayList<UserFoodChild> nChilds ,Context c ,UserFoodChildActivity act){
		mChilds=nChilds;
		mcontext=c;
		mRequest=HttpRequest.getInstance(c);
		userFoodChildActivity=act;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChilds.size();
	}

	@Override
	public UserFoodChild getItem(int position) {
		// TODO Auto-generated method stub
		return mChilds.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final UserFoodChild child =mChilds.get(position);
		if (convertView==null) {
			LayoutInflater inflater=LayoutInflater.from(parent.getContext());
			convertView=inflater.inflate(R.layout.activity_user_food_all_item, parent, false);
			HoldView holdView =new HoldView();
			holdView.imView=(CircleImageView) convertView.findViewById(R.id.activity_user_food_all_item_iv);
			holdView.textView1=(TextView) convertView.findViewById(R.id.activity_user_food_all_item_tv1);
			holdView.chTextView=(CheckedTextView) convertView.findViewById(R.id.activity_user_food_all_item_check);
			convertView.setTag(holdView);
		}
		HoldView holdView=(HoldView) convertView.getTag();
		holdView.textView1.setText(child.user_name);
		holdView.imView.setTag(child.user_image);
		ImageLoader.ImageListener lis =ImageLoader.getImageListener(
				holdView.imView, 
				R.drawable.ic_launcher, 
				R.drawable.ic_launcher);
		
		mRequest.loadImage(child.user_image, 
				lis, 
				0, 
				0);
		
		holdView.chTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(userFoodChildActivity,LoginActivity.class);
				userFoodChildActivity.startActivity(intent);
	
/**
 * 
 * 
 * 登录跳转
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 				
 */
			}
		});
		
		holdView.imView.setOnClickListener(new CircleImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int uid =child.user_id;
				System.out.println("uid ="+ uid);
				Intent intent =new Intent(userFoodChildActivity,UserFoodActivity.class);
				intent.putExtra("uid", uid);
				userFoodChildActivity.startActivity(intent);
				
			}
		});
		
		return convertView;
	}
	class HoldView {
		CircleImageView imView;
		TextView textView1;
		CheckedTextView chTextView;
		
	}
	
} 
