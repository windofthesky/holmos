package holmos.dbtest.database.dbtool.imp;

import holmos.dbtest.database.dbtool.DBToolBox;

import java.util.Set;

import Holmos.webtest.exceptions.HolmosFailedError;

public class DB2DBToolBox extends DBToolBox{
	
	public DB2DBToolBox() {
		 super("db2");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select TABNAME from SYSCAT.TABLES where TABSCHEMA = '" + getSchemaName() + "' and TYPE = 'T'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select TABNAME from SYSCAT.TABLES where TABSCHEMA = '" + getSchemaName() + "' and TYPE = 'T'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select TABNAME from SYSCAT.TABLES where TABSCHEMA = '" + getSchemaName() + "' and TYPE = 'T'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		throw new HolmosFailedError("暂不支持!");
	}

}
