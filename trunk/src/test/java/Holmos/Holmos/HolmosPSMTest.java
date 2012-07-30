package Holmos.Holmos;

import java.io.File;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.Page;

import Holmos.Holmos.innerTools.MyDirectoryOperation;
import Holmos.Holmos.plug.LIJSM.LocatorInfo;
import Holmos.Holmos.plug.PSM.INFOTYPE;
import Holmos.Holmos.plug.PSM.PSMTool;
import Holmos.Holmos.plug.PSM.container.CollectionInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import Holmos.Holmos.plug.PSM.container.SubPageInfo;
import Holmos.Holmos.plug.PSM.element.ElementInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;

public class HolmosPSMTest {
	FolderInfo pageStore=PSMTool.getPageStore();
	LocatorInfo locInfo=new LocatorInfo();
	{
		locInfo.addCss("css.css");
		locInfo.addXpath("xpath/xpath");
		locInfo.addText("text-text");
		locInfo.addAttribute("id", "idid");
		locInfo.addAttribute("name", "name-name");
		locInfo.addAttribute("self", "something");
	}
	/**测试获得PageStore*/
	@Test
	public void testFolderGetInfoFromDisk(){
		pageStore.getInfoFromDisk();
		pageStore.outPutInfo();
	}
	@Test
	public void testAddChildFolder(){
		pageStore.getInfoFromDisk();
		pageStore.addChildFolder("子目录1");
		pageStore.outPutInfo();
	}
	@Test
	public void testAddPageFile(){
		pageStore.getInfoFromDisk();
		pageStore.addPageInfo("FirstPage", "第一个Page");
		pageStore.outPutInfo();
	}
	
	@Test
	public void testDeleteFolder(){
		pageStore.getInfoFromDisk();
		pageStore.removeChildFolder("子目录1");
		pageStore.outPutInfo();
	}
	@Test
	public void testDeletePage(){
		pageStore.getInfoFromDisk();
		pageStore.removePageInfo("firstPage");
		pageStore.outPutInfo();
	}
	@Test
	public void testAddElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		ElementInfo eleInfo=new ElementInfo("firstElement", "第一个element", INFOTYPE.ELEMENT);
		eleInfo.setLocatorInfo(locInfo);
		page.addElementInfo(eleInfo);
		page.outPutInfo();
	}
	@Test
	public void testRemoveElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		page.removeElement("firstElement");
		page.outPutInfo();
	}
	@Test
	public void testRemoveSubPage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		page.removeSubPage("firstSubPage");
		page.outPutInfo();
	}
	@Test
	public void testAddSubPage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		SubPageInfo subpage=new SubPageInfo("firstSubPage", "第一个subpage");
		subpage.setLocatorInfo(locInfo);
		page.addSubPageInfo(subpage);
		page.outPutInfo();
	}
	
	@Test
	public void testAddCollection(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		CollectionInfo collection=new CollectionInfo("firstCollection", "第一个collection");
		collection.setLocatorInfo(locInfo);
		page.addCollectionInfo(collection);
		page.outPutInfo();
	}
	@Test
	public void testGetElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		page.getElement("firstElement").outPutInfo();
	}
	@Test
	public void testGetSubPage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		page.getSubPage("firstSubPage").outPutInfo();
	}
	@Test
	public void testAddRepeateElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		ElementInfo eleInfo=new ElementInfo("firstElement", "第一个element", INFOTYPE.ELEMENT);
		eleInfo.setLocatorInfo(locInfo);
		page.addElementInfo(eleInfo);
		page.outPutInfo();
	}
	@Test
	public void testContainerAddElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("firstSubPage");
		subpage.getInfoFromDisk();
		ElementInfo eleInfo=new ElementInfo("secondElement", "第二个element", INFOTYPE.ELEMENT);
		eleInfo.setLocatorInfo(locInfo);
		subpage.addElementInfo(eleInfo);
		subpage.outPutInfo();
	}
	@Test
	public void testGetContainerElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("firstSubPage");
		subpage.getInfoFromDisk();
		subpage.getElement("secondElement").outPutInfo();
	}
	@Test
	public void testRemoveContainerElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("firstSubPage");
		subpage.getInfoFromDisk();
		subpage.removeElement("secondElement");
		subpage.outPutInfo();
	}
	@Test
	public void testAddContainerSubPage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("firstSubPage");
		subpage.getInfoFromDisk();
		SubPageInfo subpage2=new SubPageInfo("secondSubPage", "第二个subpage");
		subpage2.setLocatorInfo(locInfo);
		subpage.addSubPageInfo(subpage2);
		subpage.outPutInfo();
	}
	@Test
	public void testRenamePage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("newFirstPage");
		page.getInfoFromDisk();
		page.rename("firstPage");
		page.outPutInfo();
	}
	@Test
	public void testRenameContainer(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("firstSubPage");
		subpage.rename("newFirstSubPage");
		subpage.outPutInfo();
	}
	@Test
	public void testPasteElement(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		ElementInfo eleInfo=new ElementInfo("element3", "第三个element", INFOTYPE.ELEMENT);
		eleInfo.setLocatorInfo(locInfo);
		page.pasteElementInfo(eleInfo);
		page.outPutInfo();
	}
	@Test
	public void testPasteSubPage(){
		pageStore.getInfoFromDisk();
		PageInfo page=pageStore.getPageInfoByName("firstPage");
		page.getInfoFromDisk();
		SubPageInfo subpage=page.getSubPage("newFirstSubPage");
		subpage.getInfoFromDisk();
		SubPageInfo subpage1=subpage.getSubPage("secondSubPage");
		subpage1.getInfoFromDisk();
		page.pasteSubPageInfo(subpage1);
		page.outPutInfo();
	}
	@Test
	public void testCopy(){
		File source=new File("C:\\项目\\source");
		File destination=new File("C:\\项目\\destination");
		MyDirectoryOperation.copyTo(destination, source);
	}
}
