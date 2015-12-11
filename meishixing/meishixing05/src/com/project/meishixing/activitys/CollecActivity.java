package com.project.meishixing.activitys;

import com.project.meishixing.R;
import com.project.meishixing.adapters.CollecPagerAdaprer;
import com.project.meishixing.db.OpenDBhelp;
import com.project.meishixing.fragments.CollecFrag;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CollecActivity extends BaseActivity implements
		OnPageChangeListener, OnCheckedChangeListener {

	private RadioGroup group;
	private ViewPager viewPager;
	public boolean isChoiceMd;
	private CollecPagerAdaprer adaprer;
	private int flag;
	private CollecFrag frag;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_collec);
		flag = getIntent().getIntExtra("flag", 1);
		// 模拟添加几条数据
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(getApplicationContext());
		viewPager = (ViewPager) findViewById(R.id.vp_collec);
		group = (RadioGroup) findViewById(R.id.rg_collec);
		group.setOnCheckedChangeListener(this);
		adaprer = new CollecPagerAdaprer(getSupportFragmentManager());
		viewPager.setAdapter(adaprer);
		viewPager.setOnPageChangeListener(this);
		if (flag == 1) {
			viewPager.setCurrentItem(0);
		} else {
			viewPager.setCurrentItem(1);
		}
	}

	@Override
	protected void onRestart() {
		// 刷新数据库
		frag.setAdapterDatas();
		super.onRestart();
	}

	@Override
	public void onBackPressed() {
		if (isChoiceMd) {
			frag = (CollecFrag) adaprer.getItem(viewPager.getCurrentItem());
			frag.setChoiceModer(false);
			isChoiceMd = false;
			return;
		}
		super.onBackPressed();
	}

	// pagechange Listenner
	public void onPageScrollStateChanged(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int arg0) {
		// 改变radioButton
		RadioButton button = (RadioButton) group.getChildAt(arg0);
		button.setChecked(true);
		frag = (CollecFrag) adaprer.getItem(viewPager.getCurrentItem());
		isChoiceMd = frag.isChoice;
	}

	// 单选改变
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rb_collec_lollec) {
			viewPager.setCurrentItem(1);
		} else if (checkedId == R.id.rb_collec_looked) {
			viewPager.setCurrentItem(0);
		}
	}

}
