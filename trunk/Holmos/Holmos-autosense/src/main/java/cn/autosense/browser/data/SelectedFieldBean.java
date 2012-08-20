package cn.autosense.browser.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cn.autosense.plug.data.LocatorInfo;
import cn.autosense.plug.psm.type.VarType;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-8-3 下午5:51:48<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
@Getter
@Setter
@NoArgsConstructor
public class SelectedFieldBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String comment;
    private VarType type;
    private String tagName;
    private String html;
    private LocatorInfo locatorInfo;

	public SelectedFieldBean(String id, String name, String comment, String tagName, String html, LocatorInfo locatorInfo) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.type = VarType.getInfoType(tagName);
		this.tagName = tagName;
		this.html = html;
		this.locatorInfo = locatorInfo;
	}
    
    
}
