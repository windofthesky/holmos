package cn.autosense.browser;

import javax.swing.SwingUtilities;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.gui.JMeInitFrame;
import cn.autosense.browser.gui.JMeMainFrame;

import com.breeze.core.util.Util;
import com.breeze.core.util.UtilGUI;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 下午1:41:11<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class Main {

	public static void main(String[] args) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UtilGUI.getUI();
                    String rootPath = InitDataBean.getInstance().getRootPath();
                    if(Util.strIsNullOrEmpty(rootPath)) {
                    	new JMeInitFrame(); // 初始化设置窗口
                    } else {
                    	new JMeMainFrame(); // 主窗口
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        NativeInterface.runEventPump();
    }

}
