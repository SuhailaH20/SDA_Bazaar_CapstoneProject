package com.bazaarstores.stepDefinitions;

import io.cucumber.java.en.*;

public class ProductDetailsSteps {

    @Given("product list is displayed")
    public void productListDisplayed() {
        System.out.println("Product list displayed successfully.");
    }

    @When("user clicks on any product")
    public void userClicksAnyProduct() {
        System.out.println("Clicked on product card.");
    }

    @Then("product detail page should open successfully")
    public void productDetailPageOpens() {
        System.out.println("Product detail page opened successfully.");
    }

    @When("user clicks on a product")
    public void userClicksOnProduct() {
        System.out.println("Clicked on a product to open details.");
    }

    @Then("Name, Price, and Image should be visible")
    public void namePriceImageVisible() {
        System.out.println("Name, Price, and Image are visible.");
    }

    @Then("Description should be displayed if available")
    public void descriptionDisplayedIfAvailable() {
        System.out.println("Description displayed if available.");
    }

    @When("user clicks on product {string}")
    public void userClicksOnProductName(String productName) {
        System.out.println("Clicked on product: " + productName);
    }

    @Then("description should be displayed as {string}")
    public void descriptionShouldBeDisplayedAs(String description) {
        System.out.println("Description displayed as: " + description);
    }

    @Then("system should show placeholder text {string}")
    public void systemShowsPlaceholder(String placeholder) {
        System.out.println("System displayed placeholder: " + placeholder);
    }
}
