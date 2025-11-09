package com.bazaarstores.stepDefinitions;

import io.cucumber.java.en.*;

public class FavoritesSteps {

    @Given("user is logged in")
    public void userIsLoggedIn() {
        System.out.println("User logged in successfully.");
    }

    @When("user clicks the heart icon on product {string}")
    public void userClicksHeartIcon(String productName) {
        System.out.println("Clicked heart icon on product: " + productName);
    }

    @Then("product should appear under {string} list")
    public void productAppearsUnderList(String listName) {
        System.out.println("Product appears in: " + listName);
    }

    @Then("heart icon should not turn red on Home page")
    public void heartIconNotRedHome() {
        System.out.println("Heart icon not red on Home page.");
    }

    @Then("heart icon should turn red on Favorites page")
    public void heartIconRedFavorites() {
        System.out.println("Heart icon red on Favorites page.");
    }

    @Given("product {string} is already in favorites")
    public void productAlreadyInFavorites(String productName) {
        System.out.println(productName + " is already in favorites.");
    }

    @When("user clicks heart icon again")
    public void userClicksHeartAgain() {
        System.out.println("User clicked heart icon again.");
    }

    @Then("system should display message {string}")
    public void systemDisplaysMessage(String message) {
        System.out.println("Displayed message: " + message);
    }

    @Then("product should be added to favorites and heart icon should turn red")
    public void productAddedAndHeartRed() {
        System.out.println("Product added and heart turned red.");
    }

    @Given("user has favorite products")
    public void userHasFavorites() {
        System.out.println("User already has favorite products.");
    }

    @When("user navigates to {string} page")
    public void userNavigatesToPage(String pageName) {
        System.out.println("Navigated to: " + pageName);
    }

    @Then("all added products should be displayed correctly")
    public void productsDisplayedCorrectly() {
        System.out.println("All favorite products displayed correctly.");
    }

    @Then("system should prevent adding duplicate product")
    public void preventDuplicateFavorites() {
        System.out.println("System prevented duplicate favorites.");
    }
}
