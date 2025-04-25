package com.alten.bdd.steps;

import com.alten.bdd.utils.WebDriverFactory;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.alten.bdd.pages.HomePage;
import com.alten.bdd.pages.SignInPage;
import com.alten.bdd.pages.SignInWithIMDBPage;
import org.openqa.selenium.WebDriver;

public class IMDBLoginSteps {

	
	private static final Logger LOGGER 					= LogManager.getLogger(IMDBLoginSteps.class);
	private static final Object MESSAGE_USER_NOT_FOUND 	= "there was a problem";
	private WebDriver driver = WebDriverFactory.getDriver();

	@Given("^IMDB home page is showed$")
	public void imdb_home_page_is_showed() throws Throwable {
		
		HomePage homePage = new HomePage(driver);
		homePage.verifyHomePageIsLoaded();
	}

	@When("^I Click on SignIN$")
	public void i_Click_on_SignIN() throws Throwable {
		HomePage homePage = new HomePage(driver);
		homePage.navigateToSignInPage();
	}

	@Then("^IMDB SignIN page is showed$")
	public void imdb_SignIN_page_is_showed() throws Throwable {
		SignInPage signInPage = new SignInPage(driver);
		signInPage.verifySignInPageIsLoaded();
	}

	@When("^I click on Sign In with IMDB$")
	public void i_click_on_Sign_In_with_IMDB() throws Throwable {
		SignInPage signInPage = new SignInPage(driver);
		signInPage.navigateToSignInWithIMDBPage();
	}

	@Then("^IMDB SignIn with IMDB Page is showed$")
	public void imdb_SignIn_with_IMDB_Page_is_showed() throws Throwable {
		SignInWithIMDBPage signInWithIMDBPage = new SignInWithIMDBPage(driver);
		signInWithIMDBPage.verifySignInPageIsLoaded();
	}
	
	@When("^I type (.*), (.*) and submit$")
	public void i_type_user_password_and_submit(String email, String password) throws Throwable {
		LOGGER.info("i_type_user_password_and_submit");
		SignInWithIMDBPage signInWithIMDBPage = new SignInWithIMDBPage(driver);
		signInWithIMDBPage.typeLogin(email);
		signInWithIMDBPage.typePassword(password);
		signInWithIMDBPage.submit();
	}

	@Then("^an error message to user not found is displayed$")
	public void an_error_message_is_displayed() throws Throwable {
		LOGGER.info("an_error_message_is_displayed");
		SignInWithIMDBPage signInWithIMDBPage = new SignInWithIMDBPage(driver);
	    String message = signInWithIMDBPage.getErrorMessage();
		message = message.toLowerCase();
		Assert.assertEquals(MESSAGE_USER_NOT_FOUND, message);
	}

	
}
