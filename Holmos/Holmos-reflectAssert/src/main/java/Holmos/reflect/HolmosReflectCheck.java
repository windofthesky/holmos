package holmos.reflect;
import static junit.framework.Assert.assertNotNull;
import holmos.reflect.reflectCheck.HolmosRefectionComparatorMode;
import holmos.reflect.reflectCheck.HolmosReflectionComparator;
import holmos.reflect.reflectCheck.comparator.HolmosComparatorFactory;
import holmos.reflect.reflectCheck.difference.HolmosDifference;
import holmos.reflect.reflectCheck.report.HolmosDefaultDifferentReport;
import holmos.webtest.basetools.HolmosReflectionTool;
import holmos.webtest.exceptions.HolmosFailedError;

/**反射校验的工具类，会调用这个类里面的工具方法来进行反射校验<br>
 * 校验两个Object或者两个集合，数组，map，无序集合是否相同<p>
 * 在下面几种情况将不是严格校验：<br>
 * <li>忽略默认值:比如说校验一个参数或者校验参数里面的字段，这个字段有一个默认值(比如：0,null,"")，如果当前值为默认值的时候，那么校验结果是正确的</li>
 * <li>宽松式的时间校验:就是校验两个Date类型对象是否为null或者是否都不为null，不会去比较他们的实际值是否相同</li>
 * <li>无序的集合和数组的校验:这个时候只需要两个数组或者集合里面的数值都有对应相等的值就可以了，顺序不作为校验条件或者标准存在</li>
 * <p>
 * 这里面的每种校验都会有两种不同的版本<p>
 * <li>宽松式的校验，如上所述的三种</li>
 * <li>反射式的校验，这样的话可以自己设置匹配模式，不过对于以上三个版本的匹配校验，都必须选择宽松式的校验，但是可以是部分的宽松</li>
 * @author 吴银龙(15857164387)
 * @see HolmosRefectionComparatorMode
 * @see HolmosReflectionComparator
 * */
public class HolmosReflectCheck {
	/**
	 * 根据comparatorModes的设定情况来反射比较expected与actual两个对象的值
	 * @param message 反射比较失败后输出的信息
	 * @param expected 预期值
	 * @param actual 实际值 
	 * @param modes 对比较规则的设置，看其中的部分变量是否需要宽松式比较
	 * */
	public static void assertEquals(String message,Object expected,Object actual,
			HolmosRefectionComparatorMode ...modes)throws HolmosFailedError{
		HolmosReflectionComparator reflectionComparator=HolmosComparatorFactory.createRefectionComparator(modes);
		HolmosDifference difference=reflectionComparator.getDifference(expected, actual);
		if(difference!=null){
			//如果有差异的话才会抛出异常，打印结果
			throw new HolmosFailedError(getFailedMessage(message,difference));
		}
	}
	/**进行宽松式比较<br>
	 * <li>忽略默认值</li>
	 * <li>宽松式时间校验</li>
	 * <li>无序集合和数组</li>
	 * @param message 反射比较失败后输出的信息
	 * @param expected 预期值
	 * @param actual 实际值 
	 * */
	public static void assertLenientEquals(String message,Object expected,Object actual)throws HolmosFailedError{
		assertEquals(message, expected, actual, HolmosRefectionComparatorMode.DATE,HolmosRefectionComparatorMode.IGNORE_DEFAULT,
				HolmosRefectionComparatorMode.IGNORE_COLLECTION_ORDER);
	}
	/**无用户定制信息的宽松式校验，这个可以作为常用的校验方式，因为最为简洁<br>
	 * <li>忽略默认值</li>
	 * <li>宽松式时间校验</li>
	 * <li>无序集合和数组</li>
	 * @param expected 预期值
	 * @param actual 实际值 */
	public static void assertLenientEquals(Object expected,Object actual){
		assertLenientEquals(null, expected, actual);
	}
	/**无用户定制信息的反射校验，根据用户设定的严格情况
	 * @param expected 预期值
	 * @param actual 实际值 
	 * @param modes 对比较规则的设置，看其中的部分变量是否需要宽松式比较
	 * */
	public static void assertEquals(Object expected,Object actual,
			HolmosRefectionComparatorMode ...modes)throws HolmosFailedError{
		assertEquals(null, expected, actual, modes);
	}
	/**
	 * 校验特定对象的特定字段值
	 * <li>带校验的字段的名字不存在，失败</li>
	 * <li>实际的校验对象为null，失败</li>
	 * <li>字段值和语预期的不一致，失败</li>
	 * <li>其他情况成功</li>
	 * @param fieldName 待校验的字段名
	 * @param expectedFieldValue 预期值
	 * @param actualObject 待校验的对象值
	 * */
	public static void assertFieldValueEquals(String fieldName,Object expectedFieldValue,Object actualObject){
		assertFieldValueEquals(null,fieldName,expectedFieldValue,actualObject);
	}
	/**
	 * 校验特定对象的特定字段值
	 * <li>带校验的字段的名字不存在，失败</li>
	 * <li>实际的校验对象为null，失败</li>
	 * <li>字段值和语预期的不一致，失败</li>
	 * <li>其他情况成功</li>
	 * @param message 失败的时候的信息
	 * @param fieldName 待校验的字段名
	 * @param expectedFieldValue 预期值
	 * @param actualObject 待校验的对象值
	 * */
	public static void assertFieldValueEquals(String message,String fieldName,Object expectedFieldValue,Object actualObject){
		Object actualFieldValue=HolmosReflectionTool.getFieldValue(actualObject, fieldName);
		assertNotNull("待校验的对象值为null.", actualObject);
		String fieldAssertMessage=actualObject.getClass().getName()+"的属性"+fieldName+"校验\n"+message;
		assertEquals(fieldAssertMessage, expectedFieldValue, actualFieldValue);
	}
	/**构造错误信息<br>
	 * @param message 用户设定的信息
	 * @param differcence 两个对象的差异信息
	 * @return 构造出的错误信息
	 * */
	protected static String getFailedMessage(String message,HolmosDifference difference){
		StringBuilder result=new StringBuilder();
		result.append(message==null?"":message+":\n");
		result.append(new HolmosDefaultDifferentReport().createReport(difference));
		return result.toString();
	}
	
}
