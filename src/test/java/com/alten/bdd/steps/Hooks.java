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



public class Hooks {

	private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
	private Scenario scenario = null;

	@Before
    public void setup(Scenario scenario) throws Exception{
		LOGGER.info("setup");
        WebDriverFactory.setup();
        this.scenario = scenario;
    } 
	
    
    @After
    public void tearDown(Scenario scenario) throws IOException {
    	    		
		LOGGER.info("tearDown");
		String screenshotName = scenario.getName().replaceAll(" ", "_");

		// This takes a screenshot from the driver at save it to the specified location
		File sourcePath = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

		// Building up the destination path for the screenshot to save
		// Also make sure to create a folder 'screenshots' with in the cucumber-report
		// folder
		DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		File destinationPath = new File(
				System.getProperty("user.dir") + "/target/cucumber-reports/" +  df.format(new Date()) + "_"+ screenshotName + ".png");

		// Copy taken screenshot from source location to destination location
		Files.copy(sourcePath, destinationPath);

		// This attach the specified screenshot to the test
	    if (scenario.isFailed()) {
	    	byte[] screenshot = Files.toByteArray(destinationPath);
	        scenario.attach(screenshot, "image/png", scenario.getName());
	    }

		WebDriverFactory.closeSetup();

    }
}

