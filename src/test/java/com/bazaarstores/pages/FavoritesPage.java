package com.bazaarstores.pages;

import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesPage {

    private static final By favoriteProducts = By.cssSelector("div.fav.favorite-icon");
    private static final By favoriteErrorMessage = By.xpath("//div[contains(text(),'Product is already in favorites.')]");


    public static boolean isProductInFavorites(String productName) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

        try {
            By productLocator = By.xpath("//div[contains(@class,'fav') and contains(@class,'favorite-icon') and @data-product-name='" + productName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void removeProduct(String productName) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By productLocator = By.xpath("//div[contains(@class,'fav') and contains(@class,'favorite-icon') and contains(@class,'active') and @data-product-name='" + productName + "']");

        try {
            WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));

            WebElement heartIcon = productElement.findElement(By.tagName("i"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", heartIcon);

            wait.until(ExpectedConditions.invisibilityOfElementLocated(productLocator));
            System.out.println("✅ Product '" + productName + "' removed from favorites successfully.");
        } catch (TimeoutException e) {
            System.out.println("⚠ Product '" + productName + "' not found or could not be removed.");
        } catch (Exception e) {
            System.out.println("⚠ Unexpected error while removing '" + productName + "': " + e.getMessage());
        }
    }


    public static List<String> getFavoriteProductNames() {
        List<WebElement> products = Driver.getDriver().findElements(favoriteProducts);
        return products.stream()
                .map(p -> p.getAttribute("data-product-name"))
                .collect(Collectors.toList());
    }

    public boolean isFavoriteErrorMessageDisplayed() {
        List<WebElement> errors = Driver.getDriver().findElements(favoriteErrorMessage);
        return !errors.isEmpty() && errors.get(0).isDisplayed();
    }
}
