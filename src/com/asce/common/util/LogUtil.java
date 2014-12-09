package com.asce.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * ��־������,�ṩ��ӡ�кſ��أ���ӡ���ļ�����f()
 * 
 * @author gushizigege
 * @date 2013-8-28
 */
public class LogUtil {
	
	private final static String DEFAULT_TAG = "BaiduTaxiDriver";

	/**
	 * �˳������ڿ����Ƿ����־��Logcat�� release�汾�б�����Ӧ��Ϊfalse
	 */
	private final static boolean LOGGABLE = true;
	
	/**
	 * ��ӡerror�����log
	 * 
	 * @param moduleType
	 *            moduleType ģ������
	 * @param str
	 *            ����
	 */
	public static void e(String moduleName, String str) {
		if (LOGGABLE) {
			StackTraceElement ste = new Throwable().getStackTrace()[1];
			Log.e(DEFAULT_TAG, makeLogDetailInfoString(moduleName, str, ste));
		}
	}

	/**
	 * ����־��ӡ���ļ���
	 * 
	 * @param moduleType
	 *            moduleType ģ������
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
	 * ������logλ�õ��ļ������ļ��к���ϸ��Ϣ
	 * 
	 * @param moduleType
	 *            ģ������
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
