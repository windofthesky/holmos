package Holmos.Holmos.data.database.datasetloadstrategy;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import Holmos.Holmos.data.database.connecion.HolmosDataBaseConnection;
import Holmos.Holmos.exceptions.HolmosFailedError;
/**数据库测试的时候，将数据源中的在指定连接的数据库表中存在的数据删掉
 * @author 吴银龙(1585714387)
 * */
public class HolmosDeleteLoadStrategy implements HolmosDataSetLoadStrategy{

	@Override
	public void excute(HolmosDataBaseConnection conn, IDataSet dataset) {
		try {
			DatabaseOperation.DELETE.execute(conn, dataset);
		} catch (DatabaseUnitException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		} catch (SQLException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		}
	}

}
