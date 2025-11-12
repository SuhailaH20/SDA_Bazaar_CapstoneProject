package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.FavoritesPage;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class FavoritesSteps {

    ProductsPage productPage = new ProductsPage();
    FavoritesPage favoritesPage = new FavoritesPage();

    @When("User clicks heart icon for product {string} and verifies it in favorites")
    public void user_clicks_heart_icon_and_verifies(String productName) {

        productPage.clickHeartIconForProduct(productName);

        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");

        Assert.assertTrue("Product not found in favorites", favoritesPage.isProductInFavorites(productName));
    }
}
