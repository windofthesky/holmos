package Holmos.Holmos.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import Holmos.Holmos.exceptions.HolmosFailedError;

/**操作注解的工具类
 * @author 吴银龙(15857164387)
 * */
public class HolmosAnnotationTool {
	/**获得clazz类里面被annotation注解的所有的字段，并依据参数来判断是否包含所有的祖先类中的可见字段
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @param includeSuper true 处理祖先类，<br>false 不处理祖先类
	 * @return 在clazz类里面被annotation注解的字段
	 * */
	public static <T extends Annotation> Set<Field> getFieldsWithAnnotationBase(Class<? extends Object> clazz,Class<T>annotationClass,boolean includeSuper){
		if(Object.class.equals(clazz)){
			//如果为Object类，那么不可能有被annotation注解的字段，返回空set
			return Collections.emptySet();
		}
		//deal with all the fields in current Class
		Set<Field>fieldsWithAnnotation=new HashSet<Field>();
		for(Field field:clazz.getDeclaredFields()){
			if(field.getAnnotation(annotationClass)!=null){
				fieldsWithAnnotation.add(field);
			}
		}
		//deal with all the visiable fields in super class
		if(includeSuper){
			fieldsWithAnnotation.addAll(getFieldsWithAnnotationBase(clazz.getSuperclass(), annotationClass,includeSuper));
		}
		return fieldsWithAnnotation;
	}
	/**获得clazz类里面被annotation注解的所有的字段,并获取祖先类的可见字段
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @return 在clazz类里面被annotation注解的字段
	 * */
	public static <T extends Annotation> Set<Field> getFieldsWithAnnotationIncludeSuper(Class<? extends Object> clazz,Class<T>annotationClass){
		return getFieldsWithAnnotationBase(clazz, annotationClass, true);
	}
	/**获得clazz类里面被annotation注解的所有的字段,不获取祖先类的可见字段
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @return 在clazz类里面被annotation注解的字段
	 * */
	public static <T extends Annotation> Set<Field> getFieldsWithAnnotationNotIncludeSuper(Class<? extends Object> clazz,Class<T>annotationClass){
		return getFieldsWithAnnotationBase(clazz, annotationClass, false);
	}
	/**获得clazz类里面被annotation注解的所有的方法,并依据参数来判断是否获取祖先类的可见方法
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @param includeSuper true 处理祖先类，<br>false 不处理祖先类
	 * @return 在clazz类里面被annotation注解的方法
	 * */
	public static <T extends Annotation> Set<Method> getMethodsWithAnnotationBase(Class<? extends Object> clazz,Class<T>annotationClass,boolean includeSuper){
		if(Object.class.equals(clazz)){
			return Collections.emptySet();
		}
		//deal with all the methods in current Class
		Set<Method> methodsWithAnnotation=new HashSet<Method>();
		for(Method method:clazz.getDeclaredMethods()){
			if(method.getAnnotation(annotationClass)!=null){
				methodsWithAnnotation.add(method);
			}
		}
		//deal with all the visiable methods in super class
		if(includeSuper){
			getMethodsWithAnnotationBase(clazz.getSuperclass(), annotationClass, includeSuper);
		}
		return methodsWithAnnotation;
	}
	/**获得clazz类里面被annotation注解的所有的方法,并获取祖先类的可见方法
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @return 在clazz类里面被annotation注解的方法
	 * */
	public static <T extends Annotation> Set<Method> getMethodsWithAnnotationIncludeSuper(Class<? extends Object> clazz,Class<T>annotationClass){
		return getMethodsWithAnnotationBase(clazz, annotationClass, true);
	}
	/**获得clazz类里面被annotation注解的所有的方法,不获取祖先类的可见方法
	 * @param clazz 母体类，不能为null
	 * @param annotation 指定的注解,不能为null
	 * @return 在clazz类里面被annotation注解的方法
	 * */
	public static <T extends Annotation> Set<Method> getMethodsWithAnnotationNotIncludeSuper(Class<? extends Object> clazz,Class<T>annotationClass){
		return getMethodsWithAnnotationBase(clazz, annotationClass, false);
	}
	/**获取clazz类里面所有方法级别的annotation类型的注解，包含祖先类
	 * @param clazz 母体类
	 * @param annotation 要获取的注解类型
	 * @return 获取的所有注解对象
	 * */
	public static <T extends Annotation> Set<T> getMethodLevelAnnotations(Class<?> clazz, Class<T> annotationClass){
		Set<T>result=new HashSet<T>();
		Set<Method> methods=getMethodsWithAnnotationIncludeSuper(clazz, annotationClass);
		for(Method method:methods){
			result.add(method.getAnnotation(annotationClass));
		}
		return result;
	}
	/**获取clazz类里面所有字段级别的annotation类型的注解，包含祖先类
	 * @param clazz 母体类
	 * @param annotation 要获取的注解类型
	 * @return 获取的所有注解对象
	 * */
	public static <T extends Annotation> Set<T> getFieldLevelAnnotations(Class<?>clazz,Class<T> annotationClass){
		Set<T>result=new HashSet<T>();
		Set<Field> fields=getFieldsWithAnnotationIncludeSuper(clazz, annotationClass);
		for(Field field:fields){
			result.add(field.getAnnotation(annotationClass));
		}
		return result;
	}
	/**获取clazz类里面method方法上annotation类型的注解,如果方法级别的注解存在，那么返回方法级别的，如果不存在，返回类级别的
	 * @param clazz 母体类
	 * @param method 方法对象
	 * @param annotation 要获取的注解类型
	 * @return 获取的注解对象
	 * */
	public static <T extends Annotation> T getMethodOrClassLevelAnnotation(Class<?>clazz,Method method,Class<T>annotationClass){
		T annotation=getMethodLevelAnnotation(clazz,method,annotationClass);
		if(annotation!=null){
			return annotation;
		}
		annotation=getClassLevelAnnotation(clazz,annotationClass);
		return annotation;
	}
	/**
	 * 获取clazz类里面method方法上annotation类型的注解的属性名称为annotationPropertyName的属性值,<br>
	 * 如果方法级别的注解存在，那么返回方法级别的，如果不存在，返回类级别的
	 * @param annotationClass 注解类型
	 * @param annotationPropertyName 注解的属性名称 
	 * @param defaultValue 默认值，在注解属性值获取失败的时候返回此值
	 * @param method 指定的方法
	 * @param clazz 指定的类
	 * */
	public static <S extends Annotation,T> T getMethodOrClassLevelAnnotationProperty(Class<S> annotationClass,
            String annotationPropertyName, T defaultValue, Method method, Class<?> clazz){
		S annotation=method.getAnnotation(annotationClass);
		if(annotation!=null){
			Method property=getPropertyByName(annotationPropertyName,annotationClass);
			T propertyValue=(T) getAnnotationPropertyValue(property, annotation);
			if(!defaultValue.equals(propertyValue)){
				return propertyValue;
			}
		}
		return getClassLevelAnnotationPropertyValue(clazz.getSuperclass(), annotationPropertyName, annotationClass, defaultValue);
	}
	private static Method getPropertyByName(String annotationPropertyName,Class<? extends Annotation> annotation){
		try {
			return annotation.getMethod(annotationPropertyName);
		} catch (SecurityException e) {
			throw new HolmosFailedError("注解"+annotation.getName()+"获取"+annotationPropertyName+"失败!");
		} catch (NoSuchMethodException e) {
			throw new HolmosFailedError("注解"+annotation.getName()+"获取"+annotationPropertyName+"失败!");
		}
	}
	/**获取clazz类里面method方法上annotation类型的注解,如果方法级别的注解存在，那么返回方法级别的，如果不存在，返回null
	 * @param clazz 母体类
	 * @param method 方法对象
	 * @param annotation 要获取的注解类型
	 * @return 获取的注解对象
	 * */
	public static <T extends Annotation> T getMethodLevelAnnotation(Class<?>clazz,Method method,Class<T>annotationClass){
		if(Object.class.equals(clazz)){
			return null;
		}
		return (T) method.getAnnotation(annotationClass);
	}
	/**获取clazz类上annotation类型的注解,如果方法级别的注解存在，那么返回方法级别的，如果不存在，递归获取父类，直到Object
	 * @param clazz 母体类
	 * @param annotation 要获取的注解类型
	 * @return 获取的注解对象
	 * */
	public static <T extends Annotation> T getClassLevelAnnotation(Class<?>clazz,Class<T>annotationClass){
		if(Object.class.equals(clazz)){
			return null;
		}
		T annotaion= clazz.getAnnotation(annotationClass);
		if(annotaion==null){
			annotaion=getClassLevelAnnotation(clazz.getSuperclass(), annotationClass);
		}
		return annotaion;
	}
	/**获取指定注解的指定属性
	 * @param annotation 指定的注解 不能为null
	 * @param propertyName 指定注解的属性名字 不能为null
	 * @return 指定属性,是一个方法
	 * */
	public static Method getAnnotationPropertyWithName(Class<? extends Annotation>annotation,String propertyName){
		try{
			return annotation.getMethod(propertyName);
		}catch (NoSuchMethodException e) {
			throw new HolmosFailedError("在"+annotation.getName()+"注解里面没有找到属性"+propertyName);
		}
	}
	/**获得指定注解对象的指定属性(方法)的值
	 * @param annotationProperty 方法属性对象
	 * @param 指定的注解对象
	 * @return 属性的value
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T getAnnotationPropertyValue(Method annotationProperty,Annotation annotation){
		try {
			return (T) annotationProperty.invoke(annotation);
		} catch (IllegalArgumentException e) {
			throw new HolmosFailedError("无法获得注解"+annotation.annotationType().getName()+"的"+annotationProperty.getName()+"属性信息!");
		} catch (IllegalAccessException e) {
			throw new HolmosFailedError("无法获得注解"+annotation.annotationType().getName()+"的"+annotationProperty.getName()+"属性信息!");
		} catch (InvocationTargetException e) {
			throw new HolmosFailedError("无法获得注解"+annotation.annotationType().getName()+"的"+annotationProperty.getName()+"属性信息!");
		}
	}
	/**判断在类clazz里面是否有字段，方法，类级别的注解annotationClass
	 * @param clazz 给定的类
	 * @param annotationClass 给定的注解类型
	 * @return true 字段，方法，类级别的注解都没有 <br> false 至少有其中一个级别
	 * */
	public boolean hasFieldOrMethodOrClassLevelAnnotation(Class<? extends Object>clazz,Class<? extends Annotation>annotationClass){
		return getFieldLevelAnnotations(clazz, annotationClass).isEmpty()&&
				getMethodLevelAnnotations(clazz, annotationClass).isEmpty()&&(getClassLevelAnnotation(clazz, annotationClass)!=null);
	}
	/**判断在类clazz里面是否有字段级别的注解annotationClass
	 * @param clazz 给定的类
	 * @param annotationClass 给定的注解类型
	 * @return true 没有 <br> false 有
	 * */
	public boolean hasFieldLevelAnnotation(Class<? extends Object>clazz,Class<? extends Annotation>annotationClass){
		return getFieldLevelAnnotations(clazz, annotationClass).isEmpty();
	}
	/**判断在类clazz里面是否有方法级别的注解annotationClass
	 * @param clazz 给定的类
	 * @param annotationClass 给定的注解类型
	 * @return true 没有 <br> false 有
	 * */
	public boolean hasMethodLevelAnnotation(Class<? extends Object>clazz,Class<? extends Annotation>annotationClass){
		return getMethodLevelAnnotations(clazz, annotationClass).isEmpty();
	}
	/**判断在类clazz里面是否有类级别的注解annotationClass
	 * @param clazz 给定的类
	 * @param annotationClass 给定的注解类型
	 * @return true 没有 <br> false 有
	 * */
	public static boolean hasClassLevelAnnotation(Class<? extends Object>clazz,Class<? extends Annotation>annotationClass){
		return getClassLevelAnnotation(clazz, annotationClass)!=null;
	}
	/**根据给定的类,给定的注解,给定的注解的属性名字,返回类级别的注解属性值,如果值为null,返回给定的默认值defaultValue
	 * @param clazz 给定的类
	 * @param propertyName 注解里面属性名字
	 * @param annotationClass 给定的注解
	 * @param defaultValue 值为null 的时候的返回的默认值;当得到的值与defaultValue相同的时候寻找祖先类里的值
	 * @return 获得的类级别的注解的属性值
	 * */
	public static <S extends Annotation,T> T getClassLevelAnnotationPropertyValue(Class<?>clazz,
			String propertyName,Class<S>annotationClass,T defaultValue){
		if (Object.class.equals(clazz)) {
            return defaultValue;
        }
		S annotation=clazz.getAnnotation(annotationClass);
		if(annotation!=null){
			Method annotationProperty=getAnnotationPropertyWithName(annotationClass, propertyName);
			T propertyValue=getAnnotationPropertyValue(annotationProperty, annotation);
			if(propertyValue.equals(defaultValue)){
				//如果相同的话，找寻父类
				return propertyValue;
			}
		}
		return getClassLevelAnnotationPropertyValue(clazz.getSuperclass(), propertyName, annotationClass, defaultValue);
	}
	/**根据给定的方法,给定的注解,给定的注解的属性名字,返回方法级别的注解属性值,如果值为null,返回给定的默认值defaultValue
	 * @param method 给定的方法
	 * @param propertyName 注解里面属性名字
	 * @param annotationClass 给定的注解
	 * @param defaultValue 值为null 的时候的返回的默认值;
	 * @return 获得的方法级别的注解的属性值
	 * */
	public static <S extends Annotation,T> T getMethodLevelAnnotationPropertyValue(Method method,
			String propertyName,Class<S>annotationClass,T defaultValue){
		S annotation = method.getAnnotation(annotationClass);
		Method annotationProperty= getAnnotationPropertyWithName(annotationClass, propertyName);
		T propertyValue=getAnnotationPropertyValue(annotationProperty, annotation);
		if(propertyValue==null)
			return defaultValue;
		return propertyValue;
	}
	/**根据给定的类,给定的方法,给定的注解,给定的注解的属性名字,优先返回方法级别的注解属性值,如果值为null或者为default,继续寻找类级别的注解,返回给定的默认值defaultValue
	 * @param clazz 给定的类
	 * @param method 给定的方法
	 * @param propertyName 注解里面属性名字
	 * @param annotationClass 给定的注解
	 * @param defaultValue 值为null 的时候的返回的默认值;当得到的值与defaultValue相同的时候寻找祖先类里的值
	 * @return 优先获得方法级别的注解值,如果为null或者为default获得的类级别的注解的属性值
	 * */
	public static <S extends Annotation,T> T getMethodOrClassLevelAnnotationPropertyValue(Class<?>clazz,Method method,
			String propertyName,Class<S>annotationClass,T defaultValue){
		T propertyValue=getMethodLevelAnnotationPropertyValue(method, propertyName, annotationClass, defaultValue);
		if(propertyName==null||propertyValue.equals(defaultValue)){
			return getClassLevelAnnotationPropertyValue(clazz, propertyName, annotationClass, defaultValue);
		}
		return propertyValue;
	}
	/**
	 * 创建一个类型为T的实例
	 * @param type 创建实例的类型
	 * @param bypassAccessibility 当是true 的时候，如果此类型的无参构造函数不是public的时候不抛出异常
	 * @param argumentTypes 参数类型列表
	 * @param arguments 参数列表
	 * */
	public static <T> T createInstanceOfType(Class<T>type,boolean bypassAccessibility, Class[] argumentTypes,
            Object[] arguments){
		if(type.isMemberClass()||Modifier.isStatic(type.getModifiers())){
			throw new HolmosFailedError("成员类和静态类无法为其构造实例!");
		}
		try{
			Constructor<T>constructor=type.getConstructor(argumentTypes);
			if(bypassAccessibility){
				constructor.setAccessible(bypassAccessibility);
			}
			return constructor.newInstance(arguments);
		}catch (InvocationTargetException e) {
            throw new HolmosFailedError("构造" + type.getName()+"实例的时候发生错误!");

        } catch (Exception e) {
            throw new HolmosFailedError("构造" + type.getName()+"实例的时候发生错误!");
        }
	}
}
