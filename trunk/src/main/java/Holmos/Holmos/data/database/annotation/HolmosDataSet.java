package Holmos.Holmos.data.database.annotation;

import Holmos.Holmos.data.database.datasetfactory.HolmosDataSetFactory;
import Holmos.Holmos.data.database.datasetloadstrategy.HolmosDataSetLoadStrategy;

/**加上此注解，将会启动数据库测试
 * @author 吴银龙(15857164387)
 * */
public @interface HolmosDataSet {
	String[] value() default {};
	Class<? extends HolmosDataSetLoadStrategy> loadStrategy() default HolmosDataSetLoadStrategy.class;
	Class<? extends HolmosDataSetFactory> factory() default HolmosDataSetFactory.class;
}
