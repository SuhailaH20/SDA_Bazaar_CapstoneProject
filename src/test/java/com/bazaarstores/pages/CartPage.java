package com.bazaarstores.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    private final By productNames = By.cssSelector("h3.product-name");
    private final By productPrices = By.cssSelector("span.current-price");
    private final By confirmCartButton = By.cssSelector("button.add-to-cart-checkout");
    private final By removeButtons = By.cssSelector(".remove-item");

    public CartPage() {
    }

    public List<String> getProductNames() {
        List<WebElement> items = findElements(productNames);
        List<String> names = new ArrayList<>();
        for (WebElement item : items) names.add(item.getText().trim());
        return names;
    }

    public List<Double> getProductPrices() {
        List<WebElement> prices = findElements(productPrices);
        List<Double> values = new ArrayList<>();
        for (WebElement price : prices) {
            String text = price.getText().replaceAll("[^0-9.]", "");
            if (!text.isEmpty()) values.add(Double.parseDouble(text));
        }
        return values;
    }

    public CartPage clickConfirmCart() {
        click(confirmCartButton);
        System.out.println("Confirm Cart clicked");
        return this;
    }

    public boolean isTotalPricesDisplayed() {
        return isDisplayed(By.cssSelector(".cart-totals"));
    }
    public boolean isRemoveButtonVisible() {
        return isDisplayed(removeButtons);
    }

    public CartPage removeFirstProductFromCart() {
        click(removeButtons);
        return this;
    }
}
