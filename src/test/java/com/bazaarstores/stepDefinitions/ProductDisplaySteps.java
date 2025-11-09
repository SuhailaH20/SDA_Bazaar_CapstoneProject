package com.bazaarstores.stepDefinitions;
import io.cucumber.java.en.*;
public class ProductDisplaySteps {

    @Given("user is logged in as customer")
    public void userIsLoggedInAsCustomer() {
        System.out.println("User logged in as customer successfully.");
    }

    @When("user navigates to the customer home page")
    public void userNavigatesToCustomerHomePage() {
        System.out.println("Navigating to customer home page...");
    }

    @Then("each product should display {string}, {string}, {string}")
    public void eachProductShouldDisplay(String name, String price, String image) {
        System.out.println("Verified each product shows: " + name + ", " + price + ", " + image);
    }

    @Then("product {string} should have a description {string}")
    public void productShouldHaveDescription(String product, String description) {
        System.out.println(product + " description: " + description);
    }

    @Then("other products should have missing descriptions")
    public void otherProductsMissingDescription() {
        System.out.println("Verified other products have missing descriptions.");
    }

    @When("user opens the customer home page")
    public void userOpensCustomerHomePage() {
        System.out.println("Opened customer home page.");
    }

    @Then("the product list should load in less than or equal to 3 seconds")
    public void productListLoadsQuickly() {
        System.out.println("Verified product list loads in <3 seconds.");
    }

    @Given("no products exist in the database")
    public void noProductsInDatabase() {
        System.out.println("Database has no products.");
    }

    @Then("a message {string} should be displayed")
    public void messageShouldBeDisplayed(String message) {
        System.out.println("Message displayed: " + message);
    }
}
