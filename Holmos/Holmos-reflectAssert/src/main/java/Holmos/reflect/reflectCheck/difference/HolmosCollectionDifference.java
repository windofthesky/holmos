package Holmos.reflect.reflectCheck.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Holmos.reflect.reflectCheck.HolmosDifferenceVisitor;

/**记录Collections和Arrays不同的类
 * @author 吴银龙(15857164387)
 * */
public class HolmosCollectionDifference extends HolmosDifference{
    /* 记录所有Collections和Arrays的所有元素不同的信息*/
    private Map<Integer, HolmosDifference> elementDifferences = new HashMap<Integer, HolmosDifference>();

    /* 记录左元素无值的索引信息 */
    private List<Integer> leftMissingIndexes = new ArrayList<Integer>();

    /* 记录右元素无值的索引信息 */
    private List<Integer> rightMissingIndexes = new ArrayList<Integer>();

    /* 将左元素转化为的List形式 */
    private List<?> leftList;

    /* 将右元素转化为的List形式 */
    private List<?> rightList;
    /**HolmosDifference的构造器<br>
	 * @param leftValue 参与比较的第一个参数，左参数
	 * @param rightValue 参与比较的第二个参数，右参数
	 * @param leftList 参与比较的左参数的列表形式实例
	 * @param rightList 参与比较的右参数的列表形式实例
	 * @param message 描述两个参与比较的参数的不同的信息*/
    public HolmosCollectionDifference(Object leftValue, Object rightValue,
    		List<?> leftList, List<?> rightList,String message) {
        super(leftValue, rightValue, message);
        this.leftList = leftList;
        this.rightList = rightList;
    }
    /**根据给定的index索引添加集合元素或者数组元素的不同信息
     * @param index 元素索引
     * @param difference 该索引处元素不同的信息*/
    public void addElementDifference(int index,HolmosDifference difference){
    	this.elementDifferences.put(index, difference);
    }
    /**获得所有的元素的不同信息
     * @return 所有的元素的不同信息*/
    public Map<Integer,HolmosDifference>getAllElementsDifferences(){
    	return this.elementDifferences;
    }
    
    /**
     * 添加左值为null的索引信息
     * @param index 左值为null的索引
     */
    public void addLeftMissingIndex(int index) {
        leftMissingIndexes.add(index);
    }
    /**
     * 获得所有的左值为null的索引信息
     * @return 所有的左值为null的索引信息, not null
     */
    public List<Integer> getLeftMissingIndexes() {
        return leftMissingIndexes;
    }


    /**
     * 添加右值为null的索引信息
     * @param index 左值为null的索引
     */
    public void addRightMissingIndex(int index) {
        rightMissingIndexes.add(index);
    }


    /**
     * 获得所有的右值为null的索引信息
     * @return 所有的左值为null的索引信息, not null
     */
    public List<Integer> getRightMissingIndexes() {
        return rightMissingIndexes;
    }


    /**
     * @return 左值实例的List形式
     */
    public List<?> getLeftList() {
        return leftList;
    }


    /**
     * @return 右值实例的List形式
     */
    public List<?> getRightList() {
        return rightList;
    }

    @Override
    public <T, A> T accept(HolmosDifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }
}
