package Holmos.reflect.tool;

import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;
import static org.apache.commons.lang.ClassUtils.getShortClassName;

import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**一个对象的格式化器，将对象的信息转化为需要一个字符窜信息来描述这个对象<br>
 * 递归的对该对象的内部字段进行描述，为了防止描述过多，那么设置一个最大递归深度<br>
 * 对于超过刺身的的描述，不予给出<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosObjectFormatter {
	/**对象最大递归深度*/
	private int maxDepth;
	/**数组对象和Collection对象的最大长度，对于超过此长度的采取省略描述...*/
	@SuppressWarnings("unused")
	private int maxCollectionArrayElementCount;
	/**数组对象和Collection对象的格式化器*/
	private HolmosArrayAndCollectionFormatter holmosArrayAndCollectionFormatter;
	/**{@link HolmosObjectFormatter}构造器
	 * @param maxDepth 对象最大递归深度
	 * @param maxCollectionArrayElementCount 数组对象和Collection对象的最大长度
	 * */
	public HolmosObjectFormatter(int maxDepth,int maxCollectionArrayElementCount){
		this.maxDepth=maxDepth;
		this.maxCollectionArrayElementCount=maxCollectionArrayElementCount;
		this.holmosArrayAndCollectionFormatter=new HolmosArrayAndCollectionFormatter(maxCollectionArrayElementCount, this);
	}
	/**默认构造器，最大递归深度为5，最大数组长度为20*/
	public HolmosObjectFormatter(){
		this(5,20);
	}
	/**获得对象的String类型描述，为了方便外部调用<br>
	 * @param object 待描述的对象
	 * @return 对该对象的描述
	 * */
	public String format(Object object){
		StringBuilder result=new StringBuilder();
		formatImpl(object, 0, result);
		return result.toString();
	}
	/**实现对对象进行格式化的方法，{@link HolmosObjectFormatter#format(Object)}的实现者<br>
	 * @param object 进行格式化的对象
	 * @param currentDepth 当前对象所处的递归层次
	 * @param result 描述结果 
	 * */
	public void formatImpl(Object object, int currentDepth, StringBuilder result) {
		if (object == null) {
            result.append(String.valueOf(object));
            return;
        }
        if (formatString(object, result)) {
            return;
        }
        if (formatNumberOrDate(object, result)) {
            return;
        }
        Class<?> type = object.getClass();
        if (formatCharacter(object, type, result)) {
            return;
        }
        if (formatPrimitiveOrEnum(object, type, result)) {
            return;
        }
        if (formatMock(object, result)) {
            return;
        }
        if (formatProxy(object, type, result)) {
            return;
        }
        if (formatJavaLang(object, result, type)) {
            return;
        }
        if (type.isArray()) {
            holmosArrayAndCollectionFormatter.formatterArrays(object, currentDepth, result);
            return;
        }
        if (object instanceof Collection) {
            holmosArrayAndCollectionFormatter.formatCollection((Collection<?>) object, currentDepth, result);
            return;
        }
        if (object instanceof Map) {
            holmosArrayAndCollectionFormatter.formatMap((Map<?, ?>) object, currentDepth, result);
            return;
        }
        if (currentDepth >= maxDepth) {
            result.append(getShortClassName(type));
            result.append("<...>");
            return;
        }
        if (formatFile(object, result)) {
            return;
        }
        formatObject(object, currentDepth, result);
	}
	/**用已经定义好的格式化方法，就是上面的，来格式化object<br>
	 * 这个并不是实际的实现者，真正的时间者是{@link HolmosObjectFormatter#formatFields(Object, Class, int, StringBuilder)}
	 * @param object 待进行格式化的对象，不能为null
	 * @param currentDepth 当前递归所在层
	 * @param result 格式化的结果
	 * */
	private void formatObject(Object object, int currentDepth,
			StringBuilder result) {
		Class<?> type = object.getClass();
        result.append(getShortClassName(type));
        result.append("<");
        formatFields(object, type, currentDepth, result);
        result.append(">");
	}
	/**格式化Object的实际执行者<br>
	 * @param object 待格式化的对象
	 * @param type 格式化对象的类型，或者父类型或者子类型
	 * @param currentDepth 当前递归所在层
	 * @param result 格式化的结果
	 * */
	private void formatFields(Object object, Class<?> clazz, int currentDepth,
			StringBuilder result) {
		Field[] fields=clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			if (isTransient(field.getModifiers()) || isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }
            try {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(field.getName());
                result.append("=");
                formatImpl(field.get(object), currentDepth + 1, result);

            } catch (IllegalAccessException e) {
                throw new InternalError("Unexpected IllegalAccessException");
            }
		}
		//处理父类中的信息
		Class<?> superclazz = clazz.getSuperclass();
	    while (superclazz != null && !superclazz.getName().startsWith("java.lang")) {
	        formatFields(object, superclazz, currentDepth, result);
	        superclazz = superclazz.getSuperclass();
	    }
	}
	private boolean formatFile(Object object, StringBuilder result) {
		if (object instanceof File) {
            result.append("File<");
            result.append(((File) object).getPath());
            result.append(">");
            return true;
        }
        return false;
	}
	/**格式化java.lang，实际就是加上<br>
	 * @param object 带格式化的java.lang
	 * @param result 格式化得结果
	 * @return true object是java.lang<br>
	 * 		   false 否则的话
	 * */
	private boolean formatJavaLang(Object object, StringBuilder result,
			Class<?> type) {
		 if (type.getName().startsWith("java.lang")) {
	          result.append(String.valueOf(object));
	          return true;
	     }
	     return false;
	}
	private boolean formatProxy(Object object, Class<?> type,
			StringBuilder result) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean formatMock(Object object, StringBuilder result) {
		// TODO Auto-generated method stub
		return false;
	}
	/**格式化基本数据类型或枚举类型，实际就是加上<br>
	 * @param object 带格式化的基本数据类型或枚举类型
	 * @param result 格式化得结果
	 * @return true object是基本数据类型或枚举类型<br>
	 * 		   false 否则的话
	 * */
	private boolean formatPrimitiveOrEnum(Object object, Class<?> type,
			StringBuilder result) {
		if(type.isPrimitive()||type.isEnum()){
			result.append(String.valueOf(object));
			return true;
		}
		return false;
	}
	/**格式化字符，实际就是加上''<br>
	 * @param object 带格式化的字符
	 * @param result 格式化得结果
	 * @return true object是Charcter类型<br>
	 * 		   false 否则的话
	 * */
	private boolean formatCharacter(Object object, Class<?> type,
			StringBuilder result) {
		if(object instanceof Character){
			result.append("\'"+String.valueOf(object)+"\'");
			return true;
		}
		return false;
	}
	/**格式化数字和日期，实际就是加上()<br>
	 * @param object 带格式化的数字和日期
	 * @param result 格式化得结果
	 * @return true object是Number或Date类型<br>
	 * 		   false 否则的话
	 * */
	private boolean formatNumberOrDate(Object object, StringBuilder result) {
		if(object instanceof Number || object instanceof Date){
			result.append(String.valueOf(object));
			return true;
		}
		return false;
	}
	/**格式化字符窜，实际就是加上""<br>
	 * @param object 带格式化的字符窜
	 * @param result 格式化得结果
	 * @return true object是String类型<br>
	 * 		   false 否则的话
	 * */
	private boolean formatString(Object object, StringBuilder result) {
		if(object instanceof String){
			result.append("\"");
			result.append(object);
			result.append("\"");
			return true;
		}
		return false;
	}
}
