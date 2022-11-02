package utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GenericUtils {
	public WebDriver driver;
	
	public GenericUtils(WebDriver driver)
	{
		this.driver = driver;
	}
	

	public void SwitchWindowToChild()
	{
		Set<String> s1=driver.getWindowHandles();
		Iterator<String> i1 =s1.iterator();
		String parentWindow = i1.next();
		String childWindow = i1.next();
		driver.switchTo().window(childWindow);
	}
	
	public String PickRandomNumber() {
		List<Integer> givenList1 = Arrays.asList(1, 2, 3, 4, 5, 6);
		Random rand1 = new Random();
		int randomElement1 = givenList1.get(rand1.nextInt(givenList1.size()));
		String chosenSize1 = driver.findElement(By.xpath("(//label)[" + randomElement1 + "]")).getText();
		return chosenSize1;
	}
	
}
