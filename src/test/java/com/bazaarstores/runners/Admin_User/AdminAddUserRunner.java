package com.bazaarstores.runners.Admin_User;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features/Admin_Functionalities/Users/01_AddUser.feature",
        glue = "com.bazaarstores.stepDefinitions",
        tags = "@AdminAddUser",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/admin-adduser-report.html",
                "json:target/cucumber-reports/admin-adduser-report.json",
                "junit:target/cucumber-reports/admin-adduser-report.xml"
        },
        monochrome = false
)
public class AdminAddUserRunner {
}
