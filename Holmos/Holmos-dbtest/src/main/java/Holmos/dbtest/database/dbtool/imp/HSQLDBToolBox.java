package holmos.dbtest.database.dbtool.imp;

import holmos.dbtest.database.dbtool.DBToolBox;
import holmos.webtest.exceptions.HolmosFailedError;

import java.util.Set;

public class HSQLDBToolBox extends DBToolBox{

	public HSQLDBToolBox() {
		super("hsqldb");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select TABLE_NAME from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'TABLE' AND TABLE_SCHEM = '" 
				+ getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select COLUMN_NAME from INFORMATION_SCHEMA.SYSTEM_COLUMNS where TABLE_NAME = '" + tableName
				+ "' AND TABLE_SCHEM = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select TABLE_NAME from INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'VIEW' AND TABLE_SCHEM = '"
				+ getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		// TODO Auto-generated method stub
		throw new HolmosFailedError("暂不提供支持!");
	}

}
