package Holmos.webtest.element.tool;

import java.util.ArrayList;

import Holmos.webtest.Allocator;
import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorValue;
import Holmos.webtest.struct.Collection;
import Holmos.webtest.struct.Frame;
import Holmos.webtest.struct.Page;

import com.thoughtworks.selenium.Selenium;


public class SeleniumElementExist extends WebElementExist{
	private String currentLocator;
	private ArrayList<String>locators=new ArrayList<String>();
	private Selenium selenium=(Selenium) Allocator.getInstance().currentWindow.getDriver().getEngine();
	public SeleniumElementExist(LocatorValue webElement){
		super(webElement);
	}
	@Override
	public boolean isElementExistForCheckOnce(){
		LocatorValue lastNode=infoChain.getInfoNodes().get(infoChain.getInfoNodes().size()-1);
		if(lastNode instanceof Page || lastNode instanceof Frame){
			return findOneLevelElement(1);
		}else{
			return findMuiltiLevelElement(1);
		}
	}
	@Override
	public boolean isElementExist(int waitCount) {
		LocatorValue lastNode=infoChain.getInfoNodes().get(infoChain.getInfoNodes().size()-1);
		if(lastNode instanceof Page || lastNode instanceof Frame){
			return findOneLevelElement(waitCount);
		}else{
			return findMuiltiLevelElement(waitCount);
		}
	}

