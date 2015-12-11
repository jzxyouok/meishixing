package com.project.meishixing.db;

import android.provider.BaseColumns;

public class DBmessage {

	/**
	 * 代表收藏flag
	 */
	public static final int COLLECTED=2;
	/**
	 * 代表浏览记录
	 */
	public static final int LOOKDE=1;
	/**
	 * 表名
	 */
	public static final String TNAME = "collection";

	/**
	 *图片路径字段名
	 */
	public static final String IMAGEURL = "imageUrl";
	/**
	 *内容路径
	 */
	public static final String DETAILURL = "detailUrl";
	/**
	 * 店铺名 字段
	 */
	public static final String STORNAME = "storName";
	/**
	 * 位置 字段名
	 */
	public static final String LOCATE = "locate";


	/**
	 * flag=1 足迹 2=收藏
	 */
	public static final String FLAG = "flag";
	/**
	 * type=1 店铺 2=食物
	 */
	public static final String TYPE = "type";
	
	/**
	 * 建表语句
	 */
	public static final String CREAT_TABLE = "create table " + TNAME + " ("
			+ BaseColumns._ID + " integer primary key autoincrement, " + IMAGEURL
			+ " text, " + DETAILURL + " text, "+ STORNAME + " text, " + LOCATE + " text, " 
			+TYPE+" integer, "+FLAG+" integer)";

}
