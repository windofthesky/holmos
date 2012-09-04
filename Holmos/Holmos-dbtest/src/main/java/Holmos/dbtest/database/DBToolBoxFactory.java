package holmos.dbtest.database;

import holmos.dbtest.database.dbtool.DBToolBox;
import holmos.dbtest.database.operation.SQLOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import Holmos.webtest.basetools.HolmosConfTool;
import Holmos.webtest.basetools.HolmosPropertiesTool;

public class DBToolBoxFactory {
	public static DBToolBox getToolBox(Properties properties,SQLOperation operation,String schemaName){
		return null;
	}
	/**配置所使用的数据库类型*/
	public static final String PROPKEY_DATABASE_DIALECT = "database.dialect";
	/**需要连接的数据库schema*/
	public static final String PROPKEY_DATABASE_SCHEMA_NAMES = "database.schemaNames";
	/**DBToolBox集合的缓存*/
	private static Map<String,DBToolBox> DBToolBoxCaches=new HashMap<String, DBToolBox>();
	
	/**
	 * 获取默认的DBToolBox对象，获得配置文件里第一个shemaName对应的DBToolBox
	 * @param properties 配置文件
	 * @param operation 给定的数据库操作工具
	 * */
	public static DBToolBox getDefaultDBToolBox(Properties properties,SQLOperation operation){
		String defaultSchemaName=HolmosPropertiesTool.getValueList(properties, PROPKEY_DATABASE_SCHEMA_NAMES).get(0);
		return getDBToolBox(properties,operation,defaultSchemaName);
	}
	/**
	 * 获取DBToolBox对象
	 * @param properties holmos框架配置文件
	 * @param operation 数据库操作工具对象
	 * @param schemaName 数据库的schema名字
	 * */
	public static DBToolBox getDBToolBox(Properties properties,
			SQLOperation operation, String schemaName) {
		DBToolBox dbToolBox=DBToolBoxCaches.get(schemaName);
		if(dbToolBox!=null)
			return dbToolBox;
		String dialect=HolmosPropertiesTool.getValue(properties, PROPKEY_DATABASE_DIALECT);
		dbToolBox=HolmosConfTool.getInstanceOf(DBToolBox.class, properties, dialect);
		dbToolBox.init(properties, operation, schemaName);
		DBToolBoxCaches.put(schemaName, dbToolBox);
		return dbToolBox;
	}
	/**
	 * 获取在配置文件里面配置的所有Schemas对应的DBToolboxes对象
	 * */
	public static ArrayList<DBToolBox> getDBToolBoxes(Properties properties,SQLOperation operation){
		List<String>schemas=HolmosPropertiesTool.getValueList(properties, PROPKEY_DATABASE_SCHEMA_NAMES);
		ArrayList<DBToolBox>dbToolBoxs=new ArrayList<DBToolBox>();
		for(String schema:schemas){
			dbToolBoxs.add(getDBToolBox(properties, operation, schema));
		}
		return dbToolBoxs;
	}
}
