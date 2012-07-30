package Holmos.Holmos.javascript;
import org.apache.log4j.Logger;

import Holmos.Holmos.innerTools.MyFile;
import Holmos.Holmos.tools.HolmosWindow;
/**Holmos 的js工具，开放供大家开发
 * @author 吴银龙(15857164387)
 * */
public class HolmosJavascriptTool {
	protected static Logger logger=Logger.getLogger(HolmosWindow.class);
	/**执行指定文件中的js代码
	 * @param filePath 指定文件的位置*/
	public static void runJavaScriptFromFile(String filePath){
		StringBuilder message=new StringBuilder("");
		if(MyFile.exist(filePath)){
			MyFile javaScriptFile=new MyFile(filePath);
			HolmosWindow.runJavaScript(javaScriptFile.getFileContentByString());
		}else{
			message.append(filePath+"文件不存在!");
			logger.error(message);
		}
	}
}
