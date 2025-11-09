package com.bazaarstores.pages;

import com.bazaarstores.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StorsPage  extends BasePage{

private final By StoresHeader = By.tagName("h3");
private final By StorestableRows = By.xpath("//table//tbody//tr");
private final By StorestableHeaders = By.tagName("thead");
private final By AddStoreButton = By.xpath("//button[@class=\"btn btn-outline-primary no-hover  float-start float-lg-end\"]");



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
       click(AddStoreButton);
       return new AddStoreAsAdminPage();
   }


}
