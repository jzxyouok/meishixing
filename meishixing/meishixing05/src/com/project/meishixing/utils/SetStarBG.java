package com.project.meishixing.utils;

import com.project.meishixing.R;

public class SetStarBG {

	public static int setStarBG(String starNum){
		if (starNum.equals("0")) {
			return R.drawable.star_0_0;
		}else if (starNum.equals("0.5")) {
			return R.drawable.star_0_5;
		}else if (starNum.equals("1")) {
			return R.drawable.star_1_0;
		}else if (starNum.equals("1.5")) {
			return R.drawable.star_1_5;
		}else if (starNum.equals("2")) {
			return R.drawable.star_2_0;
		}else if (starNum.equals("2.5")) {
			return R.drawable.star_2_5;
		}else if (starNum.equals("3")) {
			return R.drawable.star_3_0;
		}else if (starNum.equals("3.5")) {
			return R.drawable.star_3_5;
		}else if (starNum.equals("4")) {
			return R.drawable.star_4_0;
		}else if (starNum.equals("4.5")) {
			return R.drawable.star_4_5;
		}else if (starNum.equals("5")) {
			return R.drawable.star_5_0;
		}
		return 0;
		
	}
}
