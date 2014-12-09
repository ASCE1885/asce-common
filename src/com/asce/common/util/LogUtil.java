package com.asce.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * 日志工具类,提供打印行号开关，打印到文件函数f()
 * 
 * @author gushizigege
 * @date 2013-8-28
 */
public class LogUtil {
	
	private final static String DEFAULT_TAG = "BaiduTaxiDriver";

	/**
	 * 此常量用于控制是否打日志到Logcat中 release版本中本变量应置为false
	 */
	private final static boolean LOGGABLE = true;
	
	/**
	 * 打印error级别的log
	 * 
	 * @param moduleType
	 *            moduleType 模块名称
	 * @param str
	 *            内容
	 */
	public static void e(String moduleName, String str) {
		if (LOGGABLE) {
			StackTraceElement ste = new Throwable().getStackTrace()[1];
			Log.e(DEFAULT_TAG, makeLogDetailInfoString(moduleName, str, ste));
		}
	}

	/**
	 * 将日志打印到文件中
	 * 
	 * @param moduleType
	 *            moduleType 模块名称
	 * @param str
	 */
	public static void f(String moduleName, String str) {
		String sLogFilePath = Environment.getExternalStorageDirectory()
				.toString() + "/BaiduNaviLog.txt";
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
		String date = sDateFormat.format(new java.util.Date());
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		String strLog = date + " "
				+ makeLogDetailInfoString(moduleName, str, ste) + "\r\n";
		FileWriter writer;
		try {
			writer = new FileWriter(sLogFilePath, true);
			writer.write(strLog);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LogUtil.e("", e.toString());
		}
	}

	/**
	 * 制作打log位置的文件名与文件行号详细信息
	 * 
	 * @param moduleType
	 *            模块类型
	 * @param str
	 * @param bSrcInfo
	 * @return
	 */
	private static String makeLogDetailInfoString(String moduleName,
			String str, StackTraceElement ste) {
		String strLog = "[" + moduleName + "]-" + ste.getFileName() + "("
				+ ste.getLineNumber() + "): ";
		strLog += str;
		return strLog;
	}

}
