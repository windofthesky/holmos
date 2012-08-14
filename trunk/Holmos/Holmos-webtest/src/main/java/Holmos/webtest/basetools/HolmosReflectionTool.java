package Holmos.webtest.basetools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import Holmos.webtest.exceptions.HolmosFailedError;

/**
 * 反射的工具类,提供常见的get和set字段value,执行方法,构造对象
 * 
 * @author 吴银龙(15857164387)
 * */
public class HolmosReflectionTool {
	/**
	 * 创建typeName类型的对象实例,调用的是无参构造函数
	 * 
	 * @param typeName
	 *            类型名字
	 * @param isAccessible
	 *            true 改变构造函数的访问控制,如果构造函数的访问权不是public的话,不抛出异常 <br>
	 *            false 不改变访问权限,访问权限不够的话抛出异常
	 * @return 构造出的变量实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createInstanceAsType(String typeName,
			boolean isAccessible) {
		Class<T> typeForInstance;
		try {
			typeForInstance = (Class<T>) Class.forName(typeName);
			return createInstanceAsType(typeForInstance, isAccessible);
		} catch (ClassNotFoundException e) {
			throw new HolmosFailedError("创建无参对象实例失败!");
		}
	}

	/**
	 * 创建typeForInstance类型的对象实例,调用的是无参构造函数
	 * 
	 * @param typeForInstance
	 *            类型的Class对象
	 * @param isAccessible
	 *            true 改变构造函数的访问控制,如果构造函数的访问权不是public的话,不抛出异常 <br>
	 *            false 不改变访问权限,访问权限不够的话抛出异常
	 * @return 构造出的变量实例
	 */
	public static <T> T createInstanceAsType(Class<T> typeForInstance,
			boolean isAccessible) {
		return createInstanceOfType(typeForInstance, isAccessible,
				new Class[0], new Object[0]);
	}

	/**
	 * 创建typeForInstance类型的对象实例
	 * 
	 * @param typeForInstance
	 *            类型的Class对象
	 * @param isAccessible
	 *            true 改变构造函数的访问控制,如果构造函数的访问权不是public的话,不抛出异常 <br>
	 *            false 不改变访问权限,访问权限不够的话抛出异常
	 * @param argumentsType
	 *            参数的类型列表
	 * @param arguments
	 *            参数的实例列表
	 * @return 构造出的变量实例
	 */
	public static <T> T createInstanceOfType(Class<T> typeForInstance,
			boolean isAccessible,
			@SuppressWarnings("rawtypes") Class[] argumentsType,
			Object[] arguments) {
		if (typeForInstance.isMemberClass()
				|| Modifier.isStatic(typeForInstance.getModifiers())) {
			/*
			 * 如果是成员类(内部类的一种)或者是静态类的时候,不能实例化;原因是成员内部类在外层类的外部是不可见的,是一种
			 * 比private修饰符还要严格的一种形式;所以无法用这种方法实例化;对于静态类:由于静态类仅仅能包含静态成员,并且 不能用new
			 * 关键字生成静态类对象,所以这种类型也不支持
			 */
			throw new HolmosFailedError(
					"对于成员内部类和静态类，Holmos框架无法为其创建对象实例!详情请见java中关于成员内部类和"
							+ "静态类的定义!请参照:http://baike.baidu.com/view/6497553.htm和http://baike.baidu.com/view/3671198.htm");
		}
		try {
			Constructor<T> constructor = typeForInstance
					.getDeclaredConstructor(argumentsType);
			if (isAccessible) {
				constructor.setAccessible(isAccessible);
			}
			return constructor.newInstance(arguments);
		} catch (InvocationTargetException e) {
			throw new HolmosFailedError("创建" + typeForInstance.getName()
					+ "对象的时候发生错误!");

		} catch (Exception e) {
			throw new HolmosFailedError("创建" + typeForInstance.getName()
					+ "对象的时候发生错误!");
		}
	}

