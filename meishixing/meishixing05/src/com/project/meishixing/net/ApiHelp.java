package com.project.meishixing.net;

public class ApiHelp {

	/**
	 * 从热门美食跳转到瀑布流
	 */
	public static final int FLAG_MOREHOTFOOD = 1000;
	/**
	 * 从美食杂志跳转到瀑布流
	 */
	public static final int FLAG_FOODMAG = 1001;
	/**
	 * 从附近美食跳转到瀑布流
	 */
	public static final int FLAG_NEARBYFOOD = 1002;

	/**
	 * 列表主机地址
	 */
	public static final String HOST = "http://api.meishixing.com";
	/**
	 * 价格position 0=不限,1=50以内,2=50-100,3=100-200
	 */
	public static final String[] PRICE = { "0~200000", "0~50", "50~100",
			"100~200" };

	/**
	 * 菜系 position 0=全部,1=江浙 2=川菜3=海鲜
	 */
	public static final String[] FOODNAME = { "", "江浙菜", "川菜", "海鲜" };

	/**
	 * 距离 position 0=1000,1=500,2=2000,3=3000;
	 */
	public static final int[] DISTANCE = { 1000, 500, 2000, 3000 };

	/**
	 * 返回附近餐馆api
	 * 
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 * @price 价格 在本类中有定义
	 * @foodName 菜系 本类中定义
	 * @distance 距离 本类中定义
	 * @return 主页地址
	 */
	public static String getNearRestURL(double lat, double lng, int page,
			int offset, int distance, String price, String foodName) {

		return HOST + "/search/nearby/recommandplace/lat=" + lat + "&lng="
				+ lng + "&page=" + page + "&correct_offset=" + offset
				+ "&distance=" + distance + "&price=" + price
				+ "&food_category_name=" + foodName;
	}

	/**
	 * 返回餐馆详细的api
	 * 
	 * @param placeID
	 *            餐馆id
	 * @return
	 */
	public static String getResDetailtURL(int placeID) {
		return HOST + "/place/place/placedetail/place_id=" + placeID
				+ "&session_id=00001341ab6d6dffcb90594b1fd65b69735542";
	}

	/**
	 * 获得热门食物的api
	 * 
	 * @param cityId
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static String getHotFoodURL(int cityId, double lat, double lng) {
		return HOST
				+ "/picture/picturelist/hot_food/session_id=00001341ab6d6dffcb90594b1fd65b69735542&city_id="
				+ cityId + "&lat=" + lat + "&lng=" + lng;
	}

	/**
	 * 获得查看更多的api
	 * 
	 * @param cityId
	 * @param lat
	 * @param lng
	 * @return
	 * @which 类型 本类有定义
	 */
	public static String getForMoreURL(String which, int cityId, double lat,
			double lng, int page) {
		return HOST + "/picture/picturelist/" + which + "/city_id=" + cityId
				+ "&session_id=00001341ab6d6dffcb90594b1fd65b69735542&lat="
				+ lat + "&lng=" + lng + "&page=" + page;
	}

	/**
	 * 表示热门美食查看更多本周最热类型
	 */
	public static final String WEEK_HOT = "best_week";
	/**
	 * 表示热门美食查看更多本月最热类型
	 */
	public static final String MOUNT_HOT = "best";
	/**
	 * 表示热门美食查看更多喜欢最多类型
	 */
	public static final String LIKE_MOST = "like_most";

	/**
	 * 获得美食杂志的api
	 * 
	 * @param cityId
	 *            城市编号
	 * @param page
	 *            当前页码
	 * @return
	 */
	public static String getFoodMagaURL(int cityId, int page) {
		return HOST + "/foodzine/weekly/list/city_id=" + cityId
				+ "&state=2&page=" + page;
	}

	/**
	 * 获得热门榜单API
	 * 
	 * @param cityId
	 *            城市编号
	 * @return
	 */
	public static String getHotFirURL(int cityId) {
		return HOST + "/other/top/first/city_id=" + cityId;
	}

	/**
	 * 获得达人排行榜api
	 * 
	 * @param cityId
	 * @return
	 */
	public static String getDaRenURL(int cityId) {
		return HOST
				+ "/user/user/top/session_id=00001341ab6d6dffcb90594b1fd65b69735542&city_id="
				+ cityId;
	}

	/**
	 * 获得热店api
	 * 
	 * @param cityId
	 * @return
	 */
	public static String getHotRestaURL(int cityId, double lat, double lng,
			int page) {
		return HOST + "/place/place/topviewweek/city_id=" + cityId + "&lat="
				+ lat + "&lng=" + lng + "&page=" + page;
	}

	/**
	 * 表示热门美食查看更多喜欢最多类型的主界面接口
	 */
	public static final String HOTFOODMOREMIDPATH = "/city_id=";
	public static final String HOTFOODMOREBEFORPATH = "http://api.meishixing.com/picture/picturelist/";
	public static final String HOTFOODMOREAFTERPATH = "&session_id=00001323516495c912386a7b2197414e8492be&lat=23.148691&lng=113.34753&page=";

	/**
	 * 表示美食杂志item详情数据
	 */
	public static final String getFoodMagDetailsPath(int foodzine_id, int page) {

		return "http://api.meishixing.com/foodzine/weekly/detail/session_id=00001323516495c912386a7b2197414e8492be&foodzine_id="
				+ foodzine_id + "&page=" + page;
	}

	/**
	 * 主界面的API接口
	 * */
	public static final String HOMEACTBASEPATH = "http://api.meishixing.com/picture/"
			+ "picturelist/last/city_id=5&session_id=00001323516495c912386a7b2197414e8492be&lat=2"
			+ "3.17293&lng=113.33828&page=";

	/**
	 * 表示附近美食主界面的API
	 */
	public static final String getNearbyFoodPath(int page) {

		return "http://api.meishixing.com/picture/picturelist/nearby/correct_offset=0&lat=23.148691&lng=113.34753&page="
				+ page;

	}
	/**
	 * 
	 * 美食详情
	 */
	public static final String NEAR_FOODURL_SHOW="http://api.meishixing.com/tweet/tweet/detail/session_id=000013a57af77aae2f294b6d76bd86be477c6a&tweet_id=";
	/**
	 * 
	 * 喜欢美食的人的列表
	 */

	public static String USER_FOOD_CHILD(int food_id,int place_id){
		return "http://api.meishixing.com/like/like/like_food_user_list/" +
				"session_id=000013c38253b961c71798e455309a87f1081c" +
				"&food_id="+food_id+
				"&place_id=" +place_id+
				"&page=";
		
	}
	/**
	 * 
	 * 使用这详情
	 * @param uid
	 * @return
	 */
	public static String USE_ID(int uid){
		return 
			"http://api.meishixing.com/user/user/userbaseinfo/uid="+uid+
				"&session_id=000013c38253b961c71798e455309a87f1081c";
		
	}
	
}
