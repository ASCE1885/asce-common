package com.asce.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * package相关操作类
 * 
 * @author guhaoxin
 * @date 2014-03-03
 * 
 */
public class PackageUtil {

	/**
	 * 获取Manifest文件中的meta-data值
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	public static byte[] getPackageSignature(Context context) {
    	try {
			Signature[] sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
			byte[] data = sigs[0].toByteArray();
			return data;
		} catch (Exception e) {
			LogUtil.e("getPackageSignature", e.toString());
		}
    	
    	return null;
    }
	
	public static ApplicationInfo getAppInfo(Context context) {
		try {
			return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getAppName(Context context) {
		ApplicationInfo appInfo = getAppInfo(context);
		if (null != appInfo) {
			return context.getPackageManager().getApplicationLabel(appInfo).toString();
		}
		return null;
	}
}