	private boolean findMuiltiLevelElement(int waitCount) {
		for(int i=0;i<waitCount;i++){
			if(findMuiltiLevelElment())
				return true;
		}
		return false;
	}
	private boolean findMuiltiLevelElment(){
		int startLevel=infoChain.getInfoNodes().size()-1;
		for(;startLevel>1;startLevel--){
			if(infoChain.getInfoNodes().get(startLevel) instanceof Frame){
				//找到Frame层级，跳到下一层
				startLevel++;
				break;
			}
		}
		infoChain.addNode(webElement);
		getAllLocaors(startLevel,"");
		for(int i=0;i<locators.size();i++){
			if(selenium.isElementPresent("xpath="+locators.get(i))){
				webElement.setLocatorCurrent("xpath="+locators.get(i));
				return true;
			}
		}
		return false;
	}
	private void getAllLocaors(int level,String locatorTemp){
		if(level>=infoChain.getInfoNodes().size())return;
		if(level==infoChain.getInfoNodes().size()-1){
			if(locator.getLocatorById()!=null&&locator.getLocatorById()!=""){
				locatorTemp+=(locator.getXpathFromId());
				locators.add(locatorTemp);
			}if(locator.getLocatorByName()!=null&&locator.getLocatorByName()!=""){
				locatorTemp+=locator.getXpathFromName();
				locators.add(locatorTemp);
			}if(locator.getLocatorByXpath()!=null&&locator.getLocatorByXpath()!=""){
				locatorTemp+=locator.getLocatorByXpath();
				locators.add(locatorTemp);
			}if(locator.getLocatorByLinkText()!=null&&locator.getLocatorByLinkText()!=""){
				locatorTemp+=locator.getXpathFromLinkText();
				locators.add(locatorTemp);
			}if(locator.getLocatorByPartialLinkText()!=null&&locator.getLocatorByPartialLinkText()!=""){
				locatorTemp+=locator.getXpathFromPartialLinkText();
				locators.add(locatorTemp);
			}
		}else{
			Locator locatorCurrent=infoChain.getInfoNodes().get(level).getLocator();
			if(locatorCurrent.getLocatorById()!=null&&locatorCurrent.getLocatorById()!=""){
				locatorTemp=locatorTemp+locator.getXpathFromId();
				if(infoChain.getInfoNodes().get(level) instanceof Collection){
					locatorTemp+="["+((Collection)infoChain.getInfoNodes().get(level)).getIndex()+"]";
				}
				getAllLocaors(level+1,locatorTemp);
			}if(locatorCurrent.getLocatorByName()!=null&&locatorCurrent.getLocatorByName()!=""){
				locatorTemp=locatorTemp+locator.getXpathFromName();
				if(infoChain.getInfoNodes().get(level) instanceof Collection){
					locatorTemp+="["+((Collection)infoChain.getInfoNodes().get(level)).getIndex()+"]";
				}
				getAllLocaors(level+1,locatorTemp);
			}if(locatorCurrent.getLocatorByXpath()!=null&&locatorCurrent.getLocatorByXpath()!=""){
				locatorTemp=locatorTemp+locator.getLocatorByXpath();
				if(infoChain.getInfoNodes().get(level) instanceof Collection){
					locatorTemp+="["+((Collection)infoChain.getInfoNodes().get(level)).getIndex()+"]";
				}
				getAllLocaors(level+1,locatorTemp);
			}if(locatorCurrent.getLocatorByLinkText()!=null&&locatorCurrent.getLocatorByLinkText()!=""){
				locatorTemp=locatorTemp+locator.getXpathFromLinkText();
				if(infoChain.getInfoNodes().get(level) instanceof Collection){
					locatorTemp+="["+((Collection)infoChain.getInfoNodes().get(level)).getIndex()+"]";
				}
				getAllLocaors(level+1,locatorTemp);
			}if(locatorCurrent.getLocatorByPartialLinkText()!=null&&locatorCurrent.getLocatorByPartialLinkText()!=""){
				locatorTemp=locatorTemp+locator.getXpathFromPartialLinkText();
				if(infoChain.getInfoNodes().get(level) instanceof Collection){
					locatorTemp+="["+((Collection)infoChain.getInfoNodes().get(level)).getIndex()+"]";
				}
				getAllLocaors(level+1,locatorTemp);
			}
			
		}
	}
	private boolean findOneLevelElement(int waitCount) {
		for(int i=0;i<waitCount;i++){
			if(findOneLevelElement(locator))
				return true;
		}
		return false;
	}
	private boolean findOneLevelElement(Locator locator){
		boolean isExist=false;
		if(!isExist)
			isExist=findElementById(locator.getLocatorById());
		if(!isExist)
			isExist=findElementByName(locator.getLocatorByName());
		if(!isExist)
			isExist=findElementByXpath(locator.getLocatorByXpath());
		if(!isExist)
			isExist=findElementByCss(locator.getLocatorByCSS());
		if(!isExist)
			isExist=findElementByLinkText(locator.getLocatorByLinkText());
		if(!isExist)
			isExist=findElementByRegularLinkText(locator.getLocatorByRegular());
		return isExist;
	}
	private boolean findElementByRegularLinkText(String locatorByRegular) {
		if(locator.getLocatorByRegular()!=null&&!"".equalsIgnoreCase(locator.getLocatorByRegular())){
			if(selenium.isElementPresent("link=glob:"+locator.getLocatorByRegular())){
				currentLocator="link=glob:"+locator.getLocatorByRegular();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
	private boolean findElementByLinkText(String locatorByLinkText) {
		if(locator.getLocatorByLinkText()!=null&&!"".equalsIgnoreCase(locator.getLocatorByLinkText())){
			if(selenium.isElementPresent("link="+locator.getLocatorByLinkText())){
				currentLocator="link="+locator.getLocatorByLinkText();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
	private boolean findElementByCss(String locatorByCSS) {
		if(locator.getLocatorByCSS()!=null&&!"".equalsIgnoreCase(locator.getLocatorByCSS())){
			if(selenium.isElementPresent("css="+locator.getLocatorByCSS())){
				currentLocator="css="+locator.getLocatorByCSS();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
	private boolean findElementByXpath(String locatorByXpath) {
		if(locator.getLocatorByLinkText()!=null&&!"".equalsIgnoreCase(locator.getLocatorByLinkText())){
			if(selenium.isElementPresent("link="+locator.getLocatorByLinkText())){
				currentLocator="link="+locator.getLocatorByLinkText();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
	private boolean findElementByName(String locatorByName) {
		if(locator.getLocatorByName()!=null&&!"".equalsIgnoreCase(locator.getLocatorByName())){
			if(selenium.isElementPresent("name="+locator.getLocatorByName())){
				currentLocator="name="+locator.getLocatorByName();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
	private boolean findElementById(String locatorById) {
		if(locator.getLocatorById()!=null&&!"".equalsIgnoreCase(locator.getLocatorById())){
			if(selenium.isElementPresent("id="+locator.getLocatorById())){
				currentLocator="id="+locator.getLocatorById();
				webElement.setLocatorCurrent(currentLocator);
				return true;
			}
		}return false;
	}
}