	/**
	 * 获取object对象里面字段名field的值
	 * 
	 * @param object
	 *            字段所在的对象实例
	 * @param field
	 *            字段对象
	 * @return 获取的字段的值
	 * */
	public static Object getFieldValue(Object object, Field field) {
		// 对非public类型的字段修改访问权限
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalArgumentException e) {
			throw new HolmosFailedError("字段" + field.getName() + "的值获取失败!");
		} catch (IllegalAccessException e) {
			throw new HolmosFailedError("字段" + field.getName() + "的值获取失败!");
		}
	}

	/**
	 * 获取object对象里面字段名field的值
	 * 
	 * @param object
	 *            字段所在的对象实例
	 * @param fieldName
	 *            字段名字
	 * @return 获取的字段的值
	 * */
	public static Object getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			return getFieldValue(object, field);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 为object里面的field字段赋值
	 * 
	 * @param object
	 *            字段所在的对象母体
	 * @param field
	 *            字段信息
	 * @param value
	 *            为字段付的值
	 * */
	public static void setFieldValue(Object object, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new HolmosFailedError("字段" + field.getName() + "的值设置失败!");
		} catch (IllegalAccessException e) {
			throw new HolmosFailedError("字段" + field.getName() + "的值设置失败!");
		}
	}

	/**
	 * 为给定的object对象里面的fields字段列表赋值value,所有给定的字段都赋值为value
	 * 
	 * @param object
	 *            给定的字段母体
	 * @param fields
	 *            字段列表,此列表中的任何一个字段都必须是object里面的字段
	 * @param value
	 *            给字段赋的值
	 * 
	 * */
	public static void setFieldsValue(Object object, ArrayList<Field> fields,
			Object value) {
		for (Field field : fields) {
			setFieldValue(object, field, value);
		}
	}

	/**
	 * 通过给定的setter方法给字段赋值
	 * 
	 * @param object
	 *            给定的setter方法母体
	 * @param setter
	 *            给定的setter方法
	 * @param value
	 *            通过setter方法赋的值
	 * */
	public static void setFieldValueThroughSetter(Object object, Method setter,
			Object value) {
		if (isSetterMethod(setter)) {
			try {
				setter.invoke(object, value);
			} catch (IllegalArgumentException e) {
				throw new HolmosFailedError("通过setter方法赋值失败!");
			} catch (IllegalAccessException e) {
				throw new HolmosFailedError("通过setter方法赋值失败!");
			} catch (InvocationTargetException e) {
				throw new HolmosFailedError("通过setter方法赋值失败!");
			}
		} else {
			throw new HolmosFailedError("通过setter方法赋值失败!");
		}
	}

	/**
	 * 为给定的object对象里面通过setter方法列表赋值value,所有给定的字段都赋值为value
	 * 
	 * @param object
	 *            给定的setter方法母体
	 * @param setters
	 *            给定的setter方法列表
	 * @param value
	 *            通过setter方法赋的值
	 * */
	public static void setFieldValueThroughSetters(Object object,
			ArrayList<Method> setters, Object value) {
		for (Method setter : setters) {
			setFieldValueThroughSetter(object, setter, value);
		}
	}

	/** 给给定的字段赋值为value,还有给定的setter方法也是赋值为value */
	public static void setFieldValueThroughFieldsOrSetters(Object object,
			ArrayList<Field> fields, ArrayList<Method> setters, Object value) {
		setFieldsValue(object, fields, value);
		setFieldValueThroughSetters(object, setters, value);
	}

	/**
	 * 执行object实例的方法method,参数为argument,返回值返回
	 * 
	 * @param object
	 *            方法method的母体
	 * @param method
	 *            待执行的方法
	 * @param arguments
	 *            方法的参数
	 * */
	public static Object invokeMethod(Object object, Method method,
			Object... arguments) throws InvocationTargetException {
		try {
			method.setAccessible(true);
			return method.invoke(object, arguments);
		} catch (IllegalArgumentException e) {
			throw new HolmosFailedError("方法" + method.getName() + "执行失败!");
		} catch (IllegalAccessException e) {
			throw new HolmosFailedError("方法" + method.getName() + "执行失败!");
		}
	}

	/**
	 * 校验method是否为setter方法,校验规则为满足下面三个条件: <li>方法名长度大于3</li> <li>
	 * 方法名以set开头,并且方法名的第四个字母为大写</li> <li>方法的参数个数只有一个</li>
	 * 
	 * @param method
	 *            待校验的方法对象
	 * @return true 是setter方法 <br>
	 *         false 不是setter方法
	 * */
	private static boolean isSetterMethod(Method method) {
		String methodName = method.getName();
		if (methodName.length() > 3 || methodName.startsWith("set")
				&& method.getParameterTypes().length == 1) {
			if (methodName.substring(3, 4).indexOf(0) >= 'A'
					&& methodName.substring(3, 4).indexOf(0) <= 'Z')
				return true;
		}
		return false;
	}

	public static Class<?> getClassWithName(String valueAsString) {
		try {
			return Class.forName(valueAsString);
		} catch (ClassNotFoundException e) {
			throw new HolmosFailedError("获得" + valueAsString + "类失败!");
		}
	}
}
