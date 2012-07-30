package Holmos.Holmos.data.database.datasetfactory;

import java.io.File;
import java.util.Properties;

import Holmos.Holmos.data.database.dataset.HolmosMultiDataSet;

/**采用工厂模式，生成Dbunit的DataSet对象
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDataSetFactory {
	/**初始化该工厂对象
	 * @param defaultSchemaName 在IDataSet没有设置SchemaName的时候，设置这个默认的
	 * */
	public void init(Properties properties,String defaultSchemaName);
	
	/**根据给定的数据源准备文件来创建多数据源对象
	 * @param dataSetFiles 给定的数据源文件列表
	 * @return 根据多数据源对象构造的多数据源对象
	 * */
	public HolmosMultiDataSet createMultiDataSet(File... dataSetFiles);
	/**由于框架只支持xml和excel类型的数据源文件，检查文件是否合法<br>
	 * 如果文件列表里面的所有的文件都合法，那么返回true，否则返回false，在外层的控制中最好抛出异常
	 * @param dataSetFiles 文件列表
	 * @return true 文件列表都合法  <br>false 如果发现其中一个不合法
	 * */
	public boolean isDataSetFileAvilid(File... dataSetFiles);
	/**返回这个工厂的实例类型，其实是返回所支持的文件的扩展名列表
	 * @return 返回该工厂实例支持的文件类型列表*/
	public String getFactoryType();
}
