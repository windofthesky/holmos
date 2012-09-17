package holmos.reflect.reflectCheck.comparator;

import holmos.reflect.basetool.HolmosCollectionTool;
import holmos.reflect.reflectCheck.HolmosReflectionComparator;
import holmos.reflect.reflectCheck.difference.HolmosCollectionDifference;
import holmos.reflect.reflectCheck.difference.HolmosDifference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**collection和Array的比较器;提供顺序比较和无序比较两种比较方式<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosCollectionComparator implements HolmosComparator{

	public boolean canCompare(Object leftValue, Object rightValue) {
		if(leftValue==null||rightValue==null)
			return false;
		else if((leftValue.getClass().isArray()||leftValue instanceof Collection)&&
				(rightValue.getClass().isArray()||rightValue instanceof Collection))
			return true;
		return false;
	}

	public HolmosDifference compare(Object leftValue, Object rightValue
			,boolean onlyFirstDifference,HolmosReflectionComparator holmosreflectionComparator) {
		List<Object> leftList = new ArrayList<Object>(HolmosCollectionTool.convertToCollection(leftValue));
        List<Object> rightList = new ArrayList<Object>(HolmosCollectionTool.convertToCollection(rightValue));
        int elementIndex = -1;
        HolmosCollectionDifference difference = new HolmosCollectionDifference(leftValue, rightValue, leftList, rightList,"Different elements");
        Iterator<?> leftIterator = leftList.iterator();
        Iterator<?> rightIterator = rightList.iterator();
        while (leftIterator.hasNext() && rightIterator.hasNext()) {
            elementIndex++;
            HolmosDifference elementDifference=holmosreflectionComparator.getDifference(
            		leftIterator.next(), rightIterator.next(), onlyFirstDifference);
            if(elementDifference!=null){
            	difference.addElementDifference(elementIndex, elementDifference);
            	if(onlyFirstDifference){
            		return difference;
            	}
            }
        }
        //检查leftValue和rightValue两个不对应的元素，startIndex开始于两个Collection长度不一致的地方
        int startIndex=elementIndex;
        while(leftIterator.hasNext()){
        	leftIterator.next();
        	difference.addLeftMissingIndex(++startIndex);
        }
        while(rightIterator.hasNext()){
        	rightIterator.next();
        	difference.addRightMissingIndex(++startIndex);
        }
        if(difference.getAllElementsDifferences().isEmpty()&&difference.getLeftMissingIndexes().isEmpty()
        		&&difference.getRightMissingIndexes().isEmpty()){
        	//如果比较的结果没有不同的信息，也没有左右长度不匹配的信息，那么则判定leftValue和rightValue反射比较一致
        	return null;
        }
		return difference;
	}
	
}
