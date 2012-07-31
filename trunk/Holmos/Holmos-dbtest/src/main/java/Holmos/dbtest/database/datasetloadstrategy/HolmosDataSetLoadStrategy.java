package Holmos.dbtest.database.datasetloadstrategy;

import org.dbunit.dataset.IDataSet;

import Holmos.dbtest.database.connecion.HolmosDataBaseConnection;

/**进行数据库初始化的时候的初始化操作策略;操作的时候，调用的是Dbunit的 <code>DatabaseOperation</code>操作的对象是一个给定的HolmosDataSet对象;
 * <br>然后根据特定的操作策略与数据库完成交互;分为以下七种操作策略:
 * <li>{@link HolmosCleanInsertLoadStrategy}  删除DataSet里面所有的记录,之后将DataSet里面的记录插入数据库</li>
 * <li>{@link HolmosInsertLoadStrategy}  只将DataSet里的记录插入,有可能违反主键约束</li>
 * <li>{@link HolmosRefreshLoadStrategy}  将数据库中和DataSet中有的记录进行更新,数据库中没有的进行插入</li>
 * <li>{@link HolmosUpdateLoadStrategy}  只对数据库中和DataSet中都有的记录进行更新,数据库中没有的记录不做改变</li>
 * <li>{@link HolmosDeleteAllLoadStrategy}  在数据库测试的时候，加载数据的时候删掉指定连接的数据库表中的所有数据</li>
 * <li>{@link HolmosDeleteLoadStrategy}  数据库测试的时候，将数据源中的在指定连接的数据库表中存在的数据删掉</li>
 * <li>{@link HolmosNoneLoadStrategy}  数据库测试的时候不对数据源做任何操作</li>
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDataSetLoadStrategy {
	/**根据本策略将dataset数据源信息在conn连接指定的数据库表里面进行操作
	 * 
	 * @param conn 指定的数据库连接 不能为null
	 * @param dataset 指定的数据源 不能为null
	 * */
	public void excute(HolmosDataBaseConnection conn,IDataSet dataset);
}
