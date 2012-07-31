package Holmos.reflect.reflectCheck.comparator;

import Holmos.reflect.reflectCheck.HolmosReflectionComparator;
import Holmos.reflect.reflectCheck.difference.HolmosDifference;

/**高级比较的比较器：比较Collection,Array,Map,Set,Object等除了基本元素以外的比较器
 * @author 吴银龙(15857164387)
 * */
public interface HolmosComparator {
	/**判断leftValue和rightValue两个元素是否可以比较,如果两个对象可以比较，那么将会调用比较方法<br>
	 * 对两个对象进行比较，并返回比较的结果,如果两个对象不可比较，那么将不会调用比较方法<br>
	 * 比较方法将直接返回<br>
	 * @param leftValue  第一个参与校验的对象
	 * @param rightValue 第二个参与校验的对象
	 * @return true 两个对象可以比较
	 *         false 两个对象不可以比较*/
	public boolean canCompare(Object leftValue,Object rightValue);
	/**比较所给的两个参数leftValue和rightValue,如果有所不同，返回其不同的原因和不同的位置<br>
	 * @param leftValue 第一个参与比较的对象
	 * @param rightValue 第二个参与比较的对象
	 * @param onlyIfFirstDifference 如果为true，那么如果实例的第一个要比较的属性或者字段不相同就返回false<br>
	 * 		      如果为false，那么就会比较完，给出完整结论
	 * @param holmosreflectionComparator 反射比较器，利用这个递归比较对象实例的信息
	 * @return 返回两个对象之间的不同信息*/
	public HolmosDifference compare(Object leftValue,Object rightValue,boolean onlyIfFirstDifference,HolmosReflectionComparator holmosreflectionComparator);
}
