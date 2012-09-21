package holmos.dbtest.database.dbtool.imp;

import holmos.dbtest.database.dbtool.DBToolBox;
import holmos.webtest.exceptions.HolmosFailedError;

import java.util.Set;

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
