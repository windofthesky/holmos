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

import Holmos.Holmos.plug.PSM.INFOTYPE;
import cn.autosense.browser.data.ComponentBean;
import cn.autosense.browser.data.HtmlFiledBean;
import cn.autosense.browser.data.InitDataBean;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.gui.componment.JMeWebBrowser;
import cn.autosense.browser.util.CommonUtil;

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
        	if(null != fieldBean) {
	        	RuntimeDataBean.getInstance().putSelectedFieldBean(fieldBean);
	        	String data = new Gson().toJson(fieldBean, new TypeToken<SelectedFieldBean>() {}.getType());
	        	
	        	String selectPagePath = RuntimeDataBean.getInstance().getSelectPagePath();
	        	//String selectPageName = RuntimeDataBean.getInstance().getSelectPageName();
	        	//if(Util.strIsNullOrEmpty(selectPagePath) || Util.strIsNullOrEmpty(selectPageName)) {
	        	if(Util.strIsNullOrEmpty(selectPagePath)) {
	        		JOptionPane.showMessageDialog(null, "请先选择page!", "警告", JOptionPane.WARNING_MESSAGE);
	        	}else {
	        		putDataToSelectedFieldPanel(fieldBean);
	        		InitDataBean.getInstance().getDataExchange().addElement(selectPagePath, data);
	        	}
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
    	bean.getSelectHtmlPanel().getXpathTxa().setText(fieldBean.getFieldBean().getXpath());
    	// CSS xpath
    	bean.getSelectHtmlPanel().getCssPathTxa().setText(fieldBean.getFieldBean().getSelector());
    	// html
    	bean.getSelectHtmlPanel().getHtmlCodeTxa().setText(fieldBean.getFieldBean().getHtml());
    	// attributes
    	CommonUtil.setAttrsForTable(bean.getSelectHtmlPanel().getAttrsTable(), fieldBean.getFieldBean().getAttributes());
    	//
    	if (!Util.strIsNullOrEmpty(fieldBean.getFieldBean().getHtml())) {
    		Document selectHtmlDoc = Jsoup.parse(fieldBean.getFieldBean().getHtml());
    		
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
    		fieldBean = new SelectedFieldBean(uuid, createElementName(selectedBean, uuid), "", INFOTYPE.getInfoType(selectedBean.getTagName()), selectedBean);
    	}
    	return fieldBean;
    }

    private String createElementName(HtmlFiledBean selectedBean, String uuid) {
    	String id = selectedBean.getAttributes().get("id");
    	if(Util.strNotIsNullOrEmpty(id)) {
    		return id;
    	}

    	String name = selectedBean.getAttributes().get("name");
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
    	Object[] data = {bean.getName(), CommonUtil.firstUpper(bean.getType().name()), bean.getFieldBean().getXpath(), bean.getFieldBean().getSelector(), bean.getId() };
        if (null != table) {
            ((DefaultTableModel) table.getModel()).addRow(data);
            table.updateUI();
        }
    }

}
