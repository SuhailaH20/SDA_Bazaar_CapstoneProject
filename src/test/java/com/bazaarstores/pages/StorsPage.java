package com.bazaarstores.pages;

import com.bazaarstores.stepDefinitions.AdminStorsSteps;
import com.bazaarstores.utilities.Driver;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.bazaarstores.utilities.ApiUtilities.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StorsPage  extends BasePage{

private final By StoresHeader = By.tagName("h3");
private final By StorestableRows = By.xpath("//table//tbody//tr");
private final By StorestableHeaders = By.tagName("thead");
private final By AddStoreButton = By.xpath("//button[@class=\"btn btn-outline-primary no-hover  float-start float-lg-end\"]");
private final By SuccessAddMessage = By.xpath("//div[@class=\"toast toast-success\"]");
private final By SuccessUpdateMessag = By.xpath("//div[@class=\"toast-message\"]");
private final By ConfirmationDialogMessag = By.xpath("//div[@aria-labelledby=\"swal2-title\"]");
private final By ConfirmDeleteButton = By.xpath("//button[@class=\"swal2-confirm swal2-styled swal2-default-outline\"]");
private final By SuccessdeleteMessag = By.id("toast-container");////div[@class="toast-message"]
private final By CancelButton = By.xpath("//button[@class=\"swal2-cancel swal2-styled swal2-default-outline\"]");
private final By backdrop = By.xpath("//div[@class=\"swal2-container swal2-center swal2-backdrop-show\"]");



    public boolean isPageTitleDisplayed() {
        return isDisplayed(StoresHeader);
    }

    public StorsPage verifyStoresTableNotEmpty() {
        List<WebElement> tableRows = findElements(StorestableRows);
        Assert.assertTrue( "Store table is not displayed or has no rows.",tableRows.size() >= 1);
        return this;
    }

    public StorsPage verifyStoreTableHeaders(){

        List<String> expectedHeaders = List.of("NAME", "DESCRIPTION", "ADMIN NAME", "ACTIONS");
        for (String hedder :expectedHeaders){
            WebElement headerElement = findElement(StorestableHeaders);
            Assert.assertTrue("Header " + hedder + " is not displayed in the store table.",
                    headerElement.getText().contains(hedder));
        }
        return this;
    }


   public AddStoreAsAdminPage clickAddStoreButton(){

        waitForElementToDisappear(SuccessAddMessage);
       click(AddStoreButton);
       return new AddStoreAsAdminPage();
   }


    public StorsPage isSuccessMessageDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessAddMessage));
        Assert.assertTrue(isDisplayed(SuccessAddMessage));
        return this;

    }


    public StorsPage clickEditButtonForStore(String storeName) {
        String editIconXpath = String.format("//td[text()='%s']/following-sibling::td//i[@class='bi bi-pencil-square']", storeName);

       Driver.getDriver().findElement(By.xpath(editIconXpath)).click();

        return this;
    }



    public StorsPage isSuccessUpdatMessageDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessUpdateMessag));
        Assert.assertTrue(isDisplayed(SuccessUpdateMessag));
        return this;

    }



    public StorsPage clickdeleteButtonForStore(String storeName) {
        String editIconXpath = String.format("//td[text()='%s']/following-sibling::td//i[@class=\"bi bi-trash3\"]", storeName);

        waitForElementToDisappear(SuccessAddMessage);
        By deleteButtonLocator = By.xpath(editIconXpath);
        scrollToElement(deleteButtonLocator);
        click(deleteButtonLocator);

        return this;
    }


    public StorsPage isConfirmationDialogDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ConfirmationDialogMessag));
        Assert.assertTrue(isDisplayed(ConfirmationDialogMessag));
        return this;

    }


    public StorsPage  clickConfirmDeletStoreButton(){

        click(ConfirmDeleteButton);
        waitForElementToDisappear(ConfirmDeleteButton);

        return this;
    }


     public StorsPage isSuccessDeleteMessageDisplayed(){
        waitForElementToDisappear(ConfirmDeleteButton);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(SuccessdeleteMessag));
     isDisplayed(SuccessdeleteMessag);

            return this;}




    public StorsPage VirifyStoreDletedeViaAPI(){

        Response response = given(spec()).get("/stores");
        JsonPath jsonPath = response.jsonPath();
        String actual = jsonPath.getString("find{it.name=='" + AdminStorsSteps.createdStoreNameAPI + "'}.name");
        assertNull(actual);
        return this;
    }


    public StorsPage clickCancelButton(){
        click(CancelButton);
        return this;
    }



    public StorsPage VerifyStoreInTheTable(String storeName) {
        String stors = String.format("//td[.=\"%s\"]", storeName);

        Driver.getDriver().findElement(By.xpath(stors)).isDisplayed();

        return this;
    }

    public StorsPage clickbackdrop(){
        click(backdrop);
        return this;
    }




}
