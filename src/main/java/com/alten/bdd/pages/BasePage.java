package com.alten.bdd.pages;

import java.time.Duration;

import com.alten.bdd.utils.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	private WebDriver driver;
	protected static WebDriverWait wait;

	public BasePage(WebDriver driver) {
//		driver = WebDriverFactory.getDriver();
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	protected final WebDriver getDriver() {
		return driver;
	}

	protected void waitToElementBeClickable(By by) {
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	protected void waitPresenceOfElement(By by) {
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

}
