package holmos.dbtest.database.structmodule;

import holmos.dbtest.database.datadifference.HolmosColumnDifference;
import holmos.dbtest.database.datadifference.HolmosRowDifference;

import java.util.ArrayList;
import java.util.List;


/**数据库的数据行结构
 * @author 吴银龙(15857164387)
 * */
public class HolmosRow {
	/**此行所在数据库的主键序列*/
	private List<HolmosColumn> primaryKeyColumns=new ArrayList<HolmosColumn>();
	/**此行所在表的所有列*/
	private List<HolmosColumn> columns=new ArrayList<HolmosColumn>();
	public List<HolmosColumn> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	public List<HolmosColumn> getColumns() {
		return columns;
	}
	/**添加主键 列,column不能为null
	 * @param column 待添加的主键 列，不能为null
	 * */
	public void addPrimaryKeyColumn(HolmosColumn column){
		if(column!=null){
			if(primaryKeyColumns==null)
				primaryKeyColumns=new ArrayList<HolmosColumn>();
			primaryKeyColumns.add(column);
		}
	}
	/**添加列,column不能为null
	 * @param column 待添加的列，不能为null
	 * */
	public void addColumn(HolmosColumn column){
		if(column!=null){
			if(columns==null)
				columns=new ArrayList<HolmosColumn>();
			columns.add(column);
		}
	}
	public HolmosRow(){
		this.primaryKeyColumns=new ArrayList<HolmosColumn>();
		this.columns=new ArrayList<HolmosColumn>();
	}
	public HolmosRow(List<HolmosColumn> primaryKeyColumns,List<HolmosColumn> columns){
		this.primaryKeyColumns=primaryKeyColumns;
		this.columns=columns;
		if(primaryKeyColumns==null)
			this.primaryKeyColumns=new ArrayList<HolmosColumn>();
		if(columns==null)
			this.columns=new ArrayList<HolmosColumn>();
	}
	/**判定当前row和expectedRow的主键的值是否相同
	 * @return true 主键值相同<br> false 主键值不同
	 * */
	public boolean hasDifferentPrimaryKeyValue(HolmosRow expectedRow){
		for(HolmosColumn expectedColumn:expectedRow.primaryKeyColumns){
			HolmosColumn column=getColumn(expectedColumn.getName());
			if(column==null||column.compare(expectedColumn)!=null){
				return false;
			}
		}return true;
	}
	/**根据columnName作为索引来得到Column,由于这个方法经常用来获得主键值，那么做一级优化，先<br>
	 * 扫描主键，然后扫描整个列的列表，找不到的话返回null
	 * */
	public HolmosColumn getColumn(String columnName){
		for(HolmosColumn column:primaryKeyColumns){
			if(column.getName().equalsIgnoreCase(columnName))
				return column;
		}for(HolmosColumn column:columns){
			if(column.getName().equalsIgnoreCase(columnName))
				return column;
		}return null;
	}
	/**将此row与预期的row参与比较*/
	public HolmosRowDifference compare(HolmosRow expectedRow){
		HolmosRowDifference result=new HolmosRowDifference(this, expectedRow);
		compareColumns(primaryKeyColumns, expectedRow, result);//先比较主键，作为一级加速
		compareColumns(columns, expectedRow, result);
		if(result.isMatch())
			return null;
		return result;
	}
	/**比较此Row里面的columns和预期的columns*/
	public void compareColumns(List<HolmosColumn>columns,HolmosRow expectedRow,HolmosRowDifference result){
		for(HolmosColumn column:columns){
			HolmosColumn expectedColumn=expectedRow.getColumn(column.getName());
			if(expectedColumn==null)
				result.addMissingColumn(expectedColumn);
			else{
				HolmosColumnDifference columnDifference=column.compare(expectedColumn);
				if(columnDifference!=null){
					result.addDifferentColumn(columnDifference);
				}
			}
		}
	}
	/**获得此行的数据信息
	 * @return */
	public String toString(){
		StringBuilder result=new StringBuilder();
		for(HolmosColumn column:columns){
			result.append(column.toString()+"|");
		}return result.toString();
	}
}
