package Holmos.Holmos.tools;

import java.io.InputStream;

/**Holmos框架操作IO的工具类
 * @author 吴银龙(15857164387)
 * */
public class HolmosIOTool {
	/**关闭stream，并且不抛出异常
	 * @param stream 待关闭的输入流*/
	public static void closeStreamIgnoreExpection(InputStream stream) {
		try{
			if(stream!=null){
				stream.close();
			}
		}catch (Exception e) {
			// do nothing here
		}
	}

}
