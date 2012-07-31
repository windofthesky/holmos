package Holmos.reflect.reflectCheck.difference;

import java.util.HashMap;
import java.util.Map;

import Holmos.reflect.reflectCheck.HolmosDifferenceVisitor;

/**记录Object实例的不同信息的类
 * @author 吴银龙(15857164387)
 * */
public class HolmosObjectDifference extends HolmosDifference{
	/**HolmosDifference的构造器<br>
	 * @param left 参与比较的第一个参数，左参数
	 * @param right 参与比较的第二个参数，右参数
	 * @param message 描述两个参与比较的参数的不同的信息*/
	public HolmosObjectDifference(Object leftValue, Object rightValue,
			String message) {
		super(leftValue, rightValue, message);
	}

	/* 这个实例的所有字段的相异信息 */
    private Map<String, HolmosDifference> fieldDifferences = new HashMap<String, HolmosDifference>();
    /**
     * 增加这个实例的一个字段的相异信息
     * @param fieldName  字段名字, not null
     * @param difference 此字段的相异信息, not null
     */
    public void addFieldDifference(String fieldName, HolmosDifference difference) {
        fieldDifferences.put(fieldName, difference);
    }


    /**
     * 获得这个实例的所有的字段的相异信息
     * @return 这个实例的所有的字段的相异信息, not null
     */
    public Map<String, HolmosDifference> getFieldDifferences() {
        return fieldDifferences;
    }

    @Override
    public <T, A> T accept(HolmosDifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }

}
