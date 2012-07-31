package Holmos.Holmos.plug.PSM;

import lombok.Getter;

/**PSM 的所有的元素类型
 * @author 吴银龙(15857164387)
 * */
public enum INFOTYPE {

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
	SUBPAGE("subpage"),
	TABLE("table"),
	TEXTFIELD("input"),
	IMAGE("img");
	
	@Getter
	private String tagName;

	INFOTYPE(String tagName) {
		this.tagName = tagName;
	}

	public static INFOTYPE getInfoType(String tagName) {
		INFOTYPE infoType = INFOTYPE.ELEMENT;
		try {
			infoType = INFOTYPE.valueOf(tagName.toUpperCase());
		} catch (Exception e) {
		}
		return infoType;
	}
	
}
