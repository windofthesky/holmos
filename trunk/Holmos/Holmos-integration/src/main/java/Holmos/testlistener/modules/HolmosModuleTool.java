package holmos.testlistener.modules;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ClassUtils;

import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.basetools.HolmosReflectionTool;
import Holmos.webtest.exceptions.HolmosFailedError;
/**
 * HolmosModule的工具方法类
 * @author 吴银龙(15857164387)
 * */
public class HolmosModuleTool {
	/**
	 * 获取完成注解功能的策略实现类Class对象:
	 * <li>如果value和defaultValueClass不相同,那么返回给定的value</li>
	 * <li>如果相同,那么在allDefaultValues里面寻找默认的配置,找到了的情况下返回这个配置，如果没有找到，抛出异常</li>
	 * @param annotation 注解
	 * @param annotationPropertyName 此注解的属性名字，用来指示注解的具体实现类
	 * @param value 给定的实现方案
	 * @param allDefaultValues 所有的默认的实现方案的存储库（就是一个Cache）
	 * @param defaultValueClass 默认的实现方法
	 * */
	public static Class<?> getClassValueReplaceDefault(Class<? extends Annotation> annotationClass, String annotationPropertyName,
			Class<?> value, Map<Class<? extends Annotation>, Map<String, String>> allDefaultValues, Class<?> defaultValueClass) {
        String valueAsString = getValueAsStringReplaceDefault(annotationClass, annotationPropertyName, value.getName(), allDefaultValues, defaultValueClass.getName());
        return HolmosReflectionTool.getClassWithName(valueAsString);
    }
	/**{@link #getClassValueReplaceDefault(Class, String, Class, Map, Class)}的实现者，返回获取到的类的名字*/
	private static String getValueAsStringReplaceDefault(
			Class<? extends Annotation> annotation,
			String annotationPropertyName,
			String valueName,
			Map<Class<? extends Annotation>, Map<String, String>> allDefaultValues,
			String defaultValueName) {
		if(!defaultValueName.equalsIgnoreCase(valueName)){
			return valueName;
		}
		Map<String,String> defaultValues=allDefaultValues.get(annotation);
		String defaultValue=defaultValues.get(annotationPropertyName);
		if(defaultValue!=null)
			return defaultValue;
		throw new HolmosFailedError("没有获取到注解相应实现策略的实现类!");
	}
	/**
	 * 获取配置文件里面对指定Module的指定Annotation的指定name配置的默认配置:<br>
	 * 属性的名字格式:moduleClass.annoClass.name.default<br>
	 * 如果不存在的话，返回null，否则返回这个配置<br>
	 * */
	public static String getDefaultAnnotationProperty(Class<? extends HolmosModule>moduleClass,
			Class<? extends Annotation> annoClass,String name,Properties properties){
		String propertyName=ClassUtils.getShortClassName(moduleClass)+"."+ClassUtils.getShortClassName(annoClass);
		propertyName+="."+name+".default";
		if(!HolmosPropertiesTool.isPropertyInFile(properties, propertyName)){
			return null;
		}
		return HolmosPropertiesTool.getValue(properties, propertyName);
	}
}
