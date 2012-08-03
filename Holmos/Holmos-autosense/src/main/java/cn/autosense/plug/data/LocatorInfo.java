package cn.autosense.plug.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-2 上午10:33:16<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class LocatorInfo implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 元素的直接定位器，例如css,xpath,text
	 */
	private Map<LocatorType, String> locators = new HashMap<LocatorType, String>();
	/**
	 * 元素的属性列表
	 */
	private Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * 添加元素属性
	 * @param name
	 * @param value
	 */
	public void attr(String name, String value) {
		this.attributes.put(name, value);
	}

	/**
	 * 得到元素的属性
	 * @param name
	 * @return
	 */
	public String attr(String name) {
		return this.attributes.get(name);
	}

	/**
	 * 得到所有的属性
	 * @return
	 */
	public Map<String, String> attr() {
		return this.attributes;
	}

	/**
	 * 添加元素定位器
	 * 
	 * @param type
	 * @param value
	 */
	public void locator(LocatorType type, String value) {
		this.locators.put(type, value);
	}

	/**
	 * 得到元素的定位器
	 * 
	 * @param type
	 * @return
	 */
	public String locator(LocatorType type) {
		return this.locators.get(type);
	}

	/**
	 * 得到元素的定位器
	 * 
	 * @param type
	 * @return
	 */
	public Map<LocatorType, String> locator() {
		return this.locators;
	}

	@Override
	protected LocatorInfo clone() throws CloneNotSupportedException {
		// TODO 深度clone
		return (LocatorInfo) super.clone();
	}
	
	/**
	 * 判断是否相等
	 * @param info
	 * @return
	 */
	public boolean equals(LocatorInfo info) {
		if (locators.size() != info.locator().size()) {
			return false;
		}
		if (attributes.size() != info.attr().size()) {
			return false;
		}
		for (Entry<LocatorType, String> e : locators.entrySet()) {
			if (e.getValue() != null
					&& !e.getValue().equalsIgnoreCase(
							info.locator(e.getKey()))) {
				return false;
			}
		}
		for (Entry<String, String> e : attributes.entrySet()) {
			if (e.getValue() != null
					&& !e.getValue().equalsIgnoreCase(
							info.attr(e.getKey()))) {
				return false;
			}
		}
		return true;
	}
}
