package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class GestorBusquedaReserva {

    private static final Logger LOGGER = LogManager.getLogger(GestorBusquedaReserva.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");

    private WebDriverWait wait;

    private final By botonBuscar = By.cssSelector(props.get("selector.btn.buscar"));

    public GestorBusquedaReserva(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera de 10 segundos por defecto
    }

    public void clickBuscar() {
        LOGGER.info("Intentando hacer click en el botón 'Buscar hotel'");

        try {
            WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonBuscar));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", boton);
            LOGGER.info("Click en botón 'Buscar' realizado con éxito.");
        } catch (TimeoutException e) {
            LOGGER.error("Tiempo de espera agotado al localizar el botón 'Buscar'.", e);
            throw e;
        } catch (NoSuchElementException e) {
            LOGGER.error("  No se encontró el botón 'Buscar'.", e);
            throw e;
        } catch (ElementClickInterceptedException e) {
            LOGGER.warn("Click interceptado, intentando con JavaScript...");
            try {
                WebElement boton = getDriver().findElement(botonBuscar);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", boton);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", boton);
                LOGGER.info("Click en botón 'Buscar' realizado mediante JS.");
            } catch (Exception jsEx) {
                LOGGER.error("Fallo también al hacer click con JS.", jsEx);
                throw jsEx;
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("El botón 'Buscar' se volvió stale, reintentando...");
            try {
                WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonBuscar));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", boton);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", boton);
                LOGGER.info("Click tras recuperar botón 'stale'.");
            } catch (Exception retryEx) {
                LOGGER.error("Falló el reintento tras 'stale element'.", retryEx);
                throw retryEx;
            }
        } catch (Exception e) {
            LOGGER.error("Error inesperado al hacer click en 'Buscar'.", e);
            throw e;
        }
    }
}

