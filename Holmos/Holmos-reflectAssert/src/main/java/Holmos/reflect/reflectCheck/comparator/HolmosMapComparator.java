package holmos.reflect.reflectCheck.comparator;

import holmos.reflect.reflectCheck.HolmosReflectionComparator;
import holmos.reflect.reflectCheck.difference.HolmosDifference;
import holmos.reflect.reflectCheck.difference.HolmosMapDifference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**map和set对象比较器，根据特定的key来比较value
 * @author 吴银龙(15857164387)
 * */
public class HolmosMapComparator implements HolmosComparator{
	/**判断两个Map对象是否可比较，可比较需满足一下两个条件<br>
	 * <li>均不能为null</li>
	 * <li>必须都为Map类型实例</li>
	 * @param leftValue 第一个参与比较的对象实例
	 * @param rightValue 第二个参与比较的对象实例
	 * @return true 可比较  <br>false 不可比较*/
	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue==null || rightValue == null)
			return false;
		else if(leftValue instanceof Map && rightValue instanceof Map)
			return true;
		return false;
	}

	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyFirstDifference,
			HolmosReflectionComparator holmosreflectionComparator) {
		Map<?,?>leftMap=(Map<?, ?>) leftValue;
		Map<?,?>rightMap=(Map<?, ?>) rightValue;
		HolmosReflectionComparator keyReflectionComparator=HolmosComparatorFactory.createRefectionComparator();
		//构建一个rightMap的副本，因为下面要用到取其中的每一个元素，并且将这些找到的元素删除，不能影响rightMap，故新建一个
		Map<Object,Object>rightMapCopy=new HashMap<Object, Object>(rightMap);
		HolmosMapDifference difference=new HolmosMapDifference(leftValue, rightValue, leftMap, rightMap, "map类型不同的成员");
		for(Map.Entry<?, ?>leftEntry:leftMap.entrySet()){
			Object lkey = leftEntry.getKey();
            Object lvalue = leftEntry.getValue();
            Iterator<Map.Entry<Object, Object>>rIterator=rightMapCopy.entrySet().iterator();
            boolean found=false;
            while(rIterator.hasNext()){
            	Map.Entry<Object, Object> rEntry=rIterator.next();
            	Object rkey=rEntry.getKey();
            	Object rvalue=rEntry.getValue();
            	//验证左值和右值的key是否相同，这样他们才能对value进行比较
            	if(keyReflectionComparator.isEqual(lkey, rkey)){
            		found=true;
            		//对找到的项进行删除，也就是说，这一项已经比较过了，在map里面没有存在的价值了
            		rIterator.remove();
            		HolmosDifference elementDifference=holmosreflectionComparator.getDifference(lvalue, rvalue, onlyFirstDifference);
            		if(elementDifference!=null){
            			difference.addValueDifference(lkey, elementDifference);
            			if(onlyFirstDifference)return difference;
            		}
            		break;
            	}
            }
            if(!found){
            	difference.addLeftMissingKey(lkey);
            }
		}
		for(Object rkey:rightMapCopy.keySet()){
			difference.addRightMissingKey(rkey);
		}
		if(difference.getLeftMissingKeys().isEmpty()&&difference.getRightMissingKeys().isEmpty()
				&&difference.getValueDifferences().isEmpty()){
			return null;
		}
		return difference;
	}
}
