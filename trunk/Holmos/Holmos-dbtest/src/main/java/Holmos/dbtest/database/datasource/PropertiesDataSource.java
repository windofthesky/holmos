package Holmos.dbtest.database.datasource;

import java.util.Properties;

import Holmos.dbtest.database.HolmosDataBaseTools;

/**properties文件配置的数据源
 * @author 吴银龙(15857164387)
 * */
public class PropertiesDataSource extends HolmosDataSource{
	
	/**properties文件中数据库驱动名字的关键字key*/
	public static final String KEY_DATASOURCE_DRIVERCLASSNAME = "database.driverClassName";

    /** 数据库连接url的关键字key*/
    public static final String KEY_DATASOURCE_URL = "database.url";

    /** 用户名关键字key */
    public static final String KEY_DATASOURCE_USERNAME = "database.userName";

    /** 密码关键字key */
    public static final String KEY_DATASOURCE_PASSWORD = "database.password";
    /**根据properties文件里面的配置内容进行数据源的初始化
     * @param properties 数据源配置文件
     * */
    public void init(Properties properties){
    	dataBaseDriver=HolmosDataBaseTools.getPropertiesValue(properties, KEY_DATASOURCE_DRIVERCLASSNAME);
    	databaseUrl=HolmosDataBaseTools.getPropertiesValue(properties, KEY_DATASOURCE_URL);
    	userName=HolmosDataBaseTools.getPropertiesValue(properties, KEY_DATASOURCE_USERNAME);
    	password=HolmosDataBaseTools.getPropertiesValue(properties, KEY_DATASOURCE_PASSWORD);
    }
}
