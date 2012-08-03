package cn.autosense.plug.psm;

import java.io.Serializable;
import java.util.List;

import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-1 下午2:54:13<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface VarInfo extends Serializable, Cloneable  {
	/**
	 * 获得名称
	 * @return
	 */
	String getName();
	
	/**
	 * 设置名称
	 * @param name
	 */
	void setName(String name);

	/**
	 * 获得备注
	 * @return
	 */
	String getComment();

	/**
	 * 设置备注
	 * @param comment
	 */
	void setComment(String comment);

	/**
	 * 获得类型
	 * @return
	 */
	VarType getType();
	
	/**
	 * 设置类型
	 * @param type
	 */
	void setType(VarType type);
	
	/**
	 * 设置父容器信息
	 * @param parentContainer
	 */
	void parent(VarInfo parent);

	/**
	 * 获得父容器信息
	 * @return
	 */
	VarInfo parent();

	/**
	 * 
	 * @return
	 */
	List<VarInfo> child();
}
