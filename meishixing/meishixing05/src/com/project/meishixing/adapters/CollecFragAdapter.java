package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.db.DBbean;
import com.project.meishixing.db.OpenDBhelp;
import com.project.meishixing.net.HttpRequest;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CollecFragAdapter extends BaseAdapter {

	ArrayList<DBbean> datas = new ArrayList<DBbean>();
	private HttpRequest httpRequest;
	public boolean isChoiceMd;
	private Context context;

	public CollecFragAdapter(ArrayList<DBbean> datas, Context context) {
		this.datas = datas;
		httpRequest = HttpRequest.getInstance(context);
		this.context = context;
	}

	// 返回数据给frag
	public ArrayList<DBbean> getDatas() {
		return datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		ViewHolder holder;
		if (cView == null) {
			cView = View.inflate(parent.getContext(),
					R.layout.item_collec_list, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) cView
					.findViewById(R.id.iv_collec_item_image);
			holder.locateTv = (TextView) cView
					.findViewById(R.id.tv_colle_item_storloca);
			holder.storeNameTv = (TextView) cView
					.findViewById(R.id.tv_colle_item_storname);
			holder.checkBox = (CheckBox) cView
					.findViewById(R.id.cb_collec_item);
			cView.setTag(holder);
		}
		holder = (ViewHolder) cView.getTag();
		DBbean bean = datas.get(position);
		holder.locateTv.setText(bean.locate);
		holder.storeNameTv.setText(bean.storName);
		if (isChoiceMd) {
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.checkBox.setChecked(bean.isCheck);
		} else {
			holder.checkBox.setVisibility(View.GONE);
		}
		holder.imageView.setTag(bean.imageUrl);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.imageView,
				R.drawable.canting, R.drawable.canting);
		// 加载图片
		httpRequest.loadImage(bean.imageUrl, lis, 150, 150);
		return cView;
	}

	private class ViewHolder {
		ImageView imageView;
		TextView storeNameTv;
		TextView locateTv;
		CheckBox checkBox;
	}

	// 删除数据
	public void del(int flag) {
		ArrayList<DBbean> delData = new ArrayList<DBbean>();
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(context);
		for (DBbean data : datas) {
			if (data.isCheck) {
				delData.add(data);
				dBhelp.delDatas(data.urlID,flag);
			}
		}
		datas.removeAll(delData);
		notifyDataSetChanged();
	}

	// 全选数据
	public void choiceAll() {
		for (DBbean data : datas) {
			data.isCheck = true;
		}
		notifyDataSetChanged();
	}

	// 反选
	public void backChoice() {
		for (DBbean data : datas) {
			data.isCheck = !data.isCheck;
		}
		notifyDataSetChanged();
	}

	// 取消所有
	public void cancleAll() {
		for (DBbean data : datas) {
			data.isCheck = false;
		}
		notifyDataSetChanged();
	}

	//刷新数据
	public void setDatas(ArrayList<DBbean> datas2) {
		this.datas=datas2;
		notifyDataSetChanged();
	}
}
