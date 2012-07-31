package Holmos.dbtest.database;

import java.util.Properties;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;

import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.constvalue.ConstValue;
import Holmos.webtest.exceptions.HolmosFailedError;
/**Holmos框架配置加载器<p>
 * Holmos框架会从以下三个地方读取配置文件<p>
 * <li><b>Holmos框架默认的地址:/conf/holmos_default_conf.properties</b>
 * <p>如果这个地方加载失败，那么将抛出异常;当然了，这个默认的配置文件是不允许修改的</li>
 * 
 * <li><b>holmos_conf.properties这个名字也可以用户自定义</b><p>
 * 这个文件所在位置:可以为classpath，也可以是用户自定义的地方，定义在holmos_default_conf.properties里面<br>
 * 定义的格式属性名为:holmos.configuration.customFileName 后边指定properties地址;优先查看classpath里面有没有文件<br>
 * 这个文件的设置，将会覆盖前面文件的设置</li>
 * 
 * <li><b>holmos_local_conf.properties这个名字可以用户自定义</b><p>
 * 这个文件的位置:可以为classpath，也可以是用户自定义的位置,定义在holmos_default_conf.properties里面<br>
 * 定义此位置的属性名为:holmos.configuration.localFileName 后边指定properties地址;优先查看classpath里面有没有文件<br>
 * 这个文件里面的设置将会覆盖前面两个文件的设置</li>
 * 
 * <p><b>这里有一个隐患，那就是优先级更高的配置文件里面如果没有配置信息或者信息配置错误，框架不做检查</b></p>
 * <br>当没有找到默认的配置的时候，即/conf/holmos_default_conf.properties;框架会抛出异常，停止运行<br>
 * <br>当没有找到holmos.configuration.customFileName信息的时候，框架会给出警告信息<br>
 * <br>当没有找到holmos_local_conf.properties，框架会给出简单的提示信息<br>*/
public class HolmosConfLoader {
	/**默认的holmos配置文件的地址*/
	public static String HOLMOS_DEFAULT_CONF_PROPERTIES="/conf/holmos_default_conf.properties";
	/**default_conf_properties文件里面的holmos.configuration.customFileName 文件的属性名字*/
	public static String HOLMOS_CUSTOM_CONF_PROPERTIES="holmos.configuration.customFileName";
	/**default_conf_properties文件里面的holmos.configuration.localFileName 文件的属性名字*/
	public static String HOLMOS_LOCAL_CONF_PROPERTIES="holmos.configuration.customFileName";
	/**日志记录器*/
	public static Logger logger=Logger.getLogger(HolmosConfLoader.class);
	/**按照default->custom->local的顺序加载配置文件*/
	public Properties loadConfiguration(){
		Properties properties=new Properties();
		loadDefaultConfiguration(properties);
        loadCustomConfiguration(properties);
        loadLocalConfiguration(properties);
        loadSystemProperties(properties);
        expandPropertyValues(properties);
        return properties;
	}
	/**展开properties文件里面定义好的属性，支持用定义好的属性作为变量定义新的属性*/
	private void expandPropertyValues(Properties properties) {
		for(Object key:properties.keySet()){
			Object value=properties.get(key);
			try{
				//展开后的属性信息
				String expandedValue=StrSubstitutor.replace(value, properties);
				properties.put(key, expandedValue);
			}catch (Exception e) {
				throw new HolmosFailedError("属性定义出现错误!请检查配置文件!");
			}
		}
		
	}
	/**加载默认配置文件*/
	private void loadDefaultConfiguration(Properties properties) {
		Properties defaultProperties=HolmosPropertiesTool.
				getPropertiesFromCommonFilePath(ConstValue.CURRENDIR+HOLMOS_DEFAULT_CONF_PROPERTIES);
		if(defaultProperties==null){
			throw new HolmosFailedError("Holmos默认配置文件没有加载成功!");
		}
		properties.putAll(defaultProperties);
	}
	/**加载custom配置文件,先加载classpath里面的配置文件，如果不是的话，那么就查找全路径的配置文件*/
	private void loadCustomConfiguration(Properties properties) {
		String customConfFileName=getConfFileName(properties);
		Properties customProperties=HolmosPropertiesTool.getPropertiesFromClassPath(customConfFileName);
		if(customProperties==null)
			customProperties=HolmosPropertiesTool.getPropertiesFromCommonFilePath(customConfFileName);
		if(customProperties==null){
			logger.warn("holmos custome 配置文件加载错误!");
		}
		else{
			properties.putAll(customProperties);
		}
	}
	/**获得配置文件的名字
	 * @param properties 默认的配置文件，如果有custom配置，那么这个时候，这个配置写在了holmos default properties里面
	 * @return 从这个properties文件里面读取的custom配置文件的名字,应该是绝对路径*/
	private String getConfFileName(Properties properties) {
		return HolmosPropertiesTool.getValue(properties, HOLMOS_CUSTOM_CONF_PROPERTIES);
	}
	/**加载local 配置文件，首先user.home，没有的话加载classpath里面的，在没有的话，加载全路径的*/
	private void loadLocalConfiguration(Properties properties) {
		String localConfFileName=getConfFileName(properties);
		Properties localProperties=HolmosPropertiesTool.getPropertiesFromUserhome(localConfFileName);
		if(localProperties==null)
			localProperties=HolmosPropertiesTool.getPropertiesFromClassPath(localConfFileName);
		if(localProperties==null)
			localProperties=HolmosPropertiesTool.getPropertiesFromClassPath(localConfFileName);
		if(localProperties==null){
			logger.debug("holmos local 配置文件加载错误!");
		}else{
			properties.putAll(localProperties);
		}
	}
	/**加载系统的配置*/
	private void loadSystemProperties(Properties properties) {
		properties.putAll(System.getProperties());
	}
}
