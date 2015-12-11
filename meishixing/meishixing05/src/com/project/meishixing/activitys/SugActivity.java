package com.project.meishixing.activitys;

import com.project.meishixing.R;

import android.os.Bundle;
import android.view.View;

public class SugActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_suggestion);

	}

	//点击返回
	public void getBack(View v) {
		finish();
	}
}
