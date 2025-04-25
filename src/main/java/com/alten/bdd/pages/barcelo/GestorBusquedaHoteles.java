package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class GestorBusquedaHoteles {

    private static final Logger LOGGER = LogManager.getLogger(GestorBusquedaHoteles.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");

    private WebDriverWait wait;

    // Selectores
    private final By inputDestino = By.id(props.get("selector.input.destino"));

    public GestorBusquedaHoteles(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Espera de 10 segundos por defecto
    }

    private By getSugerenciaHotel(String hotelName) {
        return By.xpath(String.format(props.get("selector.sugerencia.hotel"), hotelName));
    }

    public void buscarHotel(String hotelName) {
        LOGGER.info("Buscando hotel: " + hotelName);

        try {
            WebElement destinoInput = wait.until(ExpectedConditions.elementToBeClickable(inputDestino));
            destinoInput.clear();
            destinoInput.sendKeys(hotelName);

            LOGGER.debug("Esperando a que aparezca la sugerencia de hotel...");
            By sugerenciaHotel = getSugerenciaHotel(hotelName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(sugerenciaHotel));

            WebElement sugerencia = wait.until(ExpectedConditions.elementToBeClickable(sugerenciaHotel));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", sugerencia);
            sugerencia.click();

            LOGGER.info("Hotel seleccionado de la lista de sugerencias.");

        } catch (TimeoutException e) {
            LOGGER.error("  No se encontró la sugerencia del hotel a tiempo: " + hotelName, e);
            throw e;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("♻️ El elemento sugerido se volvió 'stale', intentando de nuevo...");
            try {
                WebElement sugerencia = wait.until(ExpectedConditions.elementToBeClickable(getSugerenciaHotel(hotelName)));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", sugerencia);
                sugerencia.click();
                LOGGER.info("Hotel seleccionado tras reintento.");
            } catch (Exception ex) {
                LOGGER.error("No se pudo hacer clic en la sugerencia tras el reintento.", ex);
                throw ex;
            }
        } catch (Exception e) {
            LOGGER.error("Error inesperado al buscar hotel: " + hotelName, e);
            throw e;
        }
    }
}
