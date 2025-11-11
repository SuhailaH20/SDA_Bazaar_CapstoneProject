package com.bazaarstores.pages;

import com.bazaarstores.utilities.ApiUtil;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.restassured.RestAssured.given;


public class ProductCreatePage extends BasePage {

    // 🔹 Locators
    private final By nameField = By.xpath("//input[@placeholder='Name']");
    private final By priceField = By.xpath("//input[@placeholder='Price']");
    private final By stockField = By.xpath("//input[@placeholder='Stock']");
    private final By skuField = By.xpath("//input[@placeholder='SKU']");
    private final By submitButton = By.xpath("//button[text()='Submit']");

    // 🔹 Common error messages
    private final By generalErrorBox = By.xpath("//div[contains(@class,'alert-danger')]");

    // Dynamic errors by content
    private By errorMessage(String messageText) {
        return By.xpath("//*[contains(text(),\"" + messageText + "\")]");
    }

    // 🔹 Fluent field methods
    public ProductCreatePage enterName(String name) {
        clearAndType(nameField, name);
        return this;
    }

    public ProductCreatePage enterPrice(String price) {
        clearAndType(priceField, price);
        return this;
    }

    public ProductCreatePage enterStock(String stock) {
        clearAndType(stockField, stock);
        return this;
    }

    public ProductCreatePage enterSKU(String sku) {
        clearAndType(skuField, sku);
        return this;
    }

    private void clearAndType(By locator, String value) {
        WebElement element = findElement(locator);
        element.clear();
        if (value != null) element.sendKeys(value);
    }

    // 🔹 Click Submit
    public ProductPage clickSubmit() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", btn);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", btn);
        return new ProductPage();
    }


    // 🔹 Verification: Error
    public ProductCreatePage verifyErrorMessage(String expectedText) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(6));
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage(expectedText))).isDisplayed();
        Assert.assertTrue("Expected error message not displayed: " + expectedText, visible);
        System.out.println("Error message displayed: " + expectedText);
        return this;
    }

    // 🔹 General error box
    public ProductCreatePage verifyGeneralErrorBox() {
        Assert.assertTrue("General error box not displayed", isDisplayed(generalErrorBox));
        System.out.println("General error box displayed.");
        return this;
    }

    // 🔹 API Verifications
    public ProductCreatePage verifyProductCreatedInApi(String sku) {
        Assert.assertTrue("Product not found in API: " + sku, isProductInApi(sku));
        System.out.println("Product found in API: " + sku);
        return this;
    }

    public ProductCreatePage verifyProductNotCreatedInApi(String sku) {
        Assert.assertFalse("Product unexpectedly exists in API: " + sku, isProductInApi(sku));
        System.out.println("Product not found in API as expected: " + sku);
        return this;
    }

    // 🔹 API logic
    public boolean isProductInApi(String sku) {
        String token = ApiUtil.loginAndGetToken(
                ConfigReader.getStoreManagerEmail(),
                ConfigReader.getDefaultPassword());

        Response response = given()
                .baseUri(ConfigReader.getApiBaseUrl())
                .header("Authorization", "Bearer " + token)
                .accept("application/json")
                .get("/products");

        ApiUtil.verifyStatusCode(response, 200);
        List<String> allSkus = response.jsonPath().getList("products.sku");
        return allSkus.contains(sku);
    }
}