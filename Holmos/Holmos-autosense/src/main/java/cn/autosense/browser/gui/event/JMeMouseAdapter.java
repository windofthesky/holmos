package cn.autosense.browser.gui.event;

import static cn.autosense.browser.util.Const.SELECTOR_DIV_HIDE;
import static cn.autosense.browser.util.Const.SELECTOR_DIV_SHOW;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import lombok.Getter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.data.HtmlFiledBean;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.gui.componment.JMeWebBrowser;
import cn.autosense.browser.gui.render.VarNode;
import cn.autosense.browser.util.CommonUtil;
import cn.autosense.plug.data.LocatorInfo;
import cn.autosense.plug.data.LocatorType;
import cn.autosense.plug.psm.CollectionInfo;
import cn.autosense.plug.psm.ElementInfo;
import cn.autosense.plug.psm.impl.DefaultElementInfo;

import com.breeze.core.util.Util;
import com.breeze.core.util.UtilGUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2011-7-12 下午9:56:54<br>
 * @since 1.0<br>
 * @version 1.0<br>
 * 			Remark:<br>
 * 
 */
public class JMeMouseAdapter extends MouseAdapter {

    private ComponentBean bean;
    @Getter
    private HtmlFiledBean selectedBean;

    public JMeMouseAdapter() {
        bean = ComponentBean.getInstance();
        bean.setMouseAdapter(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	JMeWebBrowser browser = bean.getBrowser();
        if (browser.getDecorator().getIsSpiderTgBtn().isSelected()) {
        	String selectData = (String) browser.executeJavascriptWithResult(SELECTOR_DIV_SHOW);
        	selectedBean = getSelectedBeanFromJS(browser, selectData);
        	// 页面展现选择的元素的信息
        	SelectedFieldBean fieldBean = createSelectedFieldBean(selectedBean);
        	if(null == fieldBean) {
        		JOptionPane.showMessageDialog(null, "选择的元素为空", "警告", JOptionPane.WARNING_MESSAGE);
        		return;
        	}
        	RuntimeDataBean.getInstance().putSelectedFieldBean(fieldBean);
        	
        	VarNode selectNode = RuntimeDataBean.getInstance().getSelectNode();
        	if(Util.isNull(selectNode)) {
        		JOptionPane.showMessageDialog(null, "请先选择page!", "警告", JOptionPane.WARNING_MESSAGE);
        		return;
        	}
    		putDataToSelectedFieldPanel(fieldBean);
    		
    		ElementInfo element = new DefaultElementInfo(fieldBean.getName(), fieldBean.getComment(), fieldBean.getType(), fieldBean.getLocatorInfo());
    		
    		if(selectNode.isFolder()) {
    			JOptionPane.showMessageDialog(null, "请先选择page, 而不是Folder!", "警告", JOptionPane.WARNING_MESSAGE);
    			return;
    		}
    		
    		if(selectNode.getInfo() instanceof CollectionInfo) {
    			CollectionInfo cInfo = (CollectionInfo) selectNode.getInfo();
    			cInfo.add(element);
    		} else {
    			JOptionPane.showMessageDialog(null, "请先选择page, 未知类型!", "警告", JOptionPane.WARNING_MESSAGE);
    			return;
    		}
        } else {
            browser.executeJavascript(SELECTOR_DIV_HIDE);
        }
    }

    /**
     * 页面展现选择的元素的信息
     * @param fieldBean
     */
    private void putDataToSelectedFieldPanel(SelectedFieldBean fieldBean) {
    	// xpath
    	bean.getSelectHtmlPanel().getXpathTxa().setText(fieldBean.getLocatorInfo().locator(LocatorType.XPATH));
    	// CSS xpath
    	bean.getSelectHtmlPanel().getCssPathTxa().setText(fieldBean.getLocatorInfo().locator(LocatorType.SELECTOR));
    	// html
    	bean.getSelectHtmlPanel().getHtmlCodeTxa().setText(fieldBean.getHtml());
    	// attributes
    	CommonUtil.setAttrsForTable(bean.getSelectHtmlPanel().getAttrsTable(), fieldBean.getLocatorInfo().attr());
    	//
    	if (!Util.strIsNullOrEmpty(fieldBean.getHtml())) {
    		Document selectHtmlDoc = Jsoup.parse(fieldBean.getHtml());
    		
    		// selected html tree
    		DefaultMutableTreeNode rootNode = UtilGUI.xmlToTreeNode(selectHtmlDoc);
    		bean.getSelectHtmlPanel().getHtmlTree().setModel(new DefaultTreeModel(rootNode));
    		
    		// select info to Table
    		addSelectedRowForTable(bean.getFieldHtmlPanel().getSelectedElementTable(), fieldBean);// 选取的元素添加到table
    	}
    }

    /**
     * 创建选择的元素的Bean
     * @param selectedBean
     * @return
     */
    private SelectedFieldBean createSelectedFieldBean(HtmlFiledBean selectedBean) {
    	if(null == selectedBean) {
    		JOptionPane.showMessageDialog(null, "你选择的为空!", "警告", JOptionPane.WARNING_MESSAGE);
    		return null;
    	}
    	SelectedFieldBean fieldBean = null;

    	if (!Util.strIsNullOrEmpty(selectedBean.getHtml())) {
    		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    		
    		LocatorInfo info = new LocatorInfo();
    		info.attr(selectedBean.getAttrs());
    		info.locator(LocatorType.XPATH, selectedBean.getXpath());
    		info.locator(LocatorType.SELECTOR, selectedBean.getSelector());
    		info.locator(LocatorType.TEXT, selectedBean.getText());
    		// TODO 此处的comment设置为空
    		fieldBean = new SelectedFieldBean(uuid, createElementName(selectedBean, uuid), "", 
    				selectedBean.getTagName(), selectedBean.getHtml(), info);
    	}
    	return fieldBean;
    }

    /**
     * 得到Element的name, 此方法有待优化
     * @param selectedBean
     * @param uuid
     * @return
     */
    private String createElementName(HtmlFiledBean selectedBean, String uuid) {
    	// TODO
    	String id = selectedBean.getAttrs().get("id");
    	if(Util.strNotIsNullOrEmpty(id)) {
    		return id;
    	}

    	String name = selectedBean.getAttrs().get("name");
    	if(Util.strNotIsNullOrEmpty(id)) {
    		return name;
    	}

    	return selectedBean.getTagName() + "_" + new SimpleDateFormat("yyyymmddhhMMss").format(new Date());
    }

    /**
     * 把页面中选择的元素的信息Json数据转换为Java对象
     * @param browser
     * @param data
     * @return
     */
    private HtmlFiledBean getSelectedBeanFromJS(JMeWebBrowser browser, String data) {
		if(Util.isNull(data)) {
			// TODO
		}
		return new Gson().fromJson(data, new TypeToken<HtmlFiledBean>() {}.getType());
    }

    /**
     * 把选择元素的属性添加到界面的JTable中显示
     * @param table
     * @param bean
     */
    private void addSelectedRowForTable(JTable table, SelectedFieldBean bean) {
    	Object[] data = {bean.getName(), CommonUtil.firstUpper(bean.getType().name()), bean.getLocatorInfo().locator(LocatorType.XPATH), bean.getLocatorInfo().locator(LocatorType.SELECTOR), bean.getId() };
        if (null != table) {
            ((DefaultTableModel) table.getModel()).addRow(data);
            table.updateUI();
        }
    }

}
