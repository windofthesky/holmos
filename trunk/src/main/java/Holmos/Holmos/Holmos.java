package Holmos.Holmos;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import Holmos.Holmos.data.database.HolmosConfLoader;
import Holmos.Holmos.heart.HolmosModuleLoader;
import Holmos.Holmos.heart.HolmosModuleStore;
import Holmos.Holmos.heart.HolmosTextContext;
import Holmos.Holmos.testlistener.HolmosTestListener;
import Holmos.Holmos.testlistener.modules.HolmosModule;
import Holmos.Holmos.tools.HolmosBaseTools;

/**
 * Holmos框架的关键节点类;是Holmos框架的核心类，这个类是获取Holmos上下文内容的主要入口类;<br>
 * <p>
 * Holmos框架只有一个上下文，这个类也应用了单件模式，任何程序运行的时刻，只会存在一个此类对象<br>
 * <p>
 * 虽然用了单件模式，但是考虑到，用户可能会有自己配置的上下文，所以，将构造函数也暴漏在外边<br>
 * 以便可以设置自己的上下文，但是为了保持唯一性，给出setInstance接口，将用户自己构造的Holmos<br>
 * 对象代替系统自己构造的默认对象，当然，如果用户没有设置，系统会自动创建一个默认的对象<br>
 * <p>
 * 可能会有以下的需求：在每个用例执行的时候，需要对每个用例添加一些操作，所以在这里也实现<br>
 * {@link HolmosTestListener},可以添加必要的行为
 * @author 吴银龙(15857164387)
 * */
public class Holmos {
	private static Logger logger=Logger.getLogger(Holmos.class);
	{
		HolmosBaseTools.configLogProperties();
	}
	/**Holmos 单件模式的独一无二的对象*/
	private static Holmos holmos;
	/**
	 * 获得Holmos对象，即:holmos，如果holmos没有进行初始化，那么在这里进行初始化
	 * 
	 * @return Holmos 单件模式独一无二的对象
	 * */
	public static Holmos getInstance(){
		if(holmos==null){
			holmos=new Holmos();
			holmos.init();
		}return holmos;
	}
	/**
	 * 完成对Holmos的初始化工作
	 * */
	public void init(){
		HolmosConfLoader loader=new HolmosConfLoader();
		Properties properties=loader.loadConfiguration();
		this.properties=properties;
		moduleStore=createModuleStore(properties);
		testListener=new HolmosKeyListener();
		afterInitModules();
	}
	/**
	 * 执行Module仓库里面内所有的module的所有afterInit方法
	 * */
	private void afterInitModules() {
		ArrayList<HolmosModule>modules=moduleStore.getModules();
		for(HolmosModule module:modules){
			module.afterInit();
		}
	}
	/**
	 * 根据框架的配置文件新建Module仓库
	 * @param properties 配置文件
	 * @return 返回建好的Module仓库
	 * */
	private HolmosModuleStore createModuleStore(Properties properties) {
		HolmosModuleLoader moduleLoader=new HolmosModuleLoader();
		ArrayList<HolmosModule>modules=moduleLoader.loadModules(properties);
		return new HolmosModuleStore(modules);
	}
	/**
	 * 设置Holmos变量，不能为null,如果为null，将会采用系统默认的变量
	 * */
	public static void setInstance(Holmos holmos){
		if(holmos==null){
			logger.warn("您自己配置的Holmos实例为null，将继续用系统默认的实例!您可以检查您的配置，然后选择重启Holmos!");
		}
		Holmos.holmos=holmos;
	}
	
	
	
	/**属性配置*/
	private Properties properties;
	/**Case监听者*/
	private HolmosTestListener testListener;
	/**框架上下文*/
	private HolmosTextContext textContext;
	/**module仓库*/
	private HolmosModuleStore moduleStore;
	
	public Properties getProperties() {
		return properties;
	}
	public HolmosTestListener getTestListener() {
		return testListener;
	}
	public HolmosTextContext getTextContext() {
		return textContext;
	}
	public HolmosModuleStore getModuleStore() {
		return moduleStore;
	}

	
	public class HolmosKeyListener extends HolmosTestListener{
		@Override
		public void beforeTestClass(Class<?> testClass){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testClass);
			testContext.setTestObject(null);
			testContext.setTestMethod(null);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).beforeTestClass(testClass);
			}
		}
		@Override
		public void beforeTestSetUp(Class<?> testClass){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testClass);
			testContext.setTestObject(null);
			testContext.setTestMethod(null);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).beforeTestClass(testClass);
			}
		}
		@Override
		public void beforeTestMethod(Object testObject,Method testMethod){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testObject.getClass());
			testContext.setTestObject(testObject);
			testContext.setTestMethod(testMethod);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).beforeTestMethod(testObject, testMethod);
			}
		}
		@Override
		public void afterTestMethod(Object testObject,Method testMethod){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testObject.getClass());
			testContext.setTestObject(testObject);
			testContext.setTestMethod(testMethod);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).afterTestMethod(testObject, testMethod);
			}
		}
		@Override
		public void afterTestTearDown(Class<?> testClass){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testClass);
			testContext.setTestObject(null);
			testContext.setTestMethod(null);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).afterTestTearDown(testClass);
			}
		}
		@Override
		public void afterTestClass(Class<?> testClass){
			/*为了实现测试进度跟踪，获得TextContext，监控测试的每一个步骤*/
			HolmosTextContext testContext=getTextContext();
			testContext.setTestClass(testClass);
			testContext.setTestObject(null);
			testContext.setTestMethod(null);
			
			for(HolmosModule module:getModuleStore().getModules()){
				getModuleStore().getListener(module).afterTestClass(testClass);
			}
		}
	}
}
