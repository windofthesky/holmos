package Holmos.Holmos.testlistener.modules.imp;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import Holmos.Holmos.testlistener.HolmosTestListener;
import Holmos.Holmos.testlistener.modules.HolmosModule;
import Holmos.Holmos.tools.HolmosPropertiesTool;

/**
 * 数据库测试模块,数据库测试模块暂时不单独提供，只为其中两个方法进行提供
 * 
 * @author 吴银龙(15857164387)
 * */
public class HolmosDBModule implements HolmosModule{
	/* Propery key of the database driver class name */
    public static final String PROPKEY_DATASOURCE_DRIVERCLASSNAME = "database.driverClassName";

    /* Property key of the datasource url */
    public static final String PROPKEY_DATASOURCE_URL = "database.url";

    /* Property key of the datasource connect username */
    public static final String PROPKEY_DATASOURCE_USERNAME = "database.userName";

    /* Property key of the datasource connect password */
    public static final String PROPKEY_DATASOURCE_PASSWORD = "database.password";
    
    
	/**Holmos框架的配置*/
	private Properties properties;
	
	public void flushDatabaseUpdates(Object testObject){
		//do nothing here
	}
	public DataSource getDataSource(){
		return createDataSource();
	}

	@Override
	public void init(Properties properties) {
		// TODO Auto-generated method stub
		this.properties=properties;
	}
	private DataSource createDataSource(){
		/* The name of the <code>java.sql.Driver</code> class. */
	    String driverClassName;
	    String databaseUrl;
	    String userName;
	    String password;
	    
		driverClassName = HolmosPropertiesTool.getValue(properties, PROPKEY_DATASOURCE_DRIVERCLASSNAME);
        databaseUrl = HolmosPropertiesTool.getValue(properties, PROPKEY_DATASOURCE_URL);
        userName = HolmosPropertiesTool.getValue(properties, PROPKEY_DATASOURCE_USERNAME, null);
        password = HolmosPropertiesTool.getValue(properties, PROPKEY_DATASOURCE_PASSWORD,null);
        
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setUrl(databaseUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
	}
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HolmosTestListener createListener() {
		// TODO Auto-generated method stub
		return null;
	}
}
