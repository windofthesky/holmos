package holmos.android;

import holmos.android.utils.ThreadUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 完成所有Activity的管理工作，包括打开、控制权分配、销毁
 * @author 吴银龙(QQ:307087558)
 * */
public class ActivityAllocator {
	/**
	 * 单件管理的所有的Activity信息，在这里为弱引用类型<br>
	 * 加快垃圾清理，不必到了虚拟机内存不够用的时候再清理
	 * */
	private Stack<WeakReference<AndroidActivity>>activities=null;
	/**存储与activities栈对应的各个activity的toString()信息，两个栈一一对应*/
	private Stack<String>activityInfos;
	/**当前正在活动的Activity，即拥有焦点的Activity*/
	private AndroidActivity currentActivity=null;
	/**Activity监听器，监听新生成的Activity是否和预知的匹配；还有就是得到最新创建的Activity对象*/
	private ActivityMonitor activityMonitor=null;
	/**核心变量，这个变量会在app启动之前初始化，然后利用activityMonitor进行监听*/
	private final Instrumentation instrumentation=null;
	/**
	 * 获取所有的Activity列表，不管它活动与否
	 * */
	public Stack<WeakReference<AndroidActivity>> getActivities() {
		return activities;
	}
	/**
	 * 测试用到的所有的Activity都在这个类里进行管理,采用单件模式进行管理
	 * */
	private ActivityAllocator(){
		activities=new Stack<WeakReference<AndroidActivity>>();
		activityInfos=new Stack<String>();
	}
	
	/**独一无二的单件对象*/
	private static volatile ActivityAllocator allocator=null;
	
	/**
	 * 获取单件对象
	 * */
	public static ActivityAllocator getInstance(){
		if(null==allocator){
			synchronized (allocator) {
				allocator=new ActivityAllocator();
			}
		}return allocator;
	}
	
	/**
	 * 获取当前获得焦点的Activity，由于可能由于程序在运行的时候自动修改获得焦点的Activity<br>
	 * 那么需要对Activity堆栈进行监控，每次获得最近一个入栈的Activity，即当前获得焦点的元素<br>
	 * 由于这个Activity可能没有加载完成，需要等待其加载完成
	 * */
	public AndroidActivity getCurrentActivity(){
		//等待最近一个入栈的Activity加载
		waitForActivityBeAvailable();
		if(!activities.isEmpty()){
			currentActivity=activities.peek().get();
		}return currentActivity;
	}
	/**
	 * 获得当前用例执行过程中所有的活动Activity
	 * @return activies栈中所有活动的Activity信息
	 * */
	public ArrayList<AndroidActivity> getAllActiveActivities(){
		ArrayList<AndroidActivity>activeActivities=new ArrayList<AndroidActivity>();
		Iterator<WeakReference<AndroidActivity>>it=activities.iterator();
		while(it.hasNext()){
			AndroidActivity activity=it.next().get();
			if(activity.isActive())
				activeActivities.add(activity);
		}return activeActivities;
	}
	/**
	 * 获得当前正在执行用例所产生所有的Activity的描述信息
	 * */
	public ArrayList<String>getAllActiveActivityInfos(){
		ArrayList<String>activeActivityInfos=new ArrayList<String>();
		for(int i=0;i<activities.size();i++){
			if(activities.get(i).get().isActive()){
				activeActivityInfos.add(activityInfos.get(i));
			}
		}return activeActivityInfos;
	}
	/**
	 * 开启一个新的Activity
	 * */
	public void startActivity(){
	
//		instrumentation.startActivitySync(intent)
	}
	/**
	 * 如果Activity堆栈不空，代表当前还有Activity，那么这个函数的功能就是等待最近一次入栈的Activity<br>
	 * 加载成功，如果没有加载成功，则继续加载，程序轮询等待加载,每次等待时间默认是20ms<br>
	 * 从方法不可被继承
	 * */
	private final void waitForActivityBeAvailable() {
		if(activities.isEmpty()|| activities.peek().get()==null){
			//当现在的activity栈为空或者第一个activity被垃圾回收机制回收的时候，需要等
			//待从activityMonitor获取最后一次调用的activity
			if(null==activityMonitor){
				//当activityMonitor为null，代表监听器没有启动，需要启动监听器
				setUpActivityMonitor();
				waitForActivityBeAvailable();
				ThreadUtils.sleepMin();
			}else{
				Activity activity=activityMonitor.getLastActivity();
				while(null==activity){
					activity=activityMonitor.getLastActivity();
					ThreadUtils.sleepMin();
				}
				addActivity(activity);
			}
		}
	}
	/**
	 * 讲activity的信息添加入activities的栈里面，当然了，也要讲activity的名字添加到activityNames栈里面
	 * */
	private void addActivity(Activity activity) {
		AndroidActivity androidActivity=new AndroidActivity(activity);
		WeakReference<AndroidActivity>weakActivityReference=new WeakReference<AndroidActivity>(androidActivity);
		activityInfos.add(androidActivity.toString());
		activities.push(weakActivityReference);
	}
	/**
	 * 启动ActivityMonitor
	 * */
	private void setUpActivityMonitor() {
		IntentFilter intentFilter=null;
		try{
			activityMonitor=instrumentation.addMonitor(intentFilter, null, false);
		}catch (Exception e) {
			
		}
	}
	
	
}
