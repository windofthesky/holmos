package holmos.webtest.element;
import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.Selenium;
/**
 * @author 吴银龙(15857164387)
 * */
public class Comobobox extends Element{

	public Comobobox(String comment) {
		super(comment);
	}
	private Select select;
	/**检查combobox元素是否存在*/
	@Override
	public boolean isExist(){
		if(super.isExist()){
			select=new Select(element);
			return true;
		}return false;
	}
	/**根据combobox中项所在索引进行选择<br>
	 * @param index 要选择选项的索引号*/
	public void selectByIndex(int index){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).
				select(locator.getSeleniumCurrentLocator(), "index="+index);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				select.selectByIndex(index);
			}
			message.append("选择第"+index+"个选项成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("根据索引选择失败!找不到元素");
			logger.error(message);
		}
	}
	/**根据combobox中的内容进行选择<br>
	 * @param value 要选择项的内容*/
	public void selectByValue(String value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).
				select(locator.getSeleniumCurrentLocator(), "value="+value);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				select.selectByValue(value);
			}
			message.append("选择"+value+"所在选项成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("根据内容选择失败!找不到元素");
			logger.error(message);
		}
	}
	/**根据combobox标签之间的内容进行选择<br>
	 * 例如：<option value="foo">Bar</option> 那么参数就是Bar<br>
	 * @param value 要选择项的内容*/
	public void selectByVisiableValue(String value){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				((Selenium)currentWindow.getDriver().getEngine()).
				select(locator.getSeleniumCurrentLocator(), "label="+value);
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				select.selectByValue(value);
			}
			message.append("选择"+value+"所在选项成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("根据可见内容选择失败!找不到元素");
			logger.error(message);
		}
	}
	/**获得下拉框的选中的index ，从0开始<br>
	 * @return 被选中的索引号*/
	public int getSelectedIndex(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int selectedIndex=-1;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				selectedIndex=Integer.parseInt(((Selenium)currentWindow.getDriver().getEngine()).
						getSelectedIndex(locator.getSeleniumCurrentLocator()));
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				List<WebElement>allItems=select.getOptions();
				WebElement selectedItem=select.getFirstSelectedOption();
				for(int i=0;i<allItems.size();i++)
					if(allItems.get(i).equals(selectedItem)){
						selectedIndex=i;break;
					}
			}
			message.append("获得选中项的索引成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("获得选中项的索引失败!找不到元素");
			logger.error(message);
		}
		return selectedIndex;
	}
	/**获得选中项的内容<br>
	 * @return 选中项的内容，字符窜*/
	public String getSelectedValue(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String selectedValue=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				selectedValue=((Selenium)currentWindow.getDriver().getEngine()).
						getSelectedValue(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				selectedValue=select.getFirstSelectedOption().getText();
			}
		}else{
			message.append(this.comment+":");
			message.append("获得选中项的内容失败!找不到元素");
			logger.error(message);
		}
		return selectedValue;
	}
	/**如果选择了多个选项，那么这个时候就用此方法，获得多个选项<br>
	 * @return 被选中的所有选项的内容*/
	public String[] getSelectedValues(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String[] selectedValue=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				selectedValue=((Selenium)currentWindow.getDriver().getEngine()).
						getSelectedValues((locator.getSeleniumCurrentLocator()));
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				List<WebElement>selectedItems=select.getAllSelectedOptions();
				selectedValue=new String[selectedItems.size()];
				for(int i=0;i<selectedItems.size();i++){
					selectedValue[i]=selectedItems.get(i).getText();
				}
			}
			message.append("获得选中项的内容成功!");
			logger.error(message);
		}else{
			message.append(this.comment+":");
			message.append("获得选中项的内容失败!找不到元素");
			logger.error(message);
		}
		return selectedValue;
	}
	/**如果选择了多个选项，那么这个时候就用此方法，获得多个选项<br>
	 * @return 被选中的所有选项的索引*/
	@SuppressWarnings("null")
	public int[] getSelectedIndexes(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int[] selectedIndexes=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				String[]selectedIndexesTemp=((Selenium)currentWindow.getDriver().getEngine()).
						getSelectedIndexes(locator.getSeleniumCurrentLocator());
				for(int i=0;i<selectedIndexesTemp.length;i++){
					selectedIndexes[i]=Integer.parseInt(selectedIndexesTemp[i]);
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				List<WebElement>selectedItems=select.getAllSelectedOptions();
				List<WebElement>allItems=select.getOptions();
				selectedIndexes=new int[selectedItems.size()];
				for(int i=0;i<selectedItems.size();i++){
					for(int j=0;j<selectedItems.size();j++){
						if(selectedItems.get(i).equals(allItems.get(j))){
							selectedIndexes[i]=j;break;
						}
					}
				}
			}
			message.append("获得选中项的内容成功!");
			logger.error(message);
		}else{
			message.append(this.comment+":");
			message.append("获得选中项的内容失败!找不到元素");
			logger.error(message);
		}
		return selectedIndexes;
	}
	/**获得所有的选项内容
	 * @return 所有选项的内容，字符窜形式*/
	public String[]getItems(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String[] allItems=null;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				allItems=((Selenium)currentWindow.getDriver().getEngine()).
				getSelectOptions(locator.getSeleniumCurrentLocator());
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				List<WebElement>allItemsTemp=select.getOptions();
				allItems=new String[allItemsTemp.size()];
				for(int i=0;i<allItems.length;i++){
					allItems[i]=allItemsTemp.get(i).getText();
				}
			}
			message.append("获取所有选项值成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("获取所有选项值失败，找不到元素！");
			logger.error(message);
		}
		return allItems;
	}
	/**获得combobox的大小<br>
	 * @return combobox的大小*/
	public int getComboboxSize(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int size=-1;
		if(isExist()){
			message.append(this.comment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				size=((Selenium)currentWindow.getDriver().getEngine()).
				getSelectOptions(locator.getSeleniumCurrentLocator()).length;
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				size=select.getOptions().size();
			}
			message.append("获取combobox的大小成功！");
			logger.info(message);
		}else{
			message.append(this.comment+":");
			message.append("获取combobox的大小失败，找不到元素！");
			logger.error(message);
		}
		return size;
	}
}
