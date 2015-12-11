package com.project.meishixing.net;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MemoryCache implements ImageCache  {

	private LruCache<String, Bitmap> mLruCache;
	
	public MemoryCache(){
		int size = (int)Runtime.getRuntime().freeMemory()/4;
		mLruCache = new LruCache<String,Bitmap>(size){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		return mLruCache.get(url);
	}
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mLruCache.put(url, bitmap);
	}
	

}
