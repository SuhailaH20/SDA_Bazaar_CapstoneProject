package com.bazaarstores.pages;


import org.openqa.selenium.By;

public class DashboardPage extends BasePage {

    // By Locators
    private final By dashboard = By.xpath("//div[@class='products-grid']");
    private final By dashboardAdmin = By.xpath("//*[@id=\"sidebar\"]/div/div[2]/ul/li[2]/a");
    private final By profileVisitChart = By.xpath("//div[@class='card-body']");
    private final By welcomeMessage = By.cssSelector("h3.d-inline");
    private final By profileLink = By.cssSelector("a[href*='profile'], button:contains('Profile')");
    private final By ordersLink = By.cssSelector("a[href*='orders'], button:contains('Orders')");
    private final By productsLink =  By.xpath("//a[.//span[text()='Products']]");
    private final By logoutButton = By.xpath("//a[normalize-space()='Log Out']");
    private final By userName = By.cssSelector(".user-name, [class*='username']");
    private final By menu = By.xpath("//div[@class=\"sidebar-wrapper active ps\"]");//rajja
    private final By storeLink = By.xpath("//a[.//span[text()='Store']]");//rajja
    private final By usersMenu = By.xpath("//a[contains(.,'Users')]");

    private final By Profile= By.id("avatar");

    // Navigation Methods
    public void clickProfileLink() {
        click(Profile);
    }

    public void clickOrdersLink() {
        click(ordersLink);
    }

    public ProductsPage clickProductsLink() {
        waitForElementToBeVisible(productsLink);
        click(productsLink);
        return new ProductsPage();
    }

    public LoginPage clickLogout() {
        click(logoutButton);
        return new LoginPage();
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

    public boolean isMenuDisplayed() {//rajja1
        return isDisplayed(menu);
    }

    public boolean isStoreOptionDisplayed() {//rajja
        return isDisplayed(storeLink);
    }

    public boolean isStoreOptionEnabled() {//rajja
        return isEnabled(storeLink);
    }

    public void clickStoreMenuOption() {//rajja
        click(storeLink);
    }
    public UserPage goToUserPage() {
        click(usersMenu);
        return new UserPage();
    }
}