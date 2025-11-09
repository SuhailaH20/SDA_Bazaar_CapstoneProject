package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.DashboardPage;
import com.bazaarstores.pages.LoginPage;
import com.bazaarstores.pages.UserPage;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ViewAllUsersAsAdminSteps {

    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    UserPage userPage = new UserPage();

    @Given("Admin is logged in and on the Dashboard page")
    public void adminIsLoggedInAndOnTheDashboardPage() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
        loginPage.login(ConfigReader.getAdminEmail(), ConfigReader.getDefaultPassword());
    }

    @When("Admin navigates to the Users page")
    public void adminNavigatesToTheUsersPage() {
        dashboardPage.goToUserPage().waitForUrlContains("/users");
    }

    @Then("All users should be displayed with columns {string}, {string}, and {string}")
    public void allUsersShouldBeDisplayedWithColumnsAnd(String name, String email, String action) {
        Assert.assertTrue("Table with columns and all users Not displayed",
                userPage.isUsersTableDisplayedWithColumns(name, email , action)
                        & userPage.areAllUsersDisplayed());

    }

    @And("Admin searches for user by email {string}")
    public void adminSearchesForUserByEmail(String email) {
        userPage.searchUserByEmail(email);
    }

    @Then("User details with email {string} should be displayed")
    public void userDetailsWithEmailShouldBeDisplayed(String email) {
        Assert.assertTrue("User details Not displayed!", userPage.isUserDisplayedByEmail(email));
    }

    @And("Admin searches for users with domain {string}")
    public void adminSearchesForUsersWithDomain(String domain) {
        userPage.searchUserByEmail(domain);
    }

    @Then("All users with email domain {string} should be displayed")
    public void allUsersWithEmailDomainShouldBeDisplayed(String domain) {
        Assert.assertTrue("Users with same domain are not displayed!",userPage.areUsersDisplayedByDomain(domain));
    }

    @Then("System should display message {string}")
    public void systemShouldDisplayMessage(String msg) {
        Assert.assertTrue("Message Not displayed!", userPage.isNoUsersFoundMessageDisplayed(msg));
    }


    @And("Admin leaves the search field empty And clicks the search button")
    public void adminLeavesTheSearchFieldEmptyAndClicksTheSearchButton() {
        userPage.clearSearchField();
    }

    @Then("All users should remain displayed with no changes")
    public void allUsersShouldRemainDisplayedWithNoChanges() {
        Assert.assertTrue("All users are Not displayed!", userPage.areAllUsersDisplayed());
    }


}
