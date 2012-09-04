package holmos.dbtest.database.datasource;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import Holmos.webtest.basetools.HolmosBaseTools;

/**管理数据源的抽象父类，Holmos框架对数据源的配置支持的很好，支持的配置文件类型<br>
 * <li>properties文件</li>
 * <li>xml文件</li>
 * <li>excel文件</li>
 * <li>普通文本.txt文件</li>*/
public abstract class HolmosDataSource {


	protected Logger logger=Logger.getLogger(this.getClass().getName());
	{
		HolmosBaseTools.configLogProperties();
	}
	/**数据库连接url*/
	protected String databaseUrl=null;
	/**数据库驱动名字*/
	protected String dataBaseDriver=null;
	/**登录数据库的用户名*/
	protected String userName=null;
	/**登录数据库的密码*/
	protected String password=null;
	/**根据配置文件中的配置，创建数据源*/
	public DataSource createDataSource() {
		logger.info("开始配置数据源!");
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setDriverClassName(dataBaseDriver);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

}
