package holmos.android;

import android.app.Activity;

/**
 * 对一个Activity的封装类，holmos框架里面用到的Activity操作;<br>
 * 因为Activity的操作比较多，这里对一些常见的操作进行封装和完善
 * */
public class AndroidActivity {
	/**被封装的Activity对象，实际操作的实现者*/
	private Activity activity=null;
	public Activity getActivity() {
		return activity;
	}
	public AndroidActivity(Activity activity){
		this.activity=activity;
	}
	public String toString(){
		return this.activity.toString();
	}
	/**
	 * 此的Activity是否正在打开或者是否处于活动状态
	 * @return true 处于活动状态<br>
	 * 		   false 处于暂停状态或者已经结束状态
	 * */
	public boolean isActive(){
		if(null==activity){
			return false;
		}return true;
	}
}
