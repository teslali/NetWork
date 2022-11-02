package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteKeyboard;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.junit.Assert;
import org.assertj.core.api.SoftAssertions;
import utils.GenericUtils;
import utils.TestContextSetup;
import static org.junit.Assert.assertEquals;

public class UserEpicPage {
	public WebDriver driver;
	GenericUtils genericUtils;
	private CSVReader csvReader;
	String[] csvCell;

	public UserEpicPage(WebDriver driver) {
		this.driver = driver;

	}

	public UserEpicPage(TestContextSetup testContextSetup) {
		this.genericUtils = testContextSetup.pageObjectManager.getGenericUtils();
	}

	By acceptCookie = By.xpath("//button[@id='onetrust-accept-btn-handler']");
	By searchBar = By.id("search");
	By showAll = By.xpath("//button[@class='sgm-search-show-all']");
	By showMore = By.xpath("//button[@class='button -secondary -sm relative']");
	By selectedProduct = By.xpath("//a[@title='Siyah Kaşmir Ceket']//div[2]");
	By goTop = By.xpath("//button[@id='go-to-top']//*[name()='svg']");
	By cartSize = By.xpath("/html//div[@id='cop-app']/div/div[1]//div[@class='layout__main']/div[2]/section[@class='cartList']/div[@class='cartItem']/div[@class='cartItem__content']//div[@class='cartItem__info']/div[1]/span[@class='cartItem__attrValue']");
	By cart = By.xpath("//button[@class='header__basketTrigger js-basket-trigger -desktop']//*[name()='svg']");
	By goCart = By.xpath("//a[@class='button -primary header__basket--checkout header__basketModal -checkout']");
	By productPagePrice3 = By.cssSelector("div[class='product__item -column'] span[class='product__price -actual']");
	By cartPrice3 = By.xpath("//span[@class='cartItem__price -sale']");
	By cartPrice2 = By.xpath("//span[@class='cartItem__price -old -labelPrice']");
	By cartPrice1 = By.xpath("//span[@class='cartItem__price -old -seasonPrice']");
	By cartCont = By.xpath("//button[@class='continueButton n-button large block text-center -primary']");
	By emailInput = By.id("n-input-email");
	By passwInput = By.xpath("//input[@id='n-input-password']");
	By login = By.xpath("//button[@type='submit']");
	By homePage = By.xpath("//a[@class='headerCheckout__logo']//*[name()='svg']");
	By headerLogo = By.xpath("//a[@class='headerCheckout__logo']//*[name()='svg']");
	By emptyCart = By.cssSelector(".-scrollOne.header__basketProductList use");
	By emptyCartApp = By.xpath("/html/body/div[5]//div[@class='o-removeCartModal__buttonContainer']/button[2]");
	

	public void CheckUrl() {
		//checks the url
		String actualURL = driver.getCurrentUrl();
		String expectedURL = ("https://www.network.com.tr/");
		assertEquals(actualURL, expectedURL);

	}

	public void Search() throws IOException, InterruptedException {
		driver.findElement(acceptCookie).click();
		// Gets product name from global.properties
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String Product = prop.getProperty("product");
		driver.findElement(searchBar).sendKeys(Product);
		Thread.sleep(2000);
	}

	public void ProductPage() throws InterruptedException {
		//goes second page
		driver.findElement(showAll).click();
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		try {
			driver.findElement(By.linkText("Daha fazla göster")).isDisplayed();
		} catch (Exception E) {

			js.executeScript("scroll(0, 16000)");
		}
		driver.findElement(showMore).click();
		Thread.sleep(2000);

	}

	public void secondPage() throws InterruptedException {
		//checks the url if it is true
		String actualURL = driver.getCurrentUrl();
		String expectedURL = ("https://www.network.com.tr/search?searchKey=ceket&page=2");
		assertEquals(actualURL, expectedURL);
		Thread.sleep(2000);
	}

	public void productSelect() throws InterruptedException {
		// hovers first product
		Actions actions = new Actions(driver);
		driver.findElement(goTop).click();
		Thread.sleep(1000);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("scroll(0, 250)");
		WebElement selected = driver.findElement(selectedProduct);
		actions.moveToElement(selected).build().perform();
		String productPrice = driver.findElement(productPagePrice3).getText();
		// randomly chose a size and tries to click it, if it's in stock adds it to cart
		// and clikcs "go to cart"
		// if it is not in stock clikcs another random size
		//assert product price vs cart price
		int i = 1;
		do {
			List<Integer> givenList1 = Arrays.asList(1, 2, 3, 4, 5, 6);
			Random rand1 = new Random();
			int randomElement1 = givenList1.get(rand1.nextInt(givenList1.size()));	
			String selectedSize = driver.findElement(By.xpath("(//label)[" + randomElement1 + "]")).getText();
			driver.findElement(By.xpath("(//label)[" + randomElement1 + "]")).click();
			Thread.sleep(2000);
			try {
				//driver.findElement(By.xpath("(//label)[" + randomElement1 + "]")).getText();
				driver.findElement(goCart).click();
				Thread.sleep(2000);
				String cartcartSize = driver.findElement(cartSize).getText();
				assertEquals(cartcartSize, selectedSize);
				i++;
			} catch(Exception E) {
				Thread.sleep(1);
			}
			Thread.sleep(2000);
		} while (i < 2);
		String cartPrice = driver.findElement(cartPrice3).getText();
		assertEquals(productPrice, cartPrice);

	}
	
	public void cartPriceCompare() throws InterruptedException {
		//checks discounted price is lower than first price
		String price1 = driver.findElement(cartPrice1).getText();
		String price2 = driver.findElement(cartPrice2).getText();
		String price3 = driver.findElement(cartPrice3).getText();
		String price4 = price1.replace("TL", "0").replace(".", " ").replace(" ", "0").replace(",", "0").trim();
		String price5 = price2.replace("TL", "0").replace(".", " ").replace(" ", "0").replace(",", "0").trim();
		String price6 = price3.replace("TL", "0").replace(".", " ").replace(" ", "0").replace(",", "0").trim();
		Thread.sleep(2000);
		float kar = Float.parseFloat(price4);
		float kar1 = Float.parseFloat(price5);
		float kar2 = Float.parseFloat(price6);
		Boolean compare  = kar > kar1;
		Boolean compare1 = kar1 > kar2;
		assertEquals(compare1, true);
		assertEquals(compare, true);
	}
	
	public void cartContinue() throws CsvValidationException, IOException, InterruptedException {
		//goes login page and enter credentials
		Thread.sleep(2000);
		driver.findElement(cartCont).click();
		Thread.sleep(2000);
		csvReader = new CSVReader(new FileReader(System.getProperty("user.dir")+"//src//test//resources//csvcsv.csv"));
		while((csvCell = csvReader.readNext()) != null) {
		String email = csvCell[0];
		String password = csvCell[1];
		driver.findElement(emailInput).sendKeys(email);
		driver.findElement(passwInput).sendKeys(password);
		Boolean loginEnabled = driver.findElement(login).isEnabled();
		assertEquals(loginEnabled , true);	
		}
	}
	
	public void emptyCart() throws InterruptedException {
		//goes landing page and empty cart
		driver.findElement(headerLogo).click();
		driver.findElement(cart).click();
		Thread.sleep(2000);
		driver.findElement(emptyCart).click();
		Thread.sleep(1000);
		driver.findElement(emptyCartApp).click();
		
	}
	
}
