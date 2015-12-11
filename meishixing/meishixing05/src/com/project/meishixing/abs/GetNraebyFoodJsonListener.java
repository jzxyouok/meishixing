package com.project.meishixing.abs;

import java.util.List;

import com.project.meishixing.beans.NearbyFoodBean;

public interface GetNraebyFoodJsonListener {
	void getJsonData(List<NearbyFoodBean> beans);
	void getErrorData(String error);
}
