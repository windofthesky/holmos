package Holmos.Holmos.data.database.annotation;

import Holmos.Holmos.data.database.datasetfactory.HolmosDataSetFactory;
import Holmos.Holmos.data.database.datasetloadstrategy.HolmosDataSetLoadStrategy;

/**预期的数据源，在参与数据库测试之后，需要将数据库的信息和此注解指定的数据源进行校验
 * @author 吴银龙(15857164387)
 * */
public @interface HolmosExpectedDataSet {
	String[] value() default {};
	Class<? extends HolmosDataSetLoadStrategy> loadStrategy() default HolmosDataSetLoadStrategy.class;
	Class<? extends HolmosDataSetFactory> factory() default HolmosDataSetFactory.class;
}
