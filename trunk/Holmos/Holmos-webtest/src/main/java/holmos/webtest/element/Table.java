package holmos.webtest.element;

import holmos.webtest.Allocator;
import holmos.webtest.BrowserWindow;
import holmos.webtest.SeleniumBrowserWindow;
import holmos.webtest.WebDriverBrowserWindow;
import holmos.webtest.element.property.Location;
import holmos.webtest.exceptions.HolmosFailedError;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
/**
 * @author 吴银龙(15857164387)
 * */
public class Table extends Element{
	public Table(String comment) {
		super(comment);
	}
	/**获得table的第row行，col列的值<br>
	 * 行号和列号都从1开始<br>
	 * @param row 行号  
	 * @param col 列号
	 * @return 此table指定行，指定列的值*/
	public String getElementContent(int row,int col){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		String content=null;
		if(isExist()){
			message.append(this.wholeComment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				try{
					content=((Selenium)currentWindow.getDriver().getEngine()).
							getTable(locator.getSeleniumCurrentLocator()+"."+row+"."+col);
					message.append("第"+row+"行，第"+col+"列的元素获取成功!");
					logger.info(message);
				}catch (Exception e) {
					message.append("第"+row+"行，第"+col+"列的元素获取失败!找不到这个table的这个元素");
					logger.error(message);
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				try{
					content=element.findElement(By.xpath(".//tr["+(row)+"]/td["+(col)+"]")).getText();
					message.append("第"+row+"行，第"+col+"列的元素获取成功!");
					logger.info(message);
				}catch(Exception e){
					message.append("第"+row+"行，第"+col+"列的元素获取失败!找不到这个table的这个元素");
					logger.error(message);
				}
			}
		}else{
			message.append(this.wholeComment+":");
			message.append("第"+row+"行，第"+col+"列的元素获取失败!找不到这个table");
			logger.error(message);
		}
		return content;
	}
	/**获得此table第column列所有行的值，列号从1开始<br>
	 * @param column 列号
	 * @return 字符窜List，column列所有行的信息*/
	public List<String> getElementsContentByColumn(int column){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		List<String> contents=new ArrayList<String>();
		if(isExist()){
			message.append(this.wholeComment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				try{
					int rowCount=0;
					while(true){
						contents.add(((Selenium)currentWindow.getDriver().getEngine())
								.getTable(locator.getSeleniumCurrentLocator()+"."+rowCount+"."+column));
						rowCount++;
					}
				}catch(SeleniumException e){
					
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				List<WebElement>cells=element.findElements(By.xpath(".//td["+column+"]"));
				for(WebElement cell:cells){
					contents.add(cell.getText());
				}
			}
			message.append("第"+column+"列的元素的信息获得成功!");
			logger.info(message);
		}else{
			message.append(this.wholeComment+":");
			message.append("第"+column+"列的元素的信息获得失败!找不到该table!");
			logger.error(message);
		}
		return contents;
	}
	/**获得columns中指定的若干列的所有行的值，返回的是<br>
	 * 一个二维数组，获得的列的值的顺序和columns中的一致<br>
	 * 如果columns.length=0那么将返回null<br>
	 * @param columns 列的列表
	 * @return 字符窜二维List，返回的是找到的指定列的所有行信息*/
	public List<List<String>> getElementsContentByColumns(int[] columns){
		List<List<String>>contents=new ArrayList<List<String>>();
		System.out.println("开始获得若干列的元素内容");
		for(int i=0;i<columns.length;i++){
			contents.add(getElementsContentByColumn(columns[i]));
		}
		return contents;
	}
	/**获得此table第row行所有列的值，行号从1开始<br>
	 * @param row 行号
	 * @return 字符窜List，row列所有列的信息*/
	public List<String> getElementsContentByRow(int row){
		/***多层table的时候有点问题,暂时先放，不只是这个，很多都是有问题的，多个table的时候，对于WebDriver作为引擎的时候*/
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		List<String> contents=new ArrayList<String>();
		if(isExist()){
			message.append(this.wholeComment+":");
			if(currentWindow instanceof SeleniumBrowserWindow){
				try{
					int colCount=0;
					while(true){
						contents.add(((Selenium)currentWindow.getDriver().getEngine())
								.getTable(locator.getSeleniumCurrentLocator()+"."+row+"."+colCount));
						colCount++;
					}
				}catch(SeleniumException e){
					
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				WebElement rowCell=element.findElement(By.xpath(".//tr["+row+"]"));
				List<WebElement>cells=rowCell.findElements(By.xpath(".//td"));
				for(WebElement cell:cells){
					contents.add(cell.getText());
				}
			}
			message.append("第"+row+"行的元素的信息获得成功!");
			logger.info(message);
		}else{
			message.append(this.wholeComment+":");
			message.append("第"+row+"行的元素的信息获得失败!找不到该table!");
			logger.error(message);
		}
		return contents;
	}
	/**获得rows中指定的若干行的所有列的值，返回的是<br>
	 * 一个二维数组，获得的行的值的顺序和rows中的一致<br>
	 * 如果rows.length=0那么将返回null<br>
	 * @param rows 行的列表
	 * @return 字符窜二维List，返回的是找到的指定行的所有行信息*/
	public List<List<String>> getElementsContentByRows(int[] rows){
		StringBuilder message=new StringBuilder();
		List<List<String>>contents=new ArrayList<List<String>>();
		message.append("开始获得若干行的元素内容");
		logger.info(message);
		for(int i=0;i<rows.length;i++){
			contents.add(getElementsContentByRow(rows[i]));
		}
		return contents;
	}
	/**获得指定位置处的值，以字符窜的形式返回,按照行优先的顺序返回<br>
	 * 对于找不到的，即locations里面的值不合法的不计入，位置从0开始计数<br>
	 * @param locations 指定的位置列表
	 * @return 获得的指定位置处的值的字符窜列表*/
	public List<String> getElementsContentByLocations(Location[] locations){
		StringBuilder message=new StringBuilder();
		List<String>contents=new ArrayList<String>();
		message.append("开始获得若干行的元素内容");
		logger.info(message);
		for(int i=0;i<locations.length;i++){
			contents.add(getElementContent(locations[i].getxLocation(), locations[i].getyLocation()));
		}
		return contents;
	}
	/**获得此table所有行所有列的值
	 * @return 获得的所有行和所有列的值，如果table是空的，那么返回null
	 * */
	public List<List<String>> getAllElementsContent(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		List<List<String>>contents=new ArrayList<List<String>>();
		message.append("开始获得所有元素的内容");
		if(isExist()){
			message.append(this.wholeComment+":");
			int rowCount=0;
			if(currentWindow instanceof SeleniumBrowserWindow){
				while(true){
					try{
						((Selenium)currentWindow.getDriver().getEngine())
						.getTable(locator.getSeleniumCurrentLocator()+"."+rowCount+"."+0);
						contents.add(getElementsContentByRow(rowCount));
						rowCount++;
					}catch (SeleniumException e) {
						break;
					}
				}
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				while(true){
					if(element.findElements(By.xpath(".//tr["+rowCount+"]")).size()==0){
						break;
					}
					contents.add(getElementsContentByRow(rowCount));
					rowCount++;
				}
			}
		}else{
		}
		return contents;
	}
	/**获得table的第row行，col列的值<br>
	 * 行号和列号都从1开始<br>
	 * @param row 行号  
	 * @param col 列号
	 * @return 此table指定行，指定列的元素信息*/
	public WebElement getElement(int row,int col){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				throw new HolmosFailedError("RC不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				return element.findElement(By.xpath("./tr["+row+"]/td["+col+"]"));
			}
		}else{
			message.append("第"+(row+1)+"行，第"+(col+1)+"列的元素获取失败!,找不到这个table");
			logger.error(message);
		}
		return null;
	}
	/**获得此table第column列所有行的值，列号从1开始<br>
	 * @param column 列号
	 * @return 字符窜List，column列所有行的元素信息*/
	public List<WebElement> getElementsByColumn(int column){
		List<ArrayList<WebElement>>allElements=getAllElements();
		ArrayList<WebElement>cElement=new ArrayList<WebElement>();
		for(int row=0;row<allElements.size();row++){
			cElement.add(allElements.get(row).get(column-1));
		}
		return cElement;
	}
	/**获得columns中指定的若干列的所有行的值，返回的是<br>
	 * 一个二维数组，获得的列的值的顺序和columns中的一致<br>
	 * 如果columns.length=0那么将返回null<br>
	 * @param columns 列的列表
	 * @return 字符窜二维List，返回的是找到的指定列的所有行元素信息*/
	public List<ArrayList<WebElement>> getElementsByColumns(int[] columns){
		List<ArrayList<WebElement>>elements=getAllElements();
		List<ArrayList<WebElement>>allElements=getAllElements();
		for(int column:columns){
			ArrayList<WebElement>cElement=new ArrayList<WebElement>();
			for(int row=0;row<allElements.size();row++){
				cElement.add(allElements.get(row).get(column-1));
			}
			elements.add(cElement);
		}
		return elements;
	}
	/**获得此table第row行所有列的值，行号从0开始<br>
	 * @param row 行号
	 * @return 字符窜List，row列所有列的元素信息*/
	public List<WebElement> getElementsByRow(int row){
		StringBuilder message=new StringBuilder();
		ArrayList<WebElement>elements=new ArrayList<WebElement>();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				throw new HolmosFailedError("RC不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				elements=(ArrayList<WebElement>) element.findElements(By.xpath("./tr["+row+"]"));	
			}
		}else{
			logger.error(message);
		}
		return elements;
	}
	/**获得rows中指定的若干行的所有列的值，返回的是<br>
	 * 一个二维List，获得的行的值的顺序和rows中的一致<br>
	 * 如果rows.length=0那么将返回null<br>
	 * @param rows 行的列表
	 * @return 字符窜二维List，返回的是找到的指定行的所有行元素信息*/
	public List<ArrayList<WebElement>> getElementsByRows(int[] rows){
		List<ArrayList<WebElement>>elements=getAllElements();
		List<ArrayList<WebElement>>allElements=getAllElements();
		for(int row:rows){
			elements.add(allElements.get(row));
		}
		return elements;
	}
	/**获得指定位置处的值，以字符窜的形式返回,按照行优先的顺序返回<br>
	 * 对于找不到的，即locations里面的值不合法的不计入，位置从1开始计数<br>
	 * @param locations 指定的位置列表
	 * @return 获得的指定位置处的值的元素列表*/
	public List<WebElement> getElementsByLocations(Location[] locations){
		List<WebElement>elements=new ArrayList<WebElement>();
		List<ArrayList<WebElement>>allElements=getAllElements();
		for(Location location:locations){
			elements.add(allElements.get(location.getxLocation()).get(location.getyLocation()));
		}
		return elements;
	}
	/**获得此table所有行所有列的值
	 * @return 获得的所有行和所有列的元素，如果table是空的，那么返回null
	 * */
	public List<ArrayList<WebElement>> getAllElements(){
		StringBuilder message=new StringBuilder();
		List<ArrayList<WebElement>>elements=new ArrayList<ArrayList<WebElement>>();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				throw new HolmosFailedError("RC不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				for(WebElement rowElement:element.findElements(By.xpath("./tr"))){
					elements.add((ArrayList<WebElement>) rowElement.findElements(By.xpath("./td")));
				}
			}
		}else{
			logger.error(message);
		}
		return elements;
	}
	/**
	 * 得到该Table的行数
	 * @return 行数
	 * */
	public int getRowCount(){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int rowCount=0;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				throw new HolmosFailedError("RC不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				rowCount=element.findElements(By.xpath("./tr")).size();
			}
		}else{
			logger.error(message);
		}
		return rowCount;
	}
	/**
	 * 获取指定行的列数
	 * @return 列数
	 * */
	public int getColumnCount(int row){
		StringBuilder message=new StringBuilder();
		BrowserWindow currentWindow=Allocator.getInstance().currentWindow;
		int columnCount=0;
		if(isExist()){
			if(currentWindow instanceof SeleniumBrowserWindow){
				throw new HolmosFailedError("RC不支持");
			}else if(currentWindow instanceof WebDriverBrowserWindow){
				columnCount=element.findElements(By.xpath("./tr["+row+"]/td")).size();
			}
		}else{
			logger.error(message);
		}
		return columnCount;
	}
}
