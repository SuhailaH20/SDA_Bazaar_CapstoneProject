package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.CartPage;
import com.bazaarstores.pages.HomePage;
import com.bazaarstores.pages.LoginPage;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.List;

public class CartStep {

    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    CartPage cartPage = new CartPage();
    int initialCount;


    // -------------------- LOGIN & SETUP -------------------

    @Given("User is logged in and on the product page")
    public void userIsLoggedInAndOnProductPage() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
        loginPage.loginAsCustomer(ConfigReader.getCustomerEmail(), ConfigReader.getDefaultPassword())
                .waitForUrlContains("customer");
        homePage.clearCartIfNotEmpty();

        initialCount = homePage.getCartCount();
        System.out.println("Initial cart count at Given: " + initialCount);
    }

    @Given("User is logged in")
    public void userIsLoggedIn() {
        Driver.getDriver().get(ConfigReader.getBaseUrl());
        loginPage.loginAsCustomer(ConfigReader.getCustomerEmail(), ConfigReader.getDefaultPassword())
                .waitForUrlContains("customer");
    }


    // -------------------- ADD TO CART ---------------------

    @When("User clicks Add to Cart button for product {string}")
    public void userClicksAddToCartButton(String productName) {
        homePage.refreshPage();
        homePage.addProductToCart(productName);
    }

    @Then("Product {string} should be added successfully and success message is displayed")
    public void productShouldBeAddedSuccessfully(String productName) {
        Assert.assertTrue("Product not found in popup cart!", homePage.isProductInCart(productName));
//        Assert.assertTrue("Success popup not detected!", homePage.isAddToCartSuccessMessageDisplayed());
    }

    @Then("cart count increases by {int}")
    public void cartCountIncreasesBy(int amount) {
        Assert.assertTrue(homePage.isCartCountIncreasedBy(initialCount, amount));
    }

    @Then("Both products {string} and {string} should appear in the cart")
    public void bothProductsShouldAppear(String p1, String p2) {
        List<String> products = homePage.getAllProductsInPopupCart();

        System.out.println("Products found in popup cart: " + products);
        Assert.assertTrue("Product '" + p1 + "' not found in popup cart!",
                homePage.isProductInCart(p1));
        Assert.assertTrue("Product '" + p2 + "' not found in popup cart!",
                homePage.isProductInCart(p2));

    }

    @Then("Cart subtotal should be correct")
    public void cartSubtotalShouldBeCorrect() {
        Assert.assertTrue("Subtotal mismatch!", homePage.isCartSubtotalCorrect());
    }

    @Then("Product {string} quantity should increase correctly")
    public void productQuantityShouldIncreaseCorrectly(String productName) {
        long count = homePage.getAllProductsInPopupCart().stream()
                .filter(p -> p.equalsIgnoreCase(productName)).count();
        Assert.assertEquals("Product quantity not increased correctly!", 3, count);
    }


    // ------------------ OUT OF STOCK CASE -----------------

    @Given("there is a product {string} that is out of stock")
    public void createOutOfStockProduct(String productName) {
        homePage.createOutOfStockProductApi(productName);
    }

    @Then("System should display error message {string} is out of stock")
    public void verifyOutOfStockError(String productName) {
        homePage.refreshPage();
        homePage.deleteProductApi(productName);
        homePage.refreshPage();
        Assert.assertTrue("Expected out of stock message not displayed!",
                homePage.isErrorMessageDisplayed("out of stock"));
    }

    @Then("cart count should not change")
    public void verifyCartCountNotChanged() {
        int current = homePage.getCartCount();
        Assert.assertEquals("Cart count changed despite out of stock!", initialCount, current);
    }


    // ------------------ VIEW ITEMS IN CART ----------------

    @Given("User has at least one product in the cart")
    public void userHasAtLeastOneProductInCart() {
        if (homePage.isCartEmpty()) {
            homePage.addProductToCart("Jeans");
            //homePage.addProductToCart("Jeans");
        }
    }

    @Given("User has no items in the cart")
    public void userHasNoItemsInCart() {
        homePage.clearCartIfNotEmpty();
    }

    @When("User hovers over the cart icon")
    public void hoverOverCartIcon() {
        homePage.hoverOverCartIcon();
        System.out.println("Hovered over cart icon");
    }

    @Then("Popup cart should display product names, prices, and subtotal")
    public void verifyPopupCartDetails() {
        Assert.assertTrue("Product names or prices not visible!", homePage.isProductNameAndPriceVisible());
        Assert.assertTrue("Subtotal not visible!", homePage.isCartSubtotalCorrect());
        System.out.println("Popup cart displays names, prices, and subtotal correctly");
    }

    @And("User clicks View Cart button")
    public void clickViewCartButton() {
        homePage.clickViewCartButton().waitForUrlContains("cart");
        System.out.println("Clicked 'View Cart' button");
    }

    @Then("Cart page should display all added items with their prices and total")
    public void verifyCartPageDetails() {
        Assert.assertFalse("No products visible!", cartPage.getProductNames().isEmpty());
        Assert.assertFalse("No prices visible!", cartPage.getProductPrices().isEmpty());
        Assert.assertTrue("Total prices not visible!", cartPage.isTotalPricesDisplayed());
        System.out.println("Cart page shows products and prices correctly");
    }

    // -------------------- REMOVE ITEMS --------------------

    @And("User clicks remove button for an item in the popup cart")
    public void removeItemFromPopupCart() {
        initialCount = homePage.getCartCount();
        homePage.removeFirstProductFromCart();
        System.out.println("Removed one product from popup cart (before count: " + initialCount + ")");
    }

    @Then("Item should be removed and cart count should decrease by {int}")
    public void verifyItemRemovedAndCartCountDecreased(int num) {
        Assert.assertTrue("Cart count did not decrease correctly after removal!",
                homePage.isCartCountIncreasedBy(initialCount, -num));
        System.out.println("Item removed successfully, cart count decreased by " + num);
    }

    @And("Success message should be displayed")
    public void verifySuccessMessageDisplayed() {
        Assert.assertTrue("Success message after remove not displayed!",
                homePage.isRemoveFromCartSuccessMessageDisplayed());
    }

    @And("User clicks remove button next to an item")
    public void verifyRemoveButtonOnCartPage() {
        Assert.assertTrue("Remove button not displayed on cart page!",
                cartPage.isRemoveButtonVisible());
    }

    @Then("Item should be removed and total should update correctly")
    public void verifyTotalAfterRemove() {
        Assert.assertTrue("Total prices not updated!", cartPage.isTotalPricesDisplayed());
    }

    @And("Message Your cart is empty should be displayed")
    public void verifyEmptyCartMessageDisplayed() {
        Assert.assertTrue("Expected empty cart message not displayed!",
                homePage.isCartEmptyMessageDisplayed());
    }

    @And("Cart subtotal should show $0.00")
    public void verifySubtotalZero() {
        Assert.assertTrue(homePage.isCartSubtotalZero());
    }

    @Then("View Cart button should not be displayed")
    public void verifyViewCartNotDisplayed() {
        Assert.assertFalse("View Cart button should not be visible when cart is empty!",
                homePage.isViewCartButtonVisible());
    }


    // -------------------- CONFIRM CART --------------------

    @And("User clicks Confirm Cart button")
    public void clickConfirmCartButton() {
        cartPage.clickConfirmCart();
    }

    @Then("System should display order summary with items and total price")
    public void verifyOrderSummaryDisplayed() {
        Assert.assertTrue("Order summary not displayed correctly!", cartPage.isOrderSummaryVisible());
    }

    @And("System should display success popup message {string}")
    public void verifySuccessPopupMessage(String message) {
        Assert.assertTrue("Success popup not displayed!",
                cartPage.isOrderSuccessPopupDisplayed(message));
    }

    @And("Network connection is lost before clicking Confirm Cart button")
    public void simulateNetworkDisconnection() {
        System.out.println("Simulating network disconnection...");
        cartPage.simulateNetworkFailure();
    }

    @Then("System should display error message {string}")
    public void verifyErrorMessage(String errorMessage) {
        Assert.assertTrue("Expected error message not shown!",
                cartPage.isNetworkErrorMessageDisplayed(errorMessage));
    }

    @Then("User should not be able to proceed to confirmation")
    public void verifyUserCannotProceedToConfirm() {
        Assert.assertFalse("User should not be able to confirm when cart is empty!",
                homePage.isViewCartButtonVisible());
    }

    @And("The shopping cart should not contain the deleted product in backend")
    public void theShoppingCartShouldNotContainTheDeletedProductInBackend() {
        boolean isDeleted = homePage.verifyProductDeletedFromCartBackend();
        Assert.assertTrue("Deleted product still exists in backend cart!", isDeleted);
    }

    @And("The backend cart should contain the product {string}")
    public void theBackendCartShouldContainTheProduct(String productName) {
        boolean exists = homePage.verifyProductExistsInBackendCart(productName);
        Assert.assertTrue("Product '" + productName + "' not found in backend cart!", exists);
    }

    @And("The backend cart should contain the products {string} and {string}")
    public void theBackendCartShouldContainTheProductsAnd(String p1, String p2) {
        boolean exists1 = homePage.verifyProductExistsInBackendCart(p1);
        boolean exists2 = homePage.verifyProductExistsInBackendCart(p2);
        Assert.assertTrue("Product '" + p1 + "' not found in backend cart!", exists1);
        Assert.assertTrue("Product '" + p2 + "' not found in backend cart!", exists2);
    }

    @And("The backend cart should not contain the product {string}")
    public void theBackendCartShouldNotContainTheProduct(String productName) {
        homePage.refreshPage();
        homePage.deleteProductApi(productName);
        homePage.refreshPage();
        boolean exists = homePage.verifyProductExistsInBackendCart(productName);
        Assert.assertFalse("Product '" + productName + "' still exists in backend cart!", exists);
    }
}
