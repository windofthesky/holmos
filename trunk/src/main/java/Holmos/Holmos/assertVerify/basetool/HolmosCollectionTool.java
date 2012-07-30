package Holmos.Holmos.assertVerify.basetool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

/**在进行高级反射校验的时候对于Collection类型的工具类
 * @author 吴银龙(15857164387)
 * */
public class HolmosCollectionTool {
	/**从给定的List里面获得从fromIndex到toIndex索引的所有信息
	 * @param list 原始的List实例，是母体
	 * @param fromIndex 开始的索引位置，从0开始
	 * @param toIndex 结束的索引位置，到list.size()为止
	 * @exception 如果fromIndex和toIndex超过了索引范围，那么将其设定在合法范围内*/
	public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
		List<T> result = new ArrayList<T>();
        if (list == null) {
            return result;
        }
        if(fromIndex<0)fromIndex=0;
        if(toIndex>list.size())toIndex=list.size();
        for (int i = fromIndex; i < toIndex; i++) {
            result.add(list.get(i));
        }
        return result;
	}
	/**将给定的object转化为Collection类型，如果object不是Collection类型<br>
	 * 如果是Array类型，那么进行转化，其他情况直接返回
	 * @param object 待转换的对象实例，Collection对象或者Array对象
	 * @return 由Object对象转化而来的List*/
	public static Collection<?> convertToCollection(Object object) {
		if(object instanceof Collection<?>){
			return (Collection<?>) object;
		}else if(object.getClass().isArray()){
			Object[]array=convertToObjectArray(object);
			return asList(array);
		}return null;
	}
	/**将数组元素转换为List类型的元素
	 * @param array 数组元素
	 * @return array的List形式*/
	public static List<Object> asList(Object[] array){
		List<Object> result = new ArrayList<Object>();
        if (array == null) {
            return result;
        }
        for(Object element:array){
        	result.add(element);
        }
        return result;
	}
	/**将数组参数，转换为Set形式
	 * @param elements 数组类型参数
	 * @return 返回转换后的Set形式*/
	@SuppressWarnings("unchecked")
	public static <T> Set<T> asSet(T... elements) {
	    Set<T> result = new HashSet<T>();
	    if (elements == null) {
	        return result;
	    }
	    result.addAll((Collection<? extends T>) asList(elements));
	    return result;
	}
	/**将对象转换为Array类型
	 * @param object 待转换为Array类型的对象实例
	 * @return 由object转换而来的数组*/
	public static Object[] convertToObjectArray(Object object) {
        if (object instanceof byte[]) {
            return ArrayUtils.toObject((byte[]) object);
        } else if (object instanceof short[]) {
            return ArrayUtils.toObject((short[]) object);
        } else if (object instanceof int[]) {
            return ArrayUtils.toObject((int[]) object);
        } else if (object instanceof long[]) {
            return ArrayUtils.toObject((long[]) object);
        } else if (object instanceof char[]) {
            return ArrayUtils.toObject((char[]) object);
        } else if (object instanceof float[]) {
            return ArrayUtils.toObject((float[]) object);
        } else if (object instanceof double[]) {
            return ArrayUtils.toObject((double[]) object);
        } else if (object instanceof boolean[]) {
            return ArrayUtils.toObject((boolean[]) object);
        } else {
            return (Object[]) object;
        }
    }
}
