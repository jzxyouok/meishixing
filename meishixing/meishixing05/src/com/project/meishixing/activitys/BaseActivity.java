package com.project.meishixing.activitys;

import meishixing.MeshixingApp;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MeshixingApp.activities.add(this);
		super.onCreate(arg0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MeshixingApp.activities.remove(this);
	}
}
