package cn.autosense.plug.psm;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 下午1:34:40<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface CollectionInfo extends VarInfo {

	/**
	 * 添加元素
	 * @param info
	 * @return
	 */
	boolean add(ElementInfo info);

	/**
	 * 删除元素
	 * @param info
	 * @return
	 */
	boolean remove(ElementInfo info);

	/**
	 * 根据name得到元素
	 * @param name
	 * @return
	 */
	ElementInfo element(String name);
	
	boolean add(CollectionInfo info);

	boolean remove(CollectionInfo info);

	GroupInfo group(String name);

}
