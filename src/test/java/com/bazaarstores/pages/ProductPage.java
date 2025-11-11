package com.bazaarstores.pages;

import com.bazaarstores.utilities.ApiUtil;
import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ProductPage extends BasePage {

    private final By productTable = By.cssSelector("table.table.table-bordered");
    private final By productRows = By.cssSelector("table.table.table-bordered tbody tr");
    private final By successMessage = By.xpath("//div[contains(@class,'toast-success')]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space(text())='Yes, delete it!']");
    private final By cancelDeleteButton = By.xpath("//button[normalize-space(text())='Cancel']");

    // Dynamic locators as String templates
    private final String editButtonByProductName = "//tr[td[normalize-space(text())='%s']]//a[i[contains(@class,'bi-pencil-square')]]";
    private final String deleteButtonByProductName = "//tr[td[normalize-space(text())='%s']]//button[contains(@onclick,'confirmDelete')]";

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
        String xpath = String.format(deleteButtonByProductName, productName);
        By deleteButton = By.xpath(xpath);

        waitForElementToBeVisible(deleteButton);
        scrollToElement(deleteButton);
        click(deleteButton);

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
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        Assert.assertTrue("Expected success message not displayed", msg.getText().contains(expectedText));
        System.out.println("Success message displayed: " + expectedText);
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

    public ProductPage ensureSkuExists(String sku) {
        if (isSkuPresentInTable(sku)) {
            System.out.println("SKU already exists: " + sku);
            return this;
        }

            String token = ApiUtil.loginAndGetToken(
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
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .body(body)
                    .post("/products/create");

            com.bazaarstores.utilities.ApiUtil.verifyStatusCode(r, 201);
            System.out.println("Product created via API for SKU: " + sku);

        return this;
    }

    public ProductPage ensureNameExists(String name) {
        if (isSkuPresentInTable(name)) {
            System.out.println("name already exists: " + name);
            return this;
        }

        System.out.println("name not found, creating product manually...");

        clickAddProduct()
                .enterName(name)
                .enterPrice(Faker.instance().number().digits(3))
                .enterStock(Faker.instance().number().digits(3))
                .enterSKU(Faker.instance().number().digits(3))
                .clickSubmit()
                .verifySuccessMessage("Product created successfully");

        Assert.assertTrue("nem not created in table: " + name, isSkuPresentInTable(name));
        System.out.println("Product created successfully for name: " + name);

        return this;
    }




}






