package holmos.reflect.reflectCheck.difference;

import holmos.reflect.reflectCheck.HolmosDifferenceVisitor;

/**Holmos框架高级（反射比较）器的比较结果类
 * @author 吴银龙(15857164387)
 * */
public class HolmosDifference {
	/**参与比较的第一个参数，左参数*/
	private Object leftValue;
	
	/**获得参与比较的第一个参数，左参数*/
	public Object getLeftValue() {
		return leftValue;
	}
	/**获得参与比较的第二个参数，右参数*/
	public Object getRightValue() {
		return rightValue;
	}
	/**获得描述两个参与比较的参数的不同的信息*/
	public String getMessage() {
		return message;
	}

	/**参与比较的第二个参数，右参数*/
	private Object rightValue;
	/**描述两个参与比较的参数的不同的信息*/
	private String message;
	
	/**HolmosDifference的构造器<br>
	 * @param left 参与比较的第一个参数，左参数
	 * @param right 参与比较的第二个参数，右参数
	 * @param message 描述两个参与比较的参数的不同的信息*/
	public HolmosDifference(Object leftValue,Object rightValue,String message){
		this.leftValue=leftValue;
		this.rightValue=rightValue;
		this.message=message;
	}
	/**这个是高级校验反射校验的核心算法，在真正的比较进行的时候会递归调用这个方法<br>
	 * 不断的比较当前对象，和这个对象的子对象，递归到一下集中情况截止<br>
	 * 调用此方法实际上是调用的是visitor的visit方法<br>
	 * <li>集合类型和数组类型，最后调用HolmosCollectionComparator</li>
	 * <li>日期类型，最后调用HolmosDateComparator</li>
	 * <li>Map和Set类型，最后调用HolmosMapComparator</li>
	 * <li>Simple类型，最后调用HolmosSimpleComparator</li>
	 * 当为其他类型的时候，调用HolmosObjectComparator中的visit方法<br>
	 * 继续递归调用,所有HolmosDifference的子类都必须覆盖这个方法并重写<br>
	 * @param visitor 类的访问者，通过这个访问者来调查比较的结果
	 * @param argument visitor的配置，visitor访问的时候根据这个要求进行比较<br>*/
	public <T,A> T accept(HolmosDifferenceVisitor<T, A> visitor,A argument){
		return visitor.visit(this, argument);
	}
}
