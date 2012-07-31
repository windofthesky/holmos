package Holmos.dbtest.database.dataset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dbunit.dataset.IDataSet;

/**数据库单元测试Holmos框架封装的是Dbunit框架，但是Dbunit框架只支持单个的数据表，为了支持多数据表的配置，在此实现
 * @author 吴银龙(15857164387)
 * */
public class HolmosMultiDataSet {
	/**表示多个数据源的map,key为Schema,IDataSet表示一个表的数据源*/
	public Map<String,IDataSet> multiSchemaDataSet=new HashMap<String, IDataSet>();
	
	/**根据Schema的名字，来获取与之匹配的数据源IDataSet
	 * @param schemaName 要获取数据源的schema名称
	 * @return 与schema匹配的数据源对象，如果不存在，返回null
	 * */
	public IDataSet getDataSetBySchemaName(String schemaName){
		return multiSchemaDataSet.get(schemaName);
	}
	
	/**返回所有的Schema名字
	 * @return 该HolmosMultiDataSet对象的所有的数据源的Schema名字*/
	public Set<String> getAllSchemaNames(){
		return multiSchemaDataSet.keySet();
	}
	/**添加新的数据源对象
	 * @param schemaName 新数据源的Schema 名字
	 * @param dataset 对应的数据源
	 * */
	public void setDataSet(String schemaName,IDataSet dataset){
		multiSchemaDataSet.put(schemaName, dataset);
	}
}
