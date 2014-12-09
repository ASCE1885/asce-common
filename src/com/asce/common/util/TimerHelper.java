package com.asce.common.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ�������࣬����ʱ����ض���Ĵ������ͷţ��ṩͳһ�Ľӿڹ�������ʹ��
 * 
 * @author gushizigege
 * @date 2013-10-17
 * 
 */
public class TimerHelper {

	private TimerProcessor mProcessor;

	private int mDelayMs;	// ��λ������
	
	private long mPeriodMs;	// ��λ������

	private Timer mTimer;

	private TimerTask mTimerTask;

	/**
	 * ���캯��
	 * 
	 * @param delayMs ��ʱ
	 * @param processor ��ʱ���������ɵ����߶���ʵ��
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
	 * ������ʱ��
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
	 * ������ʱ��
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
	 * ֹͣ��ʱ��
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
