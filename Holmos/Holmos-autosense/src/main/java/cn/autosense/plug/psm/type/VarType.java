package cn.autosense.plug.psm.type;

import lombok.Getter;

/**
 * PSM 的所有的元素类型
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-8-1 下午2:54:13<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public enum VarType {

	UNKNOWN(""),
	BUTTON("button"),
	CHECKBOX("checkbox"),
	COLLECTION("collection"),
	COMOBOBOX("select"),
	ELEMENT("element"),
	FRAME("iframe"),
	LABEL("label"),
	LINK("a"),
	RADIOBUTTON("radio"),
	RICHTEXTFIELD("richtextfield"),
	TABLE("table"),
	TEXTFIELD("input"),
	IMAGE("img"),
	
	SUBPAGE("subpage"),
	FOLDER("folder"),
	PAGE("page");
	
	@Getter
	private String tagName;

	VarType(String tagName) {
		this.tagName = tagName;
	}

	public static VarType getInfoType(String tagName) {
		VarType infoType = VarType.ELEMENT;
		try {
			infoType = VarType.valueOf(tagName.toUpperCase());
		} catch (Exception e) {
		}
		return infoType;
	}
	
}
