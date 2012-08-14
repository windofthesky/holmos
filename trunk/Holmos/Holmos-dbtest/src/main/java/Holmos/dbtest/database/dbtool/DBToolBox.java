package Holmos.dbtest.database.dbtool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Properties;
import java.util.Set;

import Holmos.dbtest.database.operation.SQLOperation;
import Holmos.webtest.basetools.HolmosPropertiesTool;
import Holmos.webtest.exceptions.HolmosFailedError;

/**
 * 数据库的工具方法支持类就是工具箱，提供了对数据库的一些常规的不针对特定表的操作,比如说获得这个连接的数据库的所有表名，所有列名<p>
 * 获得触发器，获得约束，取消约束...
 * 
 * @author 吴银龙(15857164387)
 * */
public abstract class DBToolBox {
	public IdentifierCase getStoredIdentifierCase() {
		return storedIdentifierCase;
	}
	public SQLOperation getOperation() {
		return operation;
	}
	public DBToolBox(String databaseDialect){
		this.databaseDialect=databaseDialect;
	}
	/*DBMS的类型*/
	private String databaseDialect;
	/**
	 * 获得DBMS的类型
	 * */
	public String getDatabaseDialect(){
		return databaseDialect;
	}
	private String schemaName;
	public String getSchemaName() {
		return schemaName;
	}
	private String identifierQuoteString;
	private IdentifierCase storedIdentifierCase;
	public String getIdentifierQuoteString(){
		return identifierQuoteString;
	}
	/**用来实际进行数据库操作的对象*/
	private SQLOperation operation;
	/**
     * 不带标识符引用字符窜的标识符是否区分大小写的配置(lower_case, upper_case, mixed_case, auto)
     */
    public static final String PROPKEY_STORED_IDENTIFIER_CASE = "database.storedIndentifierCase";

    /**
     * 设置SQL标识符引用字符窜(比如"\""[""]""等等)
     */
    public static final String PROPKEY_IDENTIFIER_QUOTE_STRING = "database.identifierQuoteString";
    
    public void init(Properties properties,SQLOperation operation,String schemaName){
    	this.operation=operation;
    	this.schemaName=schemaName;
    	String identifierQuoteStringProperty=HolmosPropertiesTool.getValue(properties, PROPKEY_IDENTIFIER_QUOTE_STRING);
    	String storedIdentifierCaseValue=HolmosPropertiesTool.getValue(properties, PROPKEY_IDENTIFIER_QUOTE_STRING);
    	
    	this.identifierQuoteString=determineIdentifierQuoteString(identifierQuoteStringProperty);
    	this.storedIdentifierCase=determineStoredIdentifierCase(storedIdentifierCaseValue);
    }
    /**
     * 确认不带引号的SQL标识符是否区分大小写，还是大写或者小写处理
     * */
    private IdentifierCase determineStoredIdentifierCase(
			String storedIdentifierCaseValue) {
    	if(storedIdentifierCaseValue==null){
    		throw new HolmosFailedError("不带引号的SQL标识符是否区分大小写设置有误!");
    	}
		if(storedIdentifierCaseValue.equalsIgnoreCase("upper_case"))
			return IdentifierCase.UPPER_CASE;
		else if(storedIdentifierCaseValue.equalsIgnoreCase("lower_case"))
			return IdentifierCase.LOWER_CASE;
		else if(storedIdentifierCaseValue.equalsIgnoreCase("mixed_case"))
			return IdentifierCase.MIXED_CASE;
		else if(!"auto".equalsIgnoreCase(storedIdentifierCaseValue))
			throw new HolmosFailedError("不带引号的SQL标识符是否区分大小写设置有误!");
		Connection connection=null;
		try{
			connection=operation.getDataSource().getConnection();
			DatabaseMetaData metaData=connection.getMetaData();
			if(metaData.storesLowerCaseIdentifiers())
				return IdentifierCase.LOWER_CASE;
			else if(metaData.storesUpperCaseIdentifiers())
				return IdentifierCase.UPPER_CASE;
			else
				return IdentifierCase.MIXED_CASE;
			
		}catch (Exception e) {
			throw new HolmosFailedError("数据库连接异常!");
		}
	}
    /**
     * 获取数据看SQL的标识符引用字符窜<br>
     * <li>如果有配置，那么返回配置的字符窜</li>
     * <li>如果配置为none，返回null</li>
     * <li>如果配置为auto,那么依据不同数据库返回各自的引用字符窜</li>
     * */
	private String determineIdentifierQuoteString(
			String identifierQuoteStringProperty) {
		if("none".equalsIgnoreCase(identifierQuoteStringProperty))
			return null;
		else if(!"auto".equalsIgnoreCase(identifierQuoteStringProperty))
			return identifierQuoteStringProperty;
		Connection connection=null;
		try{
			connection=operation.getDataSource().getConnection();
			DatabaseMetaData metaData=connection.getMetaData();
			if("".equalsIgnoreCase(metaData.getIdentifierQuoteString())||metaData.getIdentifierQuoteString()==null)
				return null;
			return metaData.getIdentifierQuoteString();
		}catch (Exception e) {
			throw new HolmosFailedError("获取数据看SQL的标识符引用字符窜失败!");
		}
	}
	/**
	 * 获得此schemaName指定的数据库的所有的表名
	 * */
	public abstract Set<String> getAllTableNames();
	/**
	 * 获得指定表的所有列名
	 * @param tableName 此数据库指定的表名
	 * */
	public abstract Set<String> getAllColumnNames(String tableName);
	/**
	 * 获得此schemaName指定的数据库的所有视图名
	 * */
	public abstract Set<String> getAllViewNames();
	/**
	 * 获得此schemaName指定的数据库的所有触发器名
	 * */
	public abstract Set<String> getTriggerNames();
	/**
	 * 删除表
	 * */
	public void dropTable(String tableName){
		getOperation().executeUpdate("drop table " + quoteStr(tableName) + (supportsCascade() ? " cascade" : ""));
	}
	/**
	 * 删除视图
	 * */
	public void dropView(String viewName){
		getOperation().executeUpdate("drop view " + quoteStr(viewName) + (supportsCascade() ? " cascade" : ""));
	}
	/**
	 * 是否支持表和视图的级联删除
	 * */
	public boolean supportsCascade() {
	    return false;
	}
	/**
	 * 限制在当前数据库下
	 * */
	private String qulifyStr(String databaseObjectName){
		return quoteStr(schemaName)+"."+quoteStr(databaseObjectName);
	}
	/**
	 * 添加引用字符窜
	 * */
	private String quoteStr(String identifierString){
		if(identifierString==null)
			return null;
		return identifierQuoteString+identifierString+identifierQuoteString;
	}
	public enum IdentifierCase{
    	LOWER_CASE,
    	UPPER_CASE,
    	MIXED_CASE
    }
}
