package holmos.dbtest.database.datadifference;

import holmos.dbtest.database.structmodule.HolmosRow;
import holmos.dbtest.database.structmodule.HolmosTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**表示两个数据库表的差异信息
 * @author 吴银龙(15857164387)
 * */
public class HolmosTableDifference {
	/**实际的table实例*/
	private HolmosTable actualTable;
	/**预期的table实例*/
	private HolmosTable expectedTable;
	/**@return 获取实际的table实例*/
	public HolmosTable getActualTable() {
		return actualTable;
	}
	/**@return 获取预期的table实例*/
	public HolmosTable getExpectedTable() {
		return expectedTable;
	}
	public HolmosTableDifference(HolmosTable actualTable,HolmosTable expectedTable){
		this.actualTable=actualTable;
		this.expectedTable=expectedTable;
	}
	/**在预期的table有的行，但是在实际的table没有的行的  序列*/
	private List<HolmosRow> missingRows=new ArrayList<HolmosRow>();
	/**在这个序列里面的差异项，都是有差异，但是确实最佳的差异，即是最小的差异*/
	private Map<HolmosRow,HolmosRowDifference>bestMatchingDifferences=new HashMap<HolmosRow, HolmosRowDifference>();
	/**@return 在预期的table有的行，但是在实际的table没有的行的  序列*/
	public List<HolmosRow> getMissingRow() {
		return missingRows;
	}
	/**@return 获取最小差异 序列:在这个序列里面的差异项，都是有差异，但是确实最佳的差异，即是最小的差异,实际表里面的行作为索引
	 * */
	public Map<HolmosRow, HolmosRowDifference> getBestMatchingDifferences() {
		return bestMatchingDifferences;
	}
	/**添加row进入 missingRows*/
	public void addMissingRow(HolmosRow row){
		missingRows.add(row);
	}
	/**根据所给的索引row获取其最佳匹配的差异项
	 * @param row 给定的索引
	 * @return 根据索引查找到的差异项
	 * */
	public HolmosRowDifference getRowDifference(HolmosRow row){
		return this.bestMatchingDifferences.get(row);
	}
	/**当前的actualRow和expectedRow已经匹配成功，以此更新bestMatchingDifferences里面的值<br>
	 * 对里面的匹配项进行双向删除，因为这些项已经得到匹配
	 * @param actualRow 实际表里面的行
	 * @param expectedRow 预期表里面的行
	 * */
	public void setMatchingRows(HolmosRow expectedRow,HolmosRow actualRow){
		bestMatchingDifferences.remove(expectedRow);
		Iterator<HolmosRowDifference>rowDifferenceIterator=bestMatchingDifferences.values().iterator();
		while(rowDifferenceIterator.hasNext()){
			if(rowDifferenceIterator.next().getExpectedRow().equals(actualRow)){
				rowDifferenceIterator.remove();
			}
		}
	}
	/**如果该rowDifference是最佳匹配，那么更新bestMatchingDifferences的值
	 * @param rowDifference 可能的最佳匹配，需要在该方法中进行确认是否比现有的匹配要好
	 * */
	public void setIfBestMatch(HolmosRowDifference rowDifference){
		HolmosRowDifference currentMatchingDifference=bestMatchingDifferences.get(rowDifference.getExpectedRow());
		if(currentMatchingDifference==null||rowDifference.isBetterMatch(currentMatchingDifference)){
			bestMatchingDifferences.put(rowDifference.getExpectedRow(), rowDifference);
		}
	}
	/**确定 actualTable和expectedTable是否匹配
	 * @return true 匹配<br>false 不匹配*/
	public boolean isMatch(){
		return bestMatchingDifferences.isEmpty()&&missingRows.isEmpty();
	}
	/**获得两个table的不同信息的字符窜表示*/
	public String toString(){
		StringBuilder result=new StringBuilder();
		result.append("different rows:\n");
		for(HolmosRowDifference rowDifference:bestMatchingDifferences.values()){
			result.append(rowDifference.toString());
		}
		result.append("missing rows:\n");
		for(HolmosRow row:missingRows){
			result.append(row.toString()+"\n");
		}
		return result.toString();
	}
}
