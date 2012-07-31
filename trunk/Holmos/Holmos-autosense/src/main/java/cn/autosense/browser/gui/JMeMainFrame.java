package cn.autosense.browser.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.util.Const;

import com.breeze.core.util.Util;
import com.breeze.core.util.UtilGUI;

import craky.componentc.JCButton;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-8 上午12:15:00<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeMainFrame extends JFrame implements Const {

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;

    private JMeMainPanel formPanel;

    public JMeMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1220, 738);
        UtilGUI.setScreenCenter(this);
        setTitle("AutoSense......");
        setIconImage(Toolkit.getDefaultToolkit().getImage(JMeMainFrame.class.getResource("/images/globe.png")));
        setVisible(true);

        JMenuBar bar = new JMenuBar();
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(0, 0, 0, 0));
        bar.setBorderPainted(false);
        bar.setLayout(new GridLayout(1, 2));
        bar.setPreferredSize(new Dimension(40, 20));
        bar.setFocusable(false);
        bar.add(new JCButton("aaaaa"));
        bar.add(new JCButton("bbbbb"));
        bar.add(new JCButton("ccccc"));
        //getTitleContentPane().add(bar, LineLayout.END);

        formPanel = new JMeMainPanel();
        setContentPane(formPanel);
    }
    
    /*----------------------------------main----------------------------------*/
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UtilGUI.getUI();
                    String rootPath = InitDataBean.getInstance().getRootPath();
                    if(Util.strIsNullOrEmpty(rootPath)) {
                    	new JMeInitFrame();
                    } else {
                    	new JMeMainFrame();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        NativeInterface.runEventPump();
    }

}
