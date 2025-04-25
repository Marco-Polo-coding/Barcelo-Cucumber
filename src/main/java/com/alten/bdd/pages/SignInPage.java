package com.alten.bdd.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage extends BasePage{
	
	private static final Logger LOGGER = LogManager.getLogger(SignInPage.class);
	private static final By IMDB_SIGNIN_BUTTON	= By.className("imdb-logo");

	public SignInPage(WebDriver driver) {
		super(driver);
	}

	public void verifySignInPageIsLoaded() {
		LOGGER.info("verifySignInPageIsLoaded");
		this.waitToElementBeClickable(IMDB_SIGNIN_BUTTON);
	}
	
	public void navigateToSignInWithIMDBPage() {
		LOGGER.info("navigateToSignInWithIMDBPage");
		WebElement element = getDriver().findElement(IMDB_SIGNIN_BUTTON);
		element.click();
	}
}
