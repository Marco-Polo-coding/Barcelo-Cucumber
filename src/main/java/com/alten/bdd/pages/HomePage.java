package com.alten.bdd.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{
	
	private static final Logger LOGGER = LogManager.getLogger(HomePage.class);
	private static final By IMDB_SIGNIN_LINK 	= By.className("imdb-header__signin-text");
	private static final By IMDB_HOME_PAGE_LOGO	= By.id("home_img_holder");

	public HomePage(WebDriver driver) {
		super(driver);
	}


	public void verifyHomePageIsLoaded() {
		LOGGER.info("verifyHomePageIsLoaded");
		this.waitToElementBeClickable(IMDB_HOME_PAGE_LOGO);
	}
	
	public void navigateToSignInPage() {
		LOGGER.info("navigateToSignInPage");
		WebElement element = getDriver().findElement(IMDB_SIGNIN_LINK);
		element.click();
	}
}
