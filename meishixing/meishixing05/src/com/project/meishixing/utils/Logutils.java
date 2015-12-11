package com.project.meishixing.utils;
import android.util.Log;

/**
 * Created by wukai on 15/7/15.
 */
public class Logutils {

	private final boolean DEBUG_ALLOW = true;
	private static final String TAG = "DEBUG";


	public  static void logd(String content){
		logd(TAG,content);
	}

	public static void logd(String TAG,String content){
		Log.d(TAG, content);
	}
}
