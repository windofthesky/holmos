package Holmos.dbtest.database.dbtool.imp;

import java.util.Set;

import Holmos.dbtest.database.dbtool.DBToolBox;
import Holmos.exceptions.HolmosFailedError;

public class SQLServerDBToolBox extends DBToolBox{

	public SQLServerDBToolBox() {
		 super("mssql");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select t.name from sys.tables t, sys.schemas s where t.schema_id = s.schema_id and s.name = '" 
				+ getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select c.name from sys.columns c, sys.tables t, sys.schemas s where c.object_id = t.object_id and t.name = '" 
				+ tableName + "' and t.schema_id = s.schema_id and s.name = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select v.name from sys.views v, sys.schemas s where v.schema_id = s.schema_id and s.name = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		throw new HolmosFailedError("暂不提供支持!");
	}

}
