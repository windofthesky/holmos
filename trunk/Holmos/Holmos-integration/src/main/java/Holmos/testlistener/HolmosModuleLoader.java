package holmos.testlistener;

import holmos.testlistener.modules.HolmosModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.basetools.HolmosReflectionTool;
import Holmos.webtest.constvalue.ConfigConstValue;
import Holmos.webtest.exceptions.HolmosFailedError;

/**
 * Holmos 框架Module读取器
 * @author 吴银龙(15857164387)
 * */
public class HolmosModuleLoader {
	private static Logger logger=Logger.getLogger(HolmosModuleLoader.class);
	public ArrayList<HolmosModule> loadModules(Properties properties) {
		Set<String> moduleNames=new HashSet<String>(HolmosPropertiesTool.getValueList(properties, ConfigConstValue.HOLMOS_MODULES));
		removeDisableModules(moduleNames,properties);
		ArrayList<HolmosModule>modules=loadAndInitModules(moduleNames, properties);
		return modules;
	}
	/**
	 * 删除所有配置文件里面被无效化的测试module类别
	 * 
	 * @param moduleNames 配置文件里面写的所有的module的名字
	 * @param properties 配置文件信息
	 * */
	private void removeDisableModules(Set<String> moduleNames,
			Properties properties) {
		for(String moduleName:moduleNames){
			if(HolmosPropertiesTool.isPropertyInFile(properties, ConfigConstValue.HOLMOS_MODULE+"."+moduleName+ConfigConstValue.ENABLED)){
				if(!HolmosPropertiesTool.getBoolean(properties, ConfigConstValue.HOLMOS_MODULE+"."+moduleName+ConfigConstValue.ENABLED)){
					moduleNames.remove(moduleName);
				}
			}
		}
	}
	private ArrayList<HolmosModule> loadAndInitModules(Set<String> moduleNames,Properties properties){
		ArrayList<HolmosModule>modules=new ArrayList<HolmosModule>();
		for(String moduleName:moduleNames){
			modules.add(createAndInitModule(moduleName, properties));
		}
		return modules;
	}
	/**
	 * 根据Module的名字，来创建一个{@link HolmosModule}对象，并且根据properties的配置对其进行初始化<br>
	 * 如果找不到指定的moduleName对象，抛出异常
	 * @param moduleName Module名字
	 * @param properties 配置文件
	 * */
	private HolmosModule createAndInitModule(String moduleName,Properties properties){
		String className=ConfigConstValue.HOLMOS_MODULE_CLASS_PATH+moduleName;
		if(!isClassFileExists(className)){
			logger.warn("框架将跳过"+className+",没有找到与"+className+"对应的class文件");
		}
		try{
			Object holmosMOdule=HolmosReflectionTool.createInstanceAsType(className, true);
			if(!(holmosMOdule instanceof HolmosModule)){
				throw new HolmosFailedError("配置文件配置错误，新建"+className+"失败!");
			}
			((HolmosModule)holmosMOdule).init(properties);
			return (HolmosModule) holmosMOdule;
		}catch (Exception e) {
			return null;
		}
	}
	/**
	 * 判断Holmos框架内，以className为全名的class是否有对应的class文件存在
	 * @param className 全类名
	 * @return true 存在 <br> false 不存在
	 * */
	private boolean isClassFileExists(String className) {
		String classPath=className.replace('.', '/');
		return getClass().getClassLoader().getResource(classPath)!=null;
	}
}
