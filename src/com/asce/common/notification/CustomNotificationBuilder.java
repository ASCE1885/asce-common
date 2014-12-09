package com.asce.common.notification;

import com.asce.common.util.PackageUtil;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * 自定义样式Notification构造器
 * 
 * @author gushizigege
 * @date 2014-06-09
 * 
 */
public class CustomNotificationBuilder implements IMyNotificationBuilder {

	private Context mContext;
	private int mCustomeLayout;
	private int mLayoutSubjectId;
	private int mLayoutMessageId;
	private int mLayoutIconId;
	private int mStatusBarIconDrawableId;
	private Uri mSoundUri;
	private PendingIntent mPendingIntent;

	public CustomNotificationBuilder(Context context, int customeLayout,
			int layoutSubjectId, int layoutMessageId, int layoutIconId,
			int statusBarIconDrawableId, Uri soundUri,
			PendingIntent pendingIntent) {
		mContext = context;
		mCustomeLayout = customeLayout;
		mLayoutSubjectId = layoutSubjectId;
		mLayoutMessageId = layoutMessageId;
		mLayoutIconId = layoutIconId;
		mStatusBarIconDrawableId = statusBarIconDrawableId;
		mSoundUri = soundUri;
		mPendingIntent = pendingIntent;
	}

	@Override
	public Notification buildNotification(String title, String content) {
		if (TextUtils.isEmpty(content)) {
			return null;
		}

		PreFroyoNotificationStyleDiscover.getInstance().discoverStyle(mContext);

		Notification customerNotification = new Notification(
				mStatusBarIconDrawableId, title, System.currentTimeMillis());
		RemoteViews customerRemoteView = new RemoteViews(
				mContext.getPackageName(), mCustomeLayout);
		customerRemoteView.setTextViewText(mLayoutSubjectId,
				PackageUtil.getAppName(mContext));
		customerRemoteView.setTextViewText(mLayoutMessageId, content);
		customerRemoteView.setImageViewResource(mLayoutIconId,
				mStatusBarIconDrawableId);
		customerNotification.flags |= Notification.FLAG_AUTO_CANCEL;

		if (mSoundUri != null) {
			customerNotification.sound = mSoundUri;
		} else {
			customerNotification.defaults = Notification.DEFAULT_SOUND;
		}
		customerNotification.contentIntent = mPendingIntent;

		// Some SAMSUNG devices status bar cant't show two lines with the size,
		// so need to verify it, maybe increase the height or decrease the font
		// size

		customerRemoteView.setFloat(mLayoutSubjectId, "setTextSize",
				PreFroyoNotificationStyleDiscover.getInstance().getTitleSize());
		customerRemoteView
				.setTextColor(mLayoutSubjectId,
						PreFroyoNotificationStyleDiscover.getInstance()
								.getTitleColor());

		customerRemoteView.setFloat(mLayoutMessageId, "setTextSize",
				PreFroyoNotificationStyleDiscover.getInstance().getTextSize());
		customerRemoteView.setTextColor(mLayoutMessageId,
				PreFroyoNotificationStyleDiscover.getInstance().getTextColor());

		customerNotification.contentView = customerRemoteView;

		return customerNotification;
	}

	/**
	 * A class for discovering Android Notification styles on Pre-Froyo (2.3)
	 * devices
	 */
	private static class PreFroyoNotificationStyleDiscover {

		private Integer mNotifyTextColor = null;
		private float mNotifyTextSize = 11;
		private Integer mNotifyTitleColor = null;
		private float mNotifyTitleSize = 12;
		private final String TEXT_SEARCH_TEXT = "SearchForText";
		private final String TEXT_SEARCH_TITLE = "SearchForTitle";

		private static Context mContext;

		private static class LazyHolder {
			private static final PreFroyoNotificationStyleDiscover sInstance = new PreFroyoNotificationStyleDiscover();
		}

		public static PreFroyoNotificationStyleDiscover getInstance() {
			return LazyHolder.sInstance;
		}

		private PreFroyoNotificationStyleDiscover() {

		}

		public int getTextColor() {
			return mNotifyTextColor.intValue();
		}

		public float getTextSize() {
			return mNotifyTextSize;
		}

		public int getTitleColor() {
			return mNotifyTitleColor;
		}

		public float getTitleSize() {
			return mNotifyTitleSize;
		}

		private void discoverStyle(Context context) {
			mContext = context;
			// Already done
			if (null != mNotifyTextColor) {
				return;
			}

			try {
				Notification notify = new Notification();
				notify.setLatestEventInfo(mContext, TEXT_SEARCH_TITLE,
						TEXT_SEARCH_TEXT, null);
				LinearLayout group = new LinearLayout(mContext);
				ViewGroup event = (ViewGroup) notify.contentView.apply(
						mContext, group);
				recurseGroup(event);
				group.removeAllViews();
			} catch (Exception e) {
				// Default to something
				mNotifyTextColor = android.R.color.black;
				mNotifyTitleColor = android.R.color.black;
			}
		}

		private boolean recurseGroup(ViewGroup group) {
			final int count = group.getChildCount();

			for (int i = 0; i < count; ++i) {
				if (group.getChildAt(i) instanceof TextView) {
					final TextView tv = (TextView) group.getChildAt(i);
					final String text = tv.getText().toString();
					if (text.startsWith("SearchFor")) {
						DisplayMetrics metrics = new DisplayMetrics();
						WindowManager wm = (WindowManager) mContext
								.getSystemService(Context.WINDOW_SERVICE);
						wm.getDefaultDisplay().getMetrics(metrics);

						if (TEXT_SEARCH_TEXT == text) {
							mNotifyTextColor = tv.getTextColors()
									.getDefaultColor();
							mNotifyTextSize = tv.getTextSize();
							mNotifyTextSize /= metrics.scaledDensity;
						} else {
							mNotifyTitleColor = tv.getTextColors()
									.getDefaultColor();
							mNotifyTitleSize = tv.getTextSize();
							mNotifyTitleSize /= metrics.scaledDensity;
						}

						if (null != mNotifyTitleColor
								&& mNotifyTextColor != null) {
							return true;
						}
					}
				} else if (group.getChildAt(i) instanceof ViewGroup) {
					if (recurseGroup((ViewGroup) group.getChildAt(i))) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
