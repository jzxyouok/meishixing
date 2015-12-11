package com.project.meishixing.activitys;

import java.util.List;

import com.project.meishixing.R;
import com.project.meishixing.R.id;
import com.project.meishixing.R.layout;
import com.project.meishixing.R.menu;
import com.project.meishixing.abs.GetHotFoodActJsonListener;
import com.project.meishixing.abs.GetNraebyFoodJsonListener;
import com.project.meishixing.adapters.HotFoodGridAdapter;
import com.project.meishixing.adapters.NearbyFoodAdapter;
import com.project.meishixing.beans.HotFoodMoreBean;
import com.project.meishixing.beans.NearbyFoodBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.GetHoFoodActJson;
import com.project.meishixing.net.GetNearbyFoodJson;
import com.project.meishixing.utils.ToasUtils;
import com.pubu.gridview.StaggeredGridView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class NearbyFoodActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private String mTittle;
	private int mCityId;
	private double mLat;
	private double mLng;
	private String mWitch;
	private String mPath;
	private int pager=1;
	private int flag;
	private int foodzine_id;
	
	private View mBackView;
	private TextView mTitleView;
	private StaggeredGridView mGridView;
	private NearbyFoodAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_food_activity);
		// 获得传递过来的tittle
		Intent intent=getIntent();
		//获取上个activity传输过来的数据
		getLastData(intent);
		WindowManager manager= (WindowManager) getSystemService(WINDOW_SERVICE);
		int width=manager.getDefaultDisplay().getWidth();
		init(width);
		initAdapterData();
	}
		
	
	private void init(int width) {
		mAdapter=new NearbyFoodAdapter(width);
		mBackView=findViewById(R.id.hotfood_title_back_iv);
		mTitleView=(TextView) findViewById(R.id.hotfood_title_tv);
		mGridView=(StaggeredGridView) findViewById(R.id.hotfood_mystag_gdview);
		mTitleView.setText(mTittle);
		mGridView.setAdapter(mAdapter);
		mBackView.setOnClickListener(this);
		mGridView.setOnItemClickListener(this);
		
	}
	private void getLastData(Intent intent) {
		flag=intent.getIntExtra("flag", -1);
		mTittle = intent.getStringExtra("tittle");
		mCityId=intent.getIntExtra("cityId", -1);
		mLat = intent.getDoubleExtra("lat", -1);
		mLng=intent.getDoubleExtra("lng",-1);
		mWitch = intent.getStringExtra("witch");
		mPath=ApiHelp.getForMoreURL(mWitch, mCityId, mLat, mLng, pager);
	}
	//TODO
	public void initAdapterData(){
		GetNearbyFoodJson.getNearbyDataBySelf(pager,
				new GetNraebyFoodJsonListener() {
			
			@Override
			public void getJsonData(final List<NearbyFoodBean> beans) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						mAdapter.addData(beans);
					}
				});	
			}
			
			@Override
			public void getErrorData(String error) {
				// TODO Auto-generated method stub
				//数据加载失败后重新加载
				//initAdapterData();
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,HomeActivity.class);
		NearbyFoodBean bean=mAdapter.getItem(position);
		intent.putExtra("tweet_id", bean.tweet_id);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		finish();
		
	}
}
