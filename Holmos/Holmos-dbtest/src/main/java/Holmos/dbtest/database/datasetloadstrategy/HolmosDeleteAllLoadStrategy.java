package Holmos.dbtest.database.datasetloadstrategy;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import Holmos.dbtest.database.connecion.HolmosDataBaseConnection;
import Holmos.webtest.exceptions.HolmosFailedError;

/**在数据库测试的时候，加载数据的时候删掉指定连接的数据库表中的所有数据
 * @author 吴银龙(15857164387)
 * */
public class HolmosDeleteAllLoadStrategy implements HolmosDataSetLoadStrategy{

	public void excute(HolmosDataBaseConnection conn, IDataSet dataset) {
		try {
			DatabaseOperation.DELETE_ALL.execute(conn, dataset);
		} catch (DatabaseUnitException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		} catch (SQLException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		}
	}

}
