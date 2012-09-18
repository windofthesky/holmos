package holmos.dbtest.database.annotation;

import holmos.dbtest.database.datasetfactory.HolmosDataSetFactory;
import holmos.dbtest.database.datasetloadstrategy.HolmosDataSetLoadStrategy;

/**加上此注解，将会启动数据库测试
 * @author 吴银龙(15857164387)
 * */
public @interface HolmosDataSet {
	String[] value() default {};
	Class<? extends HolmosDataSetLoadStrategy> loadStrategy() default HolmosDataSetLoadStrategy.class;
	Class<? extends HolmosDataSetFactory> factory() default HolmosDataSetFactory.class;
}
