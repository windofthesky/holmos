package Holmos.Holmos.data.database.dbtool.imp;

import java.util.Set;

import Holmos.Holmos.data.database.dbtool.DBToolBox;
import Holmos.Holmos.exceptions.HolmosFailedError;

public class MySqlDBToolBox extends DBToolBox{

	public MySqlDBToolBox() {
		super("mysql");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select table_name from information_schema.tables where table_schema = '" 
				+ getSchemaName() + "' and table_type = 'BASE TABLE'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select column_name from information_schema.columns where table_name = '" 
				+ tableName + "' and table_schema = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select table_name from information_schema.tables where table_schema = '" 
				+ getSchemaName() + "' and table_type = 'VIEW'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		throw new HolmosFailedError("暂不提供支持!");
	}

}
