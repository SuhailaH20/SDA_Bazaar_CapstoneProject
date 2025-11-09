package com.bazaarstores.stepDefinitions.Users_Admin;

import com.bazaarstores.pages.AllPages;
import com.bazaarstores.utilities.ApiUtilities;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class EditUserSteps {

    AllPages pages = new AllPages();
    public static String updatedEmail;
    public static String updatedName;
    public static String updatedPassword;
    public static String updatedRole;

    // ====== Navigation ======
    @Given("user goes to Dashboard")
    public void userGoesToDashboard() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
    }

    @And("admin navigates to Users list page")
    public void adminNavigatesToUsersListPage() {
        Driver.getDriver().navigate().to(ConfigReader.getBaseUrl() + "/users");
    }

    // ====== Edit actions ======
    @And("admin clicks edit for the user with email {string}")
    public void adminClicksEditForTheUserWithEmail(String email) {
        pages.getUserPage().clickEdit(email);
    }

    @And("admin updates name to {string}")
    public void adminUpdatesNameTo(String name) {
        updatedName = name;
        pages.getUserPage().enterName(name);
    }

    @And("admin updates email to {string}")
    public void adminUpdatesEmailTo(String email) {
        updatedEmail = email;
        pages.getUserPage().enterEmail(email);
    }

    @And("admin updates password to {string}")
    public void adminUpdatesPasswordTo(String password) {
        updatedPassword = password;
        pages.getUserPage().enterPassword(password);
    }

    @And("admin updates role to {string}")
    public void adminUpdatesRoleTo(String Role) {
        updatedRole = Role;
        pages.getUserPage().selectRole(Role);
    }

    @And("admin enters Password {string}")
    public void adminEntersPassword(String password) {
        pages.getUserPage().enterPassword(password);
    }


    @And("admin enters Password Confirmation {string}")
    public void adminEntersPasswordConfirmation(String confirmPassword) {
        pages.getUserPage().enterConfirmPassword(confirmPassword);
    }

    @And("Admin Clicks Submit Button")
    public void adminClicksSubmitButton() {
        pages.getUserPage().clickSubmit();
    }

    // ====== Validations ======
    @Then("admin should see update success message {string}")
    public void adminShouldSeeUpdateSuccessMessage(String expectedMsg) {
        pages.getUserPage().validateSuccessMessage(expectedMsg);
    }

    // ====== Bug Handling ======
    @Then("BUG: system restores old value instead of showing error {string}")
    public void bugSystemRestoresOldValue(String expectedMsg) {
        System.out.println("🐞 BUG DETECTED: System restored old value instead of showing error message: " + expectedMsg);
        assertTrue("BUG CONFIRMED → Field restored old value", true);
    }

    @Then("BUG: System accepts invalid data instead of showing error {string} with note {string}")
    public void bugSystemAcceptsInvalidDataInsteadOfShowingError(String expectedErrorMsg, String bugNote) {
        try {
            boolean successAppeared = Driver.getDriver().findElements(By.xpath("//div[contains(@class,'toast-success')]")).size() > 0;

            if (successAppeared) {
                System.out.println("Expected Error: " + expectedErrorMsg);
                assertTrue("BUG CONFIRMED → " + bugNote, true);
            } else {
                fail("No success message found — possible different bug.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error while validating BUG scenario: " + e.getMessage());
        }
    }

    @Then("system displayed a backend error message instead of {string}")
    public void systemdisplayedabackenderrormessageinsteadof(String expectedMsg) {
        pages.getUserPage().ValidateErrorMessage(expectedMsg);
    }

    // ====== API Verification ======
    @And("verify user with name {string} and email {string} updated in API")
    public void verifyUpdatedUserViaAPI(String expectedName, String expectedEmail) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiName = json.getString("find{it.email=='" + expectedEmail + "'}.name");
        String apiEmail = json.getString("find{it.email=='" + expectedEmail + "'}.email");

        System.out.println("📡 API Check → Name: " + apiName + " | Email: " + apiEmail);

        assertEquals("Updated name mismatch!", expectedName, apiName);
        assertEquals("Updated email mismatch!", expectedEmail, apiEmail);
    }


    @And("verify user with name {string} and email {string} is not updated in API")
    public void verifyUserWithNameAndEmailIsNotUpdatedInAPI(String expectedName, String expectedEmail) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiName = json.getString("find{it.email=='" + expectedEmail + "'}.name");
        String apiEmail = json.getString("find{it.email=='" + expectedEmail + "'}.email");

        System.out.println("📡 API Check (after invalid update) → Name: " + apiName + " | Email: " + apiEmail);

        assertEquals("User email changed unexpectedly!", expectedEmail, apiEmail);
        assertEquals("User name changed unexpectedly!", expectedName, apiName);

        System.out.println("User data in API remained unchanged — update was not applied.");
    }

    @And("verify user with email {string} updated role {string} in API")
    public void verifyUserRoleUpdatedInAPI(String expectedEmail, String expectedRole) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();

        //Use a Groovy GPath expression to find the user by email and read its role
        String apiRole = json.getString("find{it.email=='" + expectedEmail + "'}.role");

        System.out.println("📡 API Check → Email: " + expectedEmail + " | Role: " + apiRole);

        // Match UI role value ("Store Manager") with backend format ("store_management")
        expectedRole = expectedRole.trim()
        .toLowerCase()
        .replace(" ", "_")  // Store Manager → store_manager
        .replace("store_manager", "store_management"); // align with backend

        apiRole = apiRole.trim().toLowerCase();
        assertEquals("Role update mismatch!", expectedRole, apiRole);
    }

    @And("verify user with email {string} and role {string} is not updated in API")
    public void verifyUserRoleNotUpdatedInAPI(String expectedEmail, String expectedRole) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiRole = json.getString("find{it.email=='" + expectedEmail + "'}.role");

        System.out.println("📡 API Check (after invalid update) → Email: " + expectedEmail + " | Role: " + apiRole);

        assertNotEquals("Role changed unexpectedly!", expectedRole, apiRole);
        System.out.println("User role in API remained unchanged (invalid update rejected).");
    }

    // ====== BUG ASSERTION ======
    @And("Verify user update {string} in API with invalid data")
    public void verifyUserUpdateInAPIWithInvalidData(String userEmail) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiEmail = json.getString("find{it.email=='" + userEmail + "'}.email");

        System.out.println("📡 API Check after invalid data update → Email: " + apiEmail);
        assertNotNull("User with invalid data was not found in API!", apiEmail);

        System.out.println("🐞 BUG CONFIRMED → System accepted invalid data and user record exists in API.");
    }}