package holmos.reflect.reflectCheck.comparator;

import holmos.reflect.basetool.HolmosCollectionTool;
import holmos.reflect.reflectCheck.HolmosRefectionComparatorMode;
import holmos.reflect.reflectCheck.HolmosReflectionComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**比较器产生的工厂，采用工厂模式，并且在编译期间已经将比较器生成完毕<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosComparatorFactory {
	/**Collection比较器*/
	public static final HolmosComparator HOLMOS_COLLECTION_COMPARATOR=new HolmosCollectionComparator();
	/**日期比较器*/
	public static final HolmosComparator HOLMOS_DATE_COMPARATOR=new HolmosDateComparator();
	/**map和set比较器*/
	public static final HolmosComparator HOLMOS_MAP_COMPARATOR=new HolmosMapComparator();
	/**Object比较器*/
	public static final HolmosComparator HOLMOS_OBJECT_COMPARATOR=new HolmosObjectComparator();
	/**Simple比较器*/
	public static final HolmosComparator HOLMOS_SIMPLE_COMPARATOR=new HolmosSimpleComparator();
	/**java默认比较器*/
	public static final HolmosComparator HOLMOS_IGNORE_DEFAULT_COMPARATOR=new HolmosIgnoreDefaultComparator();
	/**顺序无关Collection比较器*/
	public static final HolmosComparator HOLMOS_IGNORE_ORDER_COLLECTION_COMPARATOR=new HolmosIgnoreOrderCollectionComparator();
	/**Number比较器*/
	public static final HolmosNumberComparator HOLMOS_NUMBER_COMPARATOR=new HolmosNumberComparator();
	/**新建一个反射比较器，根据已经给好的比较器列表，给定的比较器列表都是宽松式的比较器，如果给定的比较器的列表为null<br>
	 * 这个时候会建立含有所有严格式的比较器链的反射比较器，具体的规则在getComparatorChain 方法给出<br>
	 * @param modes 给定的反射比较器中用到的比较器列表
	 * @return 新建的反射比较器
	 * */
	public static HolmosReflectionComparator createRefectionComparator(HolmosRefectionComparatorMode... modes) {
        List<HolmosComparator> comparators = getComparatorChain(HolmosCollectionTool.asSet(modes));
        return new HolmosReflectionComparator(comparators);
    }
	/**根据比较器列表建立一个宽松式比较器的链，如果modes为null，那么将建立严格式的比较器链<br>
	 * @param modes 给定的宽松式的比较器类型
	 * @return 建立的比较器链
	 * */
	protected static List<HolmosComparator> getComparatorChain(Set<HolmosRefectionComparatorMode> modes) {
		List<HolmosComparator>comparatorChain=new ArrayList<HolmosComparator>();
		if(modes.contains(HolmosRefectionComparatorMode.DATE)){
			comparatorChain.add(HOLMOS_DATE_COMPARATOR);
		}
		if(modes.contains(HolmosRefectionComparatorMode.IGNORE_DEFAULT)){
			comparatorChain.add(HOLMOS_IGNORE_DEFAULT_COMPARATOR);
		}
		if(modes.contains(HolmosRefectionComparatorMode.IGNORE_COLLECTION_ORDER)){
			comparatorChain.add(HOLMOS_IGNORE_ORDER_COLLECTION_COMPARATOR);
		}
		comparatorChain.add(HOLMOS_COLLECTION_COMPARATOR);
		comparatorChain.add(HOLMOS_MAP_COMPARATOR);
		comparatorChain.add(HOLMOS_SIMPLE_COMPARATOR);
		comparatorChain.add(HOLMOS_NUMBER_COMPARATOR);
		comparatorChain.add(HOLMOS_OBJECT_COMPARATOR);
		return comparatorChain;
	}
}
