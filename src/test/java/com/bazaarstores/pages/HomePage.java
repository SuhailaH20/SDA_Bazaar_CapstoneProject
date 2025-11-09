package com.bazaarstores.pages;

import com.bazaarstores.utilities.ApiUtil;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HomePage extends BasePage {

    // ---------- Locators ----------
    private final String addToCartByName = "//h3[@class='product-name' and normalize-space(text())='%s']"
            + "/ancestor::div[@class='product-card']//button[contains(@class,'add-to-cart')]";

    private final By homeLink = By.xpath("//a[contains(text(),'Home')]");
    private final By favoritesLink = By.xpath("//a[contains(text(),'My Favorites')]");
    private final By cartIcon = By.cssSelector(".cart-icon");
    private final By cartItem = By.cssSelector(".cart-item");
    private final By cartCount = By.cssSelector(".cart-count");
    private final By cartName = By.cssSelector(".cart-item-name");
    private final By cartPrice = By.cssSelector(".cart-item-price");
    private final By removeBtn = By.cssSelector(".remove-item");
    private final By subtotal = By.cssSelector(".cart-subtotal");
    private final By profile = By.cssSelector("div.profile-icon");
    private final By logoutButton = By.xpath("//a[normalize-space()='Log Out']");
    private final By viewCartBtn = By.xpath("//a[@class='cart-button view-cart']");
    private final By emptyMsg = By.xpath("//*[contains(text(),'Your cart is empty')]");
    private final By addSuccessMsg = By.xpath("//*[contains(text(),'Product added to cart successfully') or contains(text(),'Success!')]");
    private final By removeSuccessMsg = By.xpath("//*[contains(text(),'The item was successfully removed from the cart') or contains(text(),'Item Removed')]");

    private static int productId;
    private static String token;

    // ---------- Core Fluent Actions ----------

    public HomePage addProductToCart(String productName) {
        click(By.xpath(String.format(addToCartByName, productName)));
        return this;
    }

    public HomePage hoverOverCartIcon() {
        waitForElementToBeVisible(cartIcon);
        hoverOver(cartIcon);
        return this;
    }

    public int getCartCount() {
        waitForElementToBeVisible(cartCount);
        String text = getText(cartCount).trim();
        if (text.isEmpty()){
            text = "0";
        }
        return Integer.parseInt(text);
    }

    public boolean isCartEmpty() {
        hoverOverCartIcon();
        return findElements(cartItem).isEmpty();
    }

    // ---------- Popup Cart ----------
    public List<String> getAllProductsInPopupCart() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            try {
                hoverOverCartIcon();
                waitForElementToBeVisible(cartName);
                names.clear();

                for (WebElement item : findElements(cartName)) {
                    String name = item.getText().trim();
                    if (!name.isEmpty()){
                        names.add(name);
                    }
                }

                if (!names.isEmpty()) {
                    System.out.println("Products in popup cart: " + names);
                    return names;
                }

            } catch (StaleElementReferenceException e) {
                System.out.println("Popup cart refreshed — retry " + (i + 1));
            }
        }
        throw new RuntimeException("Failed to read products in popup cart after retries.");
    }

    public boolean isProductInCart(String name) {
        return getAllProductsInPopupCart().stream()
                .anyMatch(p -> p.equalsIgnoreCase(name));
    }

    public HomePage clearCartIfNotEmpty() {
        hoverOverCartIcon();
        if (isDisplayed(cartName)) {
            List<String> items = getAllProductsInPopupCart();
            for (int i = 0; i < items.size(); i++) {
                click(removeBtn);
            }
            System.out.println("Cart cleared before test scenario");
        }
        return this;
    }

    // ---------- Cart Count & Subtotal ----------
    public boolean isCartCountIncreasedBy(int initial, int increase) {
        int expected = initial + increase;
        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

        try {
            return wait.until(d -> {
                String t = d.findElement(cartCount).getText().trim();
                int current = t.isEmpty() ? 0 : Integer.parseInt(t);
                System.out.printf("🛒 Initial:%d | Expected:%d | Current:%d%n", initial, expected, current);
                return current == expected;
            });
        } catch (TimeoutException e) {
            System.out.println("Cart count did not update in time.");
            return false;
        }
    }

    public boolean isCartSubtotalCorrect() {
        hoverOverCartIcon();
        double calc = 0.0;

        for (WebElement price : findElements(cartPrice)) {
            String txt = price.getText();
            if (txt.contains("$"))
                calc += Double.parseDouble(txt.substring(txt.indexOf('$') + 1).trim());
        }

        double displayed = Double.parseDouble(getText(subtotal).replaceAll("[^0-9.]", ""));
        System.out.printf("Calculated: %.2f | Displayed: %.2f%n", calc, displayed);
        return Math.abs(calc - displayed) < 0.01;
    }

    public boolean isCartSubtotalZero() {
        hoverOverCartIcon();
        double displayed = Double.parseDouble(getText(subtotal).replaceAll("[^0-9.]", ""));
        return displayed == 0.0;
    }

    // ---------- UI State Checks ----------
    public boolean isProductNameAndPriceVisible() {
        hoverOverCartIcon();
        waitForElementToBeVisible(cartName);
        return !findElements(cartName).isEmpty() && !findElements(cartPrice).isEmpty();
    }

