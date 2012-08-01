package cn.autosense.browser.exchange;

import cn.autosense.browser.exchange.util.CatalogDataType;
import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ReturnInfo;
import cn.autosense.browser.gui.render.PageNode;
import cn.autosense.plug.psm.folder.FolderInfo;

/**
 * 根据传入的CatalogPathType, 决定整个类中的data是什么格式的.
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 下午2:56:57<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface IDataExchange {
	
	public String getSelectPagePath();
	public void setSelectPagePath(String selectPagePath);

	public CatalogDataType getCatalogDataType();
	public void setCatalogDataType(CatalogDataType type);
	
	public FolderInfo getRootFolderInfo();

	/**
	 * 根据参数目录得到该目录下所有的目录和文件结构
	 * @param rootPath
	 * @return
	 */
	public ReturnInfo loadInfo(PageNode selectNode);

	public CreateReturnInfo addVariable(String parentPagePath, String jsondata);
	
	////////////////////////////////////////////////////////////////////
	public void addElement(String pagePath, String jsondata);
	public void removeElement(String pageNamePath, String elementName);
	public void removeAllElements(String pageNamePath);
}
