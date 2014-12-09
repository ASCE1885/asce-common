package com.asce.common.notification;

import android.app.Notification;

/** 
 * Notification½Ó¿Ú 
 *  
 * @author gushizigege 
 * @date 2014-06-09 
 * 
 */
public interface IMyNotificationBuilder {
	
	public static final String NOTIFICATION_ID = IMyNotificationBuilder.class.getSimpleName();
	
	Notification buildNotification(String title, String content);

}
