package Holmos.dbtest.database.datasetloadstrategy;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import Holmos.dbtest.database.connecion.HolmosDataBaseConnection;
import Holmos.exceptions.HolmosFailedError;
/**数据库测试的时候不对数据源做任何操作
 * @author 吴银龙(15857164387)
 * */
public class HolmosNoneLoadStrategy implements HolmosDataSetLoadStrategy{

	public void excute(HolmosDataBaseConnection conn, IDataSet dataset) {
		try {
			DatabaseOperation.NONE.execute(conn, dataset);
		} catch (DatabaseUnitException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		} catch (SQLException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		}
	}

}
