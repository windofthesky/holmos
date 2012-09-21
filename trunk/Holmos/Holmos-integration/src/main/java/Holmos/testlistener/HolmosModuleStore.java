package holmos.testlistener;

import holmos.testlistener.modules.HolmosModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import holmos.webtest.basetools.HolmosReflectionTool;
import holmos.webtest.exceptions.HolmosFailedError;

/**
 * Holmos 框架的Module仓库
 * @author 吴银龙(15857164387)
 * */
public class HolmosModuleStore {
	/**框架里面配置的所有的测试模块*/
	private ArrayList<HolmosModule> allModules;
	/**测试模块与与其对应的Listener映射列表*/
	private Map<HolmosModule,HolmosTestListener> allListeners;
	
	public HolmosModuleStore(ArrayList<HolmosModule> modules) {
		this.allModules=modules;
		createListeners(modules);
	}
	private void createListeners(ArrayList<HolmosModule> modules) {
		Map<HolmosModule,HolmosTestListener>listeners=new HashMap<HolmosModule, HolmosTestListener>();
		for(HolmosModule module:modules){
			listeners.put(module, module.createListener());
		}allListeners=listeners;
	}
	/**
	 * 获取所有的modules
	 * */
	public ArrayList<HolmosModule> getModules() {
		return allModules;
	}
	/**
	 * 获取所有的listeners
	 * */
	public ArrayList<HolmosTestListener>getListeners(){
		return (ArrayList<HolmosTestListener>) allListeners.values();
	}
	/**
	 * 获取与module对应的Listener
	 * */
	public HolmosTestListener getListener(HolmosModule module){
		return allListeners.get(module);
	}
	/**
	 * 在allModules中检索类型为type或者为type子类型的Module，返回所有匹配的结果
	 * @param type 检索条件 type
	 * @return 检索到的所有module
	 * */
	public <T extends HolmosModule> ArrayList<T> getModulesOfType(Class<T> type){
		ArrayList<T>modulesOfType=new ArrayList<T>();
		for(HolmosModule module:allModules){
			if(type.isAssignableFrom(module.getClass())){
				modulesOfType.add((T) module);
			}
		}
		return modulesOfType;
	}
	/**
	 * 在allModules中检索类型为type或者为type子类型的Module，返回匹配的结果，如果不是一条结果，抛出异常
	 * @param type 检索条件 type
	 * @return 检索到的module
	 * */
	public <T extends HolmosModule> T getModuleOfType(Class<T>type){
		ArrayList<T>modulesOfType=new ArrayList<T>();
		modulesOfType=getModulesOfType(type);
		if(modulesOfType.size()!=1){
			throw new HolmosFailedError("检索到的"+type.getName()+"类型的module结果不是一条!");
		}return modulesOfType.get(0);
	}
	/**
	 * 判断框架当前是否正在用全类名为 fullClassName的Module，只能有一个Module，如果多个的话，抛出异常
	 * 
	 * @param fullClassName 全类名
	 * @return true 在使用 <br>false 没在使用
	 * */
	public boolean isModuleOnUse(String fullClassName){
		Class<? extends HolmosModule>moduleClass;
		try{
			moduleClass=(Class<? extends HolmosModule>) HolmosReflectionTool.createInstanceAsType(fullClassName, false);
		}catch (Exception e) {
			return false;
		}
		return getModulesOfType(moduleClass).size()==1;
	}
}
