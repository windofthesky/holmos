package back;

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
public class CopyOfHolmosDataExchangeSupport2 implements IDataExchange {
	
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
	
	public CopyOfHolmosDataExchangeSupport2() {
		this(CatalogDataType.XML, RuntimeDataBean.getInstance().getSelectPagePath(), RuntimeDataBean.getInstance().getSelectPageName());
	}
	
	public CopyOfHolmosDataExchangeSupport2(CatalogDataType catalogDataType,
			String selectPagePath, String selectPageName) {
		this(catalogDataType, selectPagePath, selectPageName, PSMTool.getPageStore());
	}

	@Override
	public String loadFolderPageInfo(String rootPath) {
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
		
		String[] pageNames = pagePath.split("/");
		if(null != pageStore) {
			pageStore.getInfoFromDisk();
			FolderInfo pageStoreThis = pageStore;
			
			if(pageNames.length == 1 || pageNames.length == 2) {
				return pageStoreThis;
			}

			for (int i = 1; i < pageNames.length - 1; i++) {
				pageStoreThis = pageStoreThis.getChildFolderByName(pageNames[i]);
				if(null != pageStoreThis) {
					pageStoreThis.getInfoFromDisk();
				} else {
					break;
				}
			}
			if(null != pageStoreThis) {
				return pageStoreThis;
			}
		}
		return null;
	}

	private PageInfo getPageThis(String pagePath, String pageName) {
		FolderInfo pageStoreThis = getPageStoreThis(pagePath);
		PageInfo page = null;
		if(null != pageStoreThis) {
			page = pageStoreThis.getPageInfoByName(pageName);
		}
		return page;
	}

	public static void main(String[] args) {
		System.out.println(new CopyOfHolmosDataExchangeSupport2().loadFolderPageInfo("firstpage"));
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
	public void addElement(String pagePath, String pageName, String jsondata) {
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
		PageInfo page = getPageThis(pagePath, pageName);
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
	public void addVariable(String parentPagePath, String jsondata) {
		PageData pageData = new Gson().fromJson(jsondata, new TypeToken<PageData>() {}.getType());
		
		FolderInfo pageStoreThis = getPageStoreThis(parentPagePath);
		
		if(null != pageStoreThis) {
			switch(PageType.valueOf(pageData.getPageType())) {
				case Page :
					pageStoreThis.addPageInfo(pageData.getName(), pageData.getComment());
					break;
				case SUBPAGE : {
					// TODO
					SubPageInfo subPageInfo = new SubPageInfo(pageData.getName(), pageData.getComment());
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageStoreThis.getName());
					pageInfo.addSubPageInfo(subPageInfo);
					break;
				}
				case FRAME : {
					// TODO
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageStoreThis.getName());
					pageInfo.addFrameInfo(pageData.getName());
					break;
				}
				case COLLECTION : {
					// TODO
					CollectionInfo collectionInfo = new CollectionInfo(pageData.getName(), pageData.getComment());
					PageInfo pageInfo = pageStoreThis.getPageInfoByName(pageStoreThis.getName());
					pageInfo.addCollectionInfo(collectionInfo);
					break;
				}
				default:
					pageStoreThis.addPageInfo(pageData.getName(), pageData.getComment());
					break;
			}
		}
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
