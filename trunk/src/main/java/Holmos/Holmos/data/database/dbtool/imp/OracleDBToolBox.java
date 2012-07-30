package Holmos.Holmos.data.database.dbtool.imp;

import java.util.Set;

import Holmos.Holmos.data.database.dbtool.DBToolBox;
import Holmos.Holmos.exceptions.HolmosFailedError;

public class OracleDBToolBox extends DBToolBox{

	public OracleDBToolBox() {
		super("oracle");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select TABLE_NAME from ALL_TABLES where OWNER = '" + getSchemaName() + 
				"' and TABLE_NAME not like 'BIN$%' minus select MVIEW_NAME from ALL_MVIEWS where OWNER = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select COLUMN_NAME from ALL_TAB_COLUMNS where TABLE_NAME = '" + tableName + "' and OWNER = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select VIEW_NAME from ALL_VIEWS where OWNER = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		throw new HolmosFailedError("暂不提供支持!");
	}

}