//    public boolean isSubtotalVisible() {
//        hoverOverCartIcon();
//        return isDisplayed(subtotal);
//    }

    public boolean isViewCartButtonVisible() {
        return hoverOverCartIcon().isDisplayed(viewCartBtn);
    }

    public boolean isCartEmptyMessageDisplayed() {
        return hoverOverCartIcon().isDisplayed(emptyMsg);
    }

    public boolean isAddToCartSuccessMessageDisplayed() {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                    .withTimeout(Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            WebElement message = wait.until(d -> findElement(addSuccessMsg));
            return message.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isRemoveFromCartSuccessMessageDisplayed() {
        try {
            FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                    .withTimeout(Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

            WebElement message = wait.until(d -> findElement(removeSuccessMsg));
            return message.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed(String text) {
        waitForElementToBeVisible(By.xpath("//*[contains(text(),'" + text + "')]"));
        WebElement msg= findElement(By.xpath("//*[contains(text(),'" + text + "')]"));;
        try {
            return msg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ---------- Navigation ----------
    public HomePage clickHomeLink() {
        click(homeLink);
        return this;
    }

    public HomePage clickFavoritesLink() {
        click(favoritesLink);
        return this;
    }

    public CartPage clickViewCartButton() {
        hoverOverCartIcon();
        click(viewCartBtn);
        return new CartPage();
    }

    public HomePage removeFirstProductFromCart() {
        hoverOverCartIcon();
        click(removeBtn);
        return this;
    }

    public void clickProfile(){
        click(profile);
    }

    public LoginPage clickLogout(){
        click(logoutButton);
        return new LoginPage();
    }
    // ---------- API Utilities ----------
    public HomePage createOutOfStockProductApi(String name) {
        token = ApiUtil.loginAndGetToken(
                ConfigReader.getStoreManagerEmail(), ConfigReader.getDefaultPassword());
        String body = String.format("""
                {
                "name": "%s",
                "price": 9.99,
                "stock": 0,
                "sku": "SKU-%d",
                "category_id": 1,
                "manufacturer": "Test",
                "image_url": "images/test.jpg",
                "discount": 0.0,
                "description": "Out of stock test"
                }
                """, name, System.currentTimeMillis());

        Response r = given()
                .baseUri(ConfigReader.getApiBaseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .post("/products/create");

        ApiUtil.verifyStatusCode(r, 201);
        productId = r.jsonPath().getInt("product.id");
        System.out.println("Created out-of-stock product: " + name);
        return this;
    }

    public HomePage deleteProductApi(String name) {
        Response r = given()
                .baseUri(ConfigReader.getApiBaseUrl())
                .header("Authorization", "Bearer " + token)
                .delete("/products/" + productId);

        if (r.statusCode() == 200 || r.statusCode() == 204)
            System.out.println("Deleted product: " + name);
        else
            System.out.println("Failed to delete product. Status: " + r.statusCode());
        return this;
    }
}
