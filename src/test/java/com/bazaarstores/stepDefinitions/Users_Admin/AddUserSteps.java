package com.bazaarstores.stepDefinitions.Users_Admin;

import com.bazaarstores.pages.AllPages;
import com.bazaarstores.utilities.ApiUtilities;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class AddUserSteps {

    AllPages pages = new AllPages();
    public static String userEmail;
    public static String userName;

    // ====== UI Steps ======
    @Given("User Goes To Dashboard")
    public void userGoesToDashboard() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
    }

    @When("admin navigates to Add User page")
    public void adminNavigatesToAddUserPage() {
        Driver.getDriver().navigate().to(ConfigReader.getBaseUrl() + "/users/create");
    }

    @And("admin enters name {string}")
    public void adminEntersName(String name) {
        userName = name;
        pages.getUserPage().enterName(name);
    }

    @And("admin enters email {string}")
    public void adminEntersEmail(String email) {
        userEmail = email;
        pages.getUserPage().enterEmail(email);
    }

    @And("admin enters password {string}")
    public void adminEntersPassword(String password) {
        pages.getUserPage().enterPassword(password);
    }

    @And("admin enters password confirmation {string}")
    public void adminEntersPasswordConfirmation(String confirmPassword) {
        pages.getUserPage().enterConfirmPassword(confirmPassword);
    }

    @And("admin selects role {string}")
    public void adminSelectsRole(String role) {
        pages.getUserPage().selectRole(role);
    }

    @And("admin clicks Submit button")
    public void adminClicksSubmitButton() {
        pages.getUserPage().clickSubmit();
    }


    // ====== Validations ======
    @Then("admin should see success message {string}")
    public void adminShouldSeeSuccessMessage(String expectedMsg) {
        pages.getUserPage().validateSuccessMessage(expectedMsg);
    }

    // ====== Updated Error Handling ======
    @Then("admin should see error message {string}")
    public void adminShouldSeeErrorMessage(String expectedMsg) {

        switch (expectedMsg) {

            case "The password field must be at least 6 characters.":
                pages.getUserPage().validateShortPassword();
                break;

            case "The password field confirmation does not match.":
                pages.getUserPage().validateMismatchPassword();
                break;

            case "The email has already been taken.":
                pages.getUserPage().validateDuplicateEmail();
                break;

            case "The email field is required.":
                pages.getUserPage().validateRequiredEmail();
                break;

            case "The password field is required.":
                pages.getUserPage().validateRequiredPassword();
                break;

            case "The name field is required.":
                pages.getUserPage().validateRequiredName();
                break;

            default:
                // Browser HTML5 popups
                pages.getUserPage().ValidateErrorMessage(expectedMsg);
                break;
        }
    }

    // ====== BUG ASSERTION ======
    @Then("System display Error {string}")
    public void systemDisplayError(String expectedErrorMsg) {
        try {
            // Check if the expected error message is visible on the page
            boolean errorVisible = Driver.getDriver().findElements(By.xpath("//*[contains(text(),'" + expectedErrorMsg + "')]")).size() > 0;

            // If the error message is found, print it and pass the test
            if (errorVisible) {
                assertTrue("System displayed the expected error message.", true);

                // If the error message is NOT found, check if a success message appeared instead
            } else {
                boolean successVisible = Driver.getDriver().findElements(By.xpath("//*[contains(text(),'User created successfully')]")).size() > 0;

                // If a success message appeared instead of the expected error → it's a BUG
                if (successVisible) {
                    assertTrue("❌ BUG: System accepted invalid data instead of showing error → " + expectedErrorMsg, false);

                    // If neither success nor error message appeared → fail the test with missing feedback
                } else {
                    assertTrue("Test failed: Expected error message not found, and no success message appeared.", false);
                }}

            // Handle any unexpected exceptions during the message validation process
        } catch (Exception e) {
            assertTrue("Exception while checking for message: " + e.getMessage(), false);
        }}

    // ====== API Verification ======
    @Then("verify user exists in API")
    public void verifyUserExistsInAPI() {
        Response response = given(ApiUtilities.spec()).when().get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String actualName = json.getString("find{it.email=='" + userEmail + "'}.name");
        String actualEmail = json.getString("find{it.email=='" + userEmail + "'}.email");

        System.out.println("📡 Checking API for: " + userEmail + " Found User: " + actualName + " | " + actualEmail);
        assertEquals("Email mismatch in API verification", userEmail, actualEmail);
        assertEquals("Name mismatch in API verification", userName, actualName);
    }

    @Then("verify user with name {string} and email {string} does not exist in API")
    public void verifyUserDoesNotExistInAPI(String expectedName, String expectedEmail) {
        Response response = given(ApiUtilities.spec()).when().get("/users");

        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String foundUser = json.getString("find { it.email == '" + expectedEmail + "' && it.name == '" + expectedName + "' }");

        if (foundUser != null) {
            System.out.println("User still exists in API: " + foundUser);
        } else {
            System.out.println("Success -> User not found in API");
        }
        assertNull("User with same email and name should not exist!", foundUser);
    }}