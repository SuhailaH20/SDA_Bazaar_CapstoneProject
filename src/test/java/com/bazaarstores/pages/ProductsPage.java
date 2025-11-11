package com.bazaarstores.pages;

import com.bazaarstores.utilities.ConfigReader;
import com.bazaarstores.utilities.Driver;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {

    private By productNames = By.cssSelector(".product-name");
    private By productPrices = By.cssSelector(".product-price");
    private By productDescriptions = By.cssSelector(".product-description");
    private By productImages = By.cssSelector(".product-image img, img.product-image");
    private By heartIcons = By.cssSelector("div.favorite.favorite-icon i");
    private By errorMessage = By.xpath("//div[contains(text(),'Product is already in favorites.')]");

    // --- Getters ---
    public List<String> getAllProductNames() {
        return Driver.getDriver().findElements(productNames)
                .stream().map(WebElement::getText).filter(t -> !t.isEmpty()).collect(Collectors.toList());
    }

    public List<String> getAllProductPrices() {
        return Driver.getDriver().findElements(productPrices)
                .stream().map(WebElement::getText).filter(t -> !t.isEmpty()).collect(Collectors.toList());
    }

    public List<String> getAllProductDescriptions() {
        return Driver.getDriver().findElements(productDescriptions)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<WebElement> getAllProductImages() {
        return Driver.getDriver().findElements(productImages);
    }

    // --- Actions ---
    public void clickHeartIconForProduct(String productName) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // العثور على بطاقة المنتج
            WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'product-card')][.//h3[text()='" + productName + "']]")
            ));

            // العثور على أيقونة القلب داخل البطاقة
            WebElement heartIcon = productCard.findElement(By.cssSelector("div.favorite.favorite-icon i"));

            // التحقق إذا كانت مفعلة بالفعل
            WebElement parentDiv = heartIcon.findElement(By.xpath(".."));
            if (!parentDiv.getAttribute("class").contains("active")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", heartIcon);
                System.out.println(productName + " added to favorites automatically.");
            } else {
                System.out.println(productName + " is already in favorites.");
            }

        } catch (TimeoutException e) {
            System.out.println("Heart icon for '" + productName + "' not found or clickable: " + e.getMessage());
        }
    }

    @Then("Product {string} should appear in favorites list")
    public void product_should_appear_in_favorites_list(String productName) {
        //
        Driver.getDriver().get(ConfigReader.getBaseUrl() + "/favorites");

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));


        By productInFavorites = By.xpath(
                "//div[contains(@class,'favorite-icon') and contains(@class,'active') and @data-product-name='" + productName + "']"
        );

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productInFavorites));
            Assert.assertTrue(" Product '" + productName + "' is in favorites.", true);
        } catch (TimeoutException e) {
            Assert.fail(" Product '" + productName + "' not found in favorites.");
        }
    }


    public boolean isFavoriteErrorMessageDisplayed(String expectedText) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement swalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-title")));
            WebElement swalText = driver.findElement(By.cssSelector(".swal2-html-container"));
            String actualMessage = swalTitle.getText().trim() + " " + swalText.getText().trim();
            System.out.println("SweetAlert message: " + actualMessage);
            return actualMessage.contains(expectedText);
        } catch (TimeoutException e) {
            return false;
        }
    }

    // --- Optional: get product detail info ---
    public String getProductDetailName() {
        return Driver.getDriver().findElement(productNames).getText();
    }

    public String getProductDetailPrice() {
        return Driver.getDriver().findElement(productPrices).getText();
    }

    public String getProductDetailDescription() {
        return Driver.getDriver().findElement(productDescriptions).getText();
    }

    public String getProductDetailImage() {
        return Driver.getDriver().findElement(productImages).getAttribute("src");
    }


    public void removeProductFromFavorites(String productName) {
        WebDriver driver = Driver.getDriver();
        try {
            WebElement heartIcon = driver.findElement(
                    By.xpath("//div[contains(@class,'product-card')][.//h3[text()='" + productName + "']]//div[contains(@class,'favorite-icon')]/i")
            );

            WebElement parentDiv = heartIcon.findElement(By.xpath(".."));
            if (parentDiv.getAttribute("class").contains("active")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", heartIcon);
                System.out.println("Product '" + productName + "' has been removed from favorites.");
            } else {
                System.out.println("Product '" + productName + "' is not in favorites.");
            }

        } catch (Exception e) {
            System.out.println("Product '" + productName + "' was not found: " + e.getMessage());
        }
    }
}
