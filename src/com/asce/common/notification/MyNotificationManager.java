package com.asce.common.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Notification¹ÜÀíÆ÷
 * 
 * @author gushizigege
 * @date 2014-04-04
 * 
 */
public class MyNotificationManager {

	private IMyNotificationBuilder mPushNotificationBuilder;

	private static final int START_ID = 1000;
	private static final int RANGE = 50;
	private int mCurrentId = START_ID;

	private MyNotificationManager() {

	}

	private static class LazyHolder {
		private static final MyNotificationManager sInstance = new MyNotificationManager();
	}

	public static MyNotificationManager getInstance() {
		return LazyHolder.sInstance;
	}

	public IMyNotificationBuilder getPushNotificationBuilder() {
		return mPushNotificationBuilder;
	}

	public void setPushNotificationBuilder(IMyNotificationBuilder builder) {
		mPushNotificationBuilder = builder;
	}

	public void deliverPushNotification(Context context, String title, String content) {
		buildAndDisplayNotification(context, title, content);
	}

	private void buildAndDisplayNotification(Context context, String title, String content) {
		if (null != mPushNotificationBuilder) {
			Notification notification = mPushNotificationBuilder.buildNotification(title, content);
			if (null != notification) {
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				NotificationManager notificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				notificationManager.notify(generateNotification(), notification);
			}
		}
	}

	private int generateNotification() {
		mCurrentId++;
		if (mCurrentId >= START_ID + RANGE) {
			mCurrentId = START_ID;
		}
		return mCurrentId;
	}
}
