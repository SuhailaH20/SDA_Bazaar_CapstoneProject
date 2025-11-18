package com.bazaarstores.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Feature
        glue = "com.bazaarstores.stepDefinitions", //  Step Definitions
        plugin = {
                "pretty",
                "html:target/cucumber-reports/customer-report.html",
                "json:target/cucumber-reports/customer-report.json",
                "junit:target/cucumber-reports/customer-report.xml"
        },
        tags = "@US07_TC001", // Customer
        monochrome = true
)
public class CustomerTestRunner {
    //  Customer Tests
}
