package com.bazaarstores.runners.Admin_User;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/Features/Admin_Functionalities/Users/01_AddUser.feature",
                "src/test/resources/Features/Admin_Functionalities/Users/02_EditUser.feature",
                "src/test/resources/Features/Admin_Functionalities/Users/03_DeleteUser.feature"
        },
        glue = "com.bazaarstores.stepDefinitions",
        tags = "@AdminAddUser or @AdminEditUser or @AdminDeleteUser",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/admin-full-suite.html",
                "json:target/cucumber-reports/admin-full-suite.json",
                "junit:target/cucumber-reports/admin-full-suite.xml"
        },
        monochrome = true
)

public class Admin_User_Runner {
}