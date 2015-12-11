package com.project.meishixing.abs;

import java.util.List;

import com.project.meishixing.beans.HotFoodMoreBean;

public interface GetHotFoodActJsonListener {
	void getHotFoodJson(List<HotFoodMoreBean> list);
	void getErrorData(String error);
}
