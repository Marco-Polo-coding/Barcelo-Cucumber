package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class GestorHuespedes {

    private static final Logger LOGGER = LogManager.getLogger(GestorHuespedes.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");

    private WebDriverWait wait;

    // Selectores de los elementos de la interfaz
    private final By iconoHuespedes = By.xpath(props.get("selector.icono.huespedes"));
    private final By botonAdultosMas = By.cssSelector(props.get("selector.btn.adultos.mas"));
    private final By botonNinosMas = By.xpath(props.get("selector.btn.ninos.mas"));
    private final By edadPrimerNino = By.cssSelector(props.get("selector.edad.nino"));
    private final By botonAceptarHuespedes = By.xpath(props.get("selector.btn.aceptar.huespedes"));

    public GestorHuespedes(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera de 10 segundos por defecto
    }

    public void abrirDesplegableHuespedes() {
        LOGGER.info("Abriendo desplegable de habitaciones y huéspedes");

        try {
            WebElement icono = wait.until(ExpectedConditions.elementToBeClickable(iconoHuespedes));

            // waitArtificially(3); // Simula tiempo humano de reacción
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", icono);
            // waitArtificially(3); // Simula duda antes de hacer clic
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", icono);

            By modalAbierto = By.xpath(props.get("selector.modal.huespedes.abierto"));
            WebDriverWait extendedWait = new WebDriverWait(getDriver(),
                    Duration.ofSeconds(Long.parseLong(props.get("espera.extendida"))));
            extendedWait.until(ExpectedConditions.visibilityOfElementLocated(modalAbierto));

            LOGGER.info("Desplegable abierto correctamente");

        } catch (Exception e) {
            LOGGER.error("Error al abrir el desplegable de huéspedes", e);
            throw e;
        }
    }

    public void aceptarSeleccionHuespedes() {
        LOGGER.info("Haciendo clic en 'Aceptar' para cerrar el selector de huéspedes");

        try {
            By btnAceptarLocator = By.xpath(props.get("selector.btn.aceptar.huespedes"));
            WebElement btnAceptar = wait.until(ExpectedConditions.elementToBeClickable(btnAceptarLocator));

            // waitArtificially(3);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                    btnAceptar);
            // waitArtificially(3);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", btnAceptar);

            LOGGER.info("Clic en botón 'Aceptar' realizado con éxito");

        } catch (Exception e) {
            LOGGER.error("Error al hacer clic en 'Aceptar' del selector de huéspedes.", e);
            throw e;
        }
    }

    public void seleccionarHuespedes(int adultos, int ninos, String edadNino) {
        LOGGER.info("Seleccionando huéspedes: Adultos = " + adultos + ", Niños = " + ninos + ", Edad niño = "
                + edadNino);

        try {
            abrirDesplegableHuespedes();
            // waitArtificially(3);

            for (int i = 0; i < ninos; i++) {
                By botonNinosMasLocator = By.xpath(props.get("selector.btn.ninos.mas"));
                WebElement btnMasNinos = wait.until(ExpectedConditions.elementToBeClickable(botonNinosMasLocator));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});",
                        btnMasNinos);
                // waitArtificially(3);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", btnMasNinos);
                LOGGER.info("Niño añadido (" + (i + 1) + ")");
                // waitArtificially(3);
            }

            if (ninos > 0) {
                By edadSelector = By.cssSelector(props.get("selector.edad.nino"));
                WebElement edadDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(edadSelector));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", edadDropdown);
                wait.until(ExpectedConditions.elementToBeClickable(edadDropdown));
                // waitArtificially(3);
                new Select(edadDropdown).selectByVisibleText(edadNino);
                LOGGER.info(" Edad del primer niño seleccionada: " + edadNino);
            }

            // waitArtificially(3);
            aceptarSeleccionHuespedes();

            LOGGER.info(" Selección de huéspedes finalizada correctamente");

        } catch (Exception e) {
            LOGGER.error("Error durante la selección de huéspedes", e);
            throw e;
        }
    }
}