package com.project.meishixing.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.meishixing.R;
import com.project.meishixing.abs.GetHomeActJsonListener;
import com.project.meishixing.adapters.HomePallViewAdapter;
import com.project.meishixing.beans.HomeGridViewBean;
import com.project.meishixing.net.ApiHelp;
import com.project.meishixing.net.HomeActGetJson;
import com.pubu.gridview.StaggeredGridView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenu.OnMenuListener;
import com.special.ResideMenu.ResideMenuItem;

public class HomeActivity extends BaseActivity implements OnScrollListener,
		OnMenuListener, OnClickListener, OnItemClickListener {
	private static final String BASETITLENAME = "最新分享";

	private LinearLayout mHomeTitleLayout;
	private ImageView mConfigImageView;
	private TextView mTitleView;
	private ImageView mSearchImageView;
	private RelativeLayout mNearbyFoodLayout;
	private RelativeLayout mNearbyStoreLayout;
	private RelativeLayout mHotFoodLayout;
	private LinearLayout mHotFirstLayout;
	private LinearLayout mMagazingLayout;
	private LinearLayout mCameraLayout;
	private LinearLayout mMapLayout;
	private LinearLayout mPersonLayout;
	private LinearLayout mBottomAllView;
	private StaggeredGridView mGridView;
	private HomePallViewAdapter mAdapter;
	private int index = 1;
	private int totalItem = 0;

	public ResideMenu resideMenu;
	private ResideMenuItem item_home;
	private ResideMenuItem item_login;
	private ResideMenuItem item_setting;
	private ResideMenuItem item_city;
	private ResideMenuItem item_chart;
	private boolean isMenuOpen;
	private boolean isBack;

	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity_main);
		init();
		addDataToAdapter();
	}

	private void addDataToAdapter() {// 初始化数据
		HomeActGetJson.getHomeActJsonData(getApplication(),
				ApiHelp.HOMEACTBASEPATH, index, new GetHomeActJsonListener() {

					@Override
					public void getHomeData(ArrayList<HomeGridViewBean> beans) {// 下载完成后的数据回调
						totalItem += beans.size();
						HomeGridViewBean bean = beans.get(0);
						mTitleView.setText(bean.city_name + BASETITLENAME);
						mAdapter.addData(beans);

					}

					@Override
					public void getErrorData(String error) {
						if (error.equals(HomeActGetJson.ERROR)) {
							addDataToAdapter();
							// TODO
							Log.d("TAG", "addDataToAdapter===" + index);
						}
					}
				});
	}

	private void init() {// 初始化各个部件
		mHomeTitleLayout = (LinearLayout) findViewById(R.id.home_title_lnlt);
		mConfigImageView = (ImageView) findViewById(R.id.home_title_config_iv);
		mTitleView = (TextView) findViewById(R.id.home_title_tv);
		mSearchImageView = (ImageView) findViewById(R.id.home_title_search_iv);
		mNearbyFoodLayout = (RelativeLayout) findViewById(R.id.home_nearbyfood_rllt);
		mNearbyStoreLayout = (RelativeLayout) findViewById(R.id.home_nearbystore_rllt);
		mHotFoodLayout = (RelativeLayout) findViewById(R.id.home_hotfood_rllt);
		mHotFirstLayout = (LinearLayout) findViewById(R.id.home_bottom_hotfirst_lnlt);
		mMagazingLayout = (LinearLayout) findViewById(R.id.home_bottom_magazine_lnlt);
		mCameraLayout = (LinearLayout) findViewById(R.id.home_bottom_camer_lnlt);
		mMapLayout = (LinearLayout) findViewById(R.id.home_bottom_map_lnlt);
		mPersonLayout = (LinearLayout) findViewById(R.id.home_bottom_person_lnlt);
		mBottomAllView = (LinearLayout) findViewById(R.id.home_bottom_all_lnlt);
		mGridView = (StaggeredGridView) findViewById(R.id.home_mystag_gdview);

		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		// 屏幕的宽高
		int width = manager.getDefaultDisplay().getWidth();
		int height = manager.getDefaultDisplay().getHeight();
		mAdapter = new HomePallViewAdapter(width, height);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnScrollListener(this);
		mGridView.setOnItemClickListener(this);
	}

	public void beChicked(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.home_title_config_iv:// 点击配置按钮跳转界面
			setResidMenu();
			resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			break;

		case R.id.home_title_search_iv:// 点击搜索按钮跳转界面

			break;
		case R.id.home_nearbyfood_rllt:// 点击美食按钮跳转界面
			intent.setClass(getApplication(), NearbyFoodActivity.class);
			intent.putExtra("tittle", "附近美食");
			intent.putExtra("flag", ApiHelp.FLAG_NEARBYFOOD);
			startActivity(intent);
			break;
		case R.id.home_nearbystore_rllt:// 点击餐馆按钮跳转界面
			intent.setClass(this, NearRestaActivit.class);
			startActivity(intent);
			break;
		case R.id.home_hotfood_rllt:// 点击热门按钮跳转界面
			intent.setClass(this, HotFoodActivity.class);
			startActivity(intent);
			break;
		case R.id.home_bottom_hotfirst_lnlt:// 点击榜单按钮跳转界面
			startActivity(new Intent(this, HotFirsActivity.class));
			break;
		case R.id.home_bottom_magazine_lnlt:// 点击杂志按钮跳转界面
			startActivity(new Intent(this, FoodMagazine.class));
			break;
		case R.id.home_bottom_camer_lnlt:// 点击相机按钮跳转界面
			showPictureDialog();
			break;
		case R.id.home_bottom_map_lnlt:// 点击足迹按钮跳转界面
			intent = new Intent(this, CollecActivity.class);
			intent.putExtra("flag", 1);
			startActivity(intent);
			break;
		case R.id.home_bottom_all_lnlt:// 点击下部分View按钮跳转界面

			break;
		case R.id.home_bottom_person_lnlt:// 点击个人主页按钮跳转界面
			intent=new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;

		}

	}

	// 相机拍照
	private void getPhotoFromCam() {
		// 用户选择拍照
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		// intent.addCategory("android.intent.category.LAUNCHER");
		startActivityForResult(intent, 2222);
	}

	// 获取图库图片
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Intent intent = new Intent(this, ShareFoodActivity.class);
		if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			intent.putExtra("flag", 1);
			intent.putExtra("uri", uri);
			startActivity(intent);
		}
		if (requestCode == 2222 && resultCode == Activity.RESULT_OK) {
			// 获得data里的图片
			Bundle bundle = data.getExtras();
			intent.putExtra("flag", 2);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}
		alertDialog.dismiss();

	}

	// 从图库选择图片
	private void selectImage() {
		// 创建隐式意图打开图库
		Intent intent = new Intent();
		// 设置intent的action
		intent.setAction("android.intent.action.GET_CONTENT");
		// 设置要发送的数据类型
		intent.setType("image/*");
		// 开启activity
		startActivityForResult(intent, 1111);
	}

	private void showPictureDialog() {
		// 创建dialog
		AlertDialog.Builder dialog = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_resdeta, null);
		View pz = view.findViewById(R.id.dialog_pz);
		pz.setOnClickListener(this);
		View tk = view.findViewById(R.id.dialog_tk);
		tk.setOnClickListener(this);
		dialog.setTitle("分享美食");
		dialog.setView(view);
		alertDialog = dialog.create();
		alertDialog.show();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case SCROLL_STATE_FLING:

			break;
		case SCROLL_STATE_IDLE:
			if (isloadmore)
				if (firstPosition + visCount == totalItem) {
					++index;
					addDataToAdapter();
					isloadmore = false;
				}
			if (firstPosition - oldVisibleItem > 0) { // 下拉的时候隐藏菜单栏
				if (mHomeTitleLayout.isShown()) {
					mHomeTitleLayout.setVisibility(View.GONE);
					mBottomAllView.setVisibility(View.GONE);
				}
				oldVisibleItem = firstPosition;
			} else if (firstPosition - oldVisibleItem < 0) { // 上拉的时候显示菜单栏
				if (!mHomeTitleLayout.isShown()) {
					mHomeTitleLayout.setVisibility(View.VISIBLE);
					mBottomAllView.setVisibility(View.VISIBLE);
				}
				oldVisibleItem = firstPosition;
			}
			break;
		case SCROLL_STATE_TOUCH_SCROLL:

			break;

		default:
			break;
		}
	}

	int firstPosition;
	int visCount;
	int oldVisibleItem;
	boolean isloadmore;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) { // 引起图片闪动
		// TODO
		isloadmore = true;
		firstPosition = firstVisibleItem;
		visCount = visibleItemCount;

	}

	// 设置侧滑
	private void setResidMenu() {
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setScaleValue(0.7f);
		// 设置3D模式
		// resideMenu.setUse3D(true);
		// 设置右边不用
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
		resideMenu.setMenuListener(this);
		// create menu items;
		String titles[] = { "返回首页", "注册登录", "切换城市", "设置", "我要反馈" };
		int icon[] = { R.drawable.menu_home, R.drawable.menu_login,
				R.drawable.menu_city, R.drawable.menu_setting,
				R.drawable.menu_chart };

		item_home = new ResideMenuItem(this, icon[0], titles[0]);
		item_home.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_home, ResideMenu.DIRECTION_LEFT);

		item_login = new ResideMenuItem(this, icon[1], titles[1]);
		item_login.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_login, ResideMenu.DIRECTION_LEFT);

		item_city = new ResideMenuItem(this, icon[2], titles[2]);
		item_city.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_city, ResideMenu.DIRECTION_LEFT);

		item_setting = new ResideMenuItem(this, icon[3], titles[3]);
		item_setting.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_setting, ResideMenu.DIRECTION_LEFT);

		item_chart = new ResideMenuItem(this, icon[4], titles[4]);
		item_chart.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_chart, ResideMenu.DIRECTION_LEFT);

	}

	// 侧滑菜单监听
	@Override
	public void openMenu() {
		isMenuOpen = true;
	}

	@Override
	public void closeMenu() {
		isMenuOpen = false;
	}

	@Override
	public void onClick(View v) { // 侧滑按钮被点击的监听
		Intent intent = new Intent();
		if (v == item_home) {// 跳到主界面
			// 关掉侧滑
			resideMenu.closeMenu();
			// 刷新数据
			fuashGridData();
		} else if (v == item_login) {// 跳到登录界面
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
		} else if (v == item_city) {// 跳到切换城市界面

		} else if (v == item_setting) {// 跳到设置界面

		} else if (v == item_chart) {// 跳到反馈界面

		} else if (v.getId() == R.id.dialog_tk) {// 图库选图
			selectImage();
		} else if (v.getId() == R.id.dialog_pz) {// 拍照
			getPhotoFromCam();
		}

	}

	private void fuashGridData() {
		index = 1;
		HomeActGetJson.getHomeActJsonData(getApplication(),
				ApiHelp.HOMEACTBASEPATH, index, new GetHomeActJsonListener() {

					@Override
					public void getHomeData(ArrayList<HomeGridViewBean> beans) {// 下载完成后的数据回调
						mAdapter.clearList();
						totalItem += beans.size();
						HomeGridViewBean bean = beans.get(0);
						mTitleView.setText(bean.city_name + BASETITLENAME);
						mAdapter.setData(beans);
					}

					@Override
					public void getErrorData(String error) {
						if (error.equals(HomeActGetJson.ERROR)) {
							addDataToAdapter();
							// TODO
							Log.d("TAG", "addDataToAdapter===" + index);
						}
					}
				});
		index++;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(this, NearFoodShowActivity.class);
		HomeGridViewBean bean=mAdapter.getItem(position);
		intent.putExtra("tweet_id", bean.tweet_id);
		startActivity(intent);
		
	}
}
