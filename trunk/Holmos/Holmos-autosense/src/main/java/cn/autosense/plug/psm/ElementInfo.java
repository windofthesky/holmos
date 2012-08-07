package cn.autosense.plug.psm;

import cn.autosense.plug.data.LocatorInfo;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-1 下午7:11:40<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface ElementInfo extends VarInfo {

	/**
	 * 获得元素的定位信息
	 * @return
	 */
	LocatorInfo getLocatorInfo();

	/**
	 * 设置元素的定位信息
	 * @param locatorInfo
	 */
	void setLocatorInfo(LocatorInfo locatorInfo);

}
