package holmos.testlistener.modules;

import holmos.testlistener.HolmosTestListener;

import java.util.Properties;


/**
 * 监听器{@link HolmosTestListener}定义了Holmos框架执行的各个阶段，监听器的实现说明了在各个阶段的执行序列<br>
 * HolmosModule提供了这些执行序列的实现，这么做主要是为了方便大家扩展，扩展方法如下:
 * <li>1:添加新的HolmosTestListener监听器对象 xxx_new_Listener extends HolmosTestListener,定义好在需要监听到的执行阶段</li>
 * <li>2:在xxx_new_Listener 里面定义好的阶段里面写好要完成的操作</li>
 * <li>3:定于与xxx_new_Listener匹配的xxx_new_module，在此文件里面实现所要完成的操作</li>
 * <br><br><b>以上就是Holmos框架的扩展方法，灵巧方便</b>
 * @author 吴银龙(15857164387)
 * */
public interface HolmosModule {
	
	/**
	 * 根据properties文件初始化HolmosModule，告诉框架使用哪些监听器，执行哪些测试；<br>
	 * 如果需要实现的监听器添加，那么完成一些必须的初始化工作
	 * @param properties 给定的初始化配置文件
	 * */
	public void init(Properties properties);
	
	/**
	 * 用户在使用的时候，在所有的Module的init()方法都执行过后，可以在这里添加一些额外的初始化工作 
	 * */
	public void afterInit();
	
	/**
	 * 创建与此HolmosModule对应的Listener对象,Module提供了对Listener的实现
	 */
	public HolmosTestListener createListener();
}
