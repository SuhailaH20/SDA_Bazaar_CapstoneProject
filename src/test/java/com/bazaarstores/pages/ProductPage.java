package com.bazaarstores.pages;

import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;

public class ProductPage extends BasePage {

    private final By productTable = By.cssSelector("table.table.table-bordered");
    private final By productRows = By.cssSelector("table.table.table-bordered tbody tr");
    private final By successMessage = By.xpath("//div[contains(@class,'toast-success')]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space(text())='Yes, delete it!']");
    private final By cancelDeleteButton = By.xpath("//button[normalize-space(text())='Cancel']");

    // 🔹 Dynamic locators as String templates
    private final String editButtonByProductName = "//tr[td[normalize-space(text())='%s']]//a[i[contains(@class,'bi-pencil-square')]]";
    private final String deleteButtonByProductName = "//tr[td[normalize-space(text())='%s']]//button[contains(@onclick,'confirmDelete')]";

    // 🔹 Verify table visible
    public boolean isTableVisible() {
        return isDisplayed(productTable);
    }

    // 🔹 Get all products
    public List<WebElement> getProductRows() {
        return findElements(productRows);
    }

    // 🔹 Click ADD PRODUCT button
    public ProductCreatePage clickAddProduct() {
        click(By.xpath("//a[contains(@href,'product/create')]"));
        return new ProductCreatePage();
    }

    // 🔹 Click Edit button by product name
    public ProductEditPage clickEditButton(String productName) {
        String xpath = String.format(editButtonByProductName, productName);
        By editButton = By.xpath(xpath);

        waitForElementToBeVisible(editButton);
        scrollToElement(editButton);
        click(editButton);

        System.out.println("Clicked Edit for product: " + productName);
        return new ProductEditPage();
    }

    // 🔹 Click Delete button by product name
    public ProductPage clickDeleteButton(String productName) {
        String xpath = String.format(deleteButtonByProductName, productName);
        By deleteButton = By.xpath(xpath);

        waitForElementToBeVisible(deleteButton);
        scrollToElement(deleteButton);
        click(deleteButton);

        System.out.println("Clicked Delete for product: " + productName);
        return this;
    }

    // 🔹 Confirm delete
    public ProductPage confirmDelete() {
        waitForElementToBeVisible(confirmDeleteButton);
        click(confirmDeleteButton);
        System.out.println("Confirmed delete action");
        return this;
    }

    // 🔹 Cancel delete
    public ProductPage cancelDelete() {
        waitForElementToBeVisible(cancelDeleteButton);
        click(cancelDeleteButton);
        System.out.println("Cancelled delete action");
        return this;
    }

    // 🔹 Verify success message
    public ProductPage verifySuccessMessage(String expectedText) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        Assert.assertTrue("Expected success message not displayed", msg.getText().contains(expectedText));
        System.out.println("Success message displayed: " + expectedText);
        return this;
    }

    // 🔹 Verify product existence in table
    public ProductPage verifyProductNotVisible(String name) {
        String productRowXpath = "//tr[td[normalize-space(text())='" + name + "']]";
        boolean visible = isDisplayed(By.xpath(productRowXpath));
        Assert.assertFalse("Product still visible after deletion: " + name, visible);
        System.out.println("Product successfully deleted: " + name);
        return this;
    }

    public ProductPage verifyProductVisible(String name) {
        String productRowXpath = "//tr[td[normalize-space(text())='" + name + "']]";
        Assert.assertTrue("Product not found: " + name, isDisplayed(By.xpath(productRowXpath)));
        System.out.println("Product found in catalog: " + name);
        return this;
    }

    public boolean isProductVisible(String productName) {
        String productRowXpath = String.format("//tr[td[normalize-space(text())='%s']]", productName);
        try {
            return isDisplayed(By.xpath(productRowXpath));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMessageDisplayed(String expectedText) {
        try {
            By messageLocator = By.xpath("//*[contains(text(),'" + expectedText + "')]");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            return msg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}






