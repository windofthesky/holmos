package cn.autosense.plug.psm;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 上午10:25:02<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface GroupInfo extends VarInfo {

	/**
	 * 添加组
	 * @param info
	 * @return
	 */
	boolean add(GroupInfo info);

	/**
	 * 删除组
	 * @param info
	 * @return
	 */
	boolean remove(GroupInfo info);

	/**
	 * 根据name得到组
	 * @param name
	 * @return
	 */
	GroupInfo group(String name);

	/**
	 * 是否是根
	 * @return
	 */
	boolean isRoot();
}
