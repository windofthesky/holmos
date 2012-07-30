package Holmos.Holmos.data.database.structmodule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Holmos.Holmos.data.database.datadifference.HolmosRowDifference;
import Holmos.Holmos.data.database.datadifference.HolmosTableDifference;
import Holmos.Holmos.plug.PSM.PSMTool;

/**对应数据库里面的一个表，有数据行组成*/
public class HolmosTable {
	public HolmosTable(){
		this.rows=new ArrayList<HolmosRow>();
	}
	public HolmosTable(String name) throws NameInValidException{
		if(!PSMTool.isValid(name)){
			throw new NameInValidException("数据库表名设置不合法!您设置的表名为"+name);
		}else{
			this.name=name;
			this.rows=new ArrayList<HolmosRow>();
		}
	}
	public HolmosTable(String name,ArrayList<HolmosRow>rows)throws NameInValidException{
		if(!PSMTool.isValid(name)){
			throw new NameInValidException("数据库表名设置不合法!您设置的表名为"+name);
		}else{
			this.name=name;
			this.rows=rows;
			if(rows==null){
				rows=new ArrayList<HolmosRow>();
			}
		}
	}
	/**数据表的表名，对表名字符窜有要求，按照java的变量名要求*/
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) throws NameInValidException{
		if(PSMTool.isValid(name)){
			this.name = name;
		}else{
			throw new NameInValidException("数据库表名设置不合法!您设置的表名为"+name);
		}
	}
	public List<HolmosRow> getRows() {
		return rows;
	}
	/**表里面的数据行*/
	private List<HolmosRow>rows;
	/**添加行数据,row不能为空，如果为空，则不进行添加，不影响程序执行
	 * @param row 待添加的行，不能为null*/
	public void addRow(HolmosRow row){
		if(row!=null)
			this.rows.add(row);
	}
	/**判断此表的数据是否为空
	 * @return true 为空 <br>false 不为空*/
	public boolean isEmpty(){
		return this.rows.isEmpty();
	}
	/**将 expectedTable与本table进行比较,将比较的结果返回,比较的方法是将expectedTable和此Table的<br>
	 * 所有行进行匹配，不按照顺序，如果全部的行都被匹配了，那么两个Table相同，那么这个时候返回null,<br>
	 * 否则返回两个table的不同
	 * @param expectedTable 预期的table
	 * @return 预期的table和本table的不同的地方
	 * */
	public HolmosTableDifference compare(HolmosTable expectedTable){
		HolmosTableDifference result=new HolmosTableDifference(this, expectedTable);
		if(isEmpty()){
			//当前table为空，那么这个时候无需比较
			return expectedTable.isEmpty()?null:result;
		}
		//接下来比较table里面的所有的行
		compareRows(rows, expectedTable, result);
		if(result.isMatch()){
			return null;
		}return result;
	}
	/**比较两个table中行的列表，作为一个工具方法
	 * @param rows 实际的rows实际上是指本table对象的row列表，方法内部不能改变这个row列表，需要以此为依据克隆一份
	 * @param expectedTable 期望的table对象
	 * @param result 比较的结果，在其调用的方法里面定义，在此方法内部更新
	 * */
	protected void compareRows(List<HolmosRow>rows,HolmosTable expectedTable,HolmosTableDifference result){
		List<HolmosRow> rowsClone=new ArrayList<HolmosRow>(rows);
		for(HolmosRow expectedRow:expectedTable.rows){
			//对于每一个期望table里面的每一个row对象,寻找此对象rows的匹配项
			Iterator<HolmosRow>rowIterator=rowsClone.iterator();
			while(rowIterator.hasNext()){
				HolmosRow row=rowIterator.next();
				if(row.hasDifferentPrimaryKeyValue(expectedRow)){
					//主键不同，不是同一列，无法比较,继续接下来的比较
					continue;
				}
				//如果主键信息相同，那么应该参与行与行的比较
				HolmosRowDifference rowDifference=row.compare(expectedRow);
				if(rowDifference==null){
					result.setMatchingRows(row, expectedRow);
					rowIterator.remove();//找到了匹配项，需要将这一份拷贝删除
					break;
				}else{
					//重新设定匹配项
					result.setIfBestMatch(rowDifference);
				}
			}
		}
		//找到了列的差异性信息，接下来寻找列的丢失的信息
		for(HolmosRow expectedRow:expectedTable.rows){
			if(result.getRowDifference(expectedRow)==null){
				result.addMissingRow(expectedRow);
			}
		}
	}
	/**获得Table的信息
	 * @return */
	public String toString(){
		StringBuilder result=new StringBuilder();
		for(HolmosRow row:rows){
			result.append(row.toString()+"\n");
		}return result.toString();
	}
}
