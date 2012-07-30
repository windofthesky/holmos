package Holmos.Holmos.assertVerify.tool;

import java.util.Collection;
import java.util.Map;

/**将集合类型和数组类型的日志信息，格式化为一组规定好的字符窜
 * @author 吴银龙(15857164387)
 * */
public class HolmosArrayAndCollectionFormatter {
	/**格式化的最大的数组或者集合的元素个数，之后的将不再日志记录中展示<br>
	 * 以默认的格式展示，比如"..."
	 * */
	protected int maxElementCount;
	/**普通对象的日志格式化器*/
	protected HolmosObjectFormatter objectFormatter;
	/**{@link HolmosArrayAndCollectionFormatter}构造器<br>
	 * @param maxElementCount 格式化的最大的数组或者集合的元素个数
	 * @param objectFormatter 普通对象的日志格式化器*/
	public HolmosArrayAndCollectionFormatter(int maxElementCount,HolmosObjectFormatter objectFormatter){
		this.maxElementCount=maxElementCount;
		this.objectFormatter=objectFormatter;
	}
	/**格式化给定的数组<br>
	 * @param array 要进行格式化的数组
	 * @param currentDepth 当前数组所在的递归层次
	 * @param result 此格式化器格式化的结果，调用这个方法，会变更result的值
	 * */
	public void formatterArrays(Object array, int currentDepth, StringBuilder result){
		if (array instanceof byte[]) {
            formatByteArray((byte[]) array, result);
            return;
        }
        if (array instanceof short[]) {
            formatShortArray((short[]) array, result);
            return;
        }
        if (array instanceof int[]) {
            formatIntArray((int[]) array, result);
            return;
        }
        if (array instanceof long[]) {
            formatLongArray((long[]) array, result);
            return;
        }
        if (array instanceof char[]) {
            formatCharArray((char[]) array, result);
            return;
        }
        if (array instanceof float[]) {
            formatFloatArray((float[]) array, result);
            return;
        }
        if (array instanceof double[]) {
            formatDoubleArray((double[]) array, result);
            return;
        }
        if (array instanceof boolean[]) {
            formatBooleanArray((boolean[]) array, result);
            return;
        }
        formatObjectArray((Object[]) array, currentDepth, result);
	}
	/**格式化object类型数组
	 * @param array 待格式化的object类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatObjectArray(Object[] array, int currentDepth,
			StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}
			//递归调用
			formatObjectArray(array, currentDepth+1, result);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
		
	}
	/**格式化boolean类型数组
	 * @param array 待格式化的boolean类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatBooleanArray(boolean[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化double类型数组
	 * @param array 待格式化的double类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatDoubleArray(double[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化float类型数组
	 * @param array 待格式化的float类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatFloatArray(float[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化char类型数组
	 * @param array 待格式化的char类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatCharArray(char[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化long类型数组
	 * @param array 待格式化的long类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatLongArray(long[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化int类型数组
	 * @param array 待格式化的int类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatIntArray(int[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化short类型数组
	 * @param array 待格式化的short类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatShortArray(short[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化byte类型数组，格式化的样式是[element1,element2,...]
	 * @param array 待格式化的byte类型数组
	 * @param result 格式化结果信息
	 * */
	private void formatByteArray(byte[] array, StringBuilder result) {
		result.append("[");
		int i=0;
		for(;i<array.length&&i<maxElementCount;i++){
			if(i>0){
				result.append(',');
			}result.append(array[i]);
		}
		if(i<array.length){
			result.append("...");
		}result.append("]");
	}
	/**格式化Collection对象，由于Collection类型的元素不知道是何类型，所以需要调用objectFormatter.formatImpl方法<br>
	 * @param collection 待格式化的collection
	 * @param currentDepth 当前collection所在的递归深度
	 * @param result 格式化结果信息
	 * */
	public void formatCollection(Collection <?> collection,int currentDepth,StringBuilder result){
		result.append("[");
		int i=0;
		for(Object element:collection){
			if(i>0){
				result.append(",");
			}
			objectFormatter.formatImpl(element, currentDepth+1, result);
			if(i>=maxElementCount&&i<collection.size()){
				result.append("...");
				break;
			}
			i++;
		}
		result.append("]");
	}
	/**格式化map对象，由于Map对象不知道Map元素不知道是何种类型，所以需要调用objectFormatter.formatImpl方法分别获得map的key何value信息<br>
	 * @param map 待格式化的map
	 * @param currentDepth 当前map所在的递归深度
	 * @param result 格式化结果信息
	 * */
	public void formatMap(Map<?,?>map,int currentDepth,StringBuilder result){
		result.append("[");
		int i=0;
		for(Map.Entry<?, ?> element : map.entrySet()){
			if(i>0){
				result.append(",");
			}
			objectFormatter.formatImpl(element.getKey(), currentDepth, result);
			result.append("=");
			objectFormatter.formatImpl(element.getValue(), currentDepth+1, result);
			if(i>=maxElementCount&&i<map.size()){
				result.append("...");
				break;
			}
			i++;
		}
		result.append("]");
	}
}
