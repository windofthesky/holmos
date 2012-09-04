package holmos.dbtest.database.datadifference;

import holmos.dbtest.database.structmodule.HolmosColumn;

/**存储两个列的不同，在数据库一列数据的比较的时候会用到
 * @author 吴银龙(15857164387)
 * */
public class HolmosColumnDifference {
	/**实际的HolmosColumn对象，其实是参与比较的一个实例*/
	private HolmosColumn actualColumn;
	/**期望的到的HolmosColumn对象，是参与比较的另一个实例*/
	private HolmosColumn expectedColumn;
	/**@return 获得实际的HolmosColumn对象*/
	public HolmosColumn getActualColumn() {
		return actualColumn;
	}
	/**@return 获得期望的HolmosColumn对象*/
	public HolmosColumn getExpectColumn() {
		return expectedColumn;
	}
	public HolmosColumnDifference(HolmosColumn actualColumn,HolmosColumn expectedColumn){
		this.actualColumn=actualColumn;
		this.expectedColumn=expectedColumn;
	}
	/**获得不同列的不同信息的字符窜表示
	 * @return */
	public String toString(){
		return "first:"+actualColumn.toString()+"|"+"second:"+expectedColumn.toString()+"\n";
	}
}
