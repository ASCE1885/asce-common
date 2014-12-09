package com.asce.common.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器管理类，负责定时器相关对象的创建和释放，提供统一的接口供调用者使用
 * 
 * @author gushizigege
 * @date 2013-10-17
 * 
 */
public class TimerHelper {

	private TimerProcessor mProcessor;

	private int mDelayMs;	// 单位：毫秒
	
	private long mPeriodMs;	// 单位：毫秒

	private Timer mTimer;

	private TimerTask mTimerTask;

	/**
	 * 构造函数
	 * 
	 * @param delayMs 延时
	 * @param processor 定时处理器，由调用者定制实现
	 */
	public TimerHelper(int delayMs, TimerProcessor processor) {
		mProcessor = processor;
		mDelayMs = delayMs;
	}
	
	public TimerHelper(int delayMs, int periodMs, TimerProcessor processor) {
		mProcessor = processor;
		mPeriodMs = periodMs;
		mDelayMs = delayMs;
	}
	
	/**
	 * 启动定时器
	 */
	public void startTimerPeriod() {
		mTimer = new Timer(this.getClass().getSimpleName()+"_process1",true);
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				if (mProcessor != null) {
					mProcessor.process();
				}
			}

		};
		mTimer.schedule(mTimerTask, mDelayMs, mPeriodMs);
	}

	/**
	 * 启动定时器
	 */
	public void startTimer() {
		mTimer = new Timer(this.getClass().getSimpleName()+"_process2", true);
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				if (mProcessor != null) {
					mProcessor.process();
				}
			}

		};
		mTimer.schedule(mTimerTask, mDelayMs);
	}

	/**
	 * 停止定时器
	 */
	public void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}

}
