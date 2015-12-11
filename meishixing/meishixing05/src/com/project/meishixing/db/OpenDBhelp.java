package com.project.meishixing.db;

import java.util.ArrayList;
import java.util.Iterator;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 构建单例
 * 
 * @author Administrator
 * 
 */
public class OpenDBhelp extends SQLiteOpenHelper {

	private static OpenDBhelp openDBhelp = null;
	private final static String DBNAME = "datas.db";
	private final static int DBVERSION = 1;
	private static SQLiteDatabase db;

	private OpenDBhelp(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		db.execSQL(DBmessage.CREAT_TABLE);
		//System.out.println("创建表成功");
	}

	/**
	 * 升级数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * 获取dbhelp
	 * 
	 * @param context
	 * @return
	 */
	public static OpenDBhelp getDBhelp(Context context) {
		if (openDBhelp == null) {
			synchronized (OpenDBhelp.class) {
				if (openDBhelp == null) {
					openDBhelp = new OpenDBhelp(context);
				}
			}
		}
		return openDBhelp;
	}

	/**
	 * 
	 * @param table
	 *            需要查询的表名和
	 * @param imageUrl
	 *            需要查询的 imageUrl,flag,查询所有 填null
	 * @return
	 */
	public ArrayList<DBbean> getDBDatas(String table, String detailUrl,
			String flag) {
		db = openDBhelp.getWritableDatabase();
		Cursor cursor = null;
		if (detailUrl == null) {
			if (flag == null) {
				cursor = db.query(table, null, null, null, null, null, null);
			} else {
				cursor = db.query(table, null, " flag=? ", new String[] { ""
						+ flag }, null, null, null);
			}
		} else {
			if (flag == null) {
				cursor = db.query(table, null, " detailUrl=? ",
						new String[] { "" + detailUrl }, null, null, null);
			} else {
				cursor = db.query(table, null, " detailUrl=? and flag=?",
						new String[] { "" + detailUrl, flag }, null, null, null);
			}
		}

		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return null;
		}
		ArrayList<DBbean> dBbeans = new ArrayList<DBbean>();
		while (cursor.moveToNext()) {
			DBbean bean = new DBbean();
			bean.storName = cursor.getString(cursor
					.getColumnIndex(DBmessage.STORNAME));
			bean.locate = cursor.getString(cursor
					.getColumnIndex(DBmessage.LOCATE));
			bean.imageUrl = cursor.getString(cursor
					.getColumnIndex(DBmessage.IMAGEURL));
			bean.urlID = cursor.getString(cursor
					.getColumnIndex(DBmessage.DETAILURL));
			dBbeans.add(bean);
			bean.type = cursor.getInt(cursor
					.getColumnIndex(DBmessage.TYPE));
		}
		cursor.close();
		db.close();
		return dBbeans;
	}

	// 插入数据方法
	private void insert2DB(String table, ContentValues values) {
		db = openDBhelp.getWritableDatabase();
		db.insert(table, null, values);
		db.close();
	}

	/**
	 * 根据flag跟 url判断数据库是否存在此记录
	 * 
	 * @param url
	 *            图片的地址
	 * @param flag
	 *            收藏的标记
	 * @return
	 */
	public boolean isExistDatas(String detailUrl,int flag){
		ArrayList<DBbean> list = getDBDatas(DBmessage.TNAME, detailUrl, flag+"");
		if (list == null) {
			return false;
		}
		return true;
	}

	/**
	 * 删除数据
	 * 
	 * @param table
	 * @param url
	 *            图片url
	 * @param flag
	 *            标记类型
	 * @return 是否删除成功
	 */
	public boolean delDatas(String detailUrl,int flag) {
		db = openDBhelp.getWritableDatabase();
		int num = db.delete(DBmessage.TNAME, "detailUrl=? and flag=?",
				new String[] { detailUrl ,flag+""});
		if (num > 0) {
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	/**
	 * 资讯添加数据
	 * 
	 * @param url
	 * @param tittle
	 * @param summary
	 * @param flag 类型 1=足迹 2=收藏
	 *  @param tyep 类型 1=店铺2=美食
	 */
	public void inserData(String urlID,String imageUrl, String storName, String locate, int flag,int type) {
		boolean isExist = isExistDatas(urlID,flag);
		if (!isExist) {
			ContentValues values = new ContentValues();
			values.put(DBmessage.DETAILURL, urlID);
			values.put(DBmessage.IMAGEURL, imageUrl);
			values.put(DBmessage.STORNAME, storName);
			values.put(DBmessage.LOCATE, locate);
			values.put(DBmessage.FLAG, flag);
			values.put(DBmessage.TYPE, type);
			insert2DB(DBmessage.TNAME, values);
		}
	}
}
