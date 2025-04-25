package com.alten.bdd.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInWithIMDBPage extends BasePage{
	
	private static final Logger LOGGER = LogManager.getLogger(SignInWithIMDBPage.class);
	private static final By IMDB_EMAIL	= By.id("ap_email");
	private static final By IMDB_SUBMIT = By.id("signInSubmit");
	private static final By IMDB_PASS = By.id("ap_password");
	private static final By IMDB_ERROR_MSG= By.xpath("//div[@id='auth-error-message-box']//h4");

	public SignInWithIMDBPage(WebDriver driver) {
		super(driver);
	}


	public void verifySignInPageIsLoaded() throws InterruptedException {
		LOGGER.info("verifySignInPageIsLoaded");
		this.waitToElementBeClickable(IMDB_EMAIL);
		this.waitToElementBeClickable(IMDB_PASS);
		this.waitToElementBeClickable(IMDB_SUBMIT);
	}
	
	public void typeLogin(String email) {		
		WebElement element = getDriver().findElement(IMDB_EMAIL);
		element.sendKeys(email);	
	}
	
	public void typePassword(String pw) {
		WebElement element = getDriver().findElement(IMDB_PASS);
		element.sendKeys(pw);
	}
	
	public void submit() {
		WebElement element = getDriver().findElement(IMDB_SUBMIT);
		element.click();
	}
	
	public String getErrorMessage() {
		LOGGER.info("getErrorMessage");
		waitPresenceOfElement(IMDB_ERROR_MSG);
		WebElement element = getDriver().findElement(IMDB_ERROR_MSG);
		String ret = element.getText();
		LOGGER.info("error="+ret);
		return ret;
	}
	

}
