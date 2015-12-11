package com.project.meishixing.fragments;

import java.util.ArrayList;

import com.project.meishixing.R;
import com.project.meishixing.activitys.CollecActivity;
import com.project.meishixing.activitys.NearFoodShowActivity;
import com.project.meishixing.activitys.RestaDetailActivity;
import com.project.meishixing.adapters.CollecFragAdapter;
import com.project.meishixing.db.DBbean;
import com.project.meishixing.db.DBmessage;
import com.project.meishixing.db.OpenDBhelp;
import com.project.meishixing.utils.ToasUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class CollecFrag extends Fragment implements OnItemLongClickListener,
		OnClickListener, OnItemClickListener {
	private int flag;
	private View layout;
	public boolean isChoice;
	private CollecFragAdapter adapter;
	private CollecActivity activity;
	private OpenDBhelp dBhelp;
	private ArrayList<DBbean> datas;

	public CollecFrag(int flag) {
		this.flag = flag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		dBhelp = OpenDBhelp.getDBhelp(getActivity());
		datas = dBhelp.getDBDatas(DBmessage.TNAME, null, flag + "");
		if (datas == null) {
			datas = new ArrayList<DBbean>();
		}
		// 解析布局
		View view = View.inflate(getActivity(), R.layout.frag_collec, null);
		ListView listView = (ListView) view.findViewById(R.id.lv_collec_frg);
		layout = view.findViewById(R.id.layout_collec_frag);
		// 设置监听
		layout.findViewById(R.id.bt_collec_frg_backchoice).setOnClickListener(
				this);
		layout.findViewById(R.id.bt_collec_frg_choiceall).setOnClickListener(
				this);
		layout.findViewById(R.id.bt_collec_frg_del).setOnClickListener(this);
		layout.findViewById(R.id.bt_collec_frg_cancle).setOnClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setOnItemClickListener(this);
		adapter = new CollecFragAdapter(datas, getActivity());
		listView.setAdapter(adapter);
		activity = (CollecActivity) getActivity();
		return view;
	}

	// 长按,弹出选择框 跟删除按钮
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		setChoiceModer(true);
		return true;
	}

	// 设置选择模式
	public void setChoiceModer(boolean choiceModel) {
		isChoice = choiceModel;
		adapter.isChoiceMd = choiceModel;
		activity.isChoiceMd = choiceModel;
		if (choiceModel) {
			layout.setVisibility(View.VISIBLE);
		}else {
			layout.setVisibility(View.GONE);
			adapter.cancleAll();
			return;
		}
		adapter.notifyDataSetChanged();
	}

	// 点击按钮
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_collec_frg_backchoice:// 点击反选
			adapter.backChoice();
			break;
		case R.id.bt_collec_frg_choiceall:// 点击全选
			adapter.choiceAll();
			break;
		case R.id.bt_collec_frg_del:// 点击删除
			adapter.del(flag);
			break;
		case R.id.bt_collec_frg_cancle:// 点击取消
			setChoiceModer(false);
			adapter.cancleAll();
			break;
		}
	}

	// listView点击
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (isChoice) {
			System.out.println("adapter.size="+adapter.getDatas().size());
			System.out.println("onItemClick"+position);
			adapter.getDatas().get(position).isCheck = !adapter.getDatas().get(
					position).isCheck;
			adapter.notifyDataSetChanged();
		}else {
			int type=adapter.getDatas().get(position).type;
			String placeID=adapter.getDatas().get(position).urlID;
			Intent intent;
			if (type==1) {//跳转到餐厅详细
				intent=new Intent(getActivity(), RestaDetailActivity.class);
				intent.putExtra("placeID", Integer.parseInt(placeID));
				startActivity(intent);
			}else if (type==2) {//跳转到美食详细
				intent=new Intent(getActivity(), NearFoodShowActivity.class);
				intent.putExtra("tweet_id", Integer.parseInt(placeID));
				startActivity(intent);
			}
		}
	}

	public void setAdapterDatas() {
		datas = dBhelp.getDBDatas(DBmessage.TNAME, null, flag + "");
		if (datas == null) {
			datas = new ArrayList<DBbean>();
		}
		adapter.setDatas(datas);
	}
}
