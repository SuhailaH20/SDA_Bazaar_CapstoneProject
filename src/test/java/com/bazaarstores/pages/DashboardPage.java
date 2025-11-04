package com.bazaarstores.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // By Locators
    private final By dashboard = By.xpath("//div[@class='products-grid']");
    private final By profileVisitChart = By.xpath("//div[@class='card-body']");
    private final By welcomeMessage = By.cssSelector(".welcome-message, [class*='welcome']");
    private final By profileLink = By.cssSelector("a[href*='profile'], button:contains('Profile')");
    private final By ordersLink = By.cssSelector("a[href*='orders'], button:contains('Orders')");
    private final By productsLink = By.cssSelector("a[href*='products'], button:contains('Products')");
    private final By logoutButton = By.cssSelector("button:contains('Logout'), a:contains('Logout')");
    private final By userName = By.cssSelector(".user-name, [class*='username']");
    private final By ProfileAdmin = By.xpath("//div[@class=\"avatar avatar-lg\"]");
    private final By menu = By.xpath("//div[@class=\"avatar avatar-lg\"]");
    private final By storemenuOption = By.xpath("//a[.//span[text()='Store']]");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Navigation Methods
    public void clickProfileLink() {
        click(profileLink);
    }

    public void clickOrdersLink() {
        click(ordersLink);
    }

    public void clickProductsLink() {
        click(productsLink);
    }

    public LoginPage clickLogout() {
        click(logoutButton);
        return new LoginPage(driver);
    }

    // Verification Methods
    public boolean isDashboardPageDisplayed() {
        return isDisplayed(dashboard);
    }

    public boolean isWelcomeMessageDisplayed() {
        return isDisplayed(welcomeMessage);
    }

    public String getWelcomeMessageText() {
        return getText(welcomeMessage);
    }

    public String getUserName() {
        return getText(userName);
    }

    public boolean isProfileLinkDisplayed() {
        return isDisplayed(profileLink);
    }

    public boolean isOrdersLinkDisplayed() {
        return isDisplayed(ordersLink);
    }

    public boolean isProductsLinkDisplayed() {
        return isDisplayed(productsLink);
    }

    public boolean isProfileVisitChartDisplayed() {
        return isDisplayed(profileVisitChart);
    }

    public boolean isProfiletAdminDisplayed() {
        return isDisplayed(ProfileAdmin);
    }

    public boolean ismenuDisplayed() {
        return isDisplayed(menu);
    }

    public boolean isStoerOptionDisplayed() {
        return isDisplayed(storemenuOption);
    }

    public boolean isStoerOptionEnabel() {
        return isEnabled(storemenuOption);
    }


    public StoresPage clickStoreMenuOption() {
        click(storemenuOption);
        return new StoresPage(driver);
    }

}

