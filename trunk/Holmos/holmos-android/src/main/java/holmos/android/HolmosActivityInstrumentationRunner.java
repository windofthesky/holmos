package holmos.android;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;
/**
 * Holmos Android测试的用例执行Runner，在InstrumentationTestRunner的基础上进行改写<br>
 * 此类继承于InstrumentationTestRunner，
 * 
 * 
 * 
 * 
 * 对于如下方法进行执行:
 * <li>(1)测试方法以"test"开头的</li>
 * <li>(2)测试方法带有@AndroidTest注解的</li>
 * <li>(3)测试方法必须是public类型的</li>
 * */
public class HolmosActivityInstrumentationRunner extends InstrumentationTestRunner{
	/**
	 * 在Instrumentation对象开启的时候调用这个方法，在此之前app还没有开始运行，调用{@link #start()}}
	 * 方法开启一个新的Instrumentation线程,然后执行{@link#onStart()}方法里面的执行内容用来执行用例
	 * */
	@Override
	public void onCreate(Bundle arguments){
		
	}
	/**
	 * 先开启一个新的进程，然后在此进程下面开启新的执行线程，防止app进程crash之后会造成整个测试工程也挂掉
	 * 开启一个新的Instrumentation线程,然后执行{@link#onStart()}方法里面的执行内容用来执行用例
	 * */
	@Override
	public void start(){
		
	}
	/**
	 * 这是Instrumentation线程执行入口，在Holmos框架里面是开启一个新的线程来执行Instrumentation，这个新线程
	 * 独立于app本身的执行线程，所以可以用Instrumentation执行像{@link #sendKeySync}和{@link #startActivitySync}.
	 * 等同步方法，如果不开启新的Instrumentation线程的话，那么这些方法可能会造成当前执行线程阻塞
	 * 
	 * <p>你可以通过调用finish方法当这个方法执行完成的时候，来终止Instrumentation线程
	 * */
	@Override
	public void onStart(){
		
	}
}
