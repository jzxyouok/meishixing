package com.project.meishixing.adapters;

import java.util.ArrayList;

import com.project.meishixing.R;
import com.project.meishixing.beans.NearRestListItemBean;
import com.project.meishixing.utils.NearResBG;
import com.project.meishixing.utils.SetStarBG;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NearRestListAdaprer extends BaseAdapter {

	private ArrayList<NearRestListItemBean> datas = new ArrayList<NearRestListItemBean>();

	public NearRestListAdaprer(ArrayList<NearRestListItemBean> datas) {
		this.datas=datas;
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
		if (cView==null) {
			cView =View.inflate(parent.getContext(), R.layout.item_nearrest_list, null);
			ViewHolder holder=new ViewHolder();
			holder.image=(ImageView) cView.findViewById(R.id.iv_nearrest_item_image);
			holder.star=(ImageView) cView.findViewById(R.id.iv_nearrest_item_star);
			holder.distance=(TextView) cView.findViewById(R.id.tv_nearrest_item_distance);
			holder.location=(TextView) cView.findViewById(R.id.tv_nearrest_item_location);
			holder.name=(TextView) cView.findViewById(R.id.tv_nearrest_item_name);
			holder.price=(TextView) cView.findViewById(R.id.tv_nearrest_item_price);
			cView.setTag(holder);
		}
		ViewHolder holder=(ViewHolder) cView.getTag();
		NearRestListItemBean bean = datas.get(position);
		int distance=(int) bean.distance;
		holder.distance.setText("距离"+distance+"米");
		holder.location.setText(bean.place_name);
		holder.name.setText(bean.category);
		holder.price.setText("人均:"+bean.price+"元");
		holder.star.setImageResource(SetStarBG.setStarBG(bean.star));
		holder.image.setImageResource(NearResBG.setStarBG(bean.category_id));
		return cView;
	}
	
	//添加数据
	public void addDatas(ArrayList<NearRestListItemBean> datas2) {
		this.datas=datas2;
		notifyDataSetChanged();
		System.out.println("notifyDataSetChanged");
	}

	//获得datas
	public ArrayList<NearRestListItemBean> getDatas(){
		return datas;
	}
	
	private class ViewHolder{
		ImageView image;
		ImageView star;
		TextView name;
		TextView distance;
		TextView location;
		TextView price;
	}
}
