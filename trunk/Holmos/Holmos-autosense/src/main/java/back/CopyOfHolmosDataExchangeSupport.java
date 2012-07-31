package back;

import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.exchange.IDataExchange;
import cn.autosense.browser.exchange.util.CatalogDataType;
import cn.autosense.browser.exchange.util.PageData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 ����1:45:09<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@AllArgsConstructor
public class CopyOfHolmosDataExchangeSupport implements IDataExchange {
	
	@Getter
	@Setter
	private CatalogDataType catalogDataType;
	@Getter
	@Setter
	private String selectPagePath;
	@Getter
	@Setter
	private String selectPageName;

	private FolderInfo pageStore;
	
	public CopyOfHolmosDataExchangeSupport() {
		this(CatalogDataType.XML, RuntimeDataBean.getInstance().getSelectPagePath(), RuntimeDataBean.getInstance().getSelectPageName());
	}
	
	public CopyOfHolmosDataExchangeSupport(CatalogDataType catalogDataType,
			String selectPagePath, String selectPageName) {
		this(catalogDataType, selectPagePath, selectPageName, PSMTool.getPageStore());
	}

	@Override
	public String loadFolderPageInfo(String rootPath) {
		String[] pageNames = rootPath.split("/");
		FolderInfo pageStoreThis = pageStore;
		for (String pageName : pageNames) {
			pageStoreThis.getInfoFromDisk();
			pageStoreThis = pageStoreThis.getChildFolderByName(pageName);
		}

		StringBuffer sb = new StringBuffer("<");
		sb.append("<").append(pageNames[pageNames.length - 1]).append(">");
		sb.append(getXmlFromPageStore(pageStoreThis));
		sb.append("</").append(pageNames[pageNames.length - 1]).append(">");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new CopyOfHolmosDataExchangeSupport().loadFolderPageInfo(""));
	}
	
	private String getXmlFromPageStore(FolderInfo pageStore) {
		StringBuffer sb = new StringBuffer();
		/* ��Ŀ¼�µ�Folder */
		for (FolderInfo folder : pageStore.getChildFolders()) {
			sb.append("<").append(folder.getName()).append(">");
			//sb.append(getXmlFromPageStore(folder, sb));
			sb.append(folder.getName());
			sb.append("</").append(folder.getName()).append(">");
		}
		/* ��Ŀ¼�µ�Page */
		for (PageInfo page : pageStore.getPages()) {
			sb.append("<").append(page.getName()).append(">");
			sb.append(page.getComment());
			sb.append("</").append(page.getName()).append(">");
		}
		return sb.toString().trim();
	}

	@Override
	public void addElement(String pagePath, String pageName, String jsondata) {
		//FolderInfo pageStore = PSMTool.getPageStore();
		pageStore.getInfoFromDisk();
		SelectedFieldBean bean = new Gson().fromJson(jsondata, new TypeToken<SelectedFieldBean>() {}.getType());
		PageInfo page = pageStore.getPageInfoByName(pageName);
		page.getInfoFromDisk();
		ElementInfo eleInfo = new ElementInfo(bean.getName(), bean.getComment(), bean.getType());
		
		LocatorInfo locInfo = new LocatorInfo();
		locInfo.addCss(bean.getFieldBean().getSelector());
		locInfo.addXpath(bean.getFieldBean().getXpath());
		locInfo.addText(bean.getFieldBean().getText());
		for (Entry<String, String> attr : bean.getFieldBean().getAttributes().entrySet()) {
			locInfo.addAttribute(attr.getKey(), attr.getValue());
		}
		eleInfo.setLocatorInfo(locInfo);
		page.addElementInfo(eleInfo);
	}

	@Override
	public void addVariable(String parentPagePath, String jsondata) {
		PageData pageData = new Gson().fromJson(jsondata, new TypeToken<PageData>() {}.getType());
		pageStore.addPageInfo(pageData.getName(), pageData.getComment());
	}

	@Override
	public void removePage(String parentPagePath, String pageName) {
		pageStore.removePageInfo(pageName);
	}

	@Override
	public void removeElement(String pageNamePath, String elementName) {
		
	}

	@Override
	public void updataElement(String pageNamePath, String elementName,
			String jsondata) {
		
		
	}

	@Override
	public void removeElements(String pageNamePath, String[] elementName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllElements(String pageNamePath) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void updataPage(String parentPagePath, String pageName,
//			String jsondata) {
//		
//		
//	}
//
//	@Override
//	public void addFolder(String folderPath, String folderName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeFolder(String parentFolderPath, String folderName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void renameFolder(String parentFolderPath, String folderName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getData(String path, boolean onlyChild) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
