package Holmos.Holmos.assertVerify.reflectCheck.comparator;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import Holmos.Holmos.assertVerify.reflectCheck.HolmosReflectionComparator;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosClassDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosObjectDifference;

/**两个对象进行比较，可以按照java本身的Object比较，和比较里面元素的值进行比较两中方式<br>
 * 默认比较全部的字段，但是可以指定比较其中部分字段<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosObjectComparator implements HolmosComparator{
	/**如果两个参与比较的实例不为null，就可以参与比较<br>
	 * */
	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue == null || rightValue == null)
			return false;
		return true;
	}

	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyFirstDifference,
			HolmosReflectionComparator holmosreflectionComparator) {
		Class<?>clazz=leftValue.getClass();
		if(!clazz.isAssignableFrom(rightValue.getClass())){
			//如果不是同一种类，那么没有可比性，直接是类级别的差异
			return new HolmosClassDifference(leftValue, rightValue, clazz, rightValue.getClass(), "类不同");
		}
		//如果具有可比性的话，那么调用compareFields方法
		HolmosObjectDifference difference=new HolmosObjectDifference(leftValue, rightValue, "object difference");
		compareFields(leftValue, rightValue, clazz, difference, onlyFirstDifference, holmosreflectionComparator);
		if(difference.getFieldDifferences().isEmpty())
			return null;
		else 
			return difference;
	}
	/**比较左值和右值的所有字段列表，注意，静态字段由于属于类字段，不参与比较；<br>
	 * transient异常不参与比较，因为这种类型的字段不做持久化保存；<br>
	 * synthetic修饰符修饰的字段也不参与比较，因为这个事复合类对象，只是在编译器编译好的文件中存在<br>
	 * @param leftValue 参与比较的左值
	 * @param rightValue 参与比较的右值
	 * @param clazz 左值的类型
	 * @param difference 左右值的相异对象
	 * @param onlyFirstDifference 是否在找到第一个字段不同就返回的标志，true为返回，false为不反回
	 * @param holmosReflectionComparator 用来比较左值和右值的比较器
	 * */
	protected void compareFields(Object leftValue,Object rightValue,Class<?>clazz,HolmosObjectDifference difference,
			boolean onlyFirstDifference,HolmosReflectionComparator holmosReflectionComparator){
		Field[] fields=clazz.getDeclaredFields();
		//使用安全性检查，设置为true是为了不改变fields中所有元素的可访问性
		AccessibleObject.setAccessible(fields, true);
		for(Field field:fields){
			if(field.isSynthetic()||Modifier.isStatic(field.getModifiers())||Modifier.isTransient(field.getModifiers())){
				continue;//对这三种类型的字段不做检查
			}
			//对每个字段进行扫描检查
			try {
				HolmosDifference innerDifference=holmosReflectionComparator.getDifference(field.get(leftValue), field.get(rightValue), onlyFirstDifference);
				if(innerDifference!=null){
					difference.addFieldDifference(field.getName(), innerDifference);
					if(onlyFirstDifference)
						return;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		//处理父类信息
		Class<?>superClass=clazz.getSuperclass();
		while(superClass!=null&&!superClass.getName().startsWith("java.lang")){
			//当不是clazz不是Object并且不是java.lang基本类的时候，就需要一直寻找其父类,递归调用此方法找出其不同的地方
			compareFields(leftValue, rightValue, superClass, difference, onlyFirstDifference, holmosReflectionComparator);
			superClass=superClass.getSuperclass();
		}
	}
}
