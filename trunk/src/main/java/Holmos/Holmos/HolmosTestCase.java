package Holmos.Holmos;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
/**
 * 继承Junit框架，作为Holmos框架测试用例执行入口
 * @author 吴银龙(15857164387)
 * */
public class HolmosTestCase extends BlockJUnit4ClassRunner{
	/**新建一个Holmos 框架的testRunner,用来运行给定的klass类里面的测试方法
	 * @param klass 待运行的测试类
	 * */
	public HolmosTestCase(Class<?> klass) throws InitializationError {
		super(klass);
	}
	/**
	 * 执行此HolmosTest的测试方法，调用此方法执行，实现了{@link Runner}类的抽象run方法
	 * @param notifier 负责将当前用例执行的状态通知junit
	 * */
	public void run(final RunNotifier notifier){
		
	}
	
}
