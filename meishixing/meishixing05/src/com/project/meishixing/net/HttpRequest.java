package com.project.meishixing.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import com.android.volley.toolbox.Volley;


public class HttpRequest {
	
	private static HttpRequest Instance;
	private RequestQueue mQueue;
	private ImageLoader mLoader;
	private boolean isStop=false;
	private HttpRequest(Context context) {
		mQueue=Volley.newRequestQueue(context);
		mLoader=new ImageLoader(mQueue, new MemoryCache());
	}
	public static HttpRequest getInstance(Context context) {
		if(Instance==null){
			synchronized (HttpRequest.class) {
				if(Instance==null){
					Instance=new HttpRequest(context);
				}
			}
		}
		return Instance;
	}
	public void sendRequest(Request request) {
		mQueue.add(request);
	}
	public void loadImage(String uri,ImageListener lis,int width,int height){
		mLoader.get(uri,lis,width,height);
	}
	
	public void clearCache(){
		mQueue.getCache().clear();
	}
	public ImageLoader getImageLoader(){
		return mLoader;
	}
//	public void stopQueue(){
//		mQueue.stop();
//		isStop=true;
//	}
	public void stopQueue() {
		mQueue.stop();
	}
}
