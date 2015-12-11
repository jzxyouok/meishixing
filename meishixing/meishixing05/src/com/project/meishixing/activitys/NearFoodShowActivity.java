package com.project.meishixing.activitys;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import com.android.volley.toolbox.StringRequest;
import com.project.meishixing.R;
import com.project.meishixing.beans.NearFoodComments;
import com.project.meishixing.beans.NearFoodLikeUser;
import com.project.meishixing.beans.NearFoodMore;
import com.project.meishixing.beans.NearFoodShow;
import com.project.meishixing.db.DBmessage;
import com.project.meishixing.db.OpenDBhelp;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HttpRequest;
import com.project.meishixing.utils.ToasUtils;
import com.project.meishixing.views.CircleImageView;
import com.project.meishixing.views.MyGridView;
import com.project.meishixing.views.MyListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NearFoodShowActivity extends BaseActivity implements OnClickListener{
	private int tweet_id ;
	private TextView level_valueView;
	private TextView food_nameView;
	private TextView user_nameView;
	private CheckedTextView want_it_totalView;
	private CircleImageView user_imageView;
	private ImageView picture_urlView;
	private TableLayout detail_howLayout;
	private MyGridView likeUsersLayout;
	private MyListView commentsLayout;
	private MyListView moresightsLayout;
	private CheckedTextView mCheckedTextView;
	
	private NearFoodShow nearFoodShow;
	//头部
	private ImageView header_right_imgview;
	private TextView headerTextView;
	private ImageView header_left_imgview;
	private ProgressBar headerProgressBar;
	//底部分享
	TextView bottom_weiboView;
	TextView bottom_wxView;
	TextView bottom_qqView;
	TextView bottom_commentView;
	CheckedTextView bottom_fav;
	private boolean isCollec;
	private OpenDBhelp dBhelp;
	private Drawable drawable;
	private boolean isLooked;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_near_food_show);
		dBhelp = OpenDBhelp.getDBhelp(getApplicationContext());
		tweet_id =getIntent().getIntExtra("tweet_id", -1);
		isCollec=dBhelp.isExistDatas(tweet_id+"", DBmessage.COLLECTED);
		isCollec=dBhelp.isExistDatas(tweet_id+"", DBmessage.LOOKDE);
		init();
	}

	private void init() {
		level_valueView = (TextView) findViewById(R.id.activity_near_food_show_food_detail_userlevelvalue);
		food_nameView = (TextView) findViewById(R.id.activity_near_food_show_food_detail_fooddesc);
		user_nameView = (TextView) findViewById(R.id.activity_near_food_show_food_detail_username);
		want_it_totalView = (CheckedTextView) findViewById(R.id.activity_near_food_show_food_detail_want);
		headerTextView=(TextView) findViewById(R.id.activity_near_food_show_header_center_text);
		user_imageView = (CircleImageView) findViewById(R.id.activity_near_food_show_food_detail_userimg);
		picture_urlView = (ImageView) findViewById(R.id.activity_near_food_show_food_detail_photo);
		detail_howLayout = (TableLayout) findViewById(R.id.activity_near_food_show_food_detail_how);
		likeUsersLayout = (MyGridView) findViewById(R.id.activity_near_food_show_food_detail_like_userlist);
		commentsLayout = (MyListView) findViewById(R.id.activity_near_food_show_food_detail_comments);
		
		//头部
		headerProgressBar=(ProgressBar) findViewById(R.id.activity_near_food_show_header_right_loading);
		headerProgressBar.setVisibility(View.VISIBLE);
		moresightsLayout=(MyListView) findViewById(R.id.activity_near_food_show_food_detail_moresights_list);
		header_left_imgview=(ImageView) findViewById(R.id.activity_near_food_show_header_left_imgview);
		header_left_imgview.setVisibility(View.VISIBLE);
		header_left_imgview.setImageResource(R.drawable.header_menu_back);
		
		header_left_imgview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		header_right_imgview=(ImageView) findViewById(R.id.activity_near_food_show_header_right_imgview);
	
		//底部
		bottom_weiboView=(TextView) findViewById(R.id.activity_near_food_show_food_detail_bottom_weibo);
		bottom_wxView=(TextView) findViewById(R.id.activity_near_food_show_food_detail_bottom_wx);
		bottom_qqView=(TextView) findViewById(R.id.activity_near_food_show_food_detail_bottom_qq);
		bottom_commentView=(TextView) findViewById(R.id.activity_near_food_show_food_detail_bottom_comment);
		
		bottom_weiboView.setOnClickListener(this);
		bottom_wxView.setOnClickListener(this);
		bottom_qqView.setOnClickListener(this);
		bottom_commentView.setOnClickListener(this);
		bottom_weiboView.setOnClickListener(this);
		
		bottom_fav=(CheckedTextView) findViewById(R.id.activity_near_food_show_food_detail_bottom_fav);
		if (isCollec) {
			drawable = getResources().getDrawable(R.drawable.tb_liked);
		}else {
			drawable = getResources().getDrawable(R.drawable.tb_like_state);
		}
		
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
		bottom_fav.setCompoundDrawables(null, drawable, null, null);
		requestDataFromNetwork();
		
		

	}
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		
		case R.id.activity_near_food_show_food_detail_bottom_weibo:
		
			intent.setAction(Intent.ACTION_SEND);
		
			intent.setType("text/plain");
		
			intent.putExtra(Intent.EXTRA_TEXT, nearFoodShow.food_name + "\n" + nearFoodShow.address + "\n"
					+ "http://meishixing.com/placenew/weixin/food?tweet_id=191562&from=groupmessage&isappinstalled=1");
			
			startActivity(intent);
			
			break;
		
		case R.id.activity_near_food_show_food_detail_bottom_wx:
			
	
			intent.setAction(Intent.ACTION_SEND);
	
			intent.setType("text/plain");
		
			intent.putExtra(Intent.EXTRA_TEXT, nearFoodShow.food_name + "\n" + nearFoodShow.address + "\n"
					+ "http://meishixing.com/placenew/weixin/food?tweet_id=191562&from=groupmessage&isappinstalled=1");
		
			startActivity(intent);
			
			break;
			
		case R.id.activity_near_food_show_food_detail_bottom_qq:
			
		
			intent.setAction(Intent.ACTION_SEND);
		
			intent.setType("text/plain");
	
			intent.putExtra(Intent.EXTRA_TEXT, nearFoodShow.food_name + "\n" + nearFoodShow.address + "\n"
					+ "http://meishixing.com/placenew/weixin/food?tweet_id=191562&from=groupmessage&isappinstalled=1");
			
			startActivity(intent);
			
			break;
		case R.id.activity_near_food_show_food_detail_bottom_comment:
			Intent intent2 =new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent2);
			
			break;

		case R.id.activity_near_food_show_food_detail_bottom_fav:
			if (isCollec) {
				dBhelp.delDatas(tweet_id+"", DBmessage.COLLECTED);
				drawable = getResources().getDrawable(R.drawable.tb_like_state);
				isCollec = false;
			} else {
				dBhelp.inserData(tweet_id+"", nearFoodShow.picture_url, nearFoodShow.food_name, nearFoodShow.address,
						DBmessage.COLLECTED,2);
				drawable = getResources().getDrawable(R.drawable.tb_liked);
				isCollec = true;
			}
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			bottom_fav.setCompoundDrawables(null, drawable, null, null);
			break;

		default:
			break;
		}
	}

	private void requestDataFromNetwork() {
		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				//System.out.println("String response =" + response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONObject object = jsonObject.getJSONObject("result");
					nearFoodShow = NearFoodShow
							.initWithJsonObject(object);
					//数据回来才点击
					bottom_fav.setOnClickListener(NearFoodShowActivity.this);
					//添加到数据库
					if (!isLooked) {
						dBhelp.inserData(tweet_id+"", nearFoodShow.picture_url, nearFoodShow.food_name, nearFoodShow.address, DBmessage.LOOKDE, 2);
					}
					upLoadingView(nearFoodShow);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		ErrorListener errorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println("回调错误");
				requestDataFromNetwork();
			}
		};
		//http://api.meishixing.com/tweet/tweet/detail/session_id=00001323516495c912386a7b2197414e8492be&tweet_id=191562
		//"http://api.meishixing.com/tweet/tweet/detail/session_id=00001323516495c912386a7b2197414e8492be&tweet_id="
		//+ tweet_id + ""
		//ApiHelp.NEAR_FOODURL_SHOW+tweet_id
		StringRequest request = new StringRequest(
				Method.GET,
				ApiHelp.NEAR_FOODURL_SHOW+tweet_id
						, listener, errorListener);
		HttpRequest.getInstance(getApplicationContext()).sendRequest(request);

	}
	//点赞
	

	/**
	 * 上传内容到控件
	 * 
	 * @param foodShow
	 */
	private void upLoadingView(final NearFoodShow foodShow) {
		level_valueView.setText(foodShow.level_value + "");
		food_nameView.setText(foodShow.food_name + "@" + foodShow.place_name
				+ "");
		//头部中心文字
		headerTextView.setVisibility(View.VISIBLE);
		headerTextView.setText(foodShow.food_name);
		
		//头部右边
		headerProgressBar.setVisibility(View.GONE);
		header_right_imgview.setVisibility(View.VISIBLE);
		header_right_imgview.setImageResource(R.drawable.header_menu_home);
		header_right_imgview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		user_nameView.setText(foodShow.user_name);
		//点赞
		want_it_totalView.setText(foodShow.want_it_total + "");
		want_it_totalView.setOnClickListener(new View.OnClickListener() {
			boolean isOnClick=false;
			@Override
			public void onClick(View v) {
				if (!isOnClick) {
					want_it_totalView.setText(foodShow.want_it_total +1+ "");
					want_it_totalView.setChecked(true);
					isOnClick=true;
				}else{
					ToasUtils.showLToast(getApplicationContext(), "您已经赞过了");
				}
				
				
			}
		});
		user_imageView.setImageResource(R.drawable.ic_launcher);

		user_imageView.setTag(foodShow.user_image);
		
		 // 人物头像
		
		ImageLoader.ImageListener lis = ImageLoader.getImageListener(
				user_imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
		HttpRequest.getInstance(getApplicationContext()).loadImage(
				foodShow.user_image, lis, 60, 60);

		 // 美食图像
		 
		picture_urlView.setTag(foodShow.picture_url);
		user_imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(getApplicationContext(), UserFoodActivity.class);
				intent.putExtra("uid", foodShow.user_id);
				startActivity(intent);
				
			}
		});
		ImageLoader.ImageListener lis2 = ImageLoader
				.getImageListener(picture_urlView, R.drawable.ic_launcher,
						R.drawable.ic_launcher);
		HttpRequest.getInstance(getApplicationContext()).loadImage(
				foodShow.picture_url, lis2, 0, 0);
		// 地址与电话
		contactWay(foodShow);
		// 他们也喜欢
		likeUser(foodShow);
		// 评论
		comments(foodShow);
		//他们也吃过
		moreSinghtings(foodShow);
	}

	
	public void moreSinghtings(NearFoodShow foodShow) {
		System.out.println("---------foodShow.foodMores  ="+foodShow.foodMores);
		TextView textView = (TextView) findViewById(R.id.activity_near_food_show_food_detail_moresights_title);
		if (foodShow.foodMores.size()==0) {
			System.out.println("-------kong ---------------");
			textView.setVisibility(View.GONE);
		
		}else{
			System.out.println("--------------------------");
			moresightsLayout.setVisibility(View.VISIBLE);
			moresightsLayout.setAdapter(new MoreSightAdapter(
					foodShow.foodMores, getApplicationContext(),NearFoodShowActivity.this));
		} 

	}

	public void comments(NearFoodShow foodShow) {
		commentsLayout.setVisibility(View.VISIBLE);
		if (foodShow.foodComments != null) {
			CommentAadapter caAadapter = new CommentAadapter(
					foodShow.foodComments, getApplicationContext(),NearFoodShowActivity.this);
			commentsLayout.setAdapter(caAadapter);
			

		}
		LayoutInflater inflater = LayoutInflater.from(this);
		TableRow row1 = (TableRow) inflater.inflate(
				R.layout.activity_near_food_show_food_detail_comment_finsh,
				commentsLayout, false);
		commentsLayout.addFooterView(row1);

		row1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			System.out.println("已经点击");
			}
		});

	}


	public void likeUser(final NearFoodShow foodShow) {
		TextView textView = (TextView) findViewById(R.id.activity_near_food_show_food_detail_like_users_title);
		if (foodShow.likeUsers.size()!=0) {
			likeUsersLayout.setVisibility(View.VISIBLE);

			likeUsersLayout.setAdapter(new LikeUsersAdapter(foodShow.likeUsers,
					getApplicationContext()));
			// likeUsersLayout.addView(gridView);
			likeUsersLayout.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				int place_id=foodShow.place_id;
				int food_id =foodShow.food_id;
				String food_name =foodShow.food_name;
				
				Intent intent =new Intent(getApplicationContext(), UserFoodChildActivity.class);
				intent.putExtra("place_id", place_id);
				intent.putExtra("food_id", food_id);
				intent.putExtra("food_name", food_name);
				startActivity(intent);
		
					
					System.out.println("已经点击");
				}
			});
		}else{
			textView.setVisibility(View.GONE);
		} 
	}

	public void contactWay(final NearFoodShow foodShow) {
		LayoutInflater inflater = LayoutInflater.from(this);
		if (foodShow.address != null || foodShow.phone != null) {
			detail_howLayout.setVisibility(View.VISIBLE);
			TableRow row1 = (TableRow) inflater.inflate(
					R.layout.activity_near_food_show_food_detail_how_item_new,
					detail_howLayout, false);
			if (foodShow.address == null) {
				row1.setVisibility(View.GONE);
			} else {
				detail_howLayout.addView(row1);

				ImageView imageView = (ImageView) row1
						.findViewById(R.id.activity_near_food_showhow_item_img);
				imageView.setImageResource(R.drawable.store_address);

				TextView textView1 = (TextView) row1
						.findViewById(R.id.activity_near_food_showhow_item_text1);
				textView1.setText(foodShow.place_name + "");

				TextView textView2 = (TextView) row1
						.findViewById(R.id.activity_near_food_showhow_item_text2);
				textView2.setText(foodShow.address + "");
				row1.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						int  place_id =foodShow.place_id;
						Intent intent=new Intent(getApplicationContext(), RestaDetailActivity.class);
						intent.putExtra("placeID", place_id);
						startActivity(intent);
						//System.out.println("已经点击");			
					}
				});
			}
			TableRow row2 = (TableRow) inflater.inflate(
					R.layout.activity_near_food_show_food_detail_how_item_new,
					detail_howLayout, false);
			if (foodShow.phone .equals("")) {

				row2.setVisibility(View.GONE);

			} else {
				detail_howLayout.addView(row2);

				ImageView imageView = (ImageView) row2
						.findViewById(R.id.activity_near_food_showhow_item_img);
				imageView.setImageResource(R.drawable.store_detail_tel);

				TextView textView1 = (TextView) row2
						.findViewById(R.id.activity_near_food_showhow_item_text1);
				textView1.setText(foodShow.phone + "");
				row2.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {


						Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +foodShow.phone));

						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						System.out.println("已经点击");
					}
				});
			}
		}
	}
	
	


}
/***
 * 
 * 美食评论Adapter
 * 
 */

