package cn.autosense.browser.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Holmos.Holmos.plug.PSM.INFOTYPE;

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
@AllArgsConstructor
public class SelectedFieldBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String comment;
    private INFOTYPE type;
    private HtmlFiledBean fieldBean;
    
}
