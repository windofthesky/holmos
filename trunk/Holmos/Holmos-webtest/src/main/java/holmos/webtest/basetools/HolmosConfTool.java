package holmos.webtest.basetools;

import holmos.webtest.exceptions.HolmosFailedError;
import holmos.webtest.log.MyLogger;

import java.util.Properties;

/**
 * Holmos 框架处理配置文件的工具类
 * 
 * @author 吴银龙(15857164387)
 * 
 * */
public class HolmosConfTool {
	private static MyLogger logger = MyLogger.getLogger(HolmosConfTool.class);
	{
		HolmosBaseTools.configLogProperties();
	}

	/**
	 * 从配置文件里面得到type的实现者的一个实例返回
	 * 
	 * @param type
	 *            待实现的父类或者接口
	 * @param properties
	 *            配置文件
	 * @param implementerValues
	 *            具体实现者，配置文件里面书写的格式为:type.impClassName.implementerValue[0].
	 *            implementerValue[1]...
	 * @return 构造出的实例
	 * */
	public static <T> T getInstanceOf(Class<T> type, Properties properties,
			String... implementerValues) {
		String fullClassName = getImplementerName(type, properties,
				implementerValues);
		logger.info("创建" + fullClassName + "实例");
		return HolmosReflectionTool.createInstanceAsType(fullClassName, false);
	}

	/**
	 * 根据配置文件中的配置，获得实现类的全路径类名
	 * 
	 * @param type
	 *            待实现的父类或者接口
	 * @param properties
	 *            配置文件
	 * @param implementerValues
	 *            具体实现者，配置文件里面书写的格式为:type.impClassName
	 *            implementerValue[0].implementerValue[1]...
	 * @return 获得配置文件中的实现类的全路径类名
	 * */
	private static String getImplementerName(Class<?> type,
			Properties properties, String[] implementerValues) {
		String propertyKey = type.getName() + ".impClassName";
		if (implementerValues != null) {
			String propertyKeyTemp = propertyKey;
			for (String implementerValue : implementerValues) {
				propertyKeyTemp += "." + implementerValue;
			}
			if (properties.contains(propertyKeyTemp)) {
				return HolmosPropertiesTool.getValue(properties,
						propertyKeyTemp);
			}
		}
		if (properties.contains(propertyKey)) {
			return HolmosPropertiesTool.getValue(properties, propertyKey);
		}
		throw new HolmosFailedError("找不到" + propertyKey + "的配置项!");
	}
}
