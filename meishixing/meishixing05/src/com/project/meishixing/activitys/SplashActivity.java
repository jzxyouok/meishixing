package com.project.meishixing.activitys;

import com.project.meishixing.R;
import com.project.meishixing.utils.Pref_Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

	protected static final String FIRST_INSTALL = "first_install";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		login();
	}


	// 展示2s图片之后跳转到其他activity
	private void login() {
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 判断是否第一次安装
						boolean isFirstInstall = Pref_Utils.getBoolean(
								SplashActivity.this, FIRST_INSTALL, true);
						Intent intent = null;
						if (isFirstInstall) {
							intent = new Intent(SplashActivity.this,
									WellComeActivity.class);
						} else {
							intent = new Intent(SplashActivity.this, HomeActivity.class);
						}
						startActivity(intent);
						Pref_Utils
								.putBoolean(SplashActivity.this, FIRST_INSTALL, false);
						finish();
					}
				}, 1000);
	}

}
