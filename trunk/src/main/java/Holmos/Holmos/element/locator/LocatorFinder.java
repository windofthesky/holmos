package Holmos.Holmos.element.locator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * @author 吴银龙(15857164387)*/
public class LocatorFinder {
	private WebDriver driver;
	private WebElement element;
	private boolean isElementFinder;
	public LocatorFinder(Object locatorFinder){
		if(locatorFinder instanceof WebDriver){
			this.driver=(WebDriver) locatorFinder;
			this.isElementFinder=false;
		}
		else if(locatorFinder instanceof WebElement){
			this.element=(WebElement) locatorFinder;
			this.isElementFinder=true;
		}	
	}
	public WebElement findElement(By by){
		if(this.isElementFinder){
			return element.findElement(by);
		}else
			return driver.findElement(by);
	}
}
