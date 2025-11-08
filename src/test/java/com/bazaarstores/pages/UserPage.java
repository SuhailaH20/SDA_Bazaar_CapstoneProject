package com.bazaarstores.pages.Users_Admin;

import com.bazaarstores.pages.BasePage;
import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.*;

public class UserPage extends BasePage {

    // ====== Locators ======
    private final By name = By.name("name");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By password_confirmation = By.name("password_confirmation");
    private final By role = By.name("role");
    private final By SubmitButton = By.xpath("//*[@id=\"multiple-column-form\"]/div/div/div/div[2]/div/form/div[2]/div/button");

    // ====== Validation Messages ======
    private final By requiredEmailMessage = By.xpath("//li[contains(text(),'The email field is required.')]");
    private final By requiredPasswordMessage = By.xpath("//li[contains(text(),'The password field is required.')]");
    private final By shortPasswordMessage = By.xpath("//li[contains(text(),'The password field must be at least 6 characters.')]");
    private final By mismatchPasswordMessage = By.xpath("//li[contains(text(),'The password field confirmation does not match.')]");
    private final By duplicateEmailMessage = By.xpath("//li[.='The email has already been taken.']");
    private final By successToast = By.xpath("//div[@class='toast toast-success']");


    // ====== Actions ======
    public UserPage enterName(String nameValue) {
        Driver.getDriver().findElement(name).clear();
        Driver.getDriver().findElement(name).sendKeys(nameValue);
        return this;
    }

    public UserPage enterEmail(String emailValue) {
        Driver.getDriver().findElement(email).clear();
        Driver.getDriver().findElement(email).sendKeys(emailValue);
        return this;
    }

    public UserPage enterPassword(String passwordValue) {
        Driver.getDriver().findElement(password).clear();
        Driver.getDriver().findElement(password).sendKeys(passwordValue);
        return this;
    }

    public UserPage enterConfirmPassword(String confirmValue) {
        Driver.getDriver().findElement(password_confirmation).clear();
        Driver.getDriver().findElement(password_confirmation).sendKeys(confirmValue);
        return this;
    }

    public UserPage selectRole(String roleValue) {
        Driver.getDriver().findElement(role).sendKeys(roleValue);
        return this;
    }

    public UserPage clickSubmit() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(SubmitButton));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", submitBtn);
   return this;
    }

    public void clickEditForUserAcrossPages(String userEmail) {
        boolean userFound = false;
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));

        while (true) {
        try {
             wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr")));

            //Get all rows from the current page
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//table//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
            try {
                // Read the email from the second column
                WebElement emailCell = Driver.getDriver().findElement(By.xpath("//table//tbody/tr[" + i + "]/td[2]"));

                String actualEmail = emailCell.getText().trim().replaceAll("\\s+", "");

                //Perform exact email comparison
                if (actualEmail.equalsIgnoreCase(userEmail)) {
                 System.out.println("Found exact match → " + actualEmail);

                 // Locate the Edit button in the same row
                 WebElement editBtn = Driver.getDriver().findElement(By.xpath("//table//tbody/tr[" + i + "]//i[contains(@class,'bi-pencil-square')]"));

                 // Scroll to the button and click it
                 ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", editBtn);
                 ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", editBtn);

                 userFound = true;
                 break;
                }
                } catch (NoSuchElementException ignored) {}
                }

                if (userFound) break;

                // Move to the next page if the 'Next' button is available
                WebElement nextBtn = Driver.getDriver().findElement(By.xpath("//*[@id=\"table-bordered\"]/div/div/div/div[2]/nav/ul/li[6]/a"));
                if (nextBtn.getAttribute("class").contains("disabled")) {
                System.out.println("X -> No more pages left!");

                break;
                }

                System.out.println("-> Moving to next page...");
                ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", nextBtn);
                ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", nextBtn);

                wait.until(ExpectedConditions.stalenessOf(rows.get(0)));

            } catch (Exception e) {
              break;
            }}
                 assertTrue(" User with email (" + userEmail + ") not found in any page!", userFound);
    }

    // ====== Validations ======
    public UserPage validateSuccessMessage(String expectedMsg) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));

        String actualMsg = toast.getText();
        assertTrue("Expected success message not found. Actual: " + actualMsg, actualMsg.contains(expectedMsg));

        return this;
    }


    // Generic validation method for any error message (DOM or browser popup).
    public UserPage ValidateErrorMessage(String expectedMsg) {

        // 1- Check for backend error message
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(4));
            WebElement backendMsg = wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//span[@class='fl-message']")));
            String backendText = backendMsg.getText().trim();
            System.out.println(" Backend error message found: " + backendText);
            return this;
        } catch (Exception ignore) {
        }

        // 2- Look for any on-page (DOM) validation error
        try {
            WebElement messageElement = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(),'" + expectedMsg + "')]")));

            String actualText = messageElement.getText().trim();
            assertTrue("Expected error message not found in page!", actualText.contains(expectedMsg));


        // 3- If not found in DOM, check if the browser itself triggered an HTML5 validation
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            WebElement emailField = Driver.getDriver().findElement(By.name("email"));
            String browserMsg = (String) js.executeScript("return arguments[0].validationMessage;", emailField);
            System.out.println("Browser validation message: " + browserMsg);

            // Assert that the browser validation message matches (case-insensitive & trimmed)
            assertTrue("Expected browser validation popup not found!\nExpected (part): " + expectedMsg + "\nActual: " + browserMsg,
            browserMsg.replaceAll("\\s+", " ").toLowerCase().contains(expectedMsg.replaceAll("\\s+", " ").toLowerCase().trim()));
        }
        return this;
    }


    // ====== Specific Validations ======

    public UserPage validateRequiredEmail() {
        assertEquals("The email field is required.",
        Driver.getDriver().findElement(requiredEmailMessage).getText());
        return this;
    }

    public UserPage validateRequiredPassword() {
        assertEquals("The password field is required.",
        Driver.getDriver().findElement(requiredPasswordMessage).getText());
        return this;
    }

    public UserPage validateShortPassword() {
        assertEquals("The password field must be at least 6 characters.",
        Driver.getDriver().findElement(shortPasswordMessage).getText());
        return this;
    }

    public UserPage validateMismatchPassword() {
        assertEquals("The password field confirmation does not match.",
        Driver.getDriver().findElement(mismatchPasswordMessage).getText());
        return this;
    }

    public UserPage validateDuplicateEmail() {
        assertEquals("The email has already been taken.",
        Driver.getDriver().findElement(duplicateEmailMessage).getText());
        return this;
    }
}
