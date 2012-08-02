package Holmos.webtest.element;

import static org.junit.Assert.fail;

import Holmos.webtest.Allocator;
import Holmos.webtest.BrowserWindow;
import Holmos.webtest.SeleniumBrowserWindow;
import Holmos.webtest.WebDriverBrowserWindow;
import Holmos.webtest.basetools.HolmosWindow;
import Holmos.webtest.constvalue.ConfigConstValue;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class CheckBox extends Element{

	public CheckBox(String comment) {
		super(comment);
	}
	/**检验这个CheckBox多选框是否被选中<br>
	 * @return true 被选中<br>
	 * 		   false 没有被选中*/
	public boolean isChecked(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		boolean checked=false;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				checked=((Selenium)currentWindow.getDriver().getEngine()).
						isChecked(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				checked=element.isSelected();
			}
		}else{
			message.append(this.comment+":");
			message.append("检验选中失败！找不到元素！");
			logger.error(message);
		}
		return checked;
	}
	/**根据value的值来设定多选框的状态<br>
	 * @param value true 设置为选中   <br>false  取消选中
	 * */
	public void setValue(boolean value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				if(((Selenium)currentWindow.getDriver().getEngine()).isChecked(locator.getSeleniumCurrentLocator())!=value){
					((Selenium)currentWindow.getDriver().getEngine()).click(locator.getSeleniumCurrentLocator());
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				if(element.isSelected()!=value){
					element.click();
				}
			}
		}else{
			message.append(this.comment+":");
			message.append("设定值失败！找不到元素！");
			logger.error(message);
		}
	}
	/**校验复选框是否被选中，如果没有被选中，则失败程序退出运行<br>
	 * 如果被选中，程序继续执行，校验成功!*/
	public void assertChecked(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				if(((Selenium)currentWindow.getDriver().getEngine()).isChecked(locator.getSeleniumCurrentLocator())){
					message.append("校验多选框选中成功！当前处于选中状态");
					logger.info(message);
				}else{
					message.append("校验多选框选中失败！当前处于未选中状态");
					logger.error(message);
					HolmosWindow.closeAllWindows();
					fail(message.toString());
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				if(element.isSelected()){
					message.append("校验多选框选中成功！当前处于选中状态");
					logger.info(message);
				}else{
					message.append("校验多选框选中失败！当前处于未选中状态");
					logger.error(message);
					HolmosWindow.closeAllWindows();
					fail(message.toString());
				}
			}
		}else{
			message.append(this.comment+":");
			message.append("校验多选框选中失败！当前处于未选中状态");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**校验复选框是否被选中，如果没有被选中，则失败程序继续运行<br>
	 * 如果被选中，程序继续执行，校验成功!*/
	public void verifyChecked(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				if(((Selenium)currentWindow.getDriver().getEngine()).isChecked(locator.getSeleniumCurrentLocator())){
					message.append("校验多选框选中成功！当前处于选中状态");
					logger.info(message);
				}else{
					message.append("校验多选框选中失败！当前处于未选中状态");
					logger.error(message);
					HolmosWindow.closeAllWindows();
					fail(message.toString());
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				if(element.isSelected()){
					message.append("校验多选框选中成功！当前处于选中状态");
					logger.info(message);
				}else{
					message.append("校验多选框选中失败！当前处于未选中状态");
					logger.error(message);
					HolmosWindow.closeAllWindows();
					fail(message.toString());
				}
			}
		}else{
			message.append(this.comment+":");
			message.append("校验多选框选中失败！当前处于未选中状态");
			logger.error(message);
			HolmosWindow.closeAllWindows();
			fail(message.toString());
		}
	}
	/**等待此复选框的状态变更改,默认等待30s<br>
	 * @param value true 选中 false 没选中*/
	private void waitForValue(boolean value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int waitStep=0;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				while(waitStep++<ConfigConstValue.waitCount){
					if(((Selenium)currentWindow.getDriver().getEngine()).
							isChecked(locator.getSeleniumCurrentLocator())==value){
						break;
					}
				}
				if(waitStep>ConfigConstValue.waitCount){
					message.append("等待时间30s,状态没有变为"+(value?"选中":"未选中"));
					logger.error(message);
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				while(waitStep++<ConfigConstValue.waitCount){
					if(element.isSelected()==value){
						break;
					}
				}
				if(waitStep>ConfigConstValue.waitCount){
					message.append("等待时间30s,状态没有变为"+(value?"选中":"未选中"));
					logger.error(message);
				}
			}
		}else{
			message.append(this.comment+":");
			message.append("无法等待元素的状态更改，找不到元素!");
			logger.error(message);
		}
	}
	/**等待复选框元素的状态变为选中，默认的等待时间为30s*/
	public void waitForChecked(){
		waitForValue(true);
	}
	/**等待复选框元素的状态变为未选中，默认的等待时间为30s*/
	public void waitForUnChecked(){
		waitForValue(false);
	}
}
