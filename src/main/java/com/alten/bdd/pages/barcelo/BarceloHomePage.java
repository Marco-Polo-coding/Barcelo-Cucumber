package com.alten.bdd.pages.barcelo;

import com.alten.bdd.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class BarceloHomePage extends BasePage {

    public BarceloHomePage(WebDriver driver) {
        super(driver);
    }

    public void aceptarCookiesSiExisten() {
        new GestorDeCookies(getDriver()).aceptarCookiesSiExisten();
    }

    public void buscarHotel(String hotelName) {
        new GestorBusquedaHoteles(getDriver()).buscarHotel(hotelName);
    }

    public void seleccionarFechas(int offset, int diasEstancia) {
        new GestorFechas(getDriver()).seleccionarFechas(offset, diasEstancia);
    }

    public void seleccionarHuespedes(int adultos, int ninos, String edadNino) {
        new GestorHuespedes(getDriver()).seleccionarHuespedes(adultos, ninos, edadNino);
    }

    public void clickBuscar() {
        new GestorBusquedaReserva(getDriver()).clickBuscar();
    }

    public void cambiarPestanaFinal() {
        new ReservarPage(getDriver()).cambiarPestanaFinal();
    }
}

