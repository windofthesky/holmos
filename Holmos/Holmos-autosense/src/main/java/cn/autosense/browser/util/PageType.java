package cn.autosense.browser.util;

import lombok.Getter;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-9 下午9:54:01<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public enum PageType {
	
	Page("page"),
	SUBPAGE("subpage"),
	FRAME("frame"),
	COLLECTION("collection"),
	Folder("folder"),
	Package("package");// 辅助使用
	
	@Getter
	private String name;
	
	PageType(String name) {
		this.name = name;
	}

}
