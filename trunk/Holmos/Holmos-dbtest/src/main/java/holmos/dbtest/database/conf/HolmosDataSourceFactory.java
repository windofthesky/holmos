package holmos.dbtest.database.conf;

import javax.sql.DataSource;

public interface HolmosDataSourceFactory {
	/**
	 * 从配置文件中的配置中获得DataSource，针对不同的配置文件类型，为一个抽象工厂
	 * */
	public DataSource getDataSource();
}
