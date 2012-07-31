
package testcase;
import java.util.ArrayList;

import org.junit.Test;

import Holmos.webtest.basetools.HolmosWindow;

import pagestore.tianya.ForumContentPage;
import pagestore.tianya.HuitieContent;
import pagestore.tianya.InfoStructure;
import pagestore.tianya.LoginPage;
import pagestore.tianya.TieziPage;

public class TestTianyaFaTie {
	LoginPage loginPage=new LoginPage();
	ForumContentPage contentPage=new ForumContentPage();
	TieziPage tieziPage=new TieziPage();
	@Test
	public void testfatie(){
		loginPage.login("地平线上的光亮", "1990Eagle");
		HolmosWindow.open(contentPage.chuangyeUrl);
		HolmosWindow.resizeTo(0, 0);
		ArrayList<InfoStructure>infos=contentPage.getInfos(1);
		InfoStructure.outputInfos(infos);
		InfoStructure.sortByFangwen(infos);
		InfoStructure.outputInfos(infos);
		InfoStructure.sortByHuifu(infos);
		InfoStructure.outputInfos(infos);
		HolmosWindow.open(infos.get(1).getUrl());
		tieziPage.huitie("看看");
		HolmosWindow.closeAllWindows();
	}
	@Test
	public void getHuitieContents(){
		HuitieContent huitieContent=new HuitieContent("D:\\data\\我把一切告诉你");
		ArrayList<ArrayList<String>>contents=huitieContent.getContents();
		for(ArrayList<String>content:contents){
			huitieContent.outPutContent(content);
			System.out.println("**************");
		}
	}
}
