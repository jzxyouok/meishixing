package com.project.meishixing.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserFoodDataUtils {

	public static String data(String time){
	
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date da=df.parse(time);
			SimpleDateFormat format =new SimpleDateFormat("yyyy年MM月dd日 ");
			String t =format.format(da);
			System.out.println(t);
			return t;
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
	
		return  null;
	}

}
