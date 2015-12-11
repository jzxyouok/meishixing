package com.project.meishixing.activitys;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.project.meishixing.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ShareFoodActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_sharefood);
		// 获得flag
		ImageView imageView = (ImageView) findViewById(R.id.iv_share);
		findViewById(R.id.back_foodshare).setOnClickListener(this);
		findViewById(R.id.bt_share).setOnClickListener(this);
		
		int flag = getIntent().getIntExtra("flag", -1);
		if (flag == 1) {
			showPicFromGarlly(imageView);
		} else if (flag == 2) {
			showPicFromCam(imageView);
		}
	}

	// 从相机获得照片
	private void showPicFromCam(ImageView imageView) {
		Bundle b = getIntent().getBundleExtra("bundle");
		Bitmap map = (Bitmap) b.get("data");
		imageView.setImageBitmap(map);
	}

	// 从图库获得照片
	private void showPicFromGarlly(ImageView imageView) {
		Uri uri = getIntent().getParcelableExtra("uri");
		// System.out.println("url=="+uri);
		try {
			// 获得内容解析器
			ContentResolver resolver = getContentResolver();
			// 解析uri
			InputStream inputStream = resolver.openInputStream(uri);
			Bitmap mBitmap = BitmapFactory.decodeStream(inputStream);
			imageView.setImageBitmap(mBitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back_foodshare) {
			finish();
		} else if (v.getId() == R.id.bt_share) {
			startActivity(new Intent(this, LoginActivity.class));
		}
	}
}
