package com.alten.bdd.pages.barcelo;

import com.alten.bdd.utils.PropertiesMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.*;
import java.util.Comparator;
import java.util.List;

import static com.alten.bdd.utils.WebDriverFactory.getDriver;

public class GestorFechas {

    private static final Logger LOGGER = LogManager.getLogger(GestorFechas.class);
    private static final PropertiesMain props = new PropertiesMain("BarceloHomePage");

    private WebDriverWait wait;

    private final By inputEntrada = By.id(props.get("selector.input.checkin"));
    private final By diaDisponible = By.cssSelector(props.get("selector.dia.disponible"));
    private final By loadingIndicator = By.cssSelector(props.get("selector.spinner"));

    public GestorFechas(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void seleccionarFechas(int offset, int diasEstancia) {
        LOGGER.info("Seleccionando fechas desde calendario visible...");

        if (offset < 0 || diasEstancia <= 0) {
            throw new IllegalArgumentException("Offset y estancia deben ser positivos.");
        }

        try {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputEntrada));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", input);
            input.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIndicator));
        } catch (Exception e) {
            LOGGER.error("No se pudo abrir el calendario.", e);
            return;
        }

        int intentos = 0;
        boolean encontrado = false;

        while (intentos < 3 && !encontrado) {
            try {
                List<WebElement> todosLosDias = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("td[class*='datepicker__month-day']"))
                );

                List<WebElement> diasVisibles = todosLosDias.stream()
                        .filter(d -> d.getAttribute("class").contains("datepicker__month-day--visibleMonth"))
                        .filter(d -> d.getAttribute("time") != null)
                        .sorted(Comparator.comparing(d -> Long.parseLong(d.getAttribute("time"))))
                        .toList();

                LocalDate hoy = LocalDate.now();
                LocalDate entradaDeseada = hoy.plusDays(offset);


                List<DiaCalendario> calendario = diasVisibles.stream()
                        .map(d -> {
                            long time = Long.parseLong(d.getAttribute("time"));
                            LocalDate fecha = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
                            boolean disponible = d.getAttribute("class").contains("valid") && d.isDisplayed() && d.getSize().getHeight() > 0;

                            return new DiaCalendario(fecha, d, disponible);
                        })
                        .filter(d -> !d.fecha.isBefore(entradaDeseada))
                        .toList();

                for (int i = 0; i <= calendario.size() - diasEstancia; i++) {
                    boolean bloqueValido = true;
                    for (int j = 0; j < diasEstancia; j++) {
                        DiaCalendario diaActual = calendario.get(i + j);
                        LocalDate fechaEsperada = calendario.get(i).fecha.plusDays(j);
                        if (!diaActual.disponible || !diaActual.fecha.equals(fechaEsperada)) {
                            bloqueValido = false;
                            break;
                        }
                    }
                    if (bloqueValido) {
                        DiaCalendario entrada = calendario.get(i);
                        DiaCalendario salida = calendario.get(i + diasEstancia - 1);

                        LOGGER.info("Rango válido encontrado: " + entrada.fecha + " -> " + salida.fecha);

                        Actions actions = new Actions(getDriver());
                        actions.moveToElement(entrada.elemento).click().perform();

                        for (int j = 1; j < diasEstancia - 1; j++) {
                            actions.moveToElement(calendario.get(i + j).elemento).pause(Duration.ofMillis(150)).perform();
                        }

                        actions.moveToElement(salida.elemento).click().perform();
                        getDriver().switchTo().activeElement().sendKeys(Keys.TAB);

                        wait.until(driver -> {
                            String value = driver.findElement(inputEntrada).getAttribute("value");
                            return value != null && value.matches(".*\\d{4}.*");
                        });

                        LOGGER.info("Fechas aplicadas correctamente.");
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    LOGGER.warn("Intento #" + (intentos + 1) + ": No se encontró un bloque válido.");
//                    Thread.sleep(800); // por si el DOM necesita un poco más de carga
                }
            } catch (Exception e) {
                LOGGER.error("Error en intento #" + (intentos + 1), e);
            }
            intentos++;
        }

//        if (!encontrado) {
//            throw new IllegalArgumentException("No se encontraron fechas disponibles en los dos meses visibles.");
//        }

        try {
            String valorFinal = getDriver().findElement(inputEntrada).getAttribute("value");
            LOGGER.info("Valor final en el input: [" + valorFinal + "]");
            // Scroll hacia el input de checkin al finalizar selección
            try {
                WebElement input = getDriver().findElement(inputEntrada);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'start'});", input);
                LOGGER.info("Scroll hecho hacia el input de fechas para reubicar el viewport.");
            } catch (Exception e) {
                LOGGER.warn("No se pudo hacer scroll hacia el input de fechas.", e);
            }

        } catch (Exception e) {
            LOGGER.warn("No se pudo verificar el valor final del input.", e);
        }
    }


    // Clase interna auxiliar
    private static class DiaCalendario {
        LocalDate fecha;
        WebElement elemento;
        boolean disponible;

        public DiaCalendario(LocalDate fecha, WebElement elemento, boolean disponible) {
            this.fecha = fecha;
            this.elemento = elemento;
            this.disponible = disponible;
        }
    }


}
