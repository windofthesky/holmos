package cn.autosense.browser.gui.componment;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import lombok.Getter;
import chrriis.dj.nativeswing.swtimpl.components.DefaultWebBrowserDecorator;
import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.data.InitDataBean;
import craky.componentc.JCButton;
import craky.componentc.JCToggleButton;

import static cn.autosense.browser.util.Const.SELECTOR_DIV_HIDE;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-7 上午11:41:31<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeWebBrowserDecorator extends DefaultWebBrowserDecorator implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Getter
    private JCButton           homePageBtn;
    @Getter
    private JTextField        urlTxf;
    @Getter
    private JButton           goBtn;
    @Getter
    private JCButton           setBtn;
    @Getter
    private JCButton           upBtn;
    @Getter
    private JCButton           downBtn;
    @Getter
    private JCToggleButton     isSpiderTgBtn;

    private JMeWebBrowser     webBrowser;
    @Getter
    private JButton backBtn;
    @Getter
    private JButton forwardBtn;
    @Getter
    private JButton reloadBtn;

    public JMeWebBrowserDecorator(JMeWebBrowser webBrowser, Component renderingComponent) {
        super(webBrowser, renderingComponent);
        this.webBrowser = webBrowser;
    }

    @Override
    protected void addButtonBarComponents(WebBrowserButtonBar buttonBar) {
        buttonBar.add(getBlackLbl());

        backBtn = buttonBar.getBackButton();
        backBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/back.png")));
        buttonBar.add(backBtn);
        buttonBar.add(getBlackLbl());

        forwardBtn = buttonBar.getForwardButton();
        forwardBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/forward.png")));
        buttonBar.add(forwardBtn);

        buttonBar.add(getBlackLbl());

        reloadBtn = buttonBar.getReloadButton();
        reloadBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/reload.png")));
        buttonBar.add(reloadBtn);

        buttonBar.add(getBlackLbl());

        buttonBar.add(buttonBar.getStopButton());

        buttonBar.add(getBlackLbl());

        /*homePageBtn = buttonBar.getReloadButton();
        homePageBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/home.png")));
        homePageBtn.addActionListener(this);
        buttonBar.add(homePageBtn);

        buttonBar.add(getBlackLbl());*/
    }

    private JLabel getBlackLbl() {
        return new JLabel(" ");
    }

    @Override
    protected void addLocationBarComponents(WebBrowserLocationBar locationBar) {
        urlTxf = locationBar.getLocationField();
        urlTxf.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        locationBar.add(urlTxf);

        locationBar.add(getBlackLbl());

        goBtn = locationBar.getGoButton();
        goBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/go.png")));
        locationBar.add(goBtn);

        locationBar.add(getBlackLbl());

        isSpiderTgBtn = new JCToggleButton(" Browser ");
        isSpiderTgBtn.addActionListener(this);
        locationBar.add(isSpiderTgBtn);

        locationBar.add(getBlackLbl());

        /*setBtn = new JCButton("");
        setBtn.setIcon(new ImageIcon(JMeWebBrowser.class.getResource("/images/wrench.png")));
        locationBar.add(setBtn);*/

        upBtn = new JCButton("");
        upBtn.setToolTipText("Ruler up");
        upBtn.setIcon(new ImageIcon(JMeWebBrowserDecorator.class.getResource("/images/ruler_plus.png")));
        upBtn.addActionListener(this);
        // locationBar.add(upBtn);

        locationBar.add(getBlackLbl());

        downBtn = new JCButton("");
        downBtn.setToolTipText("Ruler down");
        downBtn.setIcon(new ImageIcon(JMeWebBrowserDecorator.class.getResource("/images/ruler_minus.png")));
        downBtn.addActionListener(this);
       // locationBar.add(downBtn);

        locationBar.add(new JLabel("  "));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homePageBtn) {
            homePageBtn_click();
        }if (e.getSource() == upBtn) {
            upBtn_click(upBtn);
        } else if (e.getSource() == downBtn) {
            downBtn_click(downBtn);
        } else if (e.getSource() == isSpiderTgBtn) {
            isSpiderTgBtn_select(isSpiderTgBtn);
        } else {
            // for the time being, please do nothing
        }
    }

    public void homePageBtn_click() {
        // urlTxf.setText(HOME_PAGE_URL);
        webBrowser.navigate(InitDataBean.getInstance().getHomePage());
    }


    @Override
    public JMeWebBrowser getWebBrowser() {
        return webBrowser;
    }

    /**
     * isSpiderTgBtn
     * 
     * @param isSpiderTgBtn
     */
    protected void isSpiderTgBtn_select(JToggleButton isSpiderTgBtn) {
        boolean isSelected = isSpiderTgBtn.isSelected();
        MouseAdapter mouseAdapter = ComponentBean.getInstance().getMouseAdapter();
        if (isSelected) {
            webBrowser.getNativeComponent().addMouseListener(mouseAdapter);
            isSpiderTgBtn.setText(" Spider  ");
        } else {
            webBrowser.executeJavascript(SELECTOR_DIV_HIDE);
            webBrowser.getNativeComponent().removeMouseListener(mouseAdapter);
            isSpiderTgBtn.setText(" Browser ");
        }
        urlTxf.setEnabled(!isSelected);
        backBtn.setEnabled(!isSelected);
        forwardBtn.setEnabled(!isSelected);
        reloadBtn.setEnabled(!isSelected);
        goBtn.setEnabled(!isSelected);

       // executeJS(null);
    }

    /**
     * 
     * 
     * @param upBtn
     */
    protected void upBtn_click(JCButton upBtn) {
        executeJS("");
    }

    /**
     * 
     * 
     * @param downBtn
     */
    protected void downBtn_click(JCButton downBtn) {
        executeJS("");
    }

    private void executeJS(String jsStr) {
        ComponentBean bean = ComponentBean.getInstance();
        JMeWebBrowser browser = bean.getBrowser();
        if (isSpiderTgBtn.isSelected()) {
            //browser.executeJavascript(SELECTOR_DIV_ADD + SELECTOR_DIV_STYLE_ADD + jsStr);
        	//String path1 = (String) browser.executeJavascriptWithResult(Const.EXECUTE_SELECT);

            //String selectHtml = (String) browser.executeJavascriptWithResult(SELECTOR_HTML);
            //String path = (String) browser.executeJavascriptWithResult(SELECTOR_XPATH);

            //bean.getSelectHtmlPanel().getHtmlCodeTxa().setText(selectHtml);
            //bean.getSelectHtmlPanel().getXpathTxa().setText(path1);

            // selected html tree
            //DefaultMutableTreeNode rootNode = UtilGUI.xmlToTreeNode(selectHtml);
            //bean.getSelectHtmlPanel().getHtmlTree().setModel(new DefaultTreeModel(rootNode));

            // selected element attributes

        } else {
            browser.executeJavascript(SELECTOR_DIV_HIDE);
        }
    }

}