class CommentAadapter extends BaseAdapter {
	private HttpRequest mRequest;
	private ArrayList<NearFoodComments> comments;
	private Context context;
	private Activity nearFoodShowActivity;
	public CommentAadapter(ArrayList<NearFoodComments> n, Context c,NearFoodShowActivity near) {
		this.comments = n;
		this.mRequest = HttpRequest.getInstance(context);
		this.context=c;
		nearFoodShowActivity=near;
	}


	@Override
	public int getCount() {
		return comments.size();
	}

	
	@Override
	public NearFoodComments getItem(int position) {
	
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		final NearFoodComments foodComments = comments.get(position);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(
					R.layout.activity_near_food_show_food_detail_comments_item,
					parent, false);
			HoldView holdView = new HoldView();
			holdView.avatarView = (CircleImageView) convertView
					.findViewById(R.id.activity_near_food_show_food_detail_comment_img_url);
			holdView.user_nameView = (TextView) convertView
					.findViewById(R.id.activity_near_food_show_food_detail_comment_username);
			holdView.level_titlevView = (TextView) convertView
					.findViewById(R.id.activity_near_food_show_food_detail_comment_replied_user);
			holdView.commentView = (TextView) convertView
					.findViewById(R.id.activity_near_food_show_food_detail_comment_desc);
			
			holdView.imageView = (LinearLayout) convertView
					.findViewById(R.id.activity_near_food_show_food_detail_comment_reply);
			convertView.setTag(holdView);
		}
		HoldView holdView = (HoldView) convertView.getTag();
		holdView.user_nameView.setText(foodComments.user_name);
		holdView.level_titlevView.setText(foodComments.level_title);
		holdView.commentView.setText(foodComments.comment + "");
		holdView.avatarView.setTag(foodComments.avatar);

