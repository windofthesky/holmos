package cn.autosense.browser.exchange.impl.holmos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import cn.autosense.browser.data.RuntimeDataBean;
import cn.autosense.browser.exchange.IDataExchange;
import cn.autosense.browser.exchange.util.CatalogDataType;
import cn.autosense.browser.exchange.util.CreateReturnInfo;
import cn.autosense.browser.exchange.util.ErrorType;
import cn.autosense.browser.exchange.util.PageData;
import cn.autosense.browser.exchange.util.ReturnInfo;
import cn.autosense.browser.gui.render.PageNode;
import cn.autosense.browser.util.PageType;
import cn.autosense.plug.psm.PSMTool;
import cn.autosense.plug.psm.container.PageInfo;
import cn.autosense.plug.psm.folder.FolderInfo;

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
public class HolmosDataExchangeSupport implements IDataExchange {
	
	@Getter
	@Setter
	private CatalogDataType catalogDataType;
	@Getter
	@Setter
	private String selectPagePath;

	private FolderInfo pageStore;
	
	public HolmosDataExchangeSupport() {
		this(CatalogDataType.XML, RuntimeDataBean.getInstance().getSelectPagePath(), PSMTool.getPageStore());
	}

	public HolmosDataExchangeSupport(CatalogDataType catalogDataType,
			String selectPagePath, String selectPageName) {
		this(catalogDataType, selectPagePath, PSMTool.getPageStore());
	}

	@Override
	public FolderInfo getRootFolderInfo() {
		return pageStore;
	}
	
	@Override
	public ReturnInfo loadInfo(PageNode selectNode) {
		PageType type = selectNode.getType();
		switch (type) {
		case Page:
			PageInfo pageInfo = (PageInfo) selectNode.getInfo();
			pageInfo.getInfoFromDisk();
			return new ReturnInfo(pageInfo.getFrames(), pageInfo.getCollections(), pageInfo.getSubpages());
		case Folder:
			FolderInfo folderInfo = (FolderInfo) selectNode.getInfo();
			folderInfo.getInfoFromDisk();
			return new ReturnInfo(folderInfo.getChildFolders(), folderInfo.getPages());
		default:
			break;
		}
		return new ReturnInfo();
	}
	
	@Override
	public CreateReturnInfo addVariable(String pagePath, String jsondata) {
		PageData pageData = new Gson().fromJson(jsondata, new TypeToken<PageData>() {}.getType());
		
		String parentPagePath = pagePath.substring(0, pagePath.lastIndexOf("/"));
		String pageName = pagePath.substring(pagePath.lastIndexOf("/") + 1).trim();
	
		/*FolderInfo pageStoreThis = getPageStoreThis(parentPagePath);
		
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
		}*/
		return new CreateReturnInfo(ErrorType.WRONG);
	}

	@Override
	public void addElement(String pagePath, String jsondata) {
		
	}
	@Override
	public void removeElement(String pageNamePath, String elementName) {
		
	}
	@Override
	public void removeAllElements(String pageNamePath) {
		
	}
}
