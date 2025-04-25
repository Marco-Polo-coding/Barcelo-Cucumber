package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class GestorDeCookies {

    private static final Logger LOGGER = LogManager.getLogger(GestorDeCookies.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");
    // Selectores de elementos del popup de cookies
    private final By cookiesPopup = By.xpath(props.get("selector.cookies.popup"));
    private final By aceptarCookies = By.xpath(props.get("selector.cookies.aceptar"));

    public GestorDeCookies(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void aceptarCookiesSiExisten() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

            LOGGER.debug("Buscando popup de cookies...");
            wait.until(ExpectedConditions.presenceOfElementLocated(cookiesPopup));

            LOGGER.debug("Intentando hacer clic en 'Aceptar Cookies'...");
            WebElement aceptar = wait.until(ExpectedConditions.elementToBeClickable(aceptarCookies));
            aceptar.click();

            LOGGER.info("Cookies aceptadas correctamente.");
        } catch (TimeoutException e) {
            LOGGER.info("Timeout: No apareció el banner de cookies, continuando...");
        } catch (NoSuchElementException e) {
            LOGGER.warn("No se encontró el botón de aceptar cookies.");
        } catch (ElementClickInterceptedException e) {
            LOGGER.warn("El botón de cookies estaba cubierto o no era clickeable.");
        } catch (Exception e) {
            LOGGER.error("Error inesperado al aceptar cookies.", e);
        }
    }
}