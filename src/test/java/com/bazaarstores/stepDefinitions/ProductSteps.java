package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.*;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.junit.Assert;


public class ProductSteps {

    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    ProductPage productPage = new ProductPage();
    ProductCreatePage productCreate = new ProductCreatePage();

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
    @Then("the catalog table should be displayed with all products")
    public void the_catalog_table_should_be_displayed_with_all_products() {
        productPage.refreshPage();
        Assert.assertTrue("Product table is not visible!", productPage.isTableVisible());

        int productCount = productPage.getProductCount();
        Assert.assertTrue("No products found in the catalog table!", productCount >= 5);

        System.out.println("Catalog table is visible and contains " + productCount + " products.");
        productPage.getProductNamesFromUI();
    }

    // ADD PRODUCT
    @And("Store Manager clicks on ADD PRODUCT button")
    public void storeManagerClicksOnAddProductButton() {
        productPage.clickAddProduct();
    }

    @And("Store Manager fills in product details with Name {string}, Price {string}, Stock {string}, and SKU {string}")
    public void storeManagerFillsInProductDetailsWithNamePriceStockAndSKU(String name, String price, String stock, String sku) {
        productCreate
                .enterName(name)
                .enterPrice(price)
                .enterStock(stock)
                .enterSKU(sku);
    }

    @And("Store Manager clicks the Submit button")
    public void storeManagerClicksTheSubmitButton() {
        productCreate.clickSubmit();
    }

    @Then("a success message {string} should be displayed")
    public void aSuccessMessageShouldBeDisplayed(String message) {
        productPage.verifySuccessMessage(message);
    }

    @Then("an error message {string} should be displayed")
    public void anErrorMessageShouldBeDisplayed(String message) {
        productCreate.verifyErrorMessage(message);
    }

    @Given("a product with SKU {string} already exists")
    public void aProductWithSKUAlreadyExists(String sku) {
        productPage.ensureSkuExists(sku);
        Assert.assertTrue("SKU not created in table: " + sku,
                productPage.isSkuPresentInTable(sku));
    }



    // EDIT PRODUCT
    @When("Store Manager clicks on the Edit button for product with Name {string}")
    public void storeManagerClicksOnTheEditButtonForProductWithName(String productName) {
        productPage.clickEditButton(productName);
    }

    @And("Store Manager updates the product with Name {string}, Price {string}, Stock {string}")
    public void storeManagerUpdatesTheProductWithNamePriceStock(String name, String price, String stock) {
        productCreate
                .enterName(name)
                .enterPrice(price)
                .enterStock(stock);
    }

    // DELETE PRODUCT
    @And("a product with Name {string} already exists")
    public void aProductWithNameAlreadyExists(String productName) {
        Assert.assertTrue("productName not created in table: " + productName,
                productPage.isSkuPresentInTable(productName));
    }

    @When("Store Manager clicks the Delete button for product with Name {string}")
    public void storeManagerClicksTheButtonForProductWithName(String productName) {
        productPage.clickDeleteButton(productName);
    }

    @And("Store Manager confirms the deletion")
    public void storeManagerConfirmsTheDeletion() {
        productPage.confirmDelete();
    }

    @Then("the product with Name {string} should no longer be visible in the product list")
    public void theProductWithNameShouldNoLongerBeVisibleInTheProductList(String productName) {
        Assert.assertFalse("Product still visible after deletion: " + productName,
                productPage.isProductVisible("productName"));
    }

    @And("Store Manager cancels the deletion")
    public void storeManagerCancelsTheDeletion() {
        productPage.cancelDelete();
    }

    @Then("the product with Name {string} should still be visible in the product list")
    public void theProductWithNameShouldStillBeVisibleInTheProductList(String productName) {
        Assert.assertTrue("Product not found: " + productName,
                productPage.isProductVisible("productName"));
    }

}
