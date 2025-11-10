package com.bazaarstores.pages;

import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductEditPage extends BasePage {

    private final By nameField = By.xpath("//input[@placeholder='Name']");
    private final By priceField = By.xpath("//input[@placeholder='Price']");
    private final By stockField = By.xpath("//input[@placeholder='Stock']");
    private final By submitButton = By.xpath("//button[text()='Submit']");
    private final By successMessage = By.xpath("//div[contains(@class,'toast-success')]");

    // 🔹 Fluent setters
    public ProductEditPage enterName(String name) {
        clearAndType(nameField, name);
        return this;
    }

    public ProductEditPage enterPrice(String price) {
        clearAndType(priceField, price);
        return this;
    }

    public ProductEditPage enterStock(String stock) {
        clearAndType(stockField, stock);
        return this;
    }

    private void clearAndType(By locator, String value) {
        WebElement field = Driver.getDriver().findElement(locator);
        field.clear();
        field.sendKeys(value);
    }

    public ProductPage clickSubmit() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", btn);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", btn);
        System.out.println("Submitted updated product");
        return new ProductPage();
    }
}
