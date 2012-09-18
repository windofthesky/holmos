package holmos.webtest.element;

import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class TextField extends Element{

	public TextField(String comment) {
		super(comment);
	}
	/**在此文本框里面输入value值，对value没有特殊要求<br>
	 * @param value 要输入的value值*/
	public void setText(String value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).
				type(locator.getSeleniumCurrentLocator(), value);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				element.sendKeys(value);
			}
			message.append("设置值成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("设置值失败!找不到元素!");
			logger.error(message);
		}
	}
	/**这个要覆盖父类的方法，获得此输入框的内容
	 * @return 输入框的内容，字符窜信息*/
	@Override
	public String getText(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String content=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				content=((Selenium)currentWindow.getDriver().getEngine()).
						getValue(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				content=element.getAttribute("value");
			}
			message.append("获得输入框文本内容成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("获得输入框文本内容失败!找不到元素");
			logger.error(message);
		}
		return content;
	}
	/**
	 * 清空输入框里面的内容
	 * */
	public void clear(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).type(locator.getSeleniumCurrentLocator(), "");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				element.clear();
			}
			message.append("输入框的值清空成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("输入框的值清空失败!找不到元素");
			logger.error(message);
		}
	}
}
