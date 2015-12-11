package com.project.meishixing.activitys;

import com.project.meishixing.R;
import com.project.meishixing.adapters.WellComeAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class WellComeActivity extends Activity implements OnClickListener,
		OnPageChangeListener, OnCheckedChangeListener {

	private RadioGroup rg;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wellcome);
		viewPager = (ViewPager) findViewById(R.id.vp_wellcome);
		//设置监听
		viewPager.setOnPageChangeListener(this);
		rg = (RadioGroup) findViewById(R.id.rg_wellcome);
		//设置监听
		rg.setOnCheckedChangeListener(this);
		// 初始化3个欢迎页面
		View[] wellComesV = new View[3];
		initWellComesPage(wellComesV);
		viewPager.setAdapter(new WellComeAdapter(wellComesV));
	}

	//初始化数据
	private void initWellComesPage(View[] wellComesV) {
		ViewGroup.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		for (int i = 0; i < wellComesV.length; i++) {
			View v = new View(this);
			v.setLayoutParams(params);
			switch (i) {
			case 0:
				v.setBackgroundResource(R.drawable.wellcome_1);
				wellComesV[0] = v;
				break;
			case 1:
				v.setBackgroundResource(R.drawable.wellcome_2);
				wellComesV[1] = v;
				break;
			case 2:
				View v1=View.inflate(getApplicationContext(), R.layout.frg_wellcome,null );
				v1.findViewById(R.id.bt_splash_star).setOnClickListener(this);
				wellComesV[2] = v1;
				break;
			}
		}
	}

	
	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.bt_splash_star) {
			//跳转到主界面
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
	}

	/*
	 * pageChangeListener
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		//改变radioButton
		RadioButton button=(RadioButton) rg.getChildAt(arg0);
		button.setChecked(true);
	}

	/*
	 * 点击radioButtong改变选项
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_fist:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_sec:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_thre:
			viewPager.setCurrentItem(2);
			break;
		}
		
	}
}
