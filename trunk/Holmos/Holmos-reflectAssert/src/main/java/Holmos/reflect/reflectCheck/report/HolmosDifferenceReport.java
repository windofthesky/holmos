package Holmos.reflect.reflectCheck.report;

import Holmos.reflect.reflectCheck.difference.HolmosDifference;

/**为给定的Difference建立一个Report报表，是反射校验的日志形式
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDifferenceReport {
	/**根据给定的difference建立一个Report报表，做为日志的输出形式<br>
	 * <br>这里是采用了策略模式，使程序有更好的扩展性，以后开发者可以自己写自己的report报表格式<br>
	 * @param difference 个顶的difference
	 * @return 根据给定的difference建立一个Report报表*/
	public String createReport(HolmosDifference difference);
}
