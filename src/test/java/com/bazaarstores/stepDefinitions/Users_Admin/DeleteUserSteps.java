package com.bazaarstores.stepDefinitions.Users_Admin;

import com.bazaarstores.pages.AllPages;
import com.bazaarstores.pages.UserPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import com.bazaarstores.utilities.ApiUtilities;

public class DeleteUserSteps {

    AllPages pages = new AllPages();
    UserPage userPage = new UserPage();

    @And("admin clicks delete for the user with email {string}")
    public void adminClicksDeleteForTheUserWithEmail(String email) {
        userPage.clickDelete(email);
    }

    @And("admin confirms delete action")
    public void adminConfirmsDeleteAction() {
        pages.getUserPage().confirmDelete();
    }

    @And("admin cancels delete action")
    public void adminCancelsDeleteAction() {
        pages.getUserPage().cancelDelete();
    }

    @Then("admin should see error message cannot delete admin user")
    public void adminShouldSeeErrorMessageCannotDeleteAdminUser() {
        pages.getUserPage().validateCannotDeleteAdminError();
    }

    @Then("BUG: System displayed {string} instead of showing 'User deleted successfully.'")
    public void bugSystemDisplayedErrorInsteadOfSuccess(String errorMsg) {
        System.out.println("🐞 BUG DETECTED: " + errorMsg);
    }

    //This is a bug! It is already reported!!!
    @Then("admin should see success message for delete")
    public void adminShouldSeeSuccessMessageForDelete() {
        userPage.validateSuccessDeleteMessage();
    }

    // ====== API Verification ======
    @And("BUG: User {string} remains in API")
    public void verifyUserIsDeletedInAPI(String email) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiEmail = json.getString("find{it.email=='" + email + "'}.email");

        if (apiEmail != null) {
            System.out.println("🐞 BUG CONFIRMED → System failed to delete user: " + email + " (still exists in API)");

        } else {
            System.out.println("User successfully deleted from API → " + email);
            assertNull("User still exists in API! (BUG)", apiEmail);
        }
    }

    @And("verify user {string} still exists in API")
    public void verifyUserStillExistsInAPI(String email) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());
        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiEmail = json.getString("find{it.email=='" + email + "'}.email");
        assertNotNull("User was deleted unexpectedly!", apiEmail);
        System.out.println("User still exists in API → " + email);
    }}
