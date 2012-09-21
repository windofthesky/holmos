package holmos.dbtest.database.operation;

import holmos.dbtest.database.HolmosDataBaseTools;
import holmos.webtest.exceptions.HolmosFailedError;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
public class DefaultSQLOperation implements SQLOperation{
	private static Logger logger=Logger.getLogger(DefaultSQLOperation.class);
	
	/**数据源，所有操作在此数据源上进行*/
	private DataSource dataSource;
	/**dry mode,如果为true，那么写操作不生效*/
	private boolean dryMode;
	public boolean isDryMode() {
		return dryMode;
	}
	public DefaultSQLOperation(DataSource dataSource){
		this(dataSource,false);
	}
	public DefaultSQLOperation(DataSource dataSource,boolean dryMode){
		this.dataSource=dataSource;
		this.dryMode=dryMode;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public int executeUpdate(String sql) {
		if(dryMode){
			return 0;
		}
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		try{
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			return statement.executeUpdate(sql);
		}catch (Exception e) {
			HolmosDataBaseTools.closeWithoutException(connection, statement, null);
			throw new HolmosFailedError("执行失败!");
		} 
	}
	
	public void executeQuery(String sql) {
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
		} catch (SQLException e) {
			throw new HolmosFailedError("查询失败!");
		}finally{
			HolmosDataBaseTools.closeWithoutException(connection, statement, resultSet);
		}
	}
	
	public int executeUpdateAndCommit(String sql) {
		if(dryMode){
			return 0;
		}
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		try{
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			int changeLines=statement.executeUpdate(sql);
			if(!connection.getAutoCommit())
				connection.commit();
			return changeLines;
		}catch (Exception e) {
			HolmosDataBaseTools.closeWithoutException(connection, statement, null);
			throw new HolmosFailedError("执行失败!");
		}
	}
	public long getItemAsLong(String sql) {
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			if(resultSet.next())
				return resultSet.getLong(1);
		} catch (SQLException e) {
			throw new HolmosFailedError("查询失败!");
		}finally{
			HolmosDataBaseTools.closeWithoutException(connection, statement, resultSet);
		}
		throw new HolmosFailedError("没有找到结果!");
	}
	public String getItemAsString(String sql) {
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			if(resultSet.next())
				return resultSet.getString(1);
		} catch (SQLException e) {
			throw new HolmosFailedError("查询失败!");
		}finally{
			HolmosDataBaseTools.closeWithoutException(connection, statement, resultSet);
		}
		throw new HolmosFailedError("没有找到结果!");
	}
	public Set<String> getItemsAsStringSet(String sql) {
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			Set<String>result=new HashSet<String>();
			while(resultSet.next())
				result.add(resultSet.getString(1));
			return result;
		} catch (SQLException e) {
			throw new HolmosFailedError("查询失败!");
		}finally{
			HolmosDataBaseTools.closeWithoutException(connection, statement, resultSet);
		}
	}
	public boolean exists(String sql) {
		logger.info(sql);
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			connection=dataSource.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			return resultSet.next();
		} catch (SQLException e) {
			throw new HolmosFailedError("查询失败!");
		}finally{
			HolmosDataBaseTools.closeWithoutException(connection, statement, resultSet);
		}
	}
}
