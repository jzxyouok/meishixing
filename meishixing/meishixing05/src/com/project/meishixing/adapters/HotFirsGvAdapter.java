package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.HotFirBean;
import com.project.meishixing.net.HttpRequest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotFirsGvAdapter extends BaseAdapter {

	private ArrayList<HotFirBean> datas = new ArrayList<HotFirBean>();
	private HttpRequest httpRequest;

	public HotFirsGvAdapter(Context context) {
		httpRequest = HttpRequest.getInstance(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(),
					R.layout.item_hotfir_gv, null);
			ViewHolder holder = new ViewHolder();
			holder.photoIV = (ImageView) convertView
					.findViewById(R.id.iv_hotfir_item);
			holder.icIV = (ImageView) convertView
					.findViewById(R.id.iv_hotfir_item_ic);
			holder.type = (TextView) convertView
					.findViewById(R.id.tv_hotfir_item);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		HotFirBean bean = datas.get(position);
		holder.type.setText(bean.type_desc);

		holder.photoIV.setTag(bean.topic_cover);
		holder.icIV.setTag(bean.icon);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.photoIV,
				R.drawable.picture_load, R.drawable.picture_load);
		// 加载图片
		httpRequest.loadImage(bean.topic_cover, lis, 150, 150);
		// 获得图片加载回调接口
		ImageListener lis1 = ImageLoader.getImageListener(holder.icIV,
				R.drawable.picture_load, R.drawable.picture_load);
		// 加载图片
		httpRequest.loadImage(bean.icon, lis1, 0, 0);
		return convertView;
	}

	public void setDatas(ArrayList<HotFirBean> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	private class ViewHolder {
		ImageView photoIV;
		ImageView icIV;
		TextView type;
	}
}
