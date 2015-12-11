package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.meishixing.R;
import com.project.meishixing.beans.HotFootListBean;
import com.project.meishixing.net.HttpRequest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotFoodHListViewAdapter extends BaseAdapter{
	private ArrayList<HotFootListBean> datas=new ArrayList<HotFootListBean>();
	private HttpRequest httpRequest;
	public HotFoodHListViewAdapter(Context context) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		//System.out.println("getView"+position);
		if (convertView==null) {
			convertView=View.inflate(parent.getContext(), R.layout.item_hotfood_hlist, null);
			ViewHolder holder=new ViewHolder();
			holder.imageV=(ImageView) convertView.findViewById(R.id.iv_hotfood_item);
			holder.commented=(TextView) convertView.findViewById(R.id.tv_hotfood_item_comment);
			holder.liked=(TextView) convertView.findViewById(R.id.tv_hotfood_item_liked);
			convertView.setTag(holder);
		}
		ViewHolder holder=(ViewHolder) convertView.getTag();
		HotFootListBean data=datas.get(position);
		holder.commented.setText(data.comment_count+"");
		holder.liked.setText(data.want_total+"");
		//绑定图片tag
		holder.imageV.setTag(data.picture_url);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.imageV,
				R.drawable.picture_load, R.drawable.picture_load);
		//加载图片
		httpRequest.loadImage(data.picture_url, lis, 160, 160);
		return convertView;
	}

	
	//添加数据
	public void setDatas(ArrayList<HotFootListBean> data_fir) {
		this.datas=data_fir;
		notifyDataSetChanged();
	}

	
	private class ViewHolder{
		ImageView imageV;
		TextView  liked;
		TextView  commented;
	}
}
