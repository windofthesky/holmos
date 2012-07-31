package Holmos.dbtest.database.structmodule;

import java.util.ArrayList;
import java.util.List;

import Holmos.dbtest.database.datadifference.HolmosSchemaDifference;
import Holmos.dbtest.database.datadifference.HolmosTableDifference;
import Holmos.exceptions.HolmosFailedError;
/**为了支持多数据源，采用了Schema，一个Schema里面配置多个数据源，即多个Table
 * @author 吴银龙(15857164387)
 * */
public class HolmosSchema {
	/**该Schema的名字*/
	private String name;
	/**在该Schema文件里面配置的多数据表*/
	private List<HolmosTable>tables=new ArrayList<HolmosTable>();
	/**Schema的构造函数
	 * @param name Schema的名字*/
	public HolmosSchema(String name){
		this.name=name;
	}
	/**获得Schema的名字*/
	public String getName() {
		return name;
	}
	/**获得该Schema里面的Table列表*/
	public List<HolmosTable> getTables() {
		return tables;
	}
	/**添加table对象，如果要添加的table对象的名字已经存在在该Schema的table列表里面，则添加失败，抛出异常
	 * @param table 带添加的table对象
	 * @exception HolmosFailedError
	 * */
	public void addTable(HolmosTable table){
		if(getTableByName(table.getName())!=null){
			this.addTable(table);
		}else{
			throw new HolmosFailedError("无法往Schema"+name+"里面添加"+table.toString()+"已经存在table名字相同的实例!");
		}
	}
	/**获得该Schema列表里面所有table的table名字列表
	 * @return 所有table的table名字列表
	 * */
	public List<String>getTableNames(){
		ArrayList<String>tableNames=new ArrayList<String>();
		for(HolmosTable table:tables){
			tableNames.add(table.getName());
		}return tableNames;
	}
	/**扫描该Schema里面所有的table列表，如果查到table的名字和tableName相同的话，返回此Table对象，如果找不到返回null
	 * @param tableName 要查寻table对象的名字
	 * @return 满足条件的table对象，如果没有找到满足条件的返回null
	 * */
	protected HolmosTable getTableByName(String tableName){
		for(HolmosTable table:tables){
			if(table.getName().equalsIgnoreCase(tableName))
				return table;
		}return null;
	}
	/**比较两个Schema的异同，比较规则如下:<br>
	 * <li>name不做为比较内容</li>
	 * <li>调用table的比较方法，比较每一个table，如果不同，返回不同点，如果相同，返回null</li>
	 * @return 两个Schema的差异  null 表示两个Schema相匹配
	 * */
	public HolmosSchemaDifference compare(HolmosSchema expectedSchema){
		HolmosSchemaDifference schemaDifference=new HolmosSchemaDifference(this, expectedSchema);
		for(HolmosTable actualTable:this.tables){
			HolmosTable expectedTable=expectedSchema.getTableByName(actualTable.getName());
			if(expectedTable==null){
				schemaDifference.addMissingTables(actualTable);
				continue;
			}
			HolmosTableDifference tableDifference=actualTable.compare(expectedTable);
			if(tableDifference!=null){
				schemaDifference.addTableDifference(tableDifference);
			}
		}
		if(schemaDifference.isMatch()){
			return null;
		}return schemaDifference;
	}
	
}
