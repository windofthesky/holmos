package holmos.dbtest.database.datasetloadstrategy;

import holmos.dbtest.database.connecion.HolmosDataBaseConnection;
import holmos.webtest.exceptions.HolmosFailedError;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
/**将数据库中和DataSet中有的记录进行更新,数据库中没有的进行插入
 * @author 吴银龙(15857164387)
 * */
public class HolmosRefreshLoadStrategy implements HolmosDataSetLoadStrategy{

	public void excute(HolmosDataBaseConnection conn, IDataSet dataset) {
		try {
			DatabaseOperation.REFRESH.execute(conn, dataset);
		} catch (DatabaseUnitException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		} catch (SQLException e) {
			throw new HolmosFailedError("CLEAN_INSERT执行异常");
		}
	}

}
