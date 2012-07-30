package Holmos.Holmos.data.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Holmos.Holmos.exceptions.HolmosFailedError;
import Holmos.Holmos.testlistener.HolmosTestListener;
import Holmos.Holmos.testlistener.modules.HolmosModule;
import Holmos.Holmos.tools.HolmosReflectionTool;

/**Holmos框架的DBModule管理者,这个管理者管理若干个DBModule,提供存储,检索等功能
 * @author 吴银龙(15857164387)
 * */
public class HolmosDBModuleManager {
	/*此管理者管理的module*/
	private List<HolmosModule>modules;
	/**
	 * @return modules
	 * */
	public List<HolmosModule> getModules() {
		return modules;
	}
	/**
	 * @return listeners
	 * */
	public Map<HolmosModule, HolmosTestListener> getListeners() {
		return listeners;
	}
	/*存储对应module的testListener对象*/
	private Map<HolmosModule,HolmosTestListener>listeners;
	
	public HolmosDBModuleManager(List<HolmosModule>modules){
		this.modules=modules;
		this.listeners=createModuleListeners(modules);
	}
	/**根据给定的modules来创建与每个module对应的HolmosTestListener
	 * @param modules 给定的module集合
	 * @return 创建的Map
	 * */
	private Map<HolmosModule, HolmosTestListener> createModuleListeners(
			List<HolmosModule> modules) {
		Map<HolmosModule,HolmosTestListener>result=new HashMap<HolmosModule, HolmosTestListener>();
		for(HolmosModule module:modules){
			result.put(module, module.createListener());
		}
		return result;
	}
	/**获得this.modules里所有的类型为type的module对象,和type的所有子类对象,但是增加限制,如果最后得到的对象不唯一的话，抛出异常
	 * @param type 给定的module类型
	 * @return this.modules里所有的类型为type的module对象,和type的所有子类对象
	 * */
	public <T extends HolmosModule> T getModuleAsType(Class<T>type){
		List<T>result=(List<T>) getModulesAsType(type);
		if(result.size()>1){
			throw new HolmosFailedError("modules仓库里面的类型为type和是type子类的module的数量超过1!");
		}
		else if(result.size()==0){
			throw new HolmosFailedError("modules仓库里面没有类型为type和是type子类的module的module!");
		}
		return result.get(0);
	}
	/**获得this.modules里所有的类型为type的module对象,和type的所有子类对象
	 * @param type 给定的module类型
	 * @return this.modules里所有的类型为type的module对象,和type的所有子类对象
	 * */
	@SuppressWarnings("unchecked")
	public <T> List<T> getModulesAsType(Class<T>type){
		List<T>result=new ArrayList<T>();
		for(HolmosModule module:this.modules){
			if(type.isAssignableFrom(module.getClass())){
				result.add((T) module);
			}
		}
		return result;
	}
	/**判断指定类型的module在此管理仓库里面是否存在，但是还是不能超过1
	 * @param type 给定的module类型
	 * @return true 存在且仅有一个<br> false 不存在
	 * @throws HolmosFailedError 存在但不是一个
	 * */
	public boolean isModuleExist(Class<? extends HolmosModule>type){
		@SuppressWarnings("unchecked")
		List<HolmosModule> result=(List<HolmosModule>) getModulesAsType(type);
		if(result.size()>1){
			throw new HolmosFailedError("不存在指定"+type.getName()+"类型的module!");
		}return result.size()==1;
	}
	/**根据给定的类名的module在此管理仓库里面是否存在，但是还是不能超过1
	 * @param typeName 给定的module类型名称
	 * @return true 存在且仅有一个<br> false 不存在
	 * @throws HolmosFailedError 存在但不是一个
	 * */
	public boolean isModuleExist(String typeName){
		Class<? extends HolmosModule> type=HolmosReflectionTool.createInstanceAsType(typeName, false);
		return isModuleExist(type);
	}
	/**根据指定的key-> module来获取相应的testListener
	 * @param module 给定的key
	 * @return 获取的value
	 * */
	public HolmosTestListener getTestListener(HolmosModule module){
		return this.listeners.get(module);
	}
}
