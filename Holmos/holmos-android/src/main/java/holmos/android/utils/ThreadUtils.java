package holmos.android.utils;

import holmos.android.constvalue.AConstValue;

/**
 * 线程的一些基础工具类
 * @author 吴银龙(QQ:307087558)
 * */
public class ThreadUtils {
	/**
	 * 当前线程休眠框架定义的最小时间段——20ms
	 * */
	public static void sleepMin(){
		sleep(AConstValue.MINWAITTIME);
	}
	/**
	 * 框架休眠设定的millSeconds ms
	 * */
	public static void sleep(long millSeconds){
		try {
			Thread.sleep(millSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 框架默认的休眠时间——50ms
	 * */
	public static void sleep(){
		sleep(50);
	}
}
