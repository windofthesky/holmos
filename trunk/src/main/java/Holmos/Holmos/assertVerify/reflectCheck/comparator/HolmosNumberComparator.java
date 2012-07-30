package Holmos.Holmos.assertVerify.reflectCheck.comparator;

import Holmos.Holmos.assertVerify.reflectCheck.HolmosReflectionComparator;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosDifference;

/**数值比较器，一个用来比较int和double和float 的比较器
 * @author 吴银龙(15857164387)
 * */
public class HolmosNumberComparator implements HolmosComparator{
	
	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue==null || rightValue==null)
			return false;
		if((leftValue instanceof Number || leftValue instanceof Character )&&
				(rightValue instanceof Number || rightValue instanceof Character))
			return true;
		return false;
	}
	/**比较leftValue和rightValue，比较前先将两个值都转换成Double类型*/
	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyIfFirstDifference,
			HolmosReflectionComparator holmosreflectionComparator) {
		//由于long类型转换为Double类型会损失精度，造成匹配失败，所以，将long类型单独处理
		if(leftValue instanceof Long && rightValue instanceof Long){
			if(((Long)leftValue).longValue()!=((Long)rightValue).longValue())
				return new HolmosDifference(leftValue, rightValue, "数值比较两个long类型的值大小不一致!");
		}
		Double leftDouble=getDoubleValue(leftValue);
		Double rightDouble=getDoubleValue(rightValue);
		if(!leftDouble.equals(rightDouble))
			return new HolmosDifference(leftValue, rightValue, "数值比较两个实例的值的大小不一致!");
		return null;
	}
	/**将数值类型或者Character类型的实例转换为Double类型实例
	 * @param object 将数值类型或者Character类型的实例
	 * @return 数值类型或者Character类型的实例转换为Double类型实例*/
	private Double getDoubleValue(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        return (double) ((Character) object).charValue();
    }
}
