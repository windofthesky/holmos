package cn.autosense.browser.data;

import java.io.Serializable;
import java.util.Properties;

import lombok.Getter;

import cn.autosense.browser.exchange.DataExchangeFactory;
import cn.autosense.browser.exchange.IDataExchange;
import cn.autosense.browser.util.CommonUtil;
import cn.autosense.browser.util.Const;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-8 上午11:34:07<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class InitDataBean implements Serializable {
    /**
     * 
     */
    private static final long    serialVersionUID = 1L;

    private static InitDataBean bean;
    private Properties initInfo;
    @Getter
    private IDataExchange dataExchange;
    //@Getter
    //private String initNodeStr;

    private InitDataBean() {
        initInfo = CommonUtil.loadProperties(Const.INIT_FILE);
        dataExchange = DataExchangeFactory.build(getDataExchangeClassName());
        //initNodeStr = dataExchange.loadFolderPageInfo(getRootPath());
    }

    public synchronized static InitDataBean getInstance() {
        if (null == bean) {
            bean = new InitDataBean();
        }
        return bean;
    }
    
    public String getRootPath() {
    	String rootPath = initInfo.getProperty("rootPath").trim().replace("\\", "/");
    	return rootPath.endsWith("/") ? rootPath : rootPath + "/";
    }
    
    public static void main(String[] args) {
		System.out.println(InitDataBean.getInstance().getRootPath());
	}
    
    public void setRootPath(String rootPath) {
    	initInfo.setProperty("rootPath", rootPath);
    }

    /*public String getTreeNodeStr() {
    	return dataExchange.loadRootPathInfo(initInfo.getProperty("rootPath").trim());
    }*/
    
    public String getDataExchangeClassName() {
    	return initInfo.getProperty("dateExchange").trim();
    }
    
    public String getHomePage() {
    	return initInfo.getProperty("homePage").trim();
    }
}
