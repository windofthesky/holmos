package cn.autosense.browser.gui.componment;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lombok.Getter;
import lombok.Setter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserDecorator;

import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.gui.event.JMeMouseAdapter;
import cn.autosense.browser.gui.event.JMeWebBrowserAdapter;
import cn.autosense.browser.util.Const;


/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-6-28 下午05:33:15<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeWebBrowser extends JWebBrowser {

    /**
     * 
     */
    private static final long      serialVersionUID = -3119235830046796158L;

    static {
        System.setProperty("org.eclipse.swt.browser.XULRunnerPath", Const.XUL_RUNNER);
    }

    @Getter
    @Setter
    private String                 url;
    @Getter
    private JMeWebBrowserDecorator decorator;
    private ComponentBean          bean;
    @Getter
    private JMeWebBrowserAdapter   browserAdapter;
    @Getter
    private JMeMouseAdapter        mouseAdapter;

    /*-------------------------------构造器--------------------------------*/
    public JMeWebBrowser(String url) {
        this(JWebBrowser.useXULRunnerRuntime(), url);
    }

    public JMeWebBrowser(NSOption option, String url) {
        bean = ComponentBean.getInstance();

        NativeInterface.open();

        this.setUrl(url);
        this.setJavascriptEnabled(true);
        this.setBarsVisible(true);
        this.setMenuBarVisible(false);
        this.setStatusBarVisible(true);
        mouseAdapter = new JMeMouseAdapter();
        browserAdapter = new JMeWebBrowserAdapter();
        this.addWebBrowserListener(browserAdapter);
        navigate(url);
        this.bean.setBrowser(this);
    }

    /*-------------------------------JWebBrowser---------------------------------*/
    @Override
    protected WebBrowserDecorator createWebBrowserDecorator(Component renderingComponent) {
        decorator = new JMeWebBrowserDecorator(this, renderingComponent);
        return decorator;
    }

    //
    // public void execute(String url) {
    // this.setUrl(url);
    // this.navigate(getUrl());
    // }

//    public String getUrl() {
        /*
         * if (Util.strIsNullOrEmpty(url) || url.equals(Const.DEFAULT_URL)) {
         * url = Const.DEFAULT_URL;
         * }
         * try {
         * url = new URL(url).toString();
         * } catch (MalformedURLException e) {
         * System.out.println("dafjsakfjsaklfjslk");
         * JOptionPane.showMessageDialog(null, "请输入正确的网址!", "警告",
         * JOptionPane.WARNING_MESSAGE);
         * }
         */
//        return url;
//    }

    public Document getDocument() {
        return Jsoup.parse(this.getHTMLContent());
    }

    /*-------------------------------main---------------------------------*/
    public static void main(String[] args) {
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("MashupEye Browser......");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JMeWebBrowserToolBar toolBar = new
                // JMeWebBrowserToolBar(bean);
                // toolBar.getIsSpiderTgBtn().setSelected(true);
                // bean.setToolBar(toolBar);
                JMeWebBrowser browser = new JMeWebBrowser("http://www.baidu.com/");
                ComponentBean.getInstance().setBrowser(browser);
                // JWebBrowser browser = new JWebBrowser();
                // browser.navigate("http://www.baidu.com/");
                frame.getContentPane().add(browser, BorderLayout.CENTER);
                frame.setSize(800, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
    }
}