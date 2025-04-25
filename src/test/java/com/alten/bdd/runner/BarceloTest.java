package com.alten.bdd.runner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:cucumber/barcelo.feature" ,
        glue = "com.alten.bdd.steps",
        monochrome = true,
        plugin = {
                "json:target/cucumber-json-report.json",
                "html:target/cucumber-reports/report.html"
        }
//        tags = "@Barcelo" <-- Investigar
)
public class BarceloTest {
}
