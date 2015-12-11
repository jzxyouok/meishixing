package com.project.meishixing.adapters;

import java.util.ArrayList;
import java.util.List;

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
import com.project.meishixing.beans.NearbyFoodBean;
import com.project.meishixing.net.HttpRequest;

public class NearbyFoodAdapter extends BaseAdapter {
	private int width;
	private List<NearbyFoodBean> beans = new ArrayList<NearbyFoodBean>();

	public NearbyFoodAdapter(int i) {
		this.width = i;
	}

	public void addData(List<NearbyFoodBean> list) {
		this.beans.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beans.size();
	}

	@Override
	public NearbyFoodBean getItem(int position) {
		// TODO Auto-generated method stub
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 需要修改
		Log.d("TAG", "getView");
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.nearby_grid_item, null);
			findViews(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		NearbyFoodBean bean = beans.get(position);
		setViews(parent, holder, bean);
		return convertView;
	}

	private void setViews(ViewGroup parent, ViewHolder holder,
			NearbyFoodBean bean) { // 给各个控件设置属性
		if (bean.comment == null || bean.comment.equals("")) {
			holder.commentView.setVisibility(View.GONE);
		} else {
			holder.commentView.setVisibility(View.VISIBLE);
			holder.commentView.setText(bean.comment);
		}
		int howLong = (int) bean.distance;
		if (howLong != 0) {
			holder.longView.setText("距离" + howLong + "米");
			holder.longView.setVisibility(View.VISIBLE);
		} else
			holder.longView.setVisibility(View.GONE);
		if (bean.food_price == 0)
			holder.priceView.setVisibility(View.GONE);
		else {
			holder.priceView.setVisibility(View.VISIBLE);
			holder.priceView.setText("" + bean.food_price);
		}
		holder.foodnameView.setText(bean.food_name);
		if (bean.want_it_total != 0) {
			holder.heartView.setText("" + bean.want_it_total);
			holder.heartView.setVisibility(View.VISIBLE);
		} else
			holder.heartView.setVisibility(View.GONE);

		if (bean.pv_count != 0) {
			holder.eyeView.setText("" + bean.pv_count);
			holder.eyeView.setVisibility(View.VISIBLE);
		} else
			holder.eyeView.setVisibility(View.GONE);

		if (bean.comment_count != 0) {
			holder.comment_countView.setText("" + bean.comment_count);
			holder.comment_countView.setVisibility(View.VISIBLE);
		} else
			holder.comment_countView.setVisibility(View.GONE);
		holder.usernameView.setText(bean.user_name);
		holder.placeView.setText("@" + bean.place_name);
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
		viewHolder.longView = (TextView) convertView
				.findViewById(R.id.home_grid_item_long);
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
		viewHolder.phoneView = (TextView) convertView
				.findViewById(R.id.home_grid_item_bottom_from_phone);
		viewHolder.placeView = (TextView) convertView
				.findViewById(R.id.home_grid_item_bottom_place_name);
		convertView.setTag(viewHolder);
	}

	private class ViewHolder {
		TextView longView;
		ImageView headImageView;
		TextView commentView;
		TextView priceView;
		TextView foodnameView;
		TextView heartView;
		TextView eyeView;
		TextView comment_countView;
		ImageView bottomImageView;
		TextView usernameView;
		TextView phoneView;
		TextView placeView;
	}

}
