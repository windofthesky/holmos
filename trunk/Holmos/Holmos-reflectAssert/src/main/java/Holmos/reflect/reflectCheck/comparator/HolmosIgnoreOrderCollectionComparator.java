package Holmos.reflect.reflectCheck.comparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import Holmos.reflect.basetool.HolmosCollectionTool;
import Holmos.reflect.reflectCheck.HolmosMatchingScoreCalculator;
import Holmos.reflect.reflectCheck.HolmosReflectionComparator;
import Holmos.reflect.reflectCheck.difference.HolmosCollectionIgnoreOrderDifference;
import Holmos.reflect.reflectCheck.difference.HolmosDifference;
/**两个Collection对象的比较器，对Collection立面的元素的顺序没有要求
 * @author 吴银龙(15857164387)
 * */
public class HolmosIgnoreOrderCollectionComparator implements HolmosComparator{
	/**当leftValue和rightValue都不为null或者都为Collection和Array的时候返回true*/
	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue == null || rightValue == null)
			return false;
		else if((leftValue.getClass().isArray()||leftValue instanceof Collection)
				&&(rightValue.getClass().isArray()||rightValue instanceof Collection))
			return true;
		return false;
	}
	/**可以采用两中常规方法进行比较<br>
	 * <li>双重循环，如果找到匹配的那么将这个匹配的值从rightList和rightList中删除，如果最后leftList和rightList中再无元素，则匹配成功</li>
	 * <li>采用递归，如果找到匹配的那么将这个匹配的值从rightList中删除，继续用leftList中的下一个元素与接下来的rightList中的元素比较，成功的条件和第一种方法一致</li>
	 * 在此采用循环法，因为这种速度快，其实两者的时间复杂度都是一样的，只是这种方法没有递归开销<br>*/
	public HolmosDifference compare(Object leftValue, Object rightValue,
			boolean onlyIfFirstDifference,
			HolmosReflectionComparator holmosReflectionComparator) {
		ArrayList<Object>leftList=new ArrayList<Object>(HolmosCollectionTool.convertToCollection(leftValue));
		ArrayList<Object>rightList=new ArrayList<Object>(HolmosCollectionTool.convertToCollection(rightValue));
		boolean isEqual=isEqualIgnoreOrder(leftList, rightList, holmosReflectionComparator);
		if(isEqual){
			//左右匹配了，全部匹配
			return null;
		}
		//如果不匹配，找到所有的不同的可能性
		HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference=new 
				HolmosCollectionIgnoreOrderDifference(leftValue, rightValue, leftList, rightList, "无序数组或者List比较器");
		if(onlyIfFirstDifference){
			return collectionIgnoreOrderDifference;
		}
		fillAllDifferenceElments(leftList, rightList, holmosReflectionComparator, collectionIgnoreOrderDifference);
		fillBestMatches(leftList, rightList, collectionIgnoreOrderDifference);
		return collectionIgnoreOrderDifference;
	}
	/***双重循环法*/
	protected boolean isEqualIgnoreOrder(ArrayList<Object>leftList,ArrayList<Object>rightList,
			HolmosReflectionComparator holmosReflectionComparator){
		Iterator<Object>leftIterator=leftList.iterator();
		Iterator<Object>rightIterator=rightList.iterator();
		while(leftIterator.hasNext()){
			Object leftValue=leftIterator.next();
			rightIterator=rightList.iterator();
			while(rightIterator.hasNext()){
				Object rightValue=rightIterator.next();
				if(holmosReflectionComparator.getDifference(leftValue, rightValue, true)==null){
					leftIterator.remove();
					rightIterator.remove();
					break;
				}
			}
		}
		if(leftList.isEmpty()&&rightList.isEmpty())
			return true;
		return false;
	}
	/**递归法*/
	protected boolean isEqualIgnoreOrderRecursively(ArrayList<Object>leftList,ArrayList<Object>rightList,
			HolmosReflectionComparator holmosReflectionComparator){
		if(rightList.isEmpty()&&leftList.isEmpty())
			return true;
		else if(rightList.isEmpty()||leftList.isEmpty())
			return false;
		else{
			for(Object left:leftList){
				for(Object right:rightList){
					if(holmosReflectionComparator.getDifference(left, right, true)==null){
						leftList.remove(left);
						rightList.remove(right);
						if(isEqualIgnoreOrderRecursively(leftList, rightList, holmosReflectionComparator))
							return true;
					}
				}
				return false;
			}
			return false;
		}
	}
	/**找到在最佳匹配下所有不同的节点，并将其放入difference里<br>
	 * 列出所有的可能的不同，将所有的不同情况一一列出
	 * @param leftList 左值的List形式
	 * @param rightList 右值的List形式
	 * @param reflectionComparator 比较器工厂
	 * @param difference 本无序集合的差异信息
	 * */
	protected void fillAllDifferenceElments(ArrayList<Object>leftList,ArrayList<Object>rightList,
			HolmosReflectionComparator reflectionComparator,HolmosCollectionIgnoreOrderDifference difference){
		for(int leftIndex=0;leftIndex<leftList.size();leftIndex++){
			Object leftValue=leftList.get(leftIndex);
			for(int rightIndex=0;rightIndex<rightList.size();rightIndex++){
				Object rightValue=rightList.get(rightIndex);
				HolmosDifference elementDifference=reflectionComparator.getDifference(leftValue, rightValue);
				difference.addElementDifference(leftIndex, rightIndex, elementDifference);
			}
		}
	}
	/**将左值和右值中得到匹配的项全部删除，修改的是leftIndexes和rightIndexes信息<br>
	 * @param leftIndexes 左值的索引信息
	 * @param rightIndexes 右值的索引信息
	 * @param difference 本无序集合的差异信息
	 * */
	protected void removeMatchIndexes(ArrayList<Integer>leftIndexes,ArrayList<Integer>rightIndexes,HolmosCollectionIgnoreOrderDifference difference){
		Iterator<Integer>leftIterator,rightIterator;
		leftIterator=leftIndexes.iterator();
		rightIterator=rightIndexes.iterator();
		while(leftIterator.hasNext()){
			int leftIndex=leftIterator.next();
			while(rightIterator.hasNext()){
				int rightIndex=rightIterator.next();
				if(difference.getElementDifference().get(leftIndex).get(rightIndex)==null){
					leftIterator.remove();
					rightIterator.remove();
				}
			}
		}
	}
	/**在剩余的没有匹配的元素中，寻找最佳组合*/
	protected void setBestMatchIndexes(ArrayList<Integer>leftIndexes,ArrayList<Integer>rightIndexes,HolmosCollectionIgnoreOrderDifference difference){
		int score=Integer.MAX_VALUE;
		HolmosMatchingScoreCalculator matchingScoreCalculator=getMatchingScoreCalculator();
		for(Integer leftIndex:leftIndexes){
			score=Integer.MAX_VALUE;
			for(Integer rightIndex:rightIndexes){
				int matchScore=matchingScoreCalculator.calculateMatchingScore(difference);
				if(score>matchScore){
					score=matchScore;
					difference.setBestMatch(leftIndex, rightIndex);
				}
			}
		}
	}
	/**设置最佳组合*/
	protected void fillBestMatches(ArrayList<Object>leftList,ArrayList<Object>rightList,HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference){
		ArrayList<Integer>leftIndexes=createIndexList(leftList.size());
		ArrayList<Integer>rightIndexes=createIndexList(rightList.size());
		removeMatchIndexes(leftIndexes, rightIndexes, collectionIgnoreOrderDifference);
		setBestMatchIndexes(leftIndexes, rightIndexes, collectionIgnoreOrderDifference);
	}
	/**一个工具方法，新建一个索引列表，索引值从0开始，到size-1结束<br>
	 * @param size 索引大小
	 * @return 新建的索引，从0 - size-1
	 * */
	protected ArrayList<Integer>createIndexList(int size){
		ArrayList<Integer>indexes=new ArrayList<Integer>();
		for(int i=0;i<size;i++){
			indexes.add(i);
		}return indexes;
	}
	protected HolmosMatchingScoreCalculator getMatchingScoreCalculator(){
		return new HolmosMatchingScoreCalculator();
	}
}
