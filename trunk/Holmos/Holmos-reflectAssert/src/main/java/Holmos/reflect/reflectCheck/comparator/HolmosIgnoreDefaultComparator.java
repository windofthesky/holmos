package holmos.reflect.reflectCheck.comparator;

import holmos.reflect.reflectCheck.HolmosReflectionComparator;
import holmos.reflect.reflectCheck.difference.HolmosDifference;

/**处理java默认值的比较器，如果遇到null,false,0,0.0的情况，则此类型的反射比较一致
 * @author 吴银龙(15857164387)
 * */
public class HolmosIgnoreDefaultComparator implements HolmosComparator{

	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue==null)
			return true;
		else if(leftValue instanceof Boolean &&!((Boolean)leftValue).booleanValue())
			return true;
		else if(leftValue instanceof Character &&(Character)leftValue==0)
			return true;
		else if(leftValue instanceof Number && ((Number)leftValue).doubleValue()==0)
			return true;
		return false;
	}
	/**如果canCompare校验通过，那么这个就一定反射校验一致*/
	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyIfFirstDifference,
			HolmosReflectionComparator holmosreflectionComparator) {
		return null;
	}

}