		ImageLoader.ImageListener lis = ImageLoader.getImageListener(
				holdView.avatarView, R.drawable.ic_launcher,
				R.drawable.ic_launcher);

		mRequest.loadImage(foodComments.avatar, lis, 0, 0);
		
	
		holdView.avatarView.setOnClickListener(new CircleImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int uid=foodComments.user_id;
				System.out.println("uid =" +uid);
		
				Intent intent =new Intent();
				//context.getApplicationContext(),UserFoodActivity.class
				intent.setClass(nearFoodShowActivity, UserFoodActivity.class);
				intent.putExtra("uid", uid);
			
				nearFoodShowActivity.startActivity(intent);
	
				System.out.println("已经点击");
			}
		});
		holdView.imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent2 =new Intent(nearFoodShowActivity, LoginActivity.class);
				nearFoodShowActivity.startActivity(intent2);
				
			}
		});
		return convertView;
	}

	class HoldView {
		CircleImageView avatarView;
		TextView user_nameView;
		TextView level_titlevView;
		TextView commentView;
		LinearLayout imageView;
	}

}
/***
 * 他们也吃过Adapter
 * 
 *
 */
class MoreSightAdapter extends BaseAdapter{
	private HttpRequest mRequest;
	private Context context;
	private ArrayList<NearFoodMore> nearFoodMores;
	private Activity NearFoodShowActivity;
	public MoreSightAdapter(ArrayList<NearFoodMore> f,Context c,NearFoodShowActivity na) {
		nearFoodMores=f;
		mRequest=HttpRequest.getInstance(context);
		context=c;	
		NearFoodShowActivity=na;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nearFoodMores.size();
	}

