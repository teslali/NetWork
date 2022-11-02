package stepDefinitions;

import java.io.IOException;

import org.junit.After;

import utils.TestContextSetup;

public class Hooks {
	TestContextSetup testContextSetup;

	public Hooks(TestContextSetup testContextSetup) {

		this.testContextSetup = testContextSetup;
	}

	@After
	public void AfterScenario() throws IOException {

		testContextSetup.testBase.WebDriverManager().quit();

	}

}
