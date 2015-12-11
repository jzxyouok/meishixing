package com.project.meishixing.utils;

import android.content.Context;
import android.widget.Toast;

public class ToasUtils {

	private static Toast toast;

	public static void showLToast(Context c, String text) {
		if (toast == null) {
			toast=Toast.makeText(c, text, Toast.LENGTH_LONG);
		}else {
			toast.setText(text);
		}
		toast.show();
	}
}
