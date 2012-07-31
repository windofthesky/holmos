package cn.autosense.browser.exchange.impl.holmos;

import java.util.List;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.container.CollectionInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import Holmos.Holmos.plug.PSM.container.SubPageInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.data.SelectedFieldBean;
import cn.autosense.browser.exchange.IDataExchange;
import cn.autosense.browser.exchange.util.CatalogDataType;
import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ErrorType;
import cn.autosense.browser.exchange.util.PageData;
import cn.autosense.browser.util.PageType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-4 下午1:45:09<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@AllArgsConstructor
public class HolmosDataExchangeSupport_Node implements IDataExchange {
	
	@Getter
	@Setter
	private CatalogDataType catalogDataType;
	@Getter
	@Setter
	private String selectPagePath;

	private FolderInfo pageStore;
	
	public HolmosDataExchangeSupport_Node() {
		this(CatalogDataType.XML, RuntimeDataBean.getInstance().getSelectPagePath(), PSMTool.getPageStore());
	}

	public HolmosDataExchangeSupport_Node(CatalogDataType catalogDataType,
			String selectPagePath, String selectPageName) {
		this(catalogDataType, selectPagePath, PSMTool.getPageStore());
	}

	public FolderInfo getRootFolderInfo() {
		return pageStore;
	}
	
	@Override
	public String loadFolderPageInfo(String rootPath) {
		FolderInfo pageStoreThis = getPageStoreThis(rootPath);
		if(null != pageStoreThis) {
			if(rootPath.split("/").length == 2) {
				return null;
			} else {
				return getStrFromPageStore(pageStoreThis);
			}
		}
		return null;
	}
	
	private FolderInfo getPageStoreThis(String pagePath) {
//		String[] pageNames = rootPath.split("/");
//
//		if(null != pageStore) {
//			pageStore.getInfoFromDisk();
//			pageStoreThis = pageStore;
//	
//			for (int i = 1; i < pageNames.length; i++) {
//				pageStoreThis = pageStoreThis.getChildFolderByName(pageNames[i]);
//				if(null != pageStoreThis) {
//					pageStoreThis.getInfoFromDisk();
//				} else {
//					break;
//				}
//			}
//			
//			if(null != pageStoreThis) {
//				return getStrFromPageStore(pageStoreThis);
//			}
//		}
//		return null;
		pageStore.getInfoFromDisk();
		FolderInfo pageStoreThis = pageStore;
		
		if(pagePath.isEmpty()) {
			return pageStoreThis;
		}
		String[] pageNames = pagePath.substring(1).split("/");
		if(pageNames.length < 1) {
			return pageStoreThis;
		}
		for (int i = 0; i < pageNames.length; i++) {
			pageStoreThis = pageStoreThis.getChildFolderByName(pageNames[i]);
			if(null == pageStoreThis) {
				pageStoreThis = pageStore;
				break;
			} else {
				pageStoreThis.getInfoFromDisk();
			}
		}
		if(null != pageStoreThis) {
			return pageStoreThis;
		}
		return null;
	}
	
	private PageInfo getPageInfoThis(String pagePath) {
		pageStore.getInfoFromDisk();
		FolderInfo pageStoreThis = pageStore;
		FolderInfo pageStoreBack = pageStoreThis;
		String[] pageNames = pagePath.substring(1).split("/");
		
		PageInfo  pageInfo = null;
		
		for (int i = 0; i < pageNames.length; i++) {
			pageStoreBack = pageStoreThis;
			pageStoreThis = pageStoreThis.getChildFolderByName(pageNames[i]);
			if(null == pageStoreThis) {
				pageStoreThis = pageStoreBack;
				pageInfo = pageStoreThis.getPageInfoByName(pageNames[i]);
				break;
			} else {
				pageStoreThis.getInfoFromDisk();
			}
		}
		return pageInfo;
	}

	private PageInfo getPageThis(String pagePath, String pageName) {
		FolderInfo pageStoreThis = getPageStoreThis(pagePath);
		PageInfo page = null;
		if(null != pageStoreThis) {
			page = pageStoreThis.getPageInfoByName(pageName);
		}
		return page;
	}

	private String getStrFromPageStore(FolderInfo pageStore) {
		//pageStore.getInfoFromDisk();
		StringBuffer sb = new StringBuffer();
		/* 遍历Folder */
		for (FolderInfo folder : pageStore.getChildFolders()) {
			sb.append(folder.getName()).append(",");
		}
		/* 遍历Page */
		for (PageInfo page : pageStore.getPages()) {
			sb.append(page.getComment()).append(",");
		}
		if(sb.length() > 1) {
			return sb.substring(0, sb.length() - 1).trim();
		} else {
			return "";
		}
	}

