package com.bazaarstores.pages;

import com.bazaarstores.utilities.ApiUtil;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
public class ProductPage extends BasePage {

    private final By productTable = By.cssSelector("table.table.table-bordered");
    private final By productRows = By.cssSelector("table.table.table-bordered tbody tr");
    private final By successMessage = By.xpath("//div[contains(@class,'toast-success')]//div[contains(@class,'toast-message')]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space(text())='Yes, delete it!']");
    private final By cancelDeleteButton = By.xpath("//button[normalize-space(text())='Cancel']");

    // Dynamic locators as String templates
    private final String editButtonByProductName = "//tr[td[normalize-space(text())='%s']]//a[i[contains(@class,'bi-pencil-square')]]";
    private final String deleteButtonByProductName = "//tr[td[normalize-space(text())='%s']]//button[contains(@onclick,'confirmDelete')]";

    private static int productId;
    private static String storeManagerToken;

    // Verify table visible
    public boolean isTableVisible() {
        return isDisplayed(productTable);
    }

    // Get all products
    public List<WebElement> getProductRows() {
        return findElements(productRows);
    }

    public int getProductCount() {
        List<WebElement> rows = getProductRows();
        return rows.size();
    }

    // Click ADD PRODUCT button
    public ProductCreatePage clickAddProduct() {
        click(By.xpath("//a[contains(@href,'product/create')]"));
        return new ProductCreatePage();
    }

    // Click Edit button by product name
    public ProductCreatePage clickEditButton(String productName) {
        String xpath = String.format(editButtonByProductName, productName);
        By editButton = By.xpath(xpath);

        waitForElementToBeVisible(editButton);
        scrollToElement(editButton);
        click(editButton);

        System.out.println("Clicked Edit for product: " + productName);
        return new ProductCreatePage();
    }

    // Click Delete button by product name
    public ProductPage clickDeleteButton(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty!");
        }

        String xpath = String.format(deleteButtonByProductName, productName);
        By deleteButton = By.xpath(xpath);

        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement button = wait.until(driver -> driver.findElement(deleteButton));
        scrollToElement(deleteButton);
        button.click();

        System.out.println("Clicked Delete for product: " + productName);
        return this;
    }

    // Confirm delete
    public ProductPage confirmDelete() {
        waitForElementToBeVisible(confirmDeleteButton);
        click(confirmDeleteButton);
        System.out.println("Confirmed delete action");
        return this;
    }

    // Cancel delete
    public ProductPage cancelDelete() {
        waitForElementToBeVisible(cancelDeleteButton);
        click(cancelDeleteButton);
        System.out.println("Cancelled delete action");
        return this;
    }

    // Verify success message
    public ProductPage verifySuccessMessage(String expectedText) {
        Wait<WebDriver> fluentWait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement msg = fluentWait.until(driver -> driver.findElement(successMessage));

        String text = msg.getText().trim();
        Assert.assertTrue("Expected success message not found! Actual: " + text,
                text.toLowerCase().contains(expectedText.toLowerCase()));

        System.out.println("Success message displayed: " + text);
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

    public List<String> getProductNamesFromUI() {
        List<WebElement> rows = getProductRows();
        List<String> names = new java.util.ArrayList<>();

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                names.add(cells.get(0).getText().trim());
            }
        }
        return names;
    }

    public boolean isSkuPresentInTable(String sku) {
        if (!isDisplayed(productTable)) {
            System.out.println("Product table not visible on the page.");
            return false;
        }

        List<WebElement> rows = getProductRows();
        if (rows.isEmpty()) {
            System.out.println("No products found in the table.");
            return false;
        }

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                if (cell.getText().trim().equalsIgnoreCase(sku)) {
                    System.out.println("Found SKU in table: " + sku);
                    return true;
                }
            }
        }

        System.out.println("SKU not found in table: " + sku);
        return false;
    }

    public boolean isNamePresentInTable(String name) {
        if (!isDisplayed(productTable)) {
            System.out.println("Product table not visible on the page.");
            return false;
        }

        List<WebElement> rows = getProductRows();
        if (rows.isEmpty()) {
            System.out.println("No products found in the table.");
            return false;
        }

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                if (cell.getText().trim().equalsIgnoreCase(name)) {
                    System.out.println("Found Name in table: " + name);
                    return true;
                }
            }
        }

        System.out.println("Name not found in table: " + name);
        return false;
    }

    public ProductPage ensureSkuExists(String sku) {
        if (isSkuPresentInTable(sku)) {
            System.out.println("SKU already exists: " + sku);
            return this;
        }

            storeManagerToken = ApiUtil.loginAndGetToken(
                    ConfigReader.getStoreManagerEmail(),
                    ConfigReader.getDefaultPassword());

            String body = String.format("""
                {
                  "name": "Mouse",
                  "price": 29.99,
                  "stock": 50,
                  "sku": "%s",
                  "category_id": 1,
                  "manufacturer": "AutoTest",
                  "image_url": "images/test.jpg",
                  "discount": 0.0,
                  "description": "Auto-created for duplicate SKU test"
                }
                """, sku);

            Response r = given()
                    .baseUri(ConfigReader.getApiBaseUrl())
                    .header("Authorization", "Bearer " + storeManagerToken)
                    .contentType("application/json")
                    .body(body)
                    .post("/products/create");

            ApiUtil.verifyStatusCode(r, 201);
            productId = r.jsonPath().getInt("product.id");
            System.out.println("Product created via API for SKU: " + sku);

        return this;
    }

    public ProductPage deleteProductApi(String name) {
        Response r = given()
                .baseUri(ConfigReader.getApiBaseUrl())
                .header("Authorization", "Bearer " + storeManagerToken)
                .delete("/products/" + productId);

        if (r.statusCode() == 200 || r.statusCode() == 204)
            System.out.println("Deleted product: " + name);
        else
            System.out.println("Failed to delete product. Status: " + r.statusCode());
        return this;
    }

    public ProductPage ensureNameExists(String name) {
        if (isNamePresentInTable(name)) {
            System.out.println("name already exists: " + name);
            return this;
        }

        storeManagerToken = ApiUtil.loginAndGetToken(
                ConfigReader.getStoreManagerEmail(),
                ConfigReader.getDefaultPassword());

        String body = String.format("""
                {
                  "name": "%s",
                  "price": 29.99,
                  "stock": 50,
                  "sku": "Mo0011",
                  "category_id": 1,
                  "manufacturer": "AutoTest",
                  "image_url": "images/test.jpg",
                  "discount": 0.0,
                  "description": "Auto-created for duplicate SKU test"
                }
                """, name);

        Response r = given()
                .baseUri(ConfigReader.getApiBaseUrl())
                .header("Authorization", "Bearer " + storeManagerToken)
                .contentType("application/json")
                .body(body)
                .post("/products/create");

        ApiUtil.verifyStatusCode(r, 201);
        System.out.println("Product created via API for Name: " + name);

        Driver.getDriver().navigate().refresh();

        return this;
    }
}






