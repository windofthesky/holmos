package Holmos.Holmos.assertVerify.reflectCheck.difference;
/**类的类型区别类
 * @author 吴银龙(15857164387)
 * */
public class HolmosClassDifference extends HolmosDifference{
	/***/
	private Class<?> leftClass;
	/***/
    public Class<?> getLeftClass() {
		return leftClass;
	}
    /***/
	public Class<?> getRightClass() {
		return rightClass;
	}
	/***/
	private Class<?> rightClass;
	/**HolmosClassDifference的构造器<br>
	 * @param left 参与比较的第一个参数，左参数
	 * @param right 参与比较的第二个参数，右参数
	 * @param message 描述两个参与比较的参数的不同的信息*/
	public HolmosClassDifference(Object leftValue, Object rightValue,Class<?>leftClass,
			Class<?>rightClass,String message) {
		super(leftValue, rightValue, message);
		this.leftClass=leftClass;
		this.rightClass=rightClass;
	}
	@Override
	public <T, A> T accept(HolmosDifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }
}
