package com.project.meishixing.adapters;

import com.project.meishixing.fragments.CollecFrag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CollecPagerAdaprer extends FragmentPagerAdapter {

	private Fragment[]fragments=new Fragment[2];
	public CollecPagerAdaprer(FragmentManager fm) {
		super(fm);
		fragments[0]=new CollecFrag(1);
		fragments[1]=new CollecFrag(2);
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments[arg0];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

}
