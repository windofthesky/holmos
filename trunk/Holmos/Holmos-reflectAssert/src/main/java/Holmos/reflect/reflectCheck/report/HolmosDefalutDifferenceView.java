package holmos.reflect.reflectCheck.report;

import static org.apache.commons.lang.ClassUtils.getShortClassName;
import holmos.reflect.reflectCheck.HolmosDifferenceVisitor;
import holmos.reflect.reflectCheck.difference.HolmosClassDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionIgnoreOrderDifference;
import holmos.reflect.reflectCheck.difference.HolmosDifference;
import holmos.reflect.reflectCheck.difference.HolmosMapDifference;
import holmos.reflect.reflectCheck.difference.HolmosObjectDifference;
import holmos.reflect.tool.HolmosObjectFormatter;

import java.util.List;
import java.util.Map;
/**根据已知的Difference来新建一个报表，如果如果difference是一个简单的类型的difference，那么就直接调用<br>
 * {@link HolmosSimpleDifferenceView}来新建一个简单的报表，如果是一个对象，那么仍需调用{@linkplain HolmosDefalutDifferenceView}<br>
 * 给出差异树<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosDefalutDifferenceView implements HolmosDifferenceView{

	/**如果对一个无序数组或者集合格式化了，那么就将此值设置为true，是一个标记*/
	protected boolean isUnorderCollectionFormatted=false;
	/**Object对象格式化器，用来格式化Object对象*/
	protected HolmosObjectFormatter objectFormatter=new HolmosObjectFormatter();
	protected HolmosDifferenceFormatterVisitor holmosDifferenceFormatterVisitor=new HolmosDifferenceFormatterVisitor();
	/**根据difference差异树构造一个差异的视图
	 * @param difference 给定的差异树
	 * @return 构造的差异视图
	 * */
	public String createView(HolmosDifference difference) {
		return difference.accept(holmosDifferenceFormatterVisitor, null);
	}
	protected String formatDifference(HolmosDifference holmosDifference,String fieldName){
		return formatValues(fieldName, holmosDifference.getLeftValue(), holmosDifference.getRightValue());
	}
	/**
	 * 根据{@link HolmosObjectDifference}来建立差异视图
	 * @param objectDifference 对象差异树
	 * @param fieldName 该对象的变量名
	 * @return 构造的视图（String）
	 * */
	protected String formatDifference(HolmosObjectDifference objectDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		for(Map.Entry<String, HolmosDifference> fieldDifference:objectDifference.getFieldDifferences().entrySet()){
			String innerFieldName=createFieldName(fieldName, fieldDifference.getKey(), true);
			result.append(fieldDifference.getValue().accept(holmosDifferenceFormatterVisitor, innerFieldName));
		}return result.toString();
	}
	/**创建字段名字
	 * @param fieldName 外层字段名
	 * @param innerFieldName 内层字段名
	 * @param true,加点号，否则不加
	 * */
	private String createFieldName(String fieldName,String innerFieldName,boolean includePoint){
		if(fieldName==null)
			return innerFieldName;
		if(includePoint)
			return fieldName+"."+innerFieldName;
		else
			return fieldName+innerFieldName;
	}
	protected String formatDifference(HolmosClassDifference classDifference,String fieldName){
		StringBuilder result = new StringBuilder();
	    result.append("预期的对象类型:").append(getShortClassName(classDifference.getLeftClass()));
	    result.append(", 实际的对象类型: ").append(getShortClassName(classDifference.getRightClass())).append("\n");
	    return result.toString();
	}
	protected String formatDifference(HolmosCollectionDifference collectionDifference,String fieldName){
		StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, HolmosDifference> elementDifferences : collectionDifference.getAllElementsDifferences().entrySet()) {
            String innerFieldName = createFieldName(fieldName, "[" + elementDifferences.getKey() + "]", false);
            result.append(elementDifferences.getValue().accept(holmosDifferenceFormatterVisitor, innerFieldName));
        }
        //处理没有匹配到的项
        List<?> leftList = collectionDifference.getLeftList();
        List<?> rightList = collectionDifference.getRightList();
        for (Integer leftIndex : collectionDifference.getLeftMissingIndexes()) {
            String innerFieldName = createFieldName(fieldName, "[" + leftIndex + "]", false);
            result.append(formatValues(innerFieldName, leftList.get(leftIndex), HolmosDefaultDifferentReport.MatchType.NO_MATCH));
        }
        for (Integer rightIndex : collectionDifference.getRightMissingIndexes()) {
            String innerFieldName = createFieldName(fieldName, "[" + rightIndex + "]", false);
            result.append(formatValues(innerFieldName, HolmosDefaultDifferentReport.MatchType.NO_MATCH, rightList.get(rightIndex)));
        }
        return result.toString();
	}
	protected String formatDifference(HolmosMapDifference mapDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		for(Map.Entry<Object, HolmosDifference> mapValueDifference:mapDifference.getValueDifferences().entrySet()){
			String innerFieldName=createFieldName(fieldName, formatObject(mapValueDifference.getKey()), true);
			result.append(mapValueDifference.getValue().accept(holmosDifferenceFormatterVisitor, innerFieldName));
		}
		//处理没有匹配到的项
		Map<?, ?> leftMap = mapDifference.getLeftMap();
        Map<?, ?> rightMap = mapDifference.getRightMap();
        for (Object leftKey : mapDifference.getLeftMissingKeys()) {
            String innerFieldName = createFieldName(fieldName, formatObject(leftKey), true);
            result.append(formatValues(innerFieldName, leftMap.get(leftKey), ""));
        }
        for (Object rightKey : mapDifference.getRightMissingKeys()) {
            String innerFieldName = createFieldName(fieldName, formatObject(rightKey), true);
            result.append(formatValues(innerFieldName, rightMap.get(rightKey), ""));
        }
        return result.toString();
	}
	/**处理无序数组或者集合的格式化，格式化的步骤是，找最佳匹配，然后如果其中一个为-1<br>
	 * 那么此项无匹配项，分左右，然后如果根据索引找到的匹配项为null 则此项由匹配项，<br>
	 * 并且匹配成功，否则有匹配项，但是不相同<br>
	 * @param collectionIgnoreOrderDifference 待格式化的无序数组或者集合的差异树
	 * @param fieldName 当前这个数组或者集合的变量名
	 * @return 格式化的字符窜
	 * */
	protected String formatDifference(HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		result.append(fieldName+":");
		if(collectionIgnoreOrderDifference.getLeftList().size()!=collectionIgnoreOrderDifference.getRightList().size()){
			result.append("两个待比较的数组或者集合的长度不一致,左值(第一个数组或者集合)的长度为:"+collectionIgnoreOrderDifference
					.getLeftList().size()+"右值(第二个数组或集合)的长度为:"+collectionIgnoreOrderDifference.getRightList().size());
		}
		Map<Integer,Integer>bestMatchIndexes=collectionIgnoreOrderDifference.getBestMatchIndexes();
		for(Map.Entry<Integer, Integer>match:bestMatchIndexes.entrySet()){
			int leftIndex=match.getKey();
			int rightIndex=match.getValue();
			if(leftIndex==-1){
				//左值缺
				String innerFieldName = createFieldName(fieldName, "[x," + rightIndex + "]", false);
				result.append(formatValues(innerFieldName, HolmosDefaultDifferentReport.MatchType.NO_MATCH, collectionIgnoreOrderDifference
						.getRightList().get(rightIndex)));
			}if(rightIndex==-1){
				//右值缺
				String innerFieldName= createFieldName(fieldName,"[x," + rightIndex + "]", false);
				result.append(formatValues(innerFieldName, collectionIgnoreOrderDifference.getLeftList().get(leftIndex), 
						HolmosDefaultDifferentReport.MatchType.NO_MATCH));
			}
			HolmosDifference elementDifference=collectionIgnoreOrderDifference.getElementDifference(leftIndex, rightIndex);
			if(elementDifference==null){
				continue;
			}
			String innerFieldName = createFieldName(fieldName, "[" + leftIndex + "," + rightIndex + "]", false);
	        result.append(elementDifference.accept(holmosDifferenceFormatterVisitor, innerFieldName));
		}
		return result.toString();
	}
	private String formatObject(Object object){
		if(object==HolmosDefaultDifferentReport.MatchType.NO_MATCH){
			return "没有匹配项";
		}else{
			return objectFormatter.format(object);
		}
	}
	private String formatValues(String fieldName, Object leftValue,
			Object rightValue) {
		String leftValueFormatted=formatObject(leftValue);
		String rightValueFormatted=formatObject(rightValue);
		String result=formatToOneLine(fieldName, leftValueFormatted, rightValueFormatted);
		if(result.length()>HolmosDefaultDifferentReport.MAX_LINE_SIZE){
			result=formatToMultiLines(fieldName, leftValueFormatted, rightValueFormatted);
		}
		return result;
	}
	private String formatToOneLine(String fieldName,String expectedStr,String actualStr){
		StringBuilder result=new StringBuilder();
		if(fieldName!=null)
			result.append(fieldName+":");
		result.append("预期值为:"+expectedStr);
		result.append("  实际值为:"+actualStr);
		return result.toString();
	}
	private String formatToMultiLines(String fieldName,String expectedStr,String actualStr){
		StringBuilder result=new StringBuilder();
		if(fieldName!=null)
			result.append(fieldName+":\n");
		result.append("预期值为:"+expectedStr);
		result.append("\n  实际值为:"+actualStr);
		result.append("\n");
		return result.toString();
	}
	/**遍历HolmosDifference差异树的工具类
	 * @author yinlong*/
	protected class HolmosDifferenceFormatterVisitor implements HolmosDifferenceVisitor<String, String>{

		public String visit(HolmosDifference difference, String fieldName) {
            return formatDifference(difference, fieldName);
        }

        public String visit(HolmosObjectDifference objectDifference, String fieldName) {
            return formatDifference(objectDifference, fieldName);
        }

        public String visit(HolmosClassDifference classDifference, String fieldName) {
            return formatDifference(classDifference, fieldName);
        }

        public String visit(HolmosMapDifference mapDifference, String fieldName) {
            return formatDifference(mapDifference, fieldName);
        }

        public String visit(HolmosCollectionDifference collectionDifference, String fieldName) {
            return formatDifference(collectionDifference, fieldName);
        }

        public String visit(HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference, String fieldName) {
            return formatDifference(collectionIgnoreOrderDifference, fieldName);
        }
		
	}
}
