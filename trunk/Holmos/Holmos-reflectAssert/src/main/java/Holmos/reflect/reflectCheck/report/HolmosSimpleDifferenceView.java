package Holmos.reflect.reflectCheck.report;

import junit.framework.AssertionFailedError;
import Holmos.reflect.reflectCheck.difference.HolmosDifference;
import Holmos.reflect.tool.HolmosObjectFormatter;

/**建立简单类型的报表<br>
 * @author 吴银龙(15857164387)
 * */
public class HolmosSimpleDifferenceView implements HolmosDifferenceView{
	private HolmosObjectFormatter objectFormatter=new HolmosObjectFormatter();
	public String createView(HolmosDifference difference) {
		String expectedStr = objectFormatter.format(difference.getLeftValue());
        String actualStr = objectFormatter.format(difference.getRightValue());
        String formattedOnOneLine = formatToOneLine(expectedStr, actualStr);
        if (AssertionFailedError.class.getName().length() + 2  + formattedOnOneLine.length() < HolmosDefaultDifferentReport.MAX_LINE_SIZE) {
            return formattedOnOneLine;
        } else {
            return formatToTwoLines(expectedStr, actualStr);
        }
	}
	public String formatToOneLine(String expectedStr, String actualStr){
		return new StringBuilder().append("预期值: ").append(expectedStr).append(", 实际值: ").append(actualStr).toString();
	}
	public String formatToTwoLines(String expectedStr, String actualStr){
		StringBuilder result = new StringBuilder();
        result.append("\n预期值: ").append(expectedStr);
        result.append("\n实际值: ").append(actualStr);
        return result.toString();
	}
}
