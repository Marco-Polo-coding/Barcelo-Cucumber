package com.alten.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestPropertiesMain {

    private final Properties properties;

    public TestPropertiesMain(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName + ".properties")) {
            if (input == null) {
                throw new RuntimeException(" No se pudo encontrar el archivo: " + fileName + ".properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo de propiedades: " + fileName, e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}

