package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.*;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class ProductSteps {

    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    ProductPage productPage = new ProductPage();
    ProductCreatePage productCreate = new ProductCreatePage();
    ProductEditPage productEdit = new ProductEditPage();

    // Background
    @Given("Store Manager is on the Login page")
    public void store_manager_is_on_the_login_page() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
    }

    @When("Store Manager enters valid credentials and clicks login")
    public void store_manager_enters_valid_credentials_and_clicks_login() {
        loginPage.login(ConfigReader.getStoreManagerEmail(), ConfigReader.getDefaultPassword());
    }

    @When("Store Manager clicks on Products button in the menu")
    public void store_manager_clicks_on_products_button_in_the_menu() {
        dashboardPage.clickProductsLink();
    }

    // ADD PRODUCT
    @And("Store Manager clicks on ADD PRODUCT button")
    public void storeManagerClicksOnAddProductButton() {
        productPage.clickAddProduct();
    }

    @And("Store Manager fills in product details with Name {string}, Price {string}, Stock {string}, and SKU {string}")
    public void storeManagerFillsInProductDetailsWithNamePriceStockAndSKU(String name, String price, String stock, String sku) {
        productCreate.enterName(name).enterPrice(price).enterStock(stock).enterSKU(sku);
    }

    @And("Store Manager clicks the Submit button")
    public void storeManagerClicksTheSubmitButton() {
        productCreate.clickSubmit();
    }

    @Then("a success message {string} should be displayed")
    public void aSuccessMessageShouldBeDisplayed(String expectedMessage) {
        boolean successVisible = productPage.isMessageDisplayed(expectedMessage);
        Assert.assertTrue("Expected success message not displayed: " + expectedMessage, successVisible);
        System.out.println("Success message displayed: " + expectedMessage);
    }

    @Then("the product with SKU {string} should exist in the API")
    public void theProductWithSKUShouldExistInTheAPI(String sku) {
        boolean exists = productCreate.isProductInApi(sku);
        Assert.assertTrue("Product not found in API with SKU: " + sku, exists);
        System.out.println("Product verified in API with SKU: " + sku);
    }

    @Then("the product with SKU {string} should not exist in the API")
    public void theProductWithSKUShouldNotExistInTheAPI(String sku) {
        boolean exists = productCreate.isProductInApi(sku);
        Assert.assertFalse("Product should not exist in API with SKU: " + sku, exists);
        System.out.println("Product correctly not found in API: " + sku);
    }

    // EDIT PRODUCT
    @When("Store Manager clicks on the Edit button for product with Name {string}")
    public void storeManagerClicksOnTheEditButtonForProductWithName(String productName) {
        productPage.clickEditButton(productName);
    }

    @And("Store Manager updates the product with Name {string}, Price {string}, and Stock {string}")
    public void storeManagerUpdatesTheProductWithNamePriceAndStock(String name, String price, String stock) {
        productEdit.enterName(name).enterPrice(price).enterStock(stock).clickSubmit();
    }

    @Then("the product with Name {string} should display update success message {string}")
    public void theProductWithNameShouldDisplayUpdateSuccessMessage(String name, String expectedMessage) {
        boolean messageVisible = productPage.isMessageDisplayed(expectedMessage);
        Assert.assertTrue("Update message not visible for product: " + name, messageVisible);
        productPage.verifyProductVisible(name);
        System.out.println("Update success message verified for: " + name);
    }

    @Then("the product with Name {string} should be updated in the catalog")
    public void theProductWithNameShouldBeUpdatedInTheCatalog(String name) {
        boolean visible = productPage.isProductVisible(name);
        Assert.assertTrue("Updated product not found in table: " + name, visible);
        System.out.println("Updated product is visible in the catalog: " + name);
    }

    @Then("the product with SKU {string} should be updated in the API")
    public void theProductWithSKUShouldBeUpdatedInTheAPI(String sku) {
        boolean updated = productCreate.isProductInApi(sku);
        Assert.assertTrue("Updated product not reflected in API for SKU: " + sku, updated);
        System.out.println("Product update verified in API for SKU: " + sku);
    }

    // DELETE PRODUCT
    @When("Store Manager clicks on the Delete button for product with Name {string}")
    public void storeManagerClicksOnTheDeleteButtonForProductWithName(String name) {
        productPage.clickDeleteButton(name);
    }

    @And("Store Manager confirms the deletion")
    public void storeManagerConfirmsTheDeletion() {
        productPage.confirmDelete();
    }

    @And("Store Manager cancels the deletion")
    public void storeManagerCancelsTheDeletion() {
        productPage.cancelDelete();
    }

    @Then("a deletion success message {string} should be displayed")
    public void aDeletionSuccessMessageShouldBeDisplayed(String expectedMessage) {
        boolean deletedMsg = productPage.isMessageDisplayed(expectedMessage);
        Assert.assertTrue("Expected delete success message not visible: " + expectedMessage, deletedMsg);
        System.out.println("Deletion success message displayed: " + expectedMessage);
    }

    @Then("the product with Name {string} should not be visible in the catalog")
    public void theProductWithNameShouldNotBeVisibleInTheCatalog(String name) {
        boolean visible = productPage.isProductVisible(name);
        Assert.assertFalse("Product still visible after deletion: " + name, visible);
        System.out.println("Product successfully deleted: " + name);
    }

    @Then("the product with SKU {string} should be deleted from the API")
    public void theProductWithSKUShouldBeDeletedFromTheAPI(String sku) {
        boolean exists = productCreate.isProductInApi(sku);
        Assert.assertFalse("Product still exists in API after deletion: " + sku, exists);
        System.out.println("Product deleted successfully in API for SKU: " + sku);
    }

    @Then("the product with Name {string} should still be visible in the catalog")
    public void theProductWithNameShouldStillBeVisibleInTheCatalog(String name) {
        boolean visible = productPage.isProductVisible(name);
        Assert.assertTrue("Product not visible after canceling deletion: " + name, visible);
        System.out.println("Product remains visible after deletion was canceled: " + name);
    }
}
