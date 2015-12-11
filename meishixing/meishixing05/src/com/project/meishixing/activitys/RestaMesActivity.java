package com.project.meishixing.activitys;

import com.project.meishixing.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RestaMesActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_restames);
		// 获得信息
		String tags = getIntent().getStringExtra("tags");
		String bus = getIntent().getStringExtra("bus");
		findViewById(R.id.tv_restmes_bus);
		TextView twoTextView = (TextView) findViewById(R.id.tv_restmes_busmes);
		findViewById(R.id.tv_restmes_tese);
		TextView fourTextView = (TextView) findViewById(R.id.tv_restmes_tmes);
		if (!"".equals(tags)&&tags!=null) {
			fourTextView.setText(tags);
		}
		if (!"".equals(bus)&&bus!=null) {
			twoTextView.setText(bus);
		} 
	}
}
