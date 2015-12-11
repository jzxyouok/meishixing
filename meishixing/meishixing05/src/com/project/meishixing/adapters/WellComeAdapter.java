package com.project.meishixing.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class WellComeAdapter extends PagerAdapter{
	private View[] wellComesV = null;
	
	public WellComeAdapter(View[] datas) {
		this.wellComesV=datas;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(wellComesV[position]);
		return wellComesV[position];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return wellComesV.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(wellComesV[position]);
	}

}
