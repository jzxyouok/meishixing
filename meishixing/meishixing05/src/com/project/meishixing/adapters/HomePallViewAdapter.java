package com.project.meishixing.adapters;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.HomeGridViewBean;
import com.project.meishixing.net.HttpRequest;

public class HomePallViewAdapter extends BaseAdapter {
	private int width;
	private int height;
	private ArrayList<HomeGridViewBean> beans = new ArrayList<HomeGridViewBean>();
	private Bitmap bitmap3;

	public HomePallViewAdapter(int width, int height) {
		this.height = height;
		this.width = width;
	}

	@Override
	public int getCount() {
		return beans.size();
	}
	public void clearList(){
		beans.clear();
		notifyDataSetChanged();
	}
	public void setData(ArrayList<HomeGridViewBean> list) {
		this.beans = list;
		notifyDataSetChanged();
		
	}

	public void addData(ArrayList<HomeGridViewBean> list) {
		this.beans.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public HomeGridViewBean getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.home_grid_item, null);
			findViews(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		HomeGridViewBean bean = beans.get(position);
		setViews(parent, holder, bean,position);
		return convertView;
	}

	private void setViews(ViewGroup parent, final ViewHolder holder,
			HomeGridViewBean bean,int po) { // 给各个控件设置属性
		holder.commentView.setText(bean.comment);
		if (bean.food_price == 0)
			holder.priceView.setVisibility(View.GONE);
		else
			holder.priceView.setVisibility(View.GONE);
		holder.priceView.setText("" + bean.food_price);
		holder.foodnameView.setText(bean.food_name);
		holder.timeView.setVisibility(View.VISIBLE);
		holder.timeView.setText("2周前");
		if (bean.want_it_total != 0)
			holder.heartView.setVisibility(View.VISIBLE);
		else
			holder.heartView.setVisibility(View.GONE);
		holder.heartView.setText("" + bean.want_it_total);
		if (bean.pv_count != 0)
			holder.eyeView.setVisibility(View.VISIBLE);
		else
			holder.eyeView.setVisibility(View.GONE);
		holder.eyeView.setText("" + bean.pv_count);
		if (bean.pv_count != 0)
			holder.comment_countView.setVisibility(View.VISIBLE);
		else
			holder.comment_countView.setVisibility(View.GONE);
		holder.comment_countView.setText("" + bean.pv_count);
		holder.usernameView.setText(bean.user_name);
		holder.placeView.setText(bean.city_name + "@" + bean.place_name);
		holder.phoneView.setText("来自Iphone版");
		holder.headImageView.setTag(bean.picture_url);
		holder.bottomImageView.setTag(bean.user_image);
		Bitmap bitmap=Bitmap.createBitmap(width/2, (width/2)*bean.image_height/bean.image_width,Config.ALPHA_8);
		//Log.d("TAG", "宽="+bitmap2.getWidth()+"高度="+bitmap2.getHeight()+"第几张=="+po);
		ImageListener headLis=new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if (response.getBitmap()!=null) {
					holder.headImageView.setImageBitmap(response.getBitmap());
				}
			}
		};
		holder.headImageView.setImageBitmap(bitmap);
		HttpRequest.getInstance(parent.getContext()).loadImage(
				bean.picture_url, headLis, width/2, 2000);
		
		ImageListener userLis = ImageLoader.getImageListener(
				holder.bottomImageView, R.drawable.ic_launcher,
				R.drawable.ic_launcher);
		HttpRequest.getInstance(parent.getContext()).loadImage(bean.user_image,
				userLis, 80, 80);
	}

	private void findViews(View convertView) {// 从布局文件中找到各控件
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.headImageView = (ImageView) convertView
				.findViewById(R.id.home_grid_item_head_iv);
		viewHolder.commentView = (TextView) convertView
				.findViewById(R.id.home_grid_item_comment_tv);
		viewHolder.priceView = (TextView) convertView
				.findViewById(R.id.home_grid_item_foodprice_tv);
		viewHolder.foodnameView = (TextView) convertView
				.findViewById(R.id.home_grid_item_foodname_tv);
		viewHolder.timeView = (TextView) convertView
				.findViewById(R.id.home_grid_item_time);
		viewHolder.heartView = (TextView) convertView
				.findViewById(R.id.home_grid_item_heart);
		viewHolder.eyeView = (TextView) convertView
				.findViewById(R.id.home_grid_item_eye);
		viewHolder.comment_countView = (TextView) convertView
				.findViewById(R.id.home_grid_item_comment_count_tv);
		viewHolder.bottomImageView = (ImageView) convertView
				.findViewById(R.id.home_grid_item_bottom_iv);
		viewHolder.usernameView = (TextView) convertView
				.findViewById(R.id.home_grid_item_bottom_user_name);
		viewHolder.placeView = (TextView) convertView
				.findViewById(R.id.home_grid_item_bottom_place_name);
		viewHolder.phoneView = (TextView) convertView
				.findViewById(R.id.home_grid_item_bottom_from_phone);
		convertView.setTag(viewHolder);
	}

	private class ViewHolder {
		ImageView headImageView;
		TextView commentView;
		TextView priceView;
		TextView foodnameView;
		TextView timeView;
		TextView heartView;
		TextView eyeView;
		TextView comment_countView;
		ImageView bottomImageView;
		TextView usernameView;
		TextView placeView;
		TextView phoneView;
	}
}
