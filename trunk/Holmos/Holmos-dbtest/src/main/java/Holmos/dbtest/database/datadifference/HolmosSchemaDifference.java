package Holmos.dbtest.database.datadifference;

import java.util.ArrayList;
import java.util.List;

import Holmos.dbtest.database.structmodule.HolmosSchema;
import Holmos.dbtest.database.structmodule.HolmosTable;
/**两个Schema相异点的类
 * @author 吴银龙(15857164387)
 * */
public class HolmosSchemaDifference {
	private HolmosSchema actualSchema;
	/**获得actualSchema*/
	public HolmosSchema getActualSchema() {
		return actualSchema;
	}
	/**获得expectedSchema*/
	public HolmosSchema getExpectedSchema() {
		return expectedSchema;
	}

	private HolmosSchema expectedSchema;
	/**构造函数*/
	public HolmosSchemaDifference(HolmosSchema actualSchema,HolmosSchema expectedSchema){
		this.actualSchema=actualSchema;
		this.expectedSchema=expectedSchema;
	}
	/**记录在actualSchema里面含有的table，但是在expectedSchema里面缺少的table列表*/
	private List<HolmosTable> missingTables=new ArrayList<HolmosTable>();
	/**actualSchema和expectedSchema里面table的差异列表*/
	private List<HolmosTableDifference> differentTables=new ArrayList<HolmosTableDifference>();
	/**
	 * 记录在actualSchema里面含有的table，但是在expectedSchema里面缺少的table列表
	 * @return missingTables
	 * */
	public List<HolmosTable> getMissingTables() {
		return missingTables;
	}
	/**
	 * actualSchema和expectedSchema里面table的差异列表
	 * @return 获得differentTables
	 * */
	public List<HolmosTableDifference> getDifferentTables() {
		return differentTables;
	}
	/**将从expectedSchema找到的缺失的differentTable加入到missingTables*/
	public void addMissingTables(HolmosTable differentTable){
		this.missingTables.add(differentTable);
	}
	/**将新找到的table差一点加入到differentTables里面*/
	public void addTableDifference(HolmosTableDifference tableDifference){
		this.differentTables.add(tableDifference);
	}
	/**判断两个Schema是否匹配
	 * @return true 匹配成功<br> false 不匹配*/
	public boolean isMatch(){
		return this.differentTables.isEmpty()&&this.missingTables.isEmpty();
	}
	/**将不同信息汇总
	 * @return 汇总的差异信息*/
	public String toString(){
		StringBuilder result=new StringBuilder();
		if(missingTables.size()>0){
			result.append("expectedSchema缺失的表如下:\n");
		}
		for(HolmosTable table:missingTables){
			result.append(table.getName()+"|");
		}
		if(differentTables.size()>0){
			result.append("不同的表差异如下:\n");
		}
		for(HolmosTableDifference tableDifference:differentTables){
			result.append(tableDifference.toString());
		}
		return result.toString();
	}
}
