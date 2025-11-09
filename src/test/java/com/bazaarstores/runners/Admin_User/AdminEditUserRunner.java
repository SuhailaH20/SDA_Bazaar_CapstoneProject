package com.bazaarstores.runners.Admin_User;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features/Admin_Functionalities/Users/02_EditUser.feature",
        glue = "com.bazaarstores.stepDefinitions",
        tags = "@AdminEditUser",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/admin-edituser-report.html",
                "json:target/cucumber-reports/admin-edituser-report.json",
                "junit:target/cucumber-reports/admin-edituser-report.xml"
        },
        monochrome = true
)
public class AdminEditUserRunner {
}
