package Holmos.Holmos.assertVerify.reflectCheck.difference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**存储无序集合或者数组变量比较的不同的结果的类
 * @author 吴银龙(15857164387)
 * */
public class HolmosCollectionIgnoreOrderDifference extends HolmosDifference{

	public HolmosCollectionIgnoreOrderDifference(Object leftValue,
			Object rightValue, List<?>leftList,List<?>rightList,String message) {
		super(leftValue, rightValue, message);
		this.leftList=leftList;
		this.rightList=rightList;
	}
	/**记录左值的每个元素和右值的每一个元素的不同的地方*/
	private Map<Integer,Map<Integer,HolmosDifference>> elementDifference=new HashMap<Integer, Map<Integer,HolmosDifference>>();
	public Map<Integer, Map<Integer, HolmosDifference>> getElementDifference() {
		return elementDifference;
	}
	/**记录最佳匹配的左右组合*/
	private Map<Integer,Integer>bestMatchIndexes=new HashMap<Integer, Integer>();
	/**最佳匹配的得分，是一种评判机制*/
	private int bestMatchScore=Integer.MAX_VALUE;
	public int getBestMatchScore() {
		return bestMatchScore;
	}
	public Map<Integer, Integer> getBestMatchIndexes() {
		return bestMatchIndexes;
	}
	public void setBestMatchScore(int bestMatchScore) {
		this.bestMatchScore = bestMatchScore;
	}
	/**左值的list形式*/
	private List<?>leftList;
	public List<?> getLeftList() {
		return leftList;
	}
	public List<?> getRightList() {
		return rightList;
	}
	/**右值的list形式*/
	private List<?>rightList;
	
	/**增加左值和右值的各自一个元素的不同信息<br>
	 * @param leftIndex 左值索引
	 * @param rightIndex 右值索引
	 * @param difference 在此索引处的不同信息
	 * */
	public void addElementDifference(int leftIndex,int rightIndex,HolmosDifference difference){
		Map<Integer,HolmosDifference>rightDifferrenceToLeft=elementDifference.get(leftIndex);
		if(rightDifferrenceToLeft==null){
			rightDifferrenceToLeft=new HashMap<Integer, HolmosDifference>();
			elementDifference.put(leftIndex, rightDifferrenceToLeft);
		}rightDifferrenceToLeft.put(rightIndex, difference);
	}
	/**通过左值和右值的索引，获得在此两个的索引处的不同信息<br>
	 * @param leftIndex 左索引
	 * @param rightIndex 右索引
	 * @return 这两个索引处的差异值*/
	public HolmosDifference getElementDifference(int leftIndex,int rightIndex){
		Map<Integer,HolmosDifference>rightDifferrenceToLeft=elementDifference.get(leftIndex);
		if(rightDifferrenceToLeft==null)
			return null;
		return rightDifferrenceToLeft.get(rightIndex);
	}
	/**设置最佳匹配搭配<br>
	 * @param leftIndex 最佳搭配左索引
	 * @param rightIndex 最佳搭配右索引
	 * */
	public void setBestMatch(int leftIndex,int rightIndex){
		bestMatchIndexes.put(leftIndex, rightIndex);
	}
	@Override
    public <T, A> T accept(HolmosDifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }
}
