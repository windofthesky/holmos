package Holmos.dbtest.database.datadifference;

import java.util.ArrayList;
import java.util.List;

import Holmos.dbtest.database.structmodule.HolmosColumn;
import Holmos.dbtest.database.structmodule.HolmosRow;

/**一行的差异对象，数据库校验的时候会用到
 * @author 吴银龙(15857164387)
 * */
public class HolmosRowDifference {
	/**实际的row对象，参与比较的一个row实例*/
	private HolmosRow actualRow;
	/**期望的row对象，参与比较的另一个row实例*/
	private HolmosRow expectedRow;
	/**@return 获取实际的row对象
	 * */
	public HolmosRow getActualRow() {
		return actualRow;
	}
	/**@return 获取期望的row对象*/
	public HolmosRow getExpectedRow() {
		return expectedRow;
	}
	/**在期望的row对象缺少的列，那么相对应的这些列在期望的row对象里面没有*/
	private List<HolmosColumn>missingColumns=new ArrayList<HolmosColumn>();
	/**@return 获取在期望的row对象缺少的列的不同信息*/
	public List<HolmosColumn> getMissingColumns() {
		return missingColumns;
	}
	/**@return 获取两个row对象的不同的列的不同信息*/
	public List<HolmosColumnDifference> getDifferentColumns() {
		return differentColumns;
	}
	/**两个row对象的不同的列*/
	private List<HolmosColumnDifference>differentColumns=new ArrayList<HolmosColumnDifference>();
	public HolmosRowDifference(HolmosRow actualRow,HolmosRow expectedRow){
		this.actualRow=actualRow;
		this.expectedRow=expectedRow;
	}
	/**添加实际row对象不具有的列*/
	public void addMissingColumn(HolmosColumn column){
		this.missingColumns.add(column);
	}
	/**添加两个参与比较的row对象的列的差异信息*/
	public void addDifferentColumn(HolmosColumnDifference columnDifference){
		this.differentColumns.add(columnDifference);
	}
	/**获取该row对象指定列名的列的差异信息
	 * @param columnName 指定的列名，非空
	 * @return 获取他们的差异信息，如果columnName在该row对象中不存在，返回null
	 * */
	public HolmosColumnDifference getColumnDifference(String columnName){
		for(HolmosColumnDifference columnDifference:differentColumns){
			if(columnName.equals(columnDifference.getActualColumn().getName())){
				return columnDifference;
			}
		}
		return null;
	}
	/**当前对象比给定的HolmosRowDifference对象是否匹配更佳，这个必须以这两个参与比较的对象为基准才行，要不然没有比较意义
	 * @param rowDifference 给定的HolmosRowDifference对象
	 * @return true 当前对象的匹配更佳
	 * <br>false 当前对象的匹配比给定对象找到的匹配差*/
	public boolean isBetterMatch(HolmosRowDifference rowDifference){
		return (this.differentColumns.size()+this.missingColumns.size())<(rowDifference.getMissingColumns().size()
				+rowDifference.getDifferentColumns().size());
	}
	/**判定这两个Row对象是否匹配，即没有不同的列，也没有一方没有的列*/
	public boolean isMatch(){
		return this.differentColumns.isEmpty()&&this.missingColumns.isEmpty();
	}
	/**获得参与比较的两个数据行的不同信息
	 * @return */
	public String toString(){
		StringBuilder result=new StringBuilder();
		result.append("differences:\n");
		for(HolmosColumnDifference columnDifference:differentColumns){
			result.append("first:"+columnDifference.toString()+"\n");
		}
		result.append("missings:\n");
		for(HolmosColumn missingColumn:missingColumns){
			result.append(missingColumn.toString()+"\n");
		}
		return result.toString();
	}
}
