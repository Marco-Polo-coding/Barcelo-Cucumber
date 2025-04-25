package com.alten.bdd.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static Properties properties = new Properties();
    private static final String pathToPropertiesFile = "src/test/resources/TestBarcelo.properties";
	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(WebDriverFactory.class);

	private static final String BROWSER = "browser";
	private static final String URL = "URL";
	private static final String CHROME = "chrome";
	private static final String EDGE = "edge";
	private static final String FIREFOX = "firefox";

    public static void setup() throws Exception {
    	
    	properties.load(new BufferedReader(new FileReader(pathToPropertiesFile)));
        setDriver(properties.getProperty(BROWSER));
        LOGGER.info("@BeforeMethod. " + Thread.currentThread().threadId() + " and Driver reference is: "
                + getDriver());
      
    }

    private static void setDriver(String browserName) throws Exception {
    	 LOGGER.info("@setDriver," + browserName);
            switch (browserName.toLowerCase()) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    driverThreadLocal.set(new ChromeDriver());
                    break;
                case EDGE:
                	WebDriverManager.edgedriver().setup();
                    driverThreadLocal.set(new EdgeDriver());
                    break;
                case FIREFOX:
                	WebDriverManager.firefoxdriver().setup();
                    driverThreadLocal.set(new FirefoxDriver());
                    break;
                default:
                    throw new Exception("Browser no permitido: " + browserName);

            }
            driverThreadLocal.get().manage().window().maximize();
            driverThreadLocal.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driverThreadLocal.get().get((String) properties.get(URL));
    }

    public static void closeSetup() {
        driverThreadLocal.get().quit();
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}

