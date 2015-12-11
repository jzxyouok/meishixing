package com.project.meishixing.activitys;

import java.util.List;

import com.project.meishixing.R;
import com.project.meishixing.abs.GetHotFoodActJsonListener;
import com.project.meishixing.abs.GetNraebyFoodJsonListener;
import com.project.meishixing.adapters.HotFoodGridAdapter;
import com.project.meishixing.beans.HotFoodMoreBean;
import com.project.meishixing.beans.NearbyFoodBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.GetHoFoodActJson;
import com.project.meishixing.net.GetNearbyFoodJson;
import com.project.meishixing.utils.ToasUtils;
import com.pubu.gridview.StaggeredGridView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HotMoreFoodActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
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
	private HotFoodGridAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.hotfood_activity_main);
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
		mAdapter=new HotFoodGridAdapter(width);
		mBackView=findViewById(R.id.hotfood_title_back_iv);
		mTitleView=(TextView) findViewById(R.id.hotfood_title_tv);
		mGridView=(StaggeredGridView) findViewById(R.id.hotfood_mystag_gdview);
		mTitleView.setText(mTittle);
		mGridView.setAdapter(mAdapter);
		mBackView.setOnClickListener(this);
		mGridView.setOnItemClickListener(this);
		
	}
	private void getLastData(Intent intent) {
		foodzine_id=intent.getIntExtra("foodzine_id", -1);
		Log.d("TAG", "foodzine_id=="+foodzine_id);
		flag=intent.getIntExtra("flag", -1);
		mTittle = intent.getStringExtra("foodzine_name");
		mCityId=intent.getIntExtra("cityId", -1);
		mLat = intent.getDoubleExtra("lat", -1);
		mLng=intent.getDoubleExtra("lng",-1);
		mWitch = intent.getStringExtra("witch");
		mPath=ApiHelp.getForMoreURL(mWitch, mCityId, mLat, mLng, pager);
	}
	@Override
	public void onClick(View v) {
		finish();
	}
	public void initAdapterData(){
		String path=null;
		//是从更多美食跳转过来的
		if(flag==ApiHelp.FLAG_MOREHOTFOOD){
			path=ApiHelp.HOTFOODMOREBEFORPATH+mWitch+ApiHelp.HOTFOODMOREMIDPATH+mCityId+ApiHelp.HOTFOODMOREAFTERPATH+pager;
			
			GetHoFoodActJson.getJsonData(path, 
					getApplication(), 
					new GetHotFoodActJsonListener() {
				@Override
				public void getHotFoodJson(List<HotFoodMoreBean> list) {
					if(list.size()==0){
						ToasUtils.showLToast(getApplication(), "暂无数据");
					}else{
						mAdapter.setData(list);
					}	
				}
				
				@Override
				public void getErrorData(String error) {
					// TODO Auto-generated method stub
					//initAdapterData();
				}
			});
		}else if(flag==ApiHelp.FLAG_FOODMAG){//是从美食杂志跳转过来的
			path=ApiHelp.getFoodMagDetailsPath(foodzine_id, pager);
			getData(path);
		}else if(flag==ApiHelp.FLAG_NEARBYFOOD){//是从附近美食跳转过来的
			
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//TODO 重新设置class
		Intent intent=new Intent(this,HomeActivity.class);
		HotFoodMoreBean bean=mAdapter.getItem(position);
		intent.putExtra("tweet_id", bean.tweet_id);
		startActivity(intent);
		
	}
	public void getData(String path){
		GetHoFoodActJson.getNearbyDataBySelf(path, new GetHotFoodActJsonListener() {
			
			@Override
			public void getHotFoodJson(final List<HotFoodMoreBean> list) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//返回空集合
						mAdapter.addData(list);
						Log.d("TAG", "list=="+list.size());
					}
				});
				
			}
			
			@Override
			public void getErrorData(String error) {
				// TODO Auto-generated method stub
				
			}
		});
}
}
