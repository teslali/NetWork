package pageObjects;

import org.openqa.selenium.WebDriver;

import utils.GenericUtils;

public class PageObjectManager {
	
	public UserEpicPage userEpicPage;
	public WebDriver driver;
	public GenericUtils genericUtils;
	
	public PageObjectManager(WebDriver driver)
	{
		this.driver = driver;
	}

	
	
	public UserEpicPage getUserEpicPage()
	{
	
	 userEpicPage= new UserEpicPage(driver);
	 return userEpicPage;
	}
	
	public GenericUtils getGenericUtils() {
		genericUtils = new GenericUtils(driver);
		return genericUtils;
	}
	
}
