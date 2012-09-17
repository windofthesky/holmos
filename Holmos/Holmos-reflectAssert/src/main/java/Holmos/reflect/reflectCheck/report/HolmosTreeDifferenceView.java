package holmos.reflect.reflectCheck.report;

import holmos.reflect.reflectCheck.HolmosDifferenceVisitor;
import holmos.reflect.reflectCheck.difference.HolmosClassDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionDifference;
import holmos.reflect.reflectCheck.difference.HolmosCollectionIgnoreOrderDifference;
import holmos.reflect.reflectCheck.difference.HolmosDifference;
import holmos.reflect.reflectCheck.difference.HolmosMapDifference;
import holmos.reflect.reflectCheck.difference.HolmosObjectDifference;
import holmos.reflect.tool.HolmosObjectFormatter;

import java.util.Map;
/**这个将构造一个对象的差异树的全部信息，对于一个无序的数组或者集合，会将其最佳匹配项作为最后的比较结果<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosTreeDifferenceView implements HolmosDifferenceView{
	/**格式化对象的格式化器*/
	protected HolmosObjectFormatter objectFormatter=new HolmosObjectFormatter();
	/**差异树访问对象*/
	protected HolmosTreeDifferenceFormatterVisitor differenceFormatterVisitor=new HolmosTreeDifferenceFormatterVisitor();
	public String createView(HolmosDifference difference) {
		return difference.accept(differenceFormatterVisitor, null);
	}
	/**创建一个简单的视图，只针对difference所指示的对象本身创建视图<br>
	 * @param difference 给定的差异信息
	 * @param fieldName 当前差异信息的拥有者，对象字段名字
	 * */
	protected String formatDifference(HolmosDifference difference,String fieldName){
		return formatValues(fieldName,difference.getLeftValue(),difference.getRightValue());
	}
	/**格式化当前节点的值
	 * @param fieldName 当前该节点的字段名字
	 * @param leftValue 左值
	 * @param rightValue 右值
	 * @return 调用{@link HolmosObjectFormatter}获得leftValue和rightValue<br>
	 * 的信息，然后将这两块信息格式化输出*/
	private String formatValues(String fieldName, Object leftValue,
			Object rightValue) {
		StringBuilder result=new StringBuilder();
		result.append((fieldName==null)?"":fieldName);
		result.append("预期值为:\n");
		result.append(objectFormatter.format(leftValue));
		result.append("\n");
		result.append("实际值为:\n");
		result.append(objectFormatter.format(rightValue));
		result.append("\n");
		return result.toString();
	}
	/**格式化对象差异树信息，并且将给定差异化信息的所有字段信息都一并差异化给出<br>
	 * @param objectDifference 给定的对象差异化信息
	 * @param fieldName 给定对象的节点名字
	 * @return 格式化后的差异化信息
	 * */
	protected String formatDifference(HolmosObjectDifference objectDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		//格式化节点的当前信息
		result.append(formatDifference((HolmosDifference)objectDifference, fieldName));
		//处理子节点信息
		for(Map.Entry<String, HolmosDifference>fieldDifference:objectDifference.getFieldDifferences().entrySet()){
			String innerFieldName=createFieldName(fieldName,fieldDifference.getKey(),true);
			result.append(formatDifference((HolmosDifference)fieldDifference, innerFieldName));
		}
		return result.toString();
	}
	/**格式化类型差异树信息，格式化方法很简单，只需要扫描本层，不需要递归，给出类型差异化信息就OK<br>
	 * @param classDifference 类型差异化树
	 * @param fieldName 给定对象字段名字
	 * @return 格式化后的差异化信息
	 * */
	protected String formatDifference(HolmosClassDifference classDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		result.append((fieldName==null)?"":fieldName);
		result.append("预期类型为:"+classDifference.getLeftValue()+"\n");
		result.append("实际类型为:"+classDifference.getRightValue()+"\n");
		return result.toString();
	}
	/**格式化数组或者集合类型的差异树信息*/
	protected String formatDifference(HolmosCollectionDifference collectionDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		//先处理有的差异化信息
		for(Map.Entry<Integer, HolmosDifference>elementDifference:collectionDifference.getAllElementsDifferences().entrySet()){
			String innerFieldName=createFieldName(fieldName, "["+elementDifference.getKey()+"]", false);
			result.append(collectionDifference.accept(differenceFormatterVisitor, innerFieldName));
		}
		//在处理没有匹配项的信息
		for(Integer leftMiss:collectionDifference.getLeftMissingIndexes()){
			String innerFileldName=createFieldName(fieldName, "["+leftMiss.toString()+"]", false);
			result.append(formatValues(innerFileldName, "", collectionDifference.getRightList().get(leftMiss)));
		}
		for(Integer rightMiss:collectionDifference.getLeftMissingIndexes()){
			String innerFileldName=createFieldName(fieldName, "["+rightMiss.toString()+"]", false);
			result.append(formatValues(innerFileldName, collectionDifference.getRightList().get(rightMiss),""));
		}
		return result.toString();
	}
	/**格式化map类型的差异树信息*/
	protected String formatDifference(HolmosMapDifference mapDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		result.append(formatDifference((HolmosDifference)mapDifference, fieldName));
		//处理有的差异树信息
		for(Map.Entry<Object, HolmosDifference>elementDifference:mapDifference.getValueDifferences().entrySet()){
			String innerFieldName=createFieldName(fieldName, formatObject(elementDifference.getKey()), false);
			result.append(mapDifference.accept(differenceFormatterVisitor, innerFieldName));
		}
		//处理没有匹配的信息,没有的信息用""补齐
		for(Object leftMiss:mapDifference.getLeftMissingKeys()){
			String innerFieldName=createFieldName(fieldName, formatObject(leftMiss), false);
			result.append(formatValues(innerFieldName, "",objectFormatter.format(leftMiss)));
		}
		return result.toString();
	}
	private String formatObject(Object object) {
		if(object==HolmosDefaultDifferentReport.MatchType.NO_MATCH)
			return "没有匹配项";
		else
			return objectFormatter.format(object);
	}
	/**处理无序集合和数组的差异树信息,根据最佳匹配处理*/
	protected String formatDifference(HolmosCollectionIgnoreOrderDifference collectionIgnoreOrderDifference,String fieldName){
		StringBuilder result=new StringBuilder();
		result.append(formatDifference((HolmosDifference)collectionIgnoreOrderDifference, fieldName));
		//根据最佳匹配来进行格式化
		for(Map.Entry<Integer, Integer>matchIndex:collectionIgnoreOrderDifference.getBestMatchIndexes().entrySet()){
			Integer leftIndex=matchIndex.getKey();
			Integer rightIndex=matchIndex.getValue();
			if(leftIndex==-1){
				String innerFieldName=createFieldName(fieldName, "["+rightIndex+"]", false);
				result.append(formatValues(innerFieldName, HolmosDefaultDifferentReport.MatchType.NO_MATCH,
						collectionIgnoreOrderDifference.getRightList().get(rightIndex)));
				continue;
			}
			if(rightIndex==-1){
				String innerFieldName=createFieldName(fieldName, "["+leftIndex+"]", false);
				result.append(formatValues(innerFieldName, collectionIgnoreOrderDifference.getRightList().get(leftIndex),
						HolmosDefaultDifferentReport.MatchType.NO_MATCH));
				continue;
			}
			HolmosDifference difference=collectionIgnoreOrderDifference.getElementDifference(leftIndex, rightIndex);
			if(difference==null)
				continue;
			String innerFieldName = createFieldName(fieldName, "[" + leftIndex + "," + rightIndex + "]", false);
			difference.accept(differenceFormatterVisitor, innerFieldName);
		}
		return result.toString();
	}
	/**一个工具方法，用来构造字段的名字，带有两级信息的字段的名字
	 * @param fieldName 上级名字
	 * @param innerFieldName 下级名字
	 * @param true 带有点号<br>false 不带点号*/
	private String createFieldName(String fieldName, String innerFieldName,
			boolean includePoint) {
		StringBuilder fullName=new StringBuilder();
		if(fieldName==null||fieldName==""){
			fullName.append(innerFieldName);
		}else{
			if(includePoint){
				fullName.append(fieldName+"."+innerFieldName);
			}else{
				fullName.append(fieldName+innerFieldName);
			}
		}
		return fullName.toString();
	}
	/**树格式化器的访问者，作为递归格式化差异树的实现者,visit方法是调用accept方法的入口<br>
	 * @author 吴银龙(15857164387)
	 * */
	protected class HolmosTreeDifferenceFormatterVisitor implements HolmosDifferenceVisitor<String, String>{

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
