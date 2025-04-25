package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class ReservarPage {
    private static final Logger LOGGER = LogManager.getLogger(ReservarPage.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");

    private final By botonReservarLocator = By.cssSelector(props.get("selector.btn.reservar"));

    public ReservarPage(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(props.get("espera.extendida"))));
    }

    public void cambiarPestanaFinal() {
        LOGGER.info("Clic en 'Reservar' y cambio de pestaña");

        try {
            By botonReservarLocator = By.cssSelector(props.get("selector.btn.reservar"));
            WebDriverWait wait = new WebDriverWait(getDriver(),
                    Duration.ofSeconds(Long.parseLong(props.get("espera.extendida"))));

            ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement boton = wait.until(ExpectedConditions.presenceOfElementLocated(botonReservarLocator));

            ((JavascriptExecutor) getDriver())
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", boton);
            try {
                boton.click();
            } catch (Exception e) {
                LOGGER.warn("  Fallback click con JS en botón 'Reservar'");
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", boton);
            }

            LOGGER.info("  Click realizado, cambiando de pestaña...");
            String originalTab = getDriver().getWindowHandle();
            wait.until(driver -> getDriver().getWindowHandles().size() > 1);

            for (String tab : getDriver().getWindowHandles()) {
                if (!tab.equals(originalTab)) {
                    getDriver().switchTo().window(tab);
                    LOGGER.info("🔀 Nueva pestaña activa");
                    break;
                }
            }

            wait.until(ExpectedConditions.urlContains("reservation.barcelo.com"));
            LOGGER.info("Página de reservas detectada: " + getDriver().getCurrentUrl());

        } catch (TimeoutException e) {
            LOGGER.error("Timeout esperando botón 'Reservar' o pestaña de reservas", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error inesperado durante cambio a la pestaña final", e);
            throw e;
        }
    }

}

