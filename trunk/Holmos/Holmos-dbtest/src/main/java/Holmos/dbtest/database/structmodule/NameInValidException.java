package Holmos.dbtest.database.structmodule;

import Holmos.webtest.tools.HolmosWindow;

/**属性名字不合法的类
 * @author 吴银龙(15857164387)
 * */
public class NameInValidException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameInValidException(String exceStr){
		super(exceStr);
		HolmosWindow.closeAllWindows();
	}
}
