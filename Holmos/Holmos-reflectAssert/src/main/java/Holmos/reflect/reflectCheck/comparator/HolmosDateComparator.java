package holmos.reflect.reflectCheck.comparator;

import holmos.reflect.reflectCheck.HolmosReflectionComparator;
import holmos.reflect.reflectCheck.difference.HolmosDifference;

import java.util.Date;
/**日期的比较器，可以分为按格式比较和不按照格式比较<br>
 * <li>比较两个Date的时间是否为null或者是否都不为null，不比较实际的时间值</li>
 * @author 吴银龙(15857164387)
 * */
public class HolmosDateComparator implements HolmosComparator{

	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue==null&&rightValue==null)
			return true;
		else if(leftValue instanceof Date && rightValue instanceof Date)
			return true;
		else if((leftValue==null&&rightValue instanceof Date)||(leftValue instanceof Date && rightValue ==null))
			return true;
		return false;
	}
	/**返回leftValue和rightValue的比较结果，这种比较方法不看他们的时间数值
	 * 只要值都为空或者都不空那么就反射比较一致，否则不一致
	 * @param leftValue 第一个参与比较的Date实例
	 * @param rightValue 第二个参与比较的Date实例
	 * @return null反射比较一致
	 * 		        不为null，反射比较不一致，两个实例一个为null，另一个不为null
	 * */
	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyIfFirstDifference,
			HolmosReflectionComparator holmosreflectionComparator) {
		if ((rightValue == null && leftValue instanceof Date) || (leftValue == null && rightValue instanceof Date)) {
            return new HolmosDifference(leftValue, rightValue,"Date compare without value");
        }
        return null;
	}
}
