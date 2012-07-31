package cn.autosense.browser.gui.event;

import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

import cn.autosense.browser.gui.componment.JMeWebBrowser;
import cn.autosense.browser.util.Const;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-13 下午3:48:55<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeWebBrowserAdapter extends WebBrowserAdapter {

    private JMeWebBrowser browser;

    @Override
    public void locationChanged(WebBrowserNavigationEvent e) {
        browser = (JMeWebBrowser) e.getWebBrowser();
        //browser.executeJavascript(Const.TARGET_REMOVE + Const.SELECTOR_DIV_SHOW);
        // browser.getNativeComponent().setVisible(false);

        if (browser.getLoadingProgress() == 100) {
        	browser.executeJavascript(Const.JQUERY_LIB + Const.PATH_FINDER_LIB);
        	//System.out.println("*********************browser.getLoadingProgress() == 100");
            // new HtmlOperate(browser).operate();
           // AHtmlOperate htmlOperate = ComponentBean.getInstance().getHtmlOperate();
           // htmlOperate.setBrowser(browser);
           // htmlOperate.operate();
            // try {
            // Thread.sleep(Const.HTML_BEFORE_SHOW_SLEEP_TIME);
            // } catch (InterruptedException e1) {
            // e1.printStackTrace();
            // }
            // browser.getNativeComponent().setVisible(true);
        }
    }

}
