package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.FoodMagaBean;
import com.project.meishixing.net.HttpRequest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodMagAdapter extends BaseAdapter {

	private ArrayList<FoodMagaBean> datas = new ArrayList<FoodMagaBean>();
	private HttpRequest httpRequest;

	public FoodMagAdapter(Context context) {
		httpRequest = HttpRequest.getInstance(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		if (cView == null) {
			cView = View.inflate(parent.getContext(),
					R.layout.item_foodmag_list, null);
			ViewHolder holder = new ViewHolder();
			holder.foodView_lef = (ImageView) cView
					.findViewById(R.id.iv_foodmaga_foot_lef);
			holder.userIcon_lef = (ImageView) cView
					.findViewById(R.id.iv_foodmaga_icon_lef);
			holder.foodView_rig = (ImageView) cView
					.findViewById(R.id.iv_foodmaga_foot_rig);
			holder.userIcon_rig = (ImageView) cView
					.findViewById(R.id.iv_foodmaga_icon_rig);
			holder.tittle_lef = (TextView) cView
					.findViewById(R.id.tv_foodmaga_tittle_lef);
			holder.tittle_rig = (TextView) cView
					.findViewById(R.id.tv_foodmaga_tittle_rig);
			holder.layou_lef = cView.findViewById(R.id.layout_foodmaga_lef);
			holder.layou_rig = cView.findViewById(R.id.layout_foodmaga_rig);
			holder.nameTv_rig = (TextView) cView
					.findViewById(R.id.tv_foodmaga_name_rig);
			holder.nameTv_lef = (TextView) cView
					.findViewById(R.id.tv_foodmaga_name_lef);
			holder.user_lef = (TextView) cView
					.findViewById(R.id.tv_foodmaga_uesename_lef);
			holder.user_rig = (TextView) cView
					.findViewById(R.id.tv_foodmaga_uesename_rig);
			cView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) cView.getTag();
		FoodMagaBean bean = datas.get(position);
		if (position % 2 == 0) {
			holder.layou_rig.setVisibility(View.GONE);
			holder.layou_lef.setVisibility(View.VISIBLE);
			System.out.println("tittile="+bean.title);
			holder.tittle_lef.setText(bean.title);
			holder.nameTv_lef.setText("轩号："+bean.serial_number);
			holder.user_lef.setText("主编:"+bean.user_name);
			// 绑定图片tag
			holder.foodView_lef.setTag(bean.picture_url);
			holder.userIcon_lef.setTag(bean.user_image);
			// 获得图片加载回调接口
			ImageListener lis = ImageLoader.getImageListener(holder.foodView_lef,
					R.drawable.picture_load, R.drawable.picture_load);
			// 加载图片
			httpRequest.loadImage(bean.picture_url, lis, 100, 100);
			// 获得图片加载回调接口
			ImageListener lis2 = ImageLoader.getImageListener(holder.userIcon_lef,
					R.drawable.icon, R.drawable.icon);
			// 加载图片
			httpRequest.loadImage(bean.user_image, lis2, 100, 100);
		} else {
			holder.layou_rig.setVisibility(View.VISIBLE);
			holder.layou_lef.setVisibility(View.GONE);
			holder.tittle_rig.setText(bean.title);
			holder.nameTv_rig.setText("轩号："+bean.serial_number);
			holder.user_rig.setText("主编:"+bean.user_name);
			
			// 绑定图片tag
			holder.foodView_rig.setTag(bean.picture_url);
			holder.userIcon_rig.setTag(bean.user_image);
			// 获得图片加载回调接口
			ImageListener lis = ImageLoader.getImageListener(holder.foodView_rig,
					R.drawable.picture_load, R.drawable.picture_load);
			// 加载图片
			httpRequest.loadImage(bean.picture_url, lis, 100, 100);
			// 获得图片加载回调接口
			ImageListener lis2 = ImageLoader.getImageListener(holder.userIcon_rig,
					R.drawable.icon, R.drawable.icon);
			// 加载图片
			httpRequest.loadImage(bean.user_image, lis2, 100, 100);
		}
		return cView;
	}

	private class ViewHolder {
		View layou_lef;
		ImageView userIcon_lef;
		ImageView foodView_lef;
		TextView nameTv_lef;
		TextView tittle_lef;
		TextView user_lef;

		View layou_rig;
		ImageView foodView_rig;
		ImageView userIcon_rig;
		TextView nameTv_rig;
		TextView tittle_rig;
		TextView user_rig;
	}

	// 设置数据
	public void setDatas(ArrayList<FoodMagaBean> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}
}
