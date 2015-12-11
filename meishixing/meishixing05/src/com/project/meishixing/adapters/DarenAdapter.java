package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.DarenpxBean;
import com.project.meishixing.net.HttpRequest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DarenAdapter extends BaseAdapter {
	private ArrayList<DarenpxBean> datas = new ArrayList<DarenpxBean>();
	private HttpRequest httpRequest;
	public DarenAdapter(HttpRequest httpRequest) {
		this.httpRequest=httpRequest;
	}
	@Override
	public int getCount() {
		if (datas.size() == 0) {
			return 0;
		}
		return datas.size() + 2;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		System.out.println("position=" + position);
		TextView textView;
		if (position == 0) {
			textView = new TextView(parent.getContext());
			textView.setText("广州达人排行榜");
			textView.setTextSize(20f);
			return textView;
		} else if (position == 11) {
			textView = new TextView(parent.getContext());
			textView.setText("全国达人排行榜");
			textView.setTextSize(20f);
			return textView;
		} else {
			if (cView == null || cView instanceof TextView) {
				cView = View.inflate(parent.getContext(),
						R.layout.item_daren_lv, null);
				ViewHolder holder = new ViewHolder();
				holder.collecTv = (TextView) cView
						.findViewById(R.id.tv_daren_item_collec);
				holder.nameTv = (TextView) cView
						.findViewById(R.id.tv_daren_item_name);
				holder.numbreTv = (TextView) cView
						.findViewById(R.id.tv_daren_item_poin);
				holder.shareTv = (TextView) cView
						.findViewById(R.id.tv_daren_item_shar);
				holder.iconIv = (ImageView) cView
						.findViewById(R.id.iv_daren_item);
				cView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) cView.getTag();
			DarenpxBean bean;
			if (position <= 10) {
				bean = datas.get(position - 1);
				holder.numbreTv.setText(position + "");
			} else {
				bean = datas.get(position - 2);
				holder.numbreTv.setText((position - 11) + "");
			}
			holder.nameTv.setText(bean.name);
			holder.shareTv.setText("共分享" + bean.reputation + "道美食");
			holder.collecTv.setText("共收获" + bean.tobeloved + "次喜欢");
			holder.iconIv.setTag(bean.user_image);
			// 获得图片加载回调接口
			ImageListener lis = ImageLoader.getImageListener(
					holder.iconIv, R.drawable.noresult_icon,
					R.drawable.noresult_icon);
			// 加载图片
			httpRequest.loadImage(bean.user_image, lis, 150, 150);
		}

		return cView;
	}

	private class ViewHolder {
		TextView numbreTv;
		TextView nameTv;
		TextView shareTv;
		TextView collecTv;
		ImageView iconIv;
	}

	public void setDatas(ArrayList<DarenpxBean> datas2) {
		this.datas=datas2;
		notifyDataSetChanged();
	}
}
