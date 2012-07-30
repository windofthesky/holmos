package Holmos.Holmos.struct;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Holmos.Holmos.Allocator;
import Holmos.Holmos.BrowserWindow;
import Holmos.Holmos.SeleniumBrowserWindow;
import Holmos.Holmos.WebDriverBrowserWindow;
import Holmos.Holmos.constvalue.ConfigConstValue;
import Holmos.Holmos.constvalue.ConstValue;
import Holmos.Holmos.element.Element;
import Holmos.Holmos.element.locator.Locator;
import Holmos.Holmos.element.locator.LocatorChain;
import Holmos.Holmos.element.locator.LocatorFinder;
import Holmos.Holmos.element.locator.LocatorValue;
import Holmos.Holmos.tools.HolmosBaseTools;
import Holmos.Holmos.tools.HolmosWindow;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class Collection implements LocatorValue{
	/**此元素的全名，在css校验的时候，为了给css本地文件取名设置的变量信息*/
	protected String fullName="";
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**Collection的注释说明*/
	private String comment;
	/**用来保存当前用到的是collection里面的第几个元素*/
	private int index;
	public int getIndex() {
		return index;
	}
	/**SubPage里面所包含的链信息<br>
	 * 用来保存这个节点所用到的comment和locator信息<br>*/
	private LocatorChain infoChain;
	/**这个SubPage当前用到的locator*/
	protected String locatorCurrent="";
	public String getLocatorCurrent() {
		return locatorCurrent;
	}
	public void setLocatorCurrent(String locatorCurrent) {
		this.locatorCurrent = locatorCurrent;
	}
	public LocatorChain getInfoChain() {
		return infoChain;
	}
	public String getComment() {
		return "第"+index+"个"+comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**Collection里面的元素*/
	protected List<Element> elements=new ArrayList<Element>();
	/**Collection里面的subpages*/
	protected List<SubPage> subpages=new ArrayList<SubPage>();
	/**这个集合的子集合Collections*/
	protected List<Collection> collections=new ArrayList<Collection>();
	/**集合整体定位器*/
	private Locator locator;
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	/**代表这个集合的基础元素，集合其实也是一个元素，这个元素里面包含着<br>
	 * 很多结构相同或者极其相似，这个在以webdriver作为底层的时候用到<br>*/
	protected WebElement element;
	public void setElement(WebElement element) {
		this.element = element;
	}
	public WebElement getElement() {
		return element;
	}
	/**此Collection的日志记录器*/
	protected Logger logger=Logger.getLogger(this.getClass().getName());
	public Collection(String comment) {
		this.locator=new Locator();
		this.infoChain=new LocatorChain();
		this.comment=comment;
	}
	
	/**收集集合元素，这是一个简单的观察者模式的应用<br>
	 * 以此来获取页面元素的信息，在重新写locator<br>
	 * 和comment的时候用得到<br>*/
	protected void init(){
		Field[] fields=this.getClass().getDeclaredFields();
		try{
			for(Field field : fields){
				if(field.getModifiers()==ConstValue.nestedFatherObjectModifier){
					continue;
				}Object o=field.get(this);
				if(o instanceof Element){
					HolmosBaseTools.insertElementName((Element)o, field.getName());
					((Element)o).getInfoChain().addParentNode(this.getInfoChain().getInfoNodes());//这里有问题
					((Element)o).setFullName(fullName+field.getName());
					((Element)o).getInfoChain().addNode(this);
					elements.add((Element)o);
				}else if(o instanceof SubPage){
					HolmosBaseTools.insertSubPageName((SubPage)o, field.getName());
					((SubPage)o).getInfoChain().addParentNode(this.getInfoChain().getInfoNodes());
					((SubPage)o).setFullName(fullName+field.getName());
					((SubPage)o).getInfoChain().addNode(this);
					this.subpages.add((SubPage)o);
					((SubPage)o).init();
				}else if(o instanceof Collection){
					HolmosBaseTools.insertCollectionName((Collection)o, field.getName());
					((Collection)o).getInfoChain().addParentNode(this.getInfoChain().getInfoNodes());
					((Collection)o).setFullName(fullName+field.getName());
					((Collection)o).getInfoChain().addNode(this);
					this.collections.add((Collection)o);
					((Collection)o).init();
				}
			}
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	public void addIDLocator(String id){
		this.locator.addIdLocator(id);
	}
	public void addNameLocator(String name){
		this.locator.addNameLocator(name);
	}
	public void addXpathLocator(String xpath){
		this.locator.addXpathLocator(xpath);
	}
	public void addCSSLocator(String css){
		this.locator.addCSSLocator(css);
	}
	public void addLinkTextLocator(String linkText){
		this.locator.addLinkTextLocator(linkText);
	}
	public void addPartialLinkTextLocator(String partialLinkText){
		this.locator.addPartialLinkTextLocator(partialLinkText);
	}
	public void addAttributeLocator(String attributeName, String attributeValue) {
		this.locator.addAttributeLocator(attributeName, attributeValue);
	}
	
	
	/**
	 * 查看Collection是否存在，等待加载waitCount次
	 * */
	private boolean isCollectionExist(int waitCount){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow)
			return isSeleniumCollectionExist(waitCount);
		else if(currentWindow instanceof WebDriverBrowserWindow)
			return isWebDriverCollectionExist(waitCount);
		return false;
	}
	private boolean isSeleniumCollectionExist(int waitCount){
		int count=0;
		Selenium seleniumEngine=(Selenium) Allocator.getInstance().currentWindow.getDriver().getEngine();
		if(infoChain.getInfoNodes().size()<=1){
			while(count++<waitCount){
				if(findSeleniumCollection(seleniumEngine))
					return true;
			}
		}else{
			getMuliLevelLocatorInfo();
			while(count++<waitCount){
				if(seleniumEngine.isElementPresent("xpath="+locatorCurrent)){
					return true;
				}
			}
		}
		return false;
	}
	private boolean findSeleniumCollection(Selenium seleniumEngine){
		if(findSeleniumCollectionById(seleniumEngine))return true;
		if(findSeleniumCollectionByName(seleniumEngine))return true;
		if(findSeleniumCollectionByXpath(seleniumEngine))return true;
		if(findSeleniumCollectionByCss(seleniumEngine))return true;
		if(findSeleniumCollectionByLinkText(seleniumEngine))return true;
		if(findSeleniumCollectionByRegularLinkText(seleniumEngine))return true;
		return false;
	}
	private boolean findSeleniumCollectionById(Selenium seleniumEngine){
		if(locator.getLocatorById()!=null&&!"".equalsIgnoreCase(locator.getLocatorById())){
			if(seleniumEngine.isElementPresent("id="+locator.getLocatorById())){
				locatorCurrent="id="+locator.getLocatorById();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumCollectionByName(Selenium seleniumEngine){
		if(locator.getLocatorByName()!=null&&!"".equalsIgnoreCase(locator.getLocatorByName())){
			if(seleniumEngine.isElementPresent("name="+locator.getLocatorByName())){
				locatorCurrent="name="+locator.getLocatorByName();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumCollectionByXpath(Selenium seleniumEngine){
		if(locator.getLocatorByXpath()!=null&&!"".equalsIgnoreCase(locator.getLocatorByXpath())){
			if(seleniumEngine.isElementPresent("xpath="+locator.getLocatorByXpath())){
				locatorCurrent="xpath="+locator.getLocatorByXpath();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumCollectionByCss(Selenium seleniumEngine){
		if(locator.getLocatorByCSS()!=null&&!"".equalsIgnoreCase(locator.getLocatorByCSS())){
			if(seleniumEngine.isElementPresent("css="+locator.getLocatorByCSS())){
				locatorCurrent="css="+locator.getLocatorByCSS();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumCollectionByLinkText(Selenium seleniumEngine){
		if(locator.getLocatorByLinkText()!=null&&!"".equalsIgnoreCase(locator.getLocatorByLinkText())){
			if(seleniumEngine.isElementPresent("link="+locator.getLocatorByLinkText())){
				locatorCurrent="link="+locator.getLocatorByLinkText();
				return true;
			}
		}return false;
	}
	private boolean findSeleniumCollectionByRegularLinkText(Selenium seleniumEngine){
		if(locator.getLocatorByRegular()!=null&&!"".equalsIgnoreCase(locator.getLocatorByRegular())){
			if(seleniumEngine.isElementPresent("link=glob:"+locator.getLocatorByRegular())){
				locatorCurrent="link=glob:"+locator.getLocatorByRegular();
				return true;
			}
		}return false;
	}
	private boolean isWebDriverCollectionExist(int waitCount){
		int count=0;
		LocatorFinder finder=new LocatorFinder(Allocator.getInstance().currentWindow.getDriver().getEngine());
		if(infoChain.getInfoNodes().size()<=1){
			while(count++<waitCount){
				if(findWebDriverCollection(finder))
					return true;
			}
		}else{
			getMuliLevelLocatorInfo();
			while(count++<waitCount){
				try{
					this.element=finder.findElement(By.xpath(locatorCurrent));
					return true;
				}catch (NoSuchElementException e) {
					continue;
				}
			}
		}
		return false;
	}
	private void getMuliLevelLocatorInfo(){
		if(locator.getLocatorById()!=null&&!"".equalsIgnoreCase(locator.getLocatorById()))
			locatorCurrent=locator.getXpathFromId();
		else{
			for(int i=1;i<infoChain.getInfoNodes().size();i++){
				if(infoChain.getInfoNodes().get(i).getLocator().getLocatorById()!=null&&
						!"".equalsIgnoreCase(infoChain.getInfoNodes().get(i).getLocator().getLocatorById())){
					locatorCurrent=infoChain.getInfoNodes().get(i).getLocator().getXpathFromId();
				}else{
					if(infoChain.getInfoNodes().get(i) instanceof Frame){
						locatorCurrent="";
					}else{
						locatorCurrent+=infoChain.getInfoNodes().get(i).getLocator().getLocatorByXpath();
						if(infoChain.getInfoNodes().get(i) instanceof Collection){
							locatorCurrent+="["+((Collection)infoChain.getInfoNodes().get(i)).getIndex()+"]";
						}
					}
				}
			}
			locatorCurrent+=locator.getLocatorByXpath();
		}
	}
	private boolean findWebDriverCollection(LocatorFinder finder){
		if(findWebDriverCollectionById(finder))return true;
		if(findWebDriverCollectionByName(finder))return true;
		if(findWebDriverCollectionByXpath(finder))return true;
		if(findWebDriverCollectionByCss(finder))return true;
		if(findWebDriverCollectionByLinkText(finder))return true;
		if(findWebDriverCollectionByPartialLinkText(finder))return true;
		if(findWebDriverCollectionByTagName(finder))return true;
		return false;
	}
	private boolean findWebDriverCollectionById(LocatorFinder finder){
		if(locator.getLocatorById()==null||"".equalsIgnoreCase(locator.getLocatorById()))
			return false;
		try{
			element=finder.findElement(By.id(locator.getLocatorById()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByName(LocatorFinder finder){
		if(locator.getLocatorByName()==null||"".equalsIgnoreCase(locator.getLocatorByName()))
			return false;
		try{
			element=finder.findElement(By.name(locator.getLocatorByName()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByXpath(LocatorFinder finder){
		if(locator.getLocatorByXpath()==null||"".equalsIgnoreCase(locator.getLocatorByXpath()))
			return false;
		try{
			element=finder.findElement(By.xpath(locator.getLocatorByXpath()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByCss(LocatorFinder finder){
		if(locator.getLocatorByCSS()==null||"".equalsIgnoreCase(locator.getLocatorByCSS()))
			return false;
		try{
			element=finder.findElement(By.cssSelector(locator.getLocatorByCSS()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByLinkText(LocatorFinder finder){
		if(locator.getLocatorByLinkText()==null||"".equalsIgnoreCase(locator.getLocatorByLinkText()))
			return false;
		try{
			element=finder.findElement(By.linkText(locator.getLocatorByLinkText()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByPartialLinkText(LocatorFinder finder){
		if(locator.getLocatorByPartialLinkText()==null||"".equalsIgnoreCase(locator.getLocatorByPartialLinkText()))
			return false;
		try{
			element=finder.findElement(By.partialLinkText(locator.getLocatorByPartialLinkText()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	private boolean findWebDriverCollectionByTagName(LocatorFinder finder){
		if(locator.getLocatorByTagName()==null||"".equalsIgnoreCase(locator.getLocatorByTagName()))
			return false;
		try{
			element=finder.findElement(By.tagName(locator.getLocatorByTagName()));
			this.setElement(element);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	
	/**判断集合的第index个元素是否存在<br>
	 * index从1开始
	 * @param index 集合中元素的序号
	 * @return true 第index个元素存在
	 * 	false 不存在*/
	public boolean isExist(int index){
		if(!isExist())return false;
		locatorCurrent=locatorCurrent+"["+index+"]";
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			if(((Selenium)currentWindow.getDriver().getEngine()).isElementPresent("xpath="+locatorCurrent))
				return true;
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			try{
				((WebDriver)currentWindow.getDriver().getEngine()).findElement(By.xpath(locatorCurrent));
				return true;
			}catch (NoSuchElementException e) {
				return false;
			}
		}return false;
	}
	/**选择集合中的第index个元素,index从1开始<br>
	 * 选中之后，该集合的对象里面的操作就针对这个元素了<br>
	 * @param index 集合中元素的序号*/
	public void select(int index){
		this.index=index;
	}
	public Locator getLocator() {
		return this.locator;
	}
	/**判断此集合在其所在的页面上是否存在<br>
	 * 进行多方定位，直到找到或者所有的定位方法失败为止<br>
	 * 定位方法顺序id-name-xpath-css-link text-partial link text-tag name
	 * 默认等待加载的时间是5s，折5s不算框架本身所耗费的时间*/
	public boolean isExist(){
		locatorCurrent="";
		return isCollectionExist(ConfigConstValue.defaultWaitCount);
	}
	/**校验集合是否存在，则失败返回*/
	public void assertExist(){
		StringBuilder message=new StringBuilder(comment);
		if(isExist()){
			message.append(":元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append(":元素存在校验失败！元素不存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验集合是否不存在，则失败返回*/
	public void assertNotExist(){
		StringBuilder message=new StringBuilder(comment);
		if(!isExist()){
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append("元素存在校验失败！元素存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验集合是否存在，失败继续运行*/
	public void verifyExist(){
		StringBuilder message=new StringBuilder(comment);
		if(isExist()){
			message.append("元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append("元素存在校验失败！元素不存在!");
			logger.error(message);
		}
	}
	/**校验集合是否不存在，失败继续运行*/
	public void verifyNotExist(){
		StringBuilder message=new StringBuilder(comment);
		if(!isExist()){
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append("元素存在校验失败！元素存在!");
			logger.error(message);
		}
	}
	/**等待集合出现，失败打错误日志，程序继续运行<br>
	 * 默认等待时间是30s
	 * */
	public boolean waitForExist() {
	    if(!isCollectionExist(ConfigConstValue.waitCount)){
            logger.error("元素"+comment+"一直没有出现");
            return false;
        }return true;
	}
	/**等待集合消失,失败打错误日志，程序一直运行*/
	public void waitForDisppear() {
		int waitCount=0;
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			while(waitCount++<ConfigConstValue.waitCount){
				if(!findSeleniumCollection((Selenium) currentWindow.getDriver().getEngine()))
					return;
			}
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			LocatorFinder finder=new LocatorFinder(currentWindow.getDriver().getEngine());
			while(waitCount++<ConfigConstValue.waitCount){
				if(!findWebDriverCollection(finder))
					return;
			}
		}
	}
}
