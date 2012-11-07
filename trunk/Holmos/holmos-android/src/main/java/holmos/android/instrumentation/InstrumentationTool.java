package holmos.android.instrumentation;

import android.app.Instrumentation;

/**
 * 操控Instrumentation的工具类
 * @author 吴银龙(QQ:307087558)
 * */
public class InstrumentationTool {
	/**instrumentation 变量,一次测试只启动这一个*/
	private Instrumentation instrumentation;
	
	/**
	 * 将instrumentation注入到holmos框架中,在执行setUp的时候由test runner调用
	 * */
	public void injectInstrumentation(Instrumentation instrumentation){
		this.instrumentation=instrumentation;
	}
	
	public Instrumentation getInstrumentation(){
		return this.instrumentation;
	}
}
