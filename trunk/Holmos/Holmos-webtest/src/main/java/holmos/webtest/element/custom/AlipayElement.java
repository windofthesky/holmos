package holmos.webtest.element.custom;

import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;
import holmos.webtest.basetools.HolmosWindow;
import holmos.webtest.element.TextField;

import java.awt.Robot;

import com.thoughtworks.selenium.Selenium;

/**
 * 第一个定制组建，阿里巴巴的支付宝密码控件
 * @author 吴银龙(QQ:307087558)
 * */
public class AlipayElement extends TextField{

	public AlipayElement(String comment) {
		super(comment);
	}
	public void setText(String value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).
				type(locator.getSeleniumCurrentLocator(), value);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				HolmosWindow.runJavaScript("return arguments[0].focus();", element);
				setValueByRobot(value);
			}
			message.append("设置值成功!");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("设置值失败!找不到元素!");
			logger.error(message);
		}
	}
	private void setValueByRobot(String value){
		try {
			Robot robot = new Robot();
			String text = value.toUpperCase();
			for (int i = 0; i < text.length(); i++) {
				robot.keyPress(text.charAt(i));
			}
		} catch (java.awt.AWTException ex) {
			logger.error("阿里巴巴安全控件设置值失败!");
		}
	}
}
