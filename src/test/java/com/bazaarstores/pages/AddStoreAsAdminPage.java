package com.bazaarstores.pages;

import com.bazaarstores.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddStoreAsAdminPage extends BasePage {
    private final By NameField = By.id("first-name-column");
    private final By LocationField = By.id("location-id-column");
    private final By DescriptionField = By.tagName("p");
    private final By SelectAdmin = By.id("admin-column");
    private final By ClickSubmit = By.xpath("//button[@type=\"submit\"]");
    private final By iframelocater = By.xpath("//iframe");
    private final By ErrorMessageName = By.xpath("//li[.='The name field is required.']");
    private final By ErrorMessageDescription = By.xpath("//li[.='The description field is required.']");
    private final By ErrorMessageLocation = By.xpath("//li[.='The location field is required.']");
    private final By ErrorMessageAdminName = By.xpath("//li[.='The admin field is required.']");
    private final By ErrorMessageLongDescription = By.xpath("//li[.='The description field must not be greater than 255 characters.']");


    public AddStoreAsAdminPage enterStoreName(String storeName) {
        findElement(NameField).clear();
        sendKeys(NameField, storeName);
        return this;
    }

    public AddStoreAsAdminPage enterStoreLocation(String location) {
        findElement(LocationField).clear();
        sendKeys(LocationField, location);
        return this;
    }


    public AddStoreAsAdminPage enterStoreDescription(String description) {
        switchToFrame(iframelocater);
        sendKeys(DescriptionField, description);
        Driver.getDriver().switchTo().defaultContent();
        return this;}

    public AddStoreAsAdminPage selectAdminByName(String adminName) {
        selectByVisibleText(SelectAdmin, adminName);
        return this;
    }

    public AddStoreAsAdminPage ClickSubmitButton(){
        scrollToBottom();
        waitForElementToBeVisible(ClickSubmit);
        click(ClickSubmit);
        return this;
    }

    public AddStoreAsAdminPage fillAddStoreForm(String storeName, String location, String description, String adminName)  {
        enterStoreName(storeName);
        enterStoreLocation(location);
        enterStoreDescription(description);
        selectAdminByName(adminName);
        ClickSubmitButton();
        return this;
    }


    public AddStoreAsAdminPage isErrorMessageNameDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessageName));
        Assert.assertTrue(isDisplayed(ErrorMessageName));
        return this;

    }


 public AddStoreAsAdminPage isErrorMessageLocationDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessageLocation));
        Assert.assertTrue(isDisplayed(ErrorMessageLocation));
        return this;

    }


    public AddStoreAsAdminPage isErrorMessageDescriptionDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessageDescription));
        Assert.assertTrue(isDisplayed(ErrorMessageDescription));
        return this;

    }


    public AddStoreAsAdminPage isErrorMessageAdminNameDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessageAdminName));
        Assert.assertTrue(isDisplayed(ErrorMessageAdminName));
        return this;

    }

    public AddStoreAsAdminPage isErrorMessageLongDescriptionDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessageLongDescription));
        Assert.assertTrue(isDisplayed(ErrorMessageLongDescription));
        return this;

    }


    public void deselectAdmin() {
        selectByVisibleText(SelectAdmin, "Select User");

    }
}
