package Holmos.reflect.reflectCheck.difference;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import Holmos.reflect.reflectCheck.HolmosDifferenceVisitor;
/**
 * @author 吴银龙(15857164387)
 * */
public class HolmosMapDifference extends HolmosDifference{
	
	/**HolmosDifference的构造器<br>
	 * @param leftValue 参与比较的第一个参数，左参数
	 * @param rightValue 参与比较的第二个参数，右参数
	 * @param leftList 参与比较的左参数的列表形式实例
	 * @param rightList 参与比较的右参数的列表形式实例
	 * @param message 描述两个参与比较的参数的不同的信息*/
	public HolmosMapDifference(Object leftValue, Object rightValue,Map<?, ?> leftMap,Map<?, ?> rightMap,
			String message) {
		super(leftValue, rightValue, message);
		this.leftMap=leftMap;
		this.rightMap=rightMap;
	}
	 /* 所有的元素的相异信息 */
    private Map<Object, HolmosDifference> valueDifferences = new IdentityHashMap<Object, HolmosDifference>();

    /* 所有的左值为null的key信息 */
    private List<Object> leftMissingKeys = new ArrayList<Object>();

    /* 所有的右值为null的key信息 */
    private List<Object> rightMissingKeys = new ArrayList<Object>();

    /* 第一个参与比较的元素的Map形式 */
    private Map<?, ?> leftMap;

    /* 第二个参与比较的元素的Map形式 */
    private Map<?, ?> rightMap;
    
    /**
     * 根据Map元素比较中的以key为索引的相异信息
     * @param key        The key
     * @param difference 相异信息, not null
     */
    public void addValueDifference(Object key, HolmosDifference difference) {
        valueDifferences.put(key, difference);
    }


    /**
     * 获得参与比较的Map的所有相异信息
     * @return The 参与比较的Map的所有相异信息, not null
     */
    public Map<Object, HolmosDifference> getValueDifferences() {
        return valueDifferences;
    }


    /**
     * 根据参与比较的Map的key增加一个左值为null的索引信息
     * @param key 左值为null的元素的key
     */
    public void addLeftMissingKey(Object key) {
        leftMissingKeys.add(key);
    }


    /**
     * 获得所有的左值为null的key索引信息
     * @return 所有的左值为null的key索引信息, not null
     */
    public List<Object> getLeftMissingKeys() {
        return leftMissingKeys;
    }


    /**
     * 根据参与比较的Map的key增加一个右值为null的索引信息
     * @param key 右值为null的元素的key
     */
    public void addRightMissingKey(Object key) {
        rightMissingKeys.add(key);
    }


    /**
     * 获得所有的右值为null的key索引信息
     * @return 所有的右值为null的key索引信息, not null
     */
    public List<Object> getRightMissingKeys() {
        return rightMissingKeys;
    }


    /**
     * @return 获得参与比较的第一个Map元素<左值>信息
     */
    public Map<?, ?> getLeftMap() {
        return leftMap;
    }


    /**
     * @return 获得参与比较的第二个Map元素<右值>信息
     */
    public Map<?, ?> getRightMap() {
        return rightMap;
    }
    
    @Override
    public <T, A> T accept(HolmosDifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }

}
