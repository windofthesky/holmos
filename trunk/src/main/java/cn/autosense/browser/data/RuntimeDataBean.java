package cn.autosense.browser.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-9 下午4:06:52<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class RuntimeDataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static RuntimeDataBean bean;
	@Getter
    private Map<String, SelectedFieldBean> selectedData;
	@Getter
	@Setter
	private String selectPagePath;
//	@Getter
//	@Setter
//	private String selectPageName;
	

    private RuntimeDataBean() {
    	selectedData = new HashMap<String, SelectedFieldBean>();
    }

    public synchronized static RuntimeDataBean getInstance() {
        if (null == bean) {
            bean = new RuntimeDataBean();
        }
        return bean;
    }
    
    public void putSelectedFieldBean(SelectedFieldBean fieldBean) {
    	selectedData.put(fieldBean.getId(), fieldBean);
    }
}
