package Holmos.reflect.reflectCheck.comparator;

import java.util.Calendar;
import java.util.Date;

import Holmos.reflect.reflectCheck.HolmosReflectionComparator;
import Holmos.reflect.reflectCheck.difference.HolmosDifference;
/**比较简单的形式的比较，如果比较的内容或者要求比较简单，那么这个比较会加速<br>
 * <li>leftValue和rightValue是一个对象</li>
 * <li>leftValue和rightValue都是null</li>
 * <li>leftValue和rightValue都是枚举类型</li>
 * <li>leftValue和rightValue都是java.lang的基础类</li>
 * <li>leftValue和rightValue都是Charater和Number</li>
 * @author 吴银龙(15857164387)
 * */
public class HolmosSimpleComparator implements HolmosComparator{

	public boolean canCompare(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return true;
        }
        if ((left instanceof Character || left instanceof Number) && (right instanceof Character || right instanceof Number)) {
            return true;
        }
        if (left.getClass().getName().startsWith("java.lang") || right.getClass().getName().startsWith("java.lang")) {
            return true;
        }
        if (left instanceof Date || right instanceof Date) {
            return true;
        }
        if (left instanceof Calendar || right instanceof Calendar) {
            return true;
        }
        if (left instanceof Enum && right instanceof Enum) {
            return true;
        }
        return false;
    }
	public HolmosDifference compare(Object left, Object right, boolean onlyFirstDifference, HolmosReflectionComparator reflectionComparator) {
        // check if the same instance is referenced
        if (left == right) {
            return null;
        }
        // check if the left value is null
        if (left == null) {
            return new HolmosDifference( left, right,"左值为null");
        }
        // check if the right value is null
        if (right == null) {
            return new HolmosDifference(left, right,"右值为null");
        }
        // check if right and left have same number value (including NaN and Infinity)
        if ((left instanceof Character || left instanceof Number) && (right instanceof Character || right instanceof Number)) {
            Double leftDouble = getDoubleValue(left);
            Double rightDouble = getDoubleValue(right);
            if (leftDouble.equals(rightDouble)) {
                return null;
            }
            return new HolmosDifference(left, right,"Simple类型两个数值不一致");
        }
        // check if java objects are equal
        if (left.getClass().getName().startsWith("java.lang") || right.getClass().getName().startsWith("java.lang")) {
            if (left.equals(right)) {
                return null;
            }
            return new HolmosDifference(left, right,"Simple类型两个对象不一致");
        }
        // check if dates are equal
        if (left instanceof Date && right instanceof Date) {
            if (left.equals(right)) {
                return null;
            }
            return new HolmosDifference(left, right,"Simple两个时间值不一致");
        }
        // check if dates are equal
        if (left instanceof Calendar && right instanceof Calendar) {
            if (left.equals(right)) {
                return null;
            }
            return new HolmosDifference(left, right,"Calander两个实例的值不一致");
        }
        // check if enums are equal
        if (left instanceof Enum && right instanceof Enum) {
            if (left.equals(right)) {
                return null;
            }
            return new HolmosDifference( left, right,"两个实例的枚举值不一致");
        }
        return null;
    }
	private Double getDoubleValue(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        return (double) ((Character) object).charValue();
    }

}
