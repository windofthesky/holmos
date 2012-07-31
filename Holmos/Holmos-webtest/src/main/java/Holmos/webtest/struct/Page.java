package Holmos.webtest.struct;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Holmos.constvalue.ConstValue;
import Holmos.webtest.Allocator;
import Holmos.webtest.BrowserWindow;
import Holmos.webtest.SeleniumBrowserWindow;
import Holmos.webtest.WebDriverBrowserWindow;
import Holmos.webtest.element.Element;
import Holmos.webtest.element.locator.Locator;
import Holmos.webtest.element.locator.LocatorChain;
import Holmos.webtest.element.locator.LocatorValue;
import Holmos.webtest.tools.HolmosBaseTools;

import com.thoughtworks.selenium.Selenium;

/**页面模型，Holmos框架操作页面的时候，对页面进行结构化结果<br>
 * 将页面整体作为一个Page，里面包含这四种结构<br>
 * (1)Frame		 页面的Frame结构和iframe结构<br>
 * (2)Collection 一种集合结构，在页面上，集合里面的每一个元素里面的结构完全相同或者非常类似<br>
 * (3)SubPage	一种页面元素的组织，将一部分元素组织成一个SubPage，方便管理和使用<br>
 * (4)Element 	页面元素实体，映射页面的一个元素<br>
 * <br>
 * <em>其中也包括了对页面的常规操作</em>
 * @author 吴银龙(15857164387)
 * */
public class Page implements LocatorValue{
	/**这个页面的url*/
	protected String url;
	/**这个页面的注释说明*/
	protected String comment;
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	/**这个页面的所有元素的list，在清空页面缓存的时候用到*/
	protected List<Element> elements=new ArrayList<Element>();
	/**这个页面的次级页面，在清空页面缓存的时候能用到*/
	protected List<SubPage> subpages=new ArrayList<SubPage>();
	/**这个页面中的嵌套的iframe和frameset，在清空页面缓存的时候用到*/
	protected List<Frame> frames=new ArrayList<Frame>();
	/**这个页面的集合Collections*/
	protected List<Collection> collections=new ArrayList<Collection>();
	public Page(){
		HolmosBaseTools.configLogProperties();
	}
	
	/**收集页面元素，这是一个简单的观察者模式的应用<br>
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
					((Element) o).getInfoChain().addNode(this);
					((Element) o).setFullName(getClass().getName().substring(getClass().getName().lastIndexOf('.')+1)+field.getName());
					elements.add((Element)o);
				}else if(o instanceof SubPage){
					HolmosBaseTools.insertSubPageName((SubPage)o, field.getName());
					((SubPage) o).getInfoChain().addNode(this);
					((SubPage) o).setFullName(getClass().getName().substring(getClass().getName().lastIndexOf('.')+1)+field.getName());
					this.subpages.add((SubPage)o);
					((SubPage)o).init();
				}else if(o instanceof Collection){
					HolmosBaseTools.insertCollectionName((Collection)o, field.getName());
					((Collection)o).getInfoChain().addNode(this);
					((Collection)o).setFullName(getClass().getName().substring(getClass().getName().lastIndexOf('.')+1)+field.getName());
					this.collections.add((Collection)o);
					((Collection)o).init();
				}else if(o instanceof Frame){
					HolmosBaseTools.insertFrameName((Frame)o, field.getName());
					((Frame)o).getInfoChain().addNode(this);
					((Frame)o).setFullName(getClass().getName().substring(getClass().getName().lastIndexOf('.')+1)+field.getName());
					this.frames.add((Frame)o);
					((Frame)o).init();
				}
			}
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	/**获得页面html源码里面body部分的代码<br>
	 * 要求当前页面必须是活动页面，这个方法只能<br>
	 * 获得活动页面body部分的源码<br>
	 * @return 活动页面body部分的源码*/
	public String getBodyText(){
		System.out.println("获得"+this.comment+"页面body源码");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			return ((Selenium)currentWindow.getDriver().getEngine()).getBodyText();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			return ((WebDriver)currentWindow.getDriver().getEngine()).getPageSource();
		}return null;
	}
	/**获得页面html源码里面body部分的代码<br>
	 * 要求当前页面必须是活动页面，这个方法只能<br>
	 * 获得活动页面的源码<br>
	 * @return 活动页面的源码*/
	public String getHtmlText(){
		System.out.println("获得"+this.comment+"页面body源码");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			return ((Selenium)currentWindow.getDriver().getEngine()).getHtmlSource();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			return ((WebDriver)currentWindow.getDriver().getEngine()).getPageSource();
		}return null;
	}
	/**获得活动页面的title<br>
	 * @return 活动页面的title*/
	public String getTitle(){
		System.out.println("获得"+this.comment+"页面body源码");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			return ((Selenium)currentWindow.getDriver().getEngine()).getTitle();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			return ((WebDriver)currentWindow.getDriver().getEngine()).getTitle();
		}return null;
	}
	/**获得该页面所有的cookies*/
	public String getAllCookies(){
		System.out.println("获得"+this.comment+"所有的cookies");
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			return ((Selenium)currentWindow.getDriver().getEngine()).getCookie();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			Set<Cookie>cookies=((WebDriver)currentWindow.getDriver().getEngine()).manage().getCookies();
			Iterator<Cookie> cookie=cookies.iterator();
			String cookieStr="";
			while(cookie.hasNext()){
				cookieStr=cookieStr+"|"+cookie.next().getValue();
			}
			return cookieStr;
		}
		return null;
	}
	/**获得该页面下符合该名称的cookie*/
	public String getCookieByName(String name){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			return ((Selenium)currentWindow.getDriver().getEngine()).getCookieByName(name);
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			return ((WebDriver)currentWindow.getDriver().
					getEngine()).manage().getCookieNamed(name).getValue();
		}
		return null;
	}
	/**删除该指定目录下的指定名字的cookie的所有的cookie*/
	public void deleteCookie(String name,String path){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).deleteCookie(name, path);
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			((WebDriver)currentWindow.getDriver().
					getEngine()).manage().deleteCookieNamed(name);
		}
	}
	/**删除所有的cookie*/
	public void deleteAllCookie(){
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(currentWindow instanceof SeleniumBrowserWindow){
			((Selenium)currentWindow.getDriver().getEngine()).deleteAllVisibleCookies();
		}else if(currentWindow instanceof WebDriverBrowserWindow){
			((WebDriver)currentWindow.getDriver().
					getEngine()).manage().deleteAllCookies();
		}
	}

	public Locator getLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	public WebElement getElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setElement(WebElement element) {
		// TODO Auto-generated method stub
		
	}

	public String getLocatorCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLocatorCurrent(String locatorCurrent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExist() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LocatorChain getInfoChain() {
		// TODO Auto-generated method stub
		return null;
	}
}
