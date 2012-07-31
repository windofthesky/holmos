package cn.autosense.browser.gui;

import java.awt.Dimension;

import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.gui.componment.JMeFieldCollectionPanel;
import cn.autosense.browser.gui.componment.JMeSelectedFieldPanel;


import lombok.Getter;
import craky.componentc.JCTabbedPane;
import javax.swing.JTabbedPane;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-29 上午10:50:53<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeTabbedPane extends JCTabbedPane {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private JMeSelectedFieldPanel selectHtmlPanel;
    @Getter
    private JMeFieldCollectionPanel fieldHtmlPanel;

    public JMeTabbedPane() {
    	setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    	setTabPlacement(JTabbedPane.LEFT);
    	this.setMinimumSize(new Dimension(307, 650));
        this.setMaximumSize(new Dimension(307, 650));
        
        initComponents();
        addComponentToBean();
    }

    private void initComponents() {
        selectHtmlPanel = new JMeSelectedFieldPanel();
        addTab("Selected", null, selectHtmlPanel, null);

        fieldHtmlPanel = new JMeFieldCollectionPanel();
        addTab("Field", null, fieldHtmlPanel, null);
    }

    private void addComponentToBean() {
    	ComponentBean bean = ComponentBean.getInstance();
        bean.setSelectHtmlPanel(selectHtmlPanel);
        bean.setFieldHtmlPanel(fieldHtmlPanel);
    }

}
