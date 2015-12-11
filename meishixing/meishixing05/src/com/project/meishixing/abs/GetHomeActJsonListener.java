package com.project.meishixing.abs;

import java.util.ArrayList;

import com.project.meishixing.beans.HomeGridViewBean;

public interface GetHomeActJsonListener {
	void getHomeData(ArrayList<HomeGridViewBean> beans);
	void getErrorData(String error);
}
