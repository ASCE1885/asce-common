package com.asce.common.util;

/**
 * 抽象类，定义定时执行的接口函数
 * 
 * @author gushizigege
 * @date 2013-10-17
 * 
 */
public abstract class TimerProcessor {
	/**
	 * 在TimerTask中执行的操作，由调用者定制
	 */
	public abstract void process();
}
