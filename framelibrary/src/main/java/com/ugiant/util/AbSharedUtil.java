package com.ugiant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ugiant.global.AbAppConfig;

//TODO: Auto-generated Javadoc

/**
* © 2012 amsoft.cn
* 名称：AbSharedUtil.java 
* 描述：保存到 SharedPreferences 的数据.    
*
* @author cgd
 * @date 2016-01-11
*/
public class AbSharedUtil {

	private static final String SHARED_PATH = AbAppConfig.SHARED_PATH;
	private static SharedPreferences sharedPreferences;

	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		if(sharedPreferences==null){
			sharedPreferences = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
		}
		return sharedPreferences;
	}
	
	public static void putInt(Context context,String key, int value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public static int getInt(Context context,String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, 0);
	}
	
	public static void putString(Context context,String key, String value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static String getString(Context context,String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key,null);
	}
	
	public static void putBoolean(Context context,String key, boolean value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	public static boolean getBoolean(Context context,String key,boolean defValue) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key,defValue);
	}
	
	public static void remove(Context context,String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.remove(key);
		edit.commit();
	}
	
	/**
	 * 显示分享框
	 * @param context 上下文
	 * @param title   标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用  例如:"分享标题"
	 * @param titleUrl  标题的网络链接，仅在人人网和QQ空间使用     例如:"http://sharesdk.cn"
	 * @param text  分享文本，所有平台都需要这个字段     例如:"我是分享文本"
	 * @param ImageUrl  图片的路径，Linked-In以外的平台都支持此参数   例如:http://www.dzhome01.com/resources/index/images/logo.png
	 * @param url  仅在微信（包括好友和朋友圈）中使用    例如:"http://sharesdk.cn"
	 * @param comment  我对这条分享的评论，仅在人人网和QQ空间使用    例如:"我是测试评论文本"
	 * @param site   分享此内容的网站名称，仅在QQ空间使用     例如:"QQ空间"
	 * @param siteUrl   分享此内容的网站地址，仅在QQ空间使用    例如:"http://sharesdk.cn"
	 */
	public static void showShare(Context context,String title,String titleUrl,String text,String ImageUrl,String url,
			String comment,String site,String siteUrl) {
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize(); 
		/*// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("分享标题");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("QQ空间");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");*/
		
		
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(titleUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImageUrl(ImageUrl);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(comment);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(site);
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(siteUrl);

		// 启动分享GUI
		oks.show(context);
	}

}
