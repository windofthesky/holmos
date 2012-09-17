package holmos.reflect.reflectCheck.report;

import holmos.reflect.reflectCheck.difference.HolmosDifference;

/**为给定的difference 构建差异的视图结构
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDifferenceView {
	/**为给定的difference 构建差异的视图结构<br>
	 * 这里采用了策略模式，以后开发者可以开发自己的视图模式<br>
	 * @param difference 要位置建立视图的difference
	 * @return 为给定的difference 构建差异的视图结构*/
	public String createView(HolmosDifference difference);
}
