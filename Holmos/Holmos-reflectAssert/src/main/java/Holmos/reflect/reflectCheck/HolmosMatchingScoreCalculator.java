package holmos.reflect.reflectCheck;

import holmos.reflect.reflectCheck.difference.HolmosClassDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionIgnoreOrderDifference;
import holmos.reflect.reflectCheck.difference.HolmosDifference;
import holmos.reflect.reflectCheck.difference.HolmosMapDifference;
import holmos.reflect.reflectCheck.difference.HolmosObjectDifference;

import java.util.IdentityHashMap;
import java.util.Map;
/**用来处理无序集合和数组的最佳匹配的类，这个类可以计算出一个无序集合和数组的最佳匹配<br>
 * 衡量方法是，对于difference里面的属性，对于不同的属性，予以的权值不一样，那么可以<br>
 * 设定一个整形的score值来判断difference里面两个元素的不同差异程度，越小那么差异越小<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosMatchingScoreCalculator {
	private int CLASS_NOT_EQUAL=10;
	private int SIMPLE_NOT_EQUAL=1;
	/**利用访问者模式，访问HolmosDifference tree，来获得这个tree两个元素的score,实际访问的是无序集合的每个元素*/
	protected HolmosMatchingScoreVisitor matchingScoreVisitor=new HolmosMatchingScoreVisitor();
	/**相当于一个缓存，存储访问者得到的score信息*/
	protected Map<HolmosDifference,Integer> matchingScores=new IdentityHashMap<HolmosDifference, Integer>();
	/**从difference里面得到difference里两个待比较的元素的matching score 值<br>
	 * @param difference 待访问的HolmosDifference tree
	 * @return 访问者得到的两个不同的值
	 * */
	public int calculateMatchingScore(HolmosDifference difference){
		if(difference==null){
			//这个时候difference里面的两个待比较的值完全相同，那么score值为0
			return 0;
		}
		Integer matchingScore=matchingScores.get(difference);
		if(matchingScore==null){
			//如果里面没有匹配的值，那么需要访问difference树，并将访问结果put进缓存里,下次就不用再次计算
			matchingScore=matchingScoreVisitor.visit(difference, null);
			matchingScores.put(difference, matchingScore);
		}
		return matchingScore;
	}
	/**获取匹配的matchingScore,如果class不同，那么返回的值就很大，因为这样的情况下他俩更加的不可能相同
	 * 如果class相同，仅仅是数值不同，那么返回1，这个是在difference不可能为null的情况下<br>
	 * @param difference 待计算matchingScore的difference信息
	 * @return 根据框架规则返回的matchingScore的值
	 * */
	protected int getMatchingScore(HolmosDifference difference){
		Object leftValue=difference.getLeftValue();
		Object rightValue=difference.getRightValue();
		if(leftValue!=null&&rightValue!=null&&!leftValue.getClass().equals(rightValue.getClass())){
			return CLASS_NOT_EQUAL;
		}
		return SIMPLE_NOT_EQUAL;
	}
	/**获取类类型的matchingScore，规则和 {@link HolmosMatchingScoreCalculator#getMatchingScore(HolmosDifference)}
	 * 的一致
	 * */
	protected int getMatchingScore(HolmosClassDifference difference){
		@SuppressWarnings("rawtypes")
		Class leftClass=difference.getLeftClass();
		@SuppressWarnings("rawtypes")
		Class rightClass=difference.getRightClass();
		if(leftClass!=null&&rightClass!=null&&!(leftClass.equals(rightClass))){
			return CLASS_NOT_EQUAL;
		}return SIMPLE_NOT_EQUAL;
	}
	/**获取{@link HolmosCollectionDifference}类型的matchingScore<br>
	 * 规则是返回不同元素的个数*/
	protected int getMatchingScore(HolmosCollectionDifference difference){
		return difference.getAllElementsDifferences().size();
	}
	/**获取{@link HolmosMapDifference}类型的matchingScore<br>
	 * 规则是返回不同元素的个数*/
	protected int getMatchingScore(HolmosMapDifference difference){
		return difference.getValueDifferences().size();
	}
	/**获取{@link HolmosCollectionIgnoreOrderDifference}类型的matchingScore<br>
	 * 规则是返回最佳匹配数值*/
	protected int getMatchingScore(HolmosCollectionIgnoreOrderDifference difference){
		return difference.getBestMatchScore();
	}
	protected class HolmosMatchingScoreVisitor{
		public Integer visit(HolmosDifference difference, Integer argument) {
            return getMatchingScore(difference);
        }

        public Integer visit(HolmosObjectDifference objectDifference, Integer argument) {
            return getMatchingScore(objectDifference);
        }

        public Integer visit(HolmosClassDifference classDifference, Integer argument) {
            return getMatchingScore(classDifference);
        }

        public Integer visit(HolmosMapDifference mapDifference, Integer argument) {
            return getMatchingScore(mapDifference);
        }

        public Integer visit(HolmosCollectionDifference collectionDifference, Integer argument) {
            return getMatchingScore(collectionDifference);
        }

        public Integer visit(HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference, Integer argument) {
            return getMatchingScore(collectionIgnoreOrderDifference);
        }
	}
}
