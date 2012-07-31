package cn.autosense.browser.data;

import java.io.Serializable;

import lombok.Data;
import cn.autosense.browser.gui.JMePagePanel;
import cn.autosense.browser.gui.JMePageTreePanel;
import cn.autosense.browser.gui.componment.JMeFieldCollectionPanel;
import cn.autosense.browser.gui.componment.JMeFieldInfoDetailPanel;
import cn.autosense.browser.gui.componment.JMeSelectedFieldPanel;
import cn.autosense.browser.gui.componment.JMeWebBrowser;
import cn.autosense.browser.gui.event.JMeMouseAdapter;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-28 下午5:13:41<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
@Data
public class ComponentBean implements Serializable {

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;

    private static ComponentBean bean;
    private JMeWebBrowser        browser;
    private JMeSelectedFieldPanel    selectHtmlPanel;
    private JMeFieldCollectionPanel       fieldHtmlPanel;
    private JMeFieldInfoDetailPanel pageInfoDetailPanel;
    private JMePageTreePanel pageTreePanel;
    private JMePagePanel pagePanel;
    
    /*************** JMeWebBrowser��� *******************/
    //private String               htmlContent;
    //private boolean              loadingDone;
    //private String               status;
    //private String               retrievedUrl;
    //private Document             document;
    private JMeMouseAdapter      mouseAdapter;
    
    private ComponentBean() {
    }

    public synchronized static ComponentBean getInstance() {
        if (null == bean) {
            bean = new ComponentBean();
        }
        return bean;
    }

}
