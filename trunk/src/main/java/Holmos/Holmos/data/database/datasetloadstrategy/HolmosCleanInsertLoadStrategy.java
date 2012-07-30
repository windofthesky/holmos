package Holmos.Holmos.data.database.datasetloadstrategy;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import Holmos.Holmos.data.database.connecion.HolmosDataBaseConnection;
import Holmos.Holmos.exceptions.HolmosFailedError;
/**删除DataSet里面所有的记录,之后将DataSet里面的记录插入数据库
 * @author 吴银龙(15857164387)
 * */
public class HolmosCleanInsertLoadStrategy implements HolmosDataSetLoadStrategy{

	@Override
	public void excute(HolmosDataBaseConnection conn, IDataSet dataset) {
		try {
			DatabaseOperation.CLEAN_INSERT.execute(conn, dataset);
		} catch (DatabaseUnitException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		} catch (SQLException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		}
	}

}
