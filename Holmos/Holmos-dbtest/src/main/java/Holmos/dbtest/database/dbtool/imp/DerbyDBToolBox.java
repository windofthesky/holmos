package Holmos.dbtest.database.dbtool.imp;

import java.util.Set;

import Holmos.dbtest.database.dbtool.DBToolBox;
import Holmos.exceptions.HolmosFailedError;

public class DerbyDBToolBox extends DBToolBox{

	public DerbyDBToolBox() {
		super("derby");
	}

	@Override
	public Set<String> getAllTableNames() {
		String sql="select t.TABLENAME from SYS.SYSTABLES t, SYS.SYSSCHEMAS  s where t.TABLETYPE = 'T' AND t.SCHEMAID = s.SCHEMAID AND s.SCHEMANAME = '" + 
				getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllColumnNames(String tableName) {
		String sql="select c.COLUMNNAME from SYS.SYSCOLUMNS c, SYS.SYSTABLES t, SYS.SYSSCHEMAS s where c.REFERENCEID = t.TABLEID and t.TABLENAME = '" 
				+ tableName + "' AND t.SCHEMAID = s.SCHEMAID AND s.SCHEMANAME = '" + getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getAllViewNames() {
		String sql="select t.TABLENAME from SYS.SYSTABLES t, SYS.SYSSCHEMAS s where t.TABLETYPE = 'V' AND t.SCHEMAID = s.SCHEMAID AND s.SCHEMANAME = '" 
				+getSchemaName() + "'";
		return getOperation().getItemsAsStringSet(sql);
	}

	@Override
	public Set<String> getTriggerNames() {
		throw new HolmosFailedError("暂不提供支持!");
	}

}