	@Override
	public NearFoodMore getItem(int position) {
		
		return nearFoodMores.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NearFoodMore more =nearFoodMores.get(position);
		if (convertView==null) {
			LayoutInflater inflater =LayoutInflater.from(parent.getContext());
			convertView=inflater.inflate(R.layout.activity_near_food_show_food_detail_sight_item, parent, false);
			HoldView holdView =new HoldView();
			holdView.user_nameView=(TextView) convertView.findViewById(R.id.activity_near_food_show_food_detail_sight_username);
			holdView.level_titleView=(TextView) convertView.findViewById(R.id.activity_near_food_show_food_detail_sight_level_title);
			holdView.commentView=(TextView) convertView.findViewById(R.id.activity_near_food_show_food_detail_sight_desc);
			
			holdView.user_imageView=(ImageView) convertView.findViewById(R.id.activity_near_food_show_food_detail_sight_userimg);
			holdView.picture_urlView=(ImageView) convertView.findViewById(R.id.activity_near_food_show_food_detail_sight_foodimg);
			convertView.setTag(holdView);
		}
		HoldView holdView =(HoldView) convertView.getTag();
		holdView.user_nameView.setText(more.user_name);
		holdView.level_titleView.setText(more.level_title);
		holdView.commentView.setText(more.comment);
		
		holdView.user_imageView.setTag(more.user_image);
		ImageLoader.ImageListener lis =ImageLoader.getImageListener(
				holdView.user_imageView, 
				R.drawable.ic_launcher, 
				R.drawable.ic_launcher);	
		mRequest.loadImage(
				more.user_image, 
				lis, 
				0, 
				0);
		holdView.user_imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			int uid=	more.user_id;
			Intent intent =new Intent(NearFoodShowActivity, UserFoodActivity.class);
			intent.putExtra("uid", uid);
			NearFoodShowActivity.startActivity(intent);
			

/**
 * 
 * 这里写他们也吃过头像的跳转
 * 
 * 				
 */
		
				System.out.println("已经点击");
			}
		});
		holdView.picture_urlView.setTag(more.picture_url);
		
		
		ImageLoader.ImageListener liss =ImageLoader.getImageListener(
				holdView.picture_urlView, 
				R.drawable.ic_launcher, 
				R.drawable.ic_launcher);
		mRequest.loadImage(
				more.picture_url, 
				liss, 
				0, 
				0);
		holdView.picture_urlView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int tweet_id=more.tweet_id;
				Intent intent =new Intent(context, NearFoodShowActivity.class);
				intent.putExtra("tweet_id", tweet_id);
				NearFoodShowActivity.startActivity(intent);
