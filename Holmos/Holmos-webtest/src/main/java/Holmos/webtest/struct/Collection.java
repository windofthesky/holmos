package holmos.webtest.struct;

import static org.junit.Assert.fail;
import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;
import holmos.webtest.basetools.HolmosBaseTools;
import holmos.webtest.basetools.HolmosWindow;
import holmos.webtest.constvalue.ConfigConstValue;
import holmos.webtest.constvalue.ConstValue;
import holmos.webtest.element.Element;
import holmos.webtest.element.locator.Locator;
import holmos.webtest.element.locator.LocatorChain;
import holmos.webtest.element.locator.LocatorValue;
import holmos.webtest.element.tool.SeleniumElementExist;
import holmos.webtest.element.tool.WebDriverElementExist;
import holmos.webtest.element.tool.WebElementExist;
import holmos.webtest.log.MyLogger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class Collection implements LocatorValue{
	private WebElementExist exist;
	/**此元素的全名，在css校验的时候，为了给css本地文件取名设置的变量信息*/
	protected String fullName="";
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**Collection的注释说明*/
	public String comment;
	/**用来保存当前用到的是collection里面的第几个元素*/
	private int index=1;
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
	protected MyLogger logger=MyLogger.getLogger(this.getClass());
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
	public void addTagNameLocator(String tagName){
		this.locator.addTagNameLocator(tagName);
	}
	public void addClassLocator(String className){
		this.locator.addClassLocator(className);
	}
	/**
	 * 查看Collection是否存在，等待加载waitCount次
	 * */
	private boolean isCollectionExist(int waitCount){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			exist=new SeleniumElementExist(this);
		}
		else if(currentWindow instanceof WebDriverBrowserWindow){
			exist=new WebDriverElementExist(this);
		}
		return exist.isElementExist(waitCount);
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
		return isCollectionExist(ConfigConstValue.defaultWaitCount);
	}
	/**校验集合是否存在，则失败返回*/
	public void assertExist(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			message.append(":元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append(":元素存在校验失败！元素不存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验集合是否不存在，则失败返回*/
	public void assertNotExist(){
		StringBuilder message=new StringBuilder();
		if(!isExist()){
			message.append(this.comment+":");
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("元素存在校验失败！元素存在!");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验集合是否存在，失败继续运行*/
	public void verifyExist(){
		StringBuilder message=new StringBuilder();
		if(isExist()){
			message.append(this.comment+":");
			message.append("元素存在性校验成功！元素存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("元素存在校验失败！元素不存在!");
			logger.error(message);
		}
	}
	/**校验集合是否不存在，失败继续运行*/
	public void verifyNotExist(){
		StringBuilder message=new StringBuilder();
		if(!isExist()){
			message.append(this.comment+":");
			message.append("元素不存在性校验成功！元素不存在！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
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
		while(waitCount++<ConfigConstValue.waitCount){
			if(!exist.isElementExistForCheckOnce())
				return;
		}
	}
}
