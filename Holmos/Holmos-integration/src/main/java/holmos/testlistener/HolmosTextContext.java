package holmos.testlistener;

import java.lang.reflect.Method;

/**
 * Holmos这个类里面存储当前运行case的详细信息，并且它知道当前case运行的阶段
 * 
 * @author 吴银龙(15857164387)
 * */
public class HolmosTextContext {
	/**当前执行的case所在类*/
	private Class<?> testClass;
	/**当前执行的case的方法*/
	private Method testMethod;
	/**当前执行的case的对象*/
	private Object testObject;
	public Class<?> getTestClass() {
		return testClass;
	}
	public void setTestClass(Class<?> testClass) {
		this.testClass = testClass;
	}
	public Method getTestMethod() {
		return testMethod;
	}
	public void setTestMethod(Method testMethod) {
		this.testMethod = testMethod;
	}
	public Object getTestObject() {
		return testObject;
	}
	public void setTestObject(Object testObject) {
		this.testObject = testObject;
	}
	
}
