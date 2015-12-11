package com.project.meishixing.adapters;

import java.util.ArrayList;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NearRestSpinerAdapter extends BaseAdapter {

	private ArrayList<String> datas = new ArrayList<String>();

	public NearRestSpinerAdapter(ArrayList<String> datas) {
		this.datas=datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			TextView vTextView = new TextView(parent.getContext());
			vTextView.setTextSize(16f);
			vTextView.setPadding(0, 15, 0, 15);
			vTextView.setGravity(Gravity.CENTER);
			convertView=vTextView;
		}
		TextView textView=(TextView) convertView;
		textView.setText(datas.get(position));
		return convertView;
	}
}
