package Holmos.dbtest.database.connecion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.database.AbstractDatabaseConnection;

import Holmos.dbtest.database.HolmosDataBaseTools;
/**Holmos框架的数据库连接，继承了Dbunit数据库单元测试框架的{@link AbstractDatabaseConnection}，这个数据库连接从{@link BasicDataSource}构造而来<br>
 * */
public class HolmosDataBaseConnection extends AbstractDatabaseConnection{
	/**配置好的数据源
	 * */
	private DataSource dataSource;
	/**
	 * 返回配置好的数据源
	 * @see HolmosDataSource
	 * */
	public DataSource getDataSource() {
		return dataSource;
	}
	/**DataSet对应的Schema的名字*/
	private String schemaName;
	/**返回DataSet对应的Schema的名字
	 * @see HolmosSchema*/
	public String getSchemaName() {
		return schemaName;
	}
	/**构造函数
	 * @param dataSource 数据源对象
	 * @param schemaName DataSet对应的Schema名字
	 * */
	public HolmosDataBaseConnection(DataSource dataSource,String schemaName){
		this.dataSource=dataSource;
		this.schemaName=schemaName;
	}
	/*当前的数据库连接*/
	private Connection currentConnection;
	/*是currentConnection的一份拷贝，目的是为了一个在一个事务执行提交完毕之前，currentConnection可能遇到异常情况，会对当前的操作有影响，
	 * 为了避免这个影响，创建了这样的一份拷贝*/
	private Connection nativeCurrentConnection;
	/**获取数据库连接，实际上获取的是一份拷贝*/
	public Connection getConnection() throws SQLException {
		if(currentConnection==null){
			//如果当前的数据库连接没有初始化，那么这个时候初始化数据库连接，并获取一份拷贝
			currentConnection=HolmosDataBaseTools.getConnection(dataSource);
			nativeCurrentConnection=getNativeConnection(currentConnection);
		}
		else{
			//如果已经初始化，那么就不管当前的数据库连接是否有效或者是否关闭，返回有效的nativeCurrentConnection
		}
		return nativeCurrentConnection;
	}
	/**通过currentConnection的数据库元数据对象来获得一个connection的实例拷贝,如果当前连接有效，那么正常返回拷贝，如果无效，那么返回此无效连接
	 * @param connection 获取数据库连接的拷贝母体
	 * */
	protected Connection getNativeConnection(Connection connection){
		try {
			DatabaseMetaData metadata=connection.getMetaData();
			if(metadata!=null){
				//当数据库连接无效的时候，metadata=null
				Connection nativeConnection=metadata.getConnection();
				if(nativeConnection!=null){
					return nativeConnection;
				}
			}
		} catch (SQLException e) {
			//当数据库无法访问或者connection被关闭的时候抛出此异常
			e.printStackTrace();
		}
		//如果当前连接无效或者连接被关闭，那么返回此异常connection
		return connection;
	}
	public String getSchema() {
		return schemaName;
	}

	public void close() throws SQLException {
		// 这里什么也不做，因为在每一次Dbunit操作的时候，连接都会被关闭掉
	}
	/**关闭最近一次由 {@link #getConnection()}获取的数据库连接*/
	public void closeJDBCConnection(){
		if(currentConnection!=null){
			//如果为null，那么这个连接还没有进行初始化，不做关闭处理
			HolmosDataBaseTools.releaseConnection(currentConnection, dataSource);
			currentConnection=null;
			nativeCurrentConnection=null;
			//为了下次初始化的时候用到
		}
	}
}
