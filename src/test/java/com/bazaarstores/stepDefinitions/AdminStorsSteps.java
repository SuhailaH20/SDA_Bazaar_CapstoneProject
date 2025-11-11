package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.AllPages;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static com.bazaarstores.utilities.ApiUtilities.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AdminStorsSteps {

    AllPages pages = new AllPages();
    public static String storeName;
    public static String storeLocation;
    public static String description;
    public static String createdStoreNameAPI;

    @When("Admin views the sidebar menu")
    public void admin_views_the_sidebar_menu() {
        pages
                .getDashboardPage()
                .isMenuDisplayed();}

    @Then("Admin should see Store option")
    public void admin_should_see_store_option() {
        pages
                .getDashboardPage()
                .isStoreOptionDisplayed();}

    @Then("the Store option should be clickable")
    public void the_store_option_should_be_clickable() {
        pages
                .getDashboardPage()
                .isStoreOptionEnabled();}

    @When("Admin clicks on Store Link")
    public void adminClicksOnStoreLink() {
        pages
                .getDashboardPage()
                .clickStoreMenuOption();}

    @Then("Admin should be navigated to Store page")
    public void adminShouldBeNavigatedToStorePage() {
        pages
                .getStorsPage()
                .isPageTitleDisplayed();}

    @And("Table should display at least one store entry")
    public void tableShouldDisplayAtLeastOneStoreEntry() {
             pages
                .getStorsPage()
                .verifyStoresTableNotEmpty();}

    @And("Table should display information: NAME, DESCRIPTION, ADMIN NAME,ACTIONS")
    public void tableShouldDisplayInformationNAMEDESCRIPTIONADMINNAMEACTIONS() {
        pages
        .getStorsPage()
        .verifyStoreTableHeaders();}


    @When("Admin clicks on Add store button")
    public void adminClicksOnAddStoreButton() {
        pages
                .getStorsPage()
                .clickAddStoreButton();
    }

    @And("Admin enter store name")
    public void adminEnterStoreName() {
     AdminStorsSteps.storeName = Faker.instance().company().name();
       pages
               .getAddStoreAsAdminPage()
               .enterStoreName(AdminStorsSteps.storeName);
    }

    @And("Admin enter store location")
    public void adminEnterStoreLocation() {
       AdminStorsSteps.storeLocation = Faker.instance().address().fullAddress();
        pages
                .getAddStoreAsAdminPage()
                .enterStoreLocation( AdminStorsSteps.storeLocation);

    }

    @And("Admin enter store description")
    public void adminEnterStoreDescription() {
        AdminStorsSteps.description = Faker.instance().lorem().sentence();
        pages
                .getAddStoreAsAdminPage()
                .enterStoreDescription(AdminStorsSteps.description);

    }

    @And("Admin select store admin{string}")
    public void adminSelectStoreAdmin(String AdminName) {

        pages
                .getAddStoreAsAdminPage()
                .selectAdminByName(AdminName);
    }


    @And("Admin clicks on Submit button")
    public void adminClicksOnSubmitButton() {
        pages
                .getAddStoreAsAdminPage()
                .ClickSubmitButton();
    }

    @Then("Admin should see success message for adding store")
    public void adminShouldSeeSuccessMessageForAddingStore(){
        pages
                .getStorsPage()
                .isSuccessMessageDisplayed();


    }

    @And("Verify the store addition successfully via API with store name")
    public void assertTheStoreAdditionViaAPIWithStoreName() {
     Response response = given(spec()).get("/stores");
        JsonPath jsonPath = response.jsonPath();
        String actualName = jsonPath.getString("find{it.name=='" + AdminStorsSteps.storeName + "'}.name");
        assertEquals(AdminStorsSteps.storeName, actualName);
        //after verify delet store
      pages
              .getStorsPage()
              .clickdeleteButtonForStore(AdminStorsSteps.storeName)
              .clickConfirmDeletStoreButton();

    }


    @And("Admin Full Add Store Form with {string}, {string}, {string}, {string}")
    public void adminFullAddStoreFormWith(String storeName,  String  location, String AdminName,String description) throws InterruptedException {
        AdminStorsSteps.storeName = Faker.instance().company().name();
        AdminStorsSteps.description = Faker.instance().lorem().sentence();
        AdminStorsSteps.storeLocation = Faker.instance().address().fullAddress();

        pages
                .getAddStoreAsAdminPage()
                .fillAddStoreForm(AdminStorsSteps.storeName,AdminStorsSteps.storeLocation,AdminStorsSteps.description,AdminName);


    }

    @Then("Admin should see Error message for {string} field")
    public void adminShouldSeeErrorMessageForField(String fildName) {
        if(fildName.equals("name")){
            pages
                    .getAddStoreAsAdminPage()
                    .isErrorMessageNameDisplayed();
        } else if (fildName.equals("description")) {
            pages
                    .getAddStoreAsAdminPage()
                    .isErrorMessageDescriptionDisplayed();
        } else if (fildName.equals("location")) {
            pages
                    .getAddStoreAsAdminPage()
                    .isErrorMessageLocationDisplayed();
        } else if (fildName.equals("admin")) {
            pages
                    .getAddStoreAsAdminPage()
                    .isErrorMessageAdminNameDisplayed();
        }else if (fildName.equals("All field")){
            pages
                    .getAddStoreAsAdminPage()
                    .isErrorMessageNameDisplayed()
                    .isErrorMessageLocationDisplayed()
                    .isErrorMessageDescriptionDisplayed()
                    .isErrorMessageAdminNameDisplayed();
        } else {
            throw new IllegalArgumentException("Invalid field name: " + fildName);
        }

    }


    @And("Admin enter store description with length less than {int} characters")
    public void adminEnterStoreDescriptionWithLengthLessThanCharacters(int numbercharacters) {
        AdminStorsSteps.description = Faker.instance().lorem().characters(230);
        pages
                .getAddStoreAsAdminPage()
                .enterStoreDescription(AdminStorsSteps.description);
    }


    @And("Admin enter store description with {string} than {int} characters")
    public void adminEnterStoreDescriptionWithThanCharacters(String arg0, int num) {
       if (arg0.equals("length less")) {
           AdminStorsSteps.description = Faker.instance().lorem().characters(253);
           pages
                   .getAddStoreAsAdminPage()
                   .enterStoreDescription(AdminStorsSteps.description);
       } else if (arg0.equals("length more")) {
           AdminStorsSteps.description = Faker.instance().lorem().characters(260);
           pages
                   .getAddStoreAsAdminPage()
                   .enterStoreDescription(AdminStorsSteps.description);
       } else if (arg0.equals("length equal")) {

              AdminStorsSteps.description = Faker.instance().lorem().characters(255);
                pages
                     .getAddStoreAsAdminPage()
                     .enterStoreDescription(AdminStorsSteps.description);
       }else if (arg0.equals("Formatted")){
           AdminStorsSteps.description = Faker.instance().lorem().characters(254);
           pages
                   .getAddStoreAsAdminPage()
                   .FormatText()
                   .enterStoreDescription(AdminStorsSteps.description);

       }
    }

    @Then("Admin should see error message for description length store")
    public void adminShouldSeeErrorMessageForDescriptionLengthStore() {
        pages
                .getAddStoreAsAdminPage()
                .isErrorMessageLongDescriptionDisplayed();
    }


    @When("Admin click on Edit button for store {string}")
    public void adminClickOnEditButtonForStore(String storeName)  {

        pages
                .getStorsPage()
                .clickEditButtonForStore(storeName);



    }

    @Then("Admin should see success message for update store")
    public void adminShouldSeeSuccessMessageForUpdateStore() {

        pages
                .getStorsPage()
                .isSuccessUpdatMessageDisplayed();


    }

    @And("Admin Leave the {string} field empty")
    public void adminLeaveTheFieldEmpty(String fildName) {
        if (fildName.equals("name")) {
            pages
                    .getAddStoreAsAdminPage()
                    .clearNamefild();
        } else if (fildName.equals("description")) {
            pages
                    .getAddStoreAsAdminPage()
                    .clearDescriptionfild();
        } else if (fildName.equals("location")) {
            pages
                    .getAddStoreAsAdminPage()
                    .clearlocatinfild();
        }else if (fildName.equals("admin")) {
            pages
                    .getAddStoreAsAdminPage()
                    .deselectAdmin();
        } else if (fildName.equals("All field")){
            pages
                    .getAddStoreAsAdminPage()
                    .clearNamefild()
                    .clearlocatinfild()
                    .deselectAdmin()
                    .clearDescriptionfild();


        }else {
            throw new IllegalArgumentException("❌ Invalid field: " + fildName);

    }}


    @And("Verify the store update successfully via API {string}")
    public void verifyTheStoreUpdateSuccessfullyViaAPI(String feildchanged) {

        if(feildchanged.equals("Name")){
            Response response = given(spec()).get("/stores");
            JsonPath jsonPath = response.jsonPath();
            String actualName = jsonPath.getString("find{it.name=='" + AdminStorsSteps.storeName + "'}.name");
            assertEquals(AdminStorsSteps.storeName, actualName);

        } else if (feildchanged.equals("Location")){

            Response response = given(spec()).get("/stores");
            JsonPath jsonPath = response.jsonPath();
            String actuallocation = jsonPath.getString("find{it.name=='" + AdminStorsSteps.storeName + "'}.location");
            assertEquals(AdminStorsSteps.storeLocation, actuallocation);

        }else if (feildchanged.equals("description")){
            Response response = given(spec()).get("/stores");
            JsonPath jsonPath = response.jsonPath();
            String actualdescription = jsonPath.getString("find{it.name=='" + AdminStorsSteps.storeName + "'}.description");
            String actualText = actualdescription.replaceAll("<.*?>", "").replaceAll("&nbsp;", " ").trim();
            assertEquals(AdminStorsSteps.description,actualText);


    }
}


    @When("Admin Click on Delete Button for {string}")
    public void adminClickOnDeleteButtonFor(String storeName) {
        pages
                .getStorsPage()
                .clickdeleteButtonForStore(storeName);

    }

    @Then("Verify the Confirmation Dialog appers")
    public void verifyTheConfirmationDialogAppers() {
        pages
                .getStorsPage()
                .isConfirmationDialogDisplayed();
    }

    @And("Admin Clicks on Confirm Delete Button")
    public void adminClicksOnConfirmDeleteButton() {
       pages
               .getStorsPage()
               .clickConfirmDeletStoreButton();
    }

    @Then("Admin should see success message for delete store")
    public void verifyStoreDisappearFromTable() {
        pages
                .getStorsPage()
                .isSuccessDeleteMessageDisplayed();

    }

    @And("Verify the store deleted successfully via API")
    public void verifyTheStoreDeletedSuccessfullyViaAPI() {
        pages
                .getStorsPage()
                .VirifyStoreDletedeViaAPI();

    }

    @And("Admin Click on Cancel Button")
    public void adminClickOnCancelButton() {
        pages
                .getStorsPage()
                .clickCancelButton();
    }

    @Then("Verify store in the table")
    public void verifyStoreInTheTable() {
       pages
               .getStorsPage()
               .VerifyStoreInTheTable(storeName);
    }

    @And("Admin Click on Backdrop")
    public void adminClickOnBackdrop() {
   pages
           .getStorsPage()
           .clickbackdrop();


    }

    @And("Admin Full Add Store Form with {string}, {string}, {int}, {string} to edit")
    public void adminFullAddStoreFormWithToEdit(String storeName, String storeLocation, int admin, String description) {
        AdminStorsSteps.storeName =storeName;
        AdminStorsSteps.storeLocation = storeLocation;
        AdminStorsSteps.description = description;
        pages
               .getAddStoreAsAdminPage()
               .fillStoreFormToEdit(storeName,storeLocation,description,admin);
    }

}
