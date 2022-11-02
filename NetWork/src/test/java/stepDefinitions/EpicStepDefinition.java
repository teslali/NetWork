package stepDefinitions;


import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.UserEpicPage;
import utils.TestContextSetup;

public class EpicStepDefinition {
	UserEpicPage userEpicPage;
	TestContextSetup testContextSetup;
	
	public EpicStepDefinition(TestContextSetup testContextSetup) {
		this.testContextSetup=testContextSetup;
		this.userEpicPage=testContextSetup.pageObjectManager.getUserEpicPage();
		
	}
	
	@Given("User is on landing page")
	public void user_is_on_landing_page() {
	    userEpicPage.CheckUrl();
	}
	@Given("Searches for Ceket")
	public void searches_for_ceket() throws IOException, InterruptedException {
	   userEpicPage.Search();
	}
	@Given("Scrolls thorugh product page")
	public void scrolls_thorugh_product_page() throws InterruptedException {
		userEpicPage.ProductPage();  
	}
	@Given("Reach second product page")
	public void reach_second_product_page() throws InterruptedException {
	   userEpicPage.secondPage();
	}
	@Given("Adds an item to cart via hovering")
	public void adds_an_item_to_cart_via_hovering() throws InterruptedException {
	   userEpicPage.productSelect();
	}
	@Given("Goes cart")
	public void goes_cart() throws InterruptedException {
	    userEpicPage.cartPriceCompare();
	}
	@Given("Goes login page")
	public void goes_login_page() throws CsvValidationException, IOException, InterruptedException {
	    userEpicPage.cartContinue();
	}
	@Then("Empty cart")
	public void empty_cart() throws InterruptedException {
	   userEpicPage.emptyCart();
	}
	

}
