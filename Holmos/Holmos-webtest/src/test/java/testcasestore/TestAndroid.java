package testcasestore;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;

public class TestAndroid {
	@Test
	public void testGoogle(){
		WebDriver driver=new AndroidDriver();
		driver.get("http://www.google.com.au");
		WebElement element=driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		System.out.println(driver.getTitle());
		driver.quit();
	}
}
