package com.bazaarstores.stepDefinitions.Users_Admin;

import com.bazaarstores.pages.AllPages;
import com.bazaarstores.pages.UserPage;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import com.bazaarstores.utilities.ApiUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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


    @Then("Admin should see success message {string}") //
    public void adminShouldSeeSuccessMessageForDelete(String expectedMsg) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
            WebElement toastMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']//div[contains(@class,'toast-message')]")));

            String actualText = toastMsg.getText().trim();

            //Check if the actual toast message matches the expected success message
            if (actualText.equalsIgnoreCase(expectedMsg)) {
                assertTrue("System displayed success message.", true);

                // If the message indicates that admin users cannot be deleted → mark it as a BUG
            } else if (actualText.contains("You cant delete a admin role users!")) {
                assertTrue("❌ BUG: System showed error instead of success → " + actualText, false);

            }
            //Handle any unexpected exceptions while checking the toast message
        } catch (Exception e) {
            assertTrue("Exception while checking delete message: " + e.getMessage(), false);
        }}


    // ====== API Verification ======
    @And("verify user {string} not exists in API")
    public void verifyUserIsDeletedInAPI(String email) {
        Response response = given(ApiUtilities.spec()).get("/users");
        assertEquals("Unexpected status code!", 200, response.statusCode());

        System.out.println("---------------------------------API Response------------------------------------");
        response.prettyPrint();
        System.out.println("---------------------------------------------------------------------------------");

        JsonPath json = response.jsonPath();
        String apiEmail = json.getString("find{it.email=='" + email + "'}.email");

        if (apiEmail != null) {
            assertTrue("❌ BUG: User still exists in API after deletion → " + email, false); // use false to fail the test if it gets here

        } else {
            System.out.println("User successfully deleted from API → " + email);
            assertTrue("User deleted successfully from API", true);
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
        System.out.println("Success -> User still exists in API → " + email);


        // ++++++++++ Delete user from API (Cleanup only)
        String userId = json.getString("find{it.email=='" + email + "'}.id");

        if (userId != null) {
            Response deleteResponse = given(ApiUtilities.spec())
                    .delete("/users/" + userId);

            if (deleteResponse.statusCode() == 200) {
                System.out.println("Cleanup: User deleted successfully from API → " + email);
            } else {
                System.out.println("Cleanup skipped → User not deletable in API (Status: " + deleteResponse.statusCode() + ")");
            }
        }
    }

    @And("delete and verify user with email {string} from API")
    public void deleteAndVerifyUserFromAPI(String email) {

        System.out.println("🛰 Starting cleanup for user → " + email);

        // GET all users
        Response getResponse = given(ApiUtilities.spec()).get("/users");
        assertEquals("Failed to fetch users list!", 200, getResponse.getStatusCode());

        JsonPath json = getResponse.jsonPath();
        Integer userId = json.getInt("find{ it.email == '" + email + "' }.id");

        if (userId == null) {
            System.out.println("User already deleted → " + email);
            return;
        }

        // DELETE request
        Response deleteResponse = given(ApiUtilities.spec())
        .delete("/users/" + userId);

        int status = deleteResponse.getStatusCode();
        System.out.println("DELETE status = " + status);
        assertTrue("❌ Unexpected DELETE status code: " + status, status == 200 || status == 204);

        // verify deletion
        Response verifyRes = given(ApiUtilities.spec()).get("/users");
        JsonPath verifyJson = verifyRes.jsonPath();

        String deletedEmail = verifyJson.getString("find{ it.email == '" + email + "' }.email");

        if (deletedEmail == null) {
            System.out.println("Cleanup success → deleted " + email);
        } else {
            fail("❌ Cleanup failed → User still exists in API: " + email);
        }
    }}