/**
 * 
 * 这里写他们也吃过美食图片的跳转
 * 
 * 
 */
				System.out.println("已经点击");
			}
		});
		
		
		return convertView;
	}
	class HoldView{
		TextView user_nameView;
		TextView level_titleView;
		TextView commentView;
		ImageView user_imageView;
		ImageView picture_urlView;
	}
	//点赞
	
}


/***
 * 
 * 
 * 他们也喜欢爱好者Adapter
 * 
 */
class LikeUsersAdapter extends BaseAdapter {
	private ArrayList<NearFoodLikeUser> users;
	private HttpRequest mRequest;

	public LikeUsersAdapter(ArrayList<NearFoodLikeUser> u, Context context) {
		this.users = u;
		mRequest = HttpRequest.getInstance(context);
	}

	@Override
	public int getCount() {

		return users.size();
	}

	@Override
	public NearFoodLikeUser getItem(int position) {

		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NearFoodLikeUser user = users.get(position);
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		convertView = inflater.inflate(
				R.layout.activity_near_food_show_likeuse_item, parent, false);
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.activity_near_food_show_likeuse_img);
		imageView.setTag(user.user_image);
		ImageLoader.ImageListener lis = ImageLoader.getImageListener(imageView,
				R.drawable.ic_launcher, R.drawable.ic_launcher);

		mRequest.loadImage(user.user_image, lis, 0, 0);

		return convertView;
	}

}
