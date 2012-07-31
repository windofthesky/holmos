package cn.autosense.browser.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.breeze.core.util.UtilGUI;

import lombok.Getter;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.gui.componment.JMeWebBrowser;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-29 下午2:36:30<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeMainPanel extends JPanel {
	
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	@Getter
	private JMePagePanel pageTreePanel;
	@Getter
	private JMeWebBrowser browser;
	@Getter
	private JMeOperatePanel operateTabbedPane;
	
	public JMeMainPanel() {
		setLayout(new BorderLayout(0, 0));
		
		pageTreePanel = new JMePagePanel();
		add(pageTreePanel, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

        browser = new JMeWebBrowser(InitDataBean.getInstance().getHomePage());
        panel.add(browser, BorderLayout.CENTER);

        operateTabbedPane = new JMeOperatePanel();
        panel.add(operateTabbedPane, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UtilGUI.getUI();
                    JFrame frame = UtilGUI.show(new JMeMainPanel(), 800, 600);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        NativeInterface.runEventPump();
	}
}
