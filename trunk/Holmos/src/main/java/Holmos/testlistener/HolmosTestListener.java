package Holmos.testlistener;

import java.lang.reflect.Method;

/**Holmos 框架测试监听器，根据这个监听器和HolmosModule两部分来实现Holmos框架的基础架构,一个监听器和一个Module来实现<br>
 * 一种测试的接入,框架通过添加注解的方法来添加不同的测试;这个Listener定义了添加一种测试方案的时候,需要调用这种测试方案<br>
 * 方法的时间点,框架定义的顺序如下:
 * <li>1:beforeTestClass:Holmos框架会通过读取配置的module依次遍历每个module的 TestListener并执行TestListener里的beforeTestClass方法,<br>
 * 目的是为了完成各种Module的在执行测试前定义的操作,这个是Holmos框架实现的</li>
 * <li>2:beforeClassTest:在一个测试类执行所有测试方法之前执行的这个测试类的公共操作,比如一些数据的初始化,数据库连接的初始化,这个是junit定义的</li>
 * <li>3:beforeTestSetUp:调用这个方法,会在setup方法之前调用这个方法,这个方法主要用扩展使用,用来添加新的功能;在每个测试方法执行前都会执行这个方法</li>
 * <li>4:testSetUp:junit已经定义好的方法,在所有测试方法之前执行的方法,每个测试方法执行之前,此方法都会被调用</li>
 * <li>5:beforeTestMethod:调用测试方法之前执行的方法,可以指定是哪个方法,即是针对特有的测试方法执行这个方法</li>
 * <li>6:testMethod:测试方法的执行方法</li>
 * <li>7:afterTestMethod:测试方法执行之后,执行这个方法,可以指定特定的测试方法来执行</li>
 * <li>8:testTearDown:junit已经定义好的方法,在所有测试方法之后执行的方法,每个测试方法执行之后,此方法都会被调用</li>
 * <li>9:afterTestTearDown:调用这个方法,会在tearDown方法之后调用这个方法,这个方法主要用扩展使用,用来添加新的功能;在每个测试方法执行后都会执行这个方法</li>
 * <li>10:.............其他的测试方法...............</li>
 * <li>11:testAfterClass:所有测试方法结束之后执行这个方法,对应于beforeClassTest,负责此测试类的测试资源的清理</li>
 * <li>12:afterTestClass:最后一个执行的方法,对应于beforeTestClass,调用这个方法会调用Holmos配置的所有module的TestListener里面的afterTestClassm进行数据的清理</li>
 * @author 吴银龙(15857164387)
 * */
public abstract class HolmosTestListener {
	/**
	 * beforeTestClass:Holmos框架会通过读取配置的module依次遍历每个module的 TestListener并执行TestListener<br>
	 * 里的beforeTestClass方法,目的是为了完成各种Module的在执行测试前定义的操作,这个是Holmos框架实现的
	 * @param testClass 待测试的测试类
	 * */
	public void beforeTestClass(Class<?> testClass){};
	/**在一个测试类执行所有测试方法之前执行的这个测试类的公共操作,比如一些数据的初始化,数据库连接的初始化,作为扩展方法
	 * @param testClass 待测试的测试类
	 * */
	public void beforeTestSetUp(Class<?> testClass){};
	/**调用测试方法之前执行的方法,可以指定是哪个方法,即是针对特有的测试方法执行这个方法
	 * @param testObject 待测试的测试类对象
	 * @param testMethod 待测试的测试方法
	 * */
	public void beforeTestMethod(Object testObject,Method testMethod){};
	/**
	 * 测试方法执行之后,执行这个方法,可以指定特定的测试方法来执行
	 * @param testObject 待测试的测试类对象
	 * @param testMethod 待测试的测试方法
	 * */
	public void afterTestMethod(Object testObject,Method testMethod){};
	/**
	 * 调用这个方法,会在tearDown方法之后调用这个方法,这个方法主要用扩展使用,用来添加新的功能;在每个测试方法执行后都会执行这个方法
	 * @param testClass 待测试的测试类
	 * */
	public void afterTestTearDown(Class<?> testClass){};
	/**最后一个执行的方法,对应于beforeTestClass,调用这个方法会调用Holmos配置的所有module的TestListener里面的afterTestClassm进行数据的清理
	 * @param testClass 待测试的测试类
	 * */
	public void afterTestClass(Class<?> testClass){};
}
