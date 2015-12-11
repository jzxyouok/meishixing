package meishixing;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;

import android.R.integer;
import android.app.Activity;
import android.app.Application;
import android.text.StaticLayout;

public class MeshixingApp extends Application {

	public static ArrayList<Activity> activities=new ArrayList<>();
	@Override
	public void onCreate() {
		SDKInitializer.initialize(getApplicationContext());
		super.onCreate();
	}
	
	//推出或者返回主界面
	public static void back2Home(boolean isExit){
		int index=1;
		if (isExit) {
			index=0;
		}
		for (int i = index; i < activities.size(); i++) {
			activities.get(i).finish();
		}
	}
}
