package com.project.meishixing.utils;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationHelp {
	public static Location getLatAndLng(Context context) {
		String locationProvider;
		// 获取地理位置管理器
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器
		List<String> providers = locationManager.getProviders(true);
		if (providers.contains(LocationManager.GPS_PROVIDER)) {
			// 如果是GPS
			locationProvider = LocationManager.GPS_PROVIDER;
		} else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
			// 如果是Network
			locationProvider = LocationManager.NETWORK_PROVIDER;
		} else {
			ToasUtils.showLToast(context, "没有可用位置");
			return null;
		}
		// 获取Location
		Location location = locationManager
				.getLastKnownLocation(locationProvider);
		if (location != null) {
			return location;
		}
		return null;
	}

}