	@Override
	public void addElement(String pagePath, String jsondata) {
//		//FolderInfo pageStore = PSMTool.getPageStore();
//		pageStore.getInfoFromDisk();
//		SelectedFieldBean bean = new Gson().fromJson(jsondata, new TypeToken<SelectedFieldBean>() {}.getType());
//		PageInfo page = pageStore.getPageInfoByName(pageName);
//		page.getInfoFromDisk();
//		ElementInfo eleInfo = new ElementInfo(bean.getName(), bean.getComment(), bean.getType());
//
//		LocatorInfo locInfo = new LocatorInfo();
//		locInfo.addCss(bean.getFieldBean().getSelector());
//		locInfo.addXpath(bean.getFieldBean().getXpath());
//		locInfo.addText(bean.getFieldBean().getText());
//		for (Entry<String, String> attr : bean.getFieldBean().getAttributes().entrySet()) {
//			locInfo.addAttribute(attr.getKey(), attr.getValue());
//		}
//		eleInfo.setLocatorInfo(locInfo);
//		page.addElementInfo(eleInfo);
		//FolderInfo pageStore = PSMTool.getPageStore();
		String parentPagePath = pagePath.substring(0, pagePath.lastIndexOf("/"));
		String pageName = pagePath.substring(pagePath.lastIndexOf("/") + 1).trim();
		
		PageInfo page = getPageThis(parentPagePath, pageName);
		if(null != page) {
			page.getInfoFromDisk();
			ElementInfo eleInfo = createElementInfo(jsondata);
			page.addElementInfo(eleInfo);
		} else {
			// TODO PageInfo不存在
			
		}
	}
	
	/**
	 * 添加ElementInfo
	 * @param jsondata
	 * @return
	 */
	private ElementInfo createElementInfo(String jsondata) {
		SelectedFieldBean bean = new Gson().fromJson(jsondata, new TypeToken<SelectedFieldBean>() {}.getType());
		ElementInfo eleInfo = new ElementInfo(bean.getName(), bean.getComment(), bean.getType());
		
		LocatorInfo locInfo = new LocatorInfo();
		locInfo.addCss(bean.getFieldBean().getSelector());
		locInfo.addXpath(bean.getFieldBean().getXpath());
		locInfo.addText(bean.getFieldBean().getText());
		for (Entry<String, String> attr : bean.getFieldBean().getAttributes().entrySet()) {
			locInfo.addAttribute(attr.getKey(), attr.getValue());
		}
		eleInfo.setLocatorInfo(locInfo);
		return eleInfo;
	}

	@Override
	public CreateReturnInfo addVariable(String pagePath, String jsondata) {
		PageData pageData = new Gson().fromJson(jsondata, new TypeToken<PageData>() {}.getType());
		
		String parentPagePath = pagePath.substring(0, pagePath.lastIndexOf("/"));
		String pageName = pagePath.substring(pagePath.lastIndexOf("/") + 1).trim();
	
		FolderInfo pageStoreThis = getPageStoreThis(parentPagePath);
		
		if(null != pageStoreThis) {
			switch(PageType.valueOf(pageData.getPageType())) {
				case Page :
					return pageStoreThis.addPageInfo(pageData.getName(), pageData.getComment());
				case SUBPAGE : {
					// TODO
					SubPageInfo subPageInfo = new SubPageInfo(pageData.getName(), pageData.getComment());
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageName);
					return pageInfo.addSubPageInfo(subPageInfo);
				}
				case FRAME : {
					// TODO
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageStoreThis.getName());
					return pageInfo.addFrameInfo(pageData.getName());
				}
				case COLLECTION : {
					// TODO
					CollectionInfo collectionInfo = new CollectionInfo(pageData.getName(), pageData.getComment());
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageStoreThis.getName());
					return pageInfo.addCollectionInfo(collectionInfo);
				}
				default:
					return pageStoreThis.addPageInfo(pageData.getName(), pageData.getComment());
			}
		}
		return new CreateReturnInfo(ErrorType.WRONG);
	}

	@Override
	public void removePage(String parentPagePath, String pageName) {
		FolderInfo pageStoreThis = getPageStoreThis(parentPagePath);
		if(null != pageStoreThis) {
			pageStoreThis.removePageInfo(pageName);
		}
	}

	@Override
	public void removeElement(String pageNamePath, String elementName) {
		String pageName = pageNamePath.substring(pageNamePath.lastIndexOf("/") + 1).trim();
		PageInfo page = getPageThis(pageNamePath, pageName);
		if(null != page) {
			page.removeElement(elementName);
		}
	}

	@Override
	public void removeElements(String pageNamePath, String[] elementNames) {
		String pageName = pageNamePath.substring(pageNamePath.lastIndexOf("/") + 1).trim();
		PageInfo page = getPageThis(pageNamePath, pageName);
		if(null != page) {
			for (String elementName : elementNames) {
				page.removeElement(elementName);
			}
		}
	}
	
	@Override
	public void removeAllElements(String pageNamePath) {
		String pageName = pageNamePath.substring(pageNamePath.lastIndexOf("/") + 1).trim();
		PageInfo page = getPageThis(pageNamePath, pageName);
		if(null != page) {
			List<ElementInfo> eleInfos = page.getElements();
			for (ElementInfo element : eleInfos) {
				page.removeElement(element.getName());
			}
		}
	}

	@Override
	public void updataElement(String pageNamePath, String elementName,
			String jsondata) {
		String pageName = pageNamePath.substring(pageNamePath.lastIndexOf("/") + 1).trim();
		PageInfo page = getPageThis(pageNamePath, pageName);
		if(null != page) {
			page.removeElement(elementName);
			page.addElementInfo(createElementInfo(jsondata));
		}
	}

}
