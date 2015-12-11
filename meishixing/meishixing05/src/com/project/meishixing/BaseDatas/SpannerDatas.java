package com.project.meishixing.BaseDatas;

import java.util.ArrayList;

public class SpannerDatas {

	/**
	 * 获取餐馆附近选择框1数据
	 * 
	 * @return
	 */
	public static ArrayList<String> getSpnaner1() {
		ArrayList<String> datas = new ArrayList<String>();
		datas.clear();
		datas.add("周边1公里");
		datas.add("500米内");
		datas.add("周边2公里");
		datas.add("周边3公里");
		return datas;
	}

	/**
	 * 获取餐馆附近选择框2数据
	 * 
	 * @return
	 */
	public static ArrayList<String> getSpnaner2() {
		ArrayList<String> datas = new ArrayList<String>();
		datas.clear();
		datas.add("人均不限");
		datas.add("50元以下");
		datas.add("50-100元");
		datas.add("100-200元");
		return datas;
	}

	/**
	 * 获取餐馆附近选择框2数据
	 * 
	 * @return
	 */
	public static ArrayList<String> getSpnaner3() {
		ArrayList<String> datas = new ArrayList<String>();
		datas.clear();
		datas.add("全部菜系");
		datas.add("江浙菜");
		datas.add("川菜");
		datas.add("海鲜");
		return datas;
	}
}
