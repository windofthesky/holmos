package Holmos.Holmos.assertVerify.reflectCheck.difference;
/**
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDifferenceVisitor<T,A> {
	public T visit(HolmosDifference difference,A argument);
}
