package com.asce.common.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

/** 
 * Ä¬ÈÏNotification¹¹ÔìÆ÷ 
 *  
 * @author gushizigege
 * @date 2014-06-09 
 *  
 */
public class BasicNotificationBuilder implements IMyNotificationBuilder {

	private Context mContext;
	
	private PendingIntent mPendingIntent;
	
	public int mIconDrawableId;
	
	public BasicNotificationBuilder(Context context, PendingIntent pendingIntent, int iconDrawableId) {
		mContext = context;
		mPendingIntent = pendingIntent;
		mIconDrawableId = iconDrawableId;
	}
	
	@Override
	public Notification buildNotification(String title, String content) {
		Notification basicNotification = new Notification(mIconDrawableId, title, System.currentTimeMillis());
		basicNotification.setLatestEventInfo(mContext, title, content, mPendingIntent);
		basicNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		basicNotification.defaults = Notification.DEFAULT_SOUND;
		basicNotification.contentIntent = mPendingIntent;
		return basicNotification;
	}

}



















