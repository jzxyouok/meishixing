package com.project.meishixing.adapters;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.HomeGridViewBean;
import com.project.meishixing.beans.HotFoodMoreBean;
import com.project.meishixing.net.HttpRequest;

public class HotFoodGridAdapter extends BaseAdapter {
	private List<HotFoodMoreBean> beans=new ArrayList<>();
	private int width;
	public HotFoodGridAdapter(int i){
		this.width=i;
	}
	@Override
	public int getCount() {
		return beans.size();
	}
	
	public void setData(List<HotFoodMoreBean> list){
		this.beans.clear();
		this.beans=list;
		notifyDataSetChanged();
	}
	public void addData(List<HotFoodMoreBean> list) {
		this.beans.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public HotFoodMoreBean getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("TAG", "getView");
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.home_grid_item, null);
			findViews(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		HotFoodMoreBean bean = beans.get(position);
		setViews(parent, holder, bean);
		return convertView;
	}

	private void setViews(ViewGroup parent, ViewHolder holder,
			HotFoodMoreBean bean) { // 给各个控件设置属性
		holder.commentView.setText(bean.comment);
		if (bean.food_price == 0)
			holder.priceView.setVisibility(View.GONE);
		else
			holder.priceView.setVisibility(View.GONE);
		holder.priceView.setText("" + bean.food_price);
		holder.foodnameView.setText(bean.food_name);
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
		holder.comment_countView.setText("" + bean.comment_count);
		holder.usernameView.setText(bean.user_name);
		String place_name=bean.city_name;
		holder.placeView.setText(bean.city_name + "@" + bean.place_name);
		holder.phoneView.setText("来自网页版");
		holder.headImageView.setTag(bean.picture_url);
		holder.bottomImageView.setTag(bean.user_image);
		ImageListener headLis = ImageLoader.getImageListener(
				holder.headImageView, R.drawable.ic_launcher,
				R.drawable.ic_launcher);
		HttpRequest.getInstance(parent.getContext()).loadImage(
				bean.picture_url, headLis, width / 2, 0);
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
