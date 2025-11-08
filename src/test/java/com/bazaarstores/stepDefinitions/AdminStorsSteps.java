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

    @And("Admin enter store name {string}")
    public void adminEnterStoreName(String storeName) {
     AdminStorsSteps.storeName = Faker.instance().company().name();
       pages
               .getAddStoreAsAdminPage()
               .enterStoreName(AdminStorsSteps.storeName);
    }

    @And("Admin enter store location {string}")
    public void adminEnterStoreLocation(String storeLocation) {
       AdminStorsSteps.storeLocation = Faker.instance().address().fullAddress();
        pages
                .getAddStoreAsAdminPage()
                .enterStoreLocation( AdminStorsSteps.storeLocation);

    }

    @And("Admin enter store description {string}")
    public void adminEnterStoreDescription(String description) {
        AdminStorsSteps.description = Faker.instance().lorem().paragraph();
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
                .getAddStoreAsAdminPage()
                .isSuccessMessageDisplayed()
                .waitForElementToDisappear(pages.getAddStoreAsAdminPage().getSuccessMessageLocator());


    }

    @And("Verify the store addition successfully via API with store name {string}")
    public void assertTheStoreAdditionViaAPIWithStoreName(String storeName) {
     Response response = given(spec()).get("/stores");
        JsonPath jsonPath = response.jsonPath();
        String actualName = jsonPath.getString("find{it.name=='" + AdminStorsSteps.storeName + "'}.name");
        System.out.println("actualName = " + actualName);
        assertEquals(AdminStorsSteps.storeName, actualName);

    }


    @And("Admin Full Add Store Form with {string}, {string}, {string}, {string}")
    public void adminFullAddStoreFormWith(String storeName,  String  location, String AdminName,String description) {
        AdminStorsSteps.storeName = Faker.instance().company().name();
        AdminStorsSteps.description = Faker.instance().lorem().paragraph();
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
    public void adminEnterStoreDescriptionWithThanCharacters(String arg0, int arg1) {
       if (arg0.equals("length less")) {
           AdminStorsSteps.description = Faker.instance().lorem().characters(240);
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
       }
    }

    @Then("Admin should see error message for description length store")
    public void adminShouldSeeErrorMessageForDescriptionLengthStore() {
        pages
                .getAddStoreAsAdminPage()
                .isErrorMessageLongDescriptionDisplayed();
    }

    @And("Create a store via API with name {string}")
    public void createAStoreViaAPIWithName(String storeName) {
        AdminStorsSteps.storeName=storeName;
        String payload = """
                {
                    "name": "Store Edit",
                    "description": "Store description Edit",
                    "location": "Store location Edit",
                    "admin_id": 1
                }
                """;

        Response response = given(spec())
                .body(payload)
                .post("/stores/create");

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
      createdStoreNameAPI = jsonPath.getString("product.name");
        assertEquals("Store Edit", createdStoreNameAPI);




    }
}
