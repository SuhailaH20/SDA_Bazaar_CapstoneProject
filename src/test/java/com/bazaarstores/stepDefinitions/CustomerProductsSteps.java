package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.*;
import com.bazaarstores.utilities.*;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.List;
import java.util.Objects;

public class CustomerProductsSteps {

    LoginPage loginPage = new LoginPage();
    CustomerProductsPage productPage = new CustomerProductsPage();
    FavoritesPage favoritesPage = new FavoritesPage();

    // ---------- Background ----------
    @Given("User is logged in and on the customer product page")
    public void user_is_logged_in_and_on_customer_product_page() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
        loginPage.loginAsCustomer(ConfigReader.getCustomerEmail(), ConfigReader.getDefaultPassword());
        Assert.assertTrue("Login failed!", Objects.requireNonNull(Driver.getDriver().getCurrentUrl()).contains("/customer"));
    }

    // ---------- US04 ----------
    @Then("All products should display name, price, and image")
    public void all_products_should_display_name_price_and_image() {
        Assert.assertFalse("Names missing!", productPage.getAllProductNames().isEmpty());
        Assert.assertFalse("Prices missing!", productPage.getAllProductPrices().isEmpty());
        Assert.assertFalse("Images missing!", productPage.getAllProductImages().isEmpty());
    }

    @Then("Product list should load within {int} seconds")
    public void product_list_should_load_within_seconds(Integer seconds) {
        long start = System.currentTimeMillis();
        productPage.getAllProductNames();
        long loadTime = System.currentTimeMillis() - start;
        Assert.assertTrue("Page load took too long", loadTime < seconds * 1000);
    }

    @Then("Each product may or may not have a description, but description field should be visible")
    public void each_product_may_or_may_not_have_description_but_field_visible() {
        List<String> names = productPage.getAllProductNames();
        List<String> descs = productPage.getAllProductDescriptions();

        Assert.assertEquals("Mismatch between product names and descriptions count", names.size(), descs.size());

        for (int i = 0; i < names.size(); i++) {
            String productName = names.get(i);
            String description = descs.get(i);
            Assert.assertNotNull("Description element missing for product: " + productName, description);
            System.out.println("Product: " + productName + " | Description: " + (description.isEmpty() ? "[No description]" : description));
        }
    }

    // ---------- US07 ----------
    @When("User clicks heart icon for product {string}")
    public void user_clicks_heart_icon_for_product(String productName) {
        productPage.clickHeartIconForProduct(productName);
    }

    @When("User clicks heart icon for product {string} again")
    public void user_clicks_heart_icon_for_product_again(String productName) {
        productPage.clickHeartIconForProduct(productName);
    }

    @Then("Product {string} should appear in favorites list")
    public void product_should_appear_in_favorites_list(String productName) {
        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");
        boolean isInFavorites = favoritesPage.isProductInFavorites(productName);
        Assert.assertTrue("Product not in favorites", isInFavorites);
    }

    @Then("Error message should be displayed {string}")
    public void error_message_should_be_displayed(String message) {
        Assert.assertTrue("Error message not shown", productPage.isFavoriteErrorMessageDisplayed(message));
    }

    @Given("Product {string} is in favorites")
    public void product_is_in_favorites(String productName) {
        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");
        if (!favoritesPage.isProductInFavorites(productName)) {
            Driver.getDriver().get(ConfigReader.getBaseUrl() + "/customer");
            productPage.clickHeartIconForProduct(productName);
        }
    }

    @When("User removes product {string} from favorites")
    public void user_removes_product_from_favorites(String productName) {
        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");
        favoritesPage.removeProduct(productName);
    }

    @Then("Product {string} should not be in favorites list")
    public void product_should_not_be_in_favorites_list(String productName) {
        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");
        Assert.assertFalse("Product still in favorites", favoritesPage.isProductInFavorites(productName));
    }
}
