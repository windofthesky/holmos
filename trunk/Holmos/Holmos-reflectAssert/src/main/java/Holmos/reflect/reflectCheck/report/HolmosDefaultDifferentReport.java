package holmos.reflect.reflectCheck.report;

import holmos.reflect.reflectCheck.difference.HolmosDifference;

/**根据给定的difference生成报表，这个类会是第一个用{@link HolmosDifferenceView}来输出<br>
 * 比较结果，根据difference的类型，如果不是一个简单的Difference，那么就需要调用{@link HolmosTreeDifferenceView}<br>
 * 来输出比较结果<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosDefaultDifferentReport implements HolmosDifferenceReport{
	/**构建一个Report
	 * @param difference 指定的difference
	 * @return 生成的Report
	 * */
	public static final int MAX_LINE_SIZE = 110;

    public static enum MatchType {NO_MATCH};
	public String createReport(HolmosDifference difference) {
		StringBuilder report=new StringBuilder();
		//规范化视图
		report.append(new HolmosSimpleDifferenceView().createView(difference));
		report.append("\n找到的不同的地方如下:\n");
		report.append(new HolmosDefalutDifferenceView().createView(difference));
		if(HolmosDifference.class.equals(difference.getClass())){
			//当无法判定是哪种difference的时候，将找到差异树上所有的信息
			report.append("\n找到的不同树如下:\n");
			report.append(new HolmosTreeDifferenceView().createView(difference));
		}
		return report.toString();
	}
}
