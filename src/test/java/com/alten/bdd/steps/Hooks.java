package com.alten.bdd.steps;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.alten.bdd.utils.WebDriverFactory;
import com.google.common.io.Files;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.alten.utils.ExtentManager;



public class Hooks {

	private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
	private Scenario scenario = null;
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Before
	public void setup(Scenario scenario) throws Exception {
		LOGGER.info("setup");
		WebDriverFactory.setup();

		if (extent == null) {
			extent = ExtentManager.createReportIfNeeded("Barcelo Test Suite");
		}

		ExtentTest test = extent.createTest(scenario.getName());
		extentTest.set(test);
	}





	@After
	public void tearDown(Scenario scenario) throws IOException {
		LOGGER.info("tearDown");

		File screenshot = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotPath = System.getProperty("user.dir") + "/target/cucumber-reports/" + timestamp + "_" + scenario.getName().replace(" ", "_") + ".png";
		File destFile = new File(screenshotPath);
		Files.copy(screenshot, destFile);

		if (scenario.isFailed()) {
			byte[] screenshotBytes = Files.toByteArray(destFile);
			scenario.attach(screenshotBytes, "image/png", scenario.getName());

			extentTest.get().fail("Escenario fallido: " + scenario.getName())
					.addScreenCaptureFromPath(destFile.getAbsolutePath());
		} else {
			extentTest.get().pass("Escenario pasado: " + scenario.getName());
		}

		WebDriverFactory.closeSetup();
		extent.flush(); // importante para guardar el reporte
	}

}

