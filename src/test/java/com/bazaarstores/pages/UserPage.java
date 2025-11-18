package com.bazaarstores.pages;

import com.bazaarstores.pages.BasePage;
import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.*;

public class UserPage extends BasePage {

    // ====== Locators (Add/Edit) ======
    private final By name = By.name("name");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By password_confirmation = By.name("password_confirmation");
    private final By role = By.name("role");
    private final By SubmitButton = By.xpath("//*[@id=\"multiple-column-form\"]/div/div/div/div[2]/div/form/div[2]/div/button");

    // ====== Locators (delete) ======
    private final By confirmDeleteBtn = By.xpath("//button[contains(@class,'swal2-confirm') and contains(text(),'Yes, delete it!')]");
    private final By cancelDeleteBtn = By.xpath("//button[contains(@class,'swal2-cancel') and contains(text(),'Cancel')]");

    // ====== Locators (View all users) ======
    private final By searchField = By.cssSelector("input[placeholder='Search by email']");
    private final By searchButton = By.xpath("//button[contains(.,'Search')]");
    private final By usersTable = By.cssSelector("table");
    private final By tableHeaders = By.xpath("//table//th");
    private final By userRows = By.xpath("//table//tbody//tr");
    private final By paginationItems = By.xpath("//ul[contains(@class,'pagination')]//li[contains(@class,'page-item')]");

    // ====== Validation Messages ======
    private final By requiredNameMessage = By.xpath("//li[contains(text(),'The name field is required.')]");
    private final By requiredEmailMessage = By.xpath("//li[contains(text(),'The email field is required.')]");
    private final By requiredPasswordMessage = By.xpath("//li[contains(text(),'The password field is required.')]");
    private final By shortPasswordMessage = By.xpath("//li[contains(text(),'The password field must be at least 6 characters.')]");
    private final By mismatchPasswordMessage = By.xpath("//li[contains(text(),'The password field confirmation does not match.')]");
    private final By duplicateEmailMessage = By.xpath("//li[.='The email has already been taken.']");
   // private final By successToast = By.xpath("//div[@class='toast toast-success']");
    private final By successToast = By.xpath("//*[@id=\"toast-container\"]/div/div[3]");
    private final By successDeleteToast = By.xpath("//div[contains(text(),'User deleted successfully')]");
    private final By cannotDeleteAdminError = By.xpath("//*[@id=\"toast-container\"]/div/div[2]");


    // ====== Actions (Add/Edit) ======
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

        // Scroll to Submit button
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", submitBtn); // true = top
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", submitBtn);
        return this;
    }

    public void clickEdit(String userEmail) {
        boolean userFound = false;
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr")));

                //Get all rows from the current page
                List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//table//tbody/tr"));
                for (int i = 1; i <= rows.size(); i++) {
                    try {
                        // Read the email from the second column
                        WebElement emailCell = Driver.getDriver().findElement(By.xpath("//table//tbody/tr[" + i + "]/td[2]"));

                        String actualEmail = emailCell.getText().trim().replaceAll("\\s+", ""); //remove any space from email get from browser
                        System.out.println("🔎 Checking row " + i + " → [" + actualEmail + "]");

                        //Perform exact email comparison
                        if (actualEmail.equals(userEmail)) {
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

                // Get the "Next" button
                WebElement nextBtn = Driver.getDriver().findElement(By.xpath("//*[@id=\"table-bordered\"]/div/div/div/div[2]/nav/ul/li[last()]/a"));

                // Stop if Next is disabled
                if (nextBtn.getAttribute("class").contains("disabled")) {
                    System.out.println(" X-> No more pages left!");
                    break;
                }

                System.out.println("-> Moving to next page...");
                js.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
                js.executeScript("arguments[0].click();", nextBtn);

                // Wait for the table to reload before continuing search
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr[1]")));


            } catch (StaleElementReferenceException e) {
                System.out.println("Table reloaded, retrying...");
            } catch (Exception e) {
                break;
            }}
        assertTrue("User with email (" + userEmail + ") not found in any page!",userFound);
    }

    // ====== Validations (Add/Edit) ======
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
            browserMsg.replaceAll("\\s+", " ").toLowerCase().contains(expectedMsg.replaceAll("\\s+", " ").toLowerCase().trim())); // to make comparison more flexible and make sure spacing and case differences do not fail the test
        }
        return this;
    }


    // ====== Specific Validations (Add/Edit) ======
    public UserPage validateRequiredName() {
        assertEquals("The name field is required.",
        Driver.getDriver().findElement(requiredNameMessage).getText());
        return this;
    }

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


    // ====== Actions (Delete) ======
    public void clickDelete(String userEmail) {
        boolean userFound = false;
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table//tbody//tr")));

                //Get all user
                List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//table//tbody/tr"));
                for (int i = 1; i <= rows.size(); i++) {
                 WebElement emailCell = Driver.getDriver().findElement(By.xpath("//table//tbody/tr[" + i + "]/td[2]"));
                 String actualEmail = emailCell.getText().trim();
                 System.out.println("🔎 Checking row " + i + " → [" + actualEmail + "]");

                if (actualEmail.equalsIgnoreCase(userEmail)) {
                WebElement deleteBtn = Driver.getDriver().findElement(By.xpath("//table//tbody/tr[" + i + "]//i[contains(@class,'bi-trash3')]"));
                js.executeScript("arguments[0].scrollIntoView(true);", deleteBtn);
                js.executeScript("arguments[0].click();", deleteBtn);
                userFound = true;
                break;
                }}

                if (userFound) break;

                WebElement nextBtn = Driver.getDriver().findElement(By.xpath("//*[@id=\"table-bordered\"]/div/div/div/div[2]/nav/ul/li[last()]/a"));
                Thread.sleep(1000);

                // Check if the “Next” button is disabled (no more pages)
                if (nextBtn.getAttribute("class").contains("disabled")) {
                System.out.println("No more pages left!");
                break;
                }

                // Click on the next button
                js.executeScript("arguments[0].click();", nextBtn);
                Thread.sleep(1000);

            }   catch (Exception e) {
                break;
            }}
                assertTrue("X -> User with email (" + userEmail + ") not found in any page!", userFound);
    }

    public UserPage confirmDelete() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", confirmBtn);
        return this;
    }

    public UserPage cancelDelete() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement cancelBtn = wait.until(ExpectedConditions.elementToBeClickable(cancelDeleteBtn));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", cancelBtn);
        return this;
    }

    //This is a bug! It is already reported!!!
    public UserPage validateSuccessDeleteMessage() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successDeleteToast));
        assertTrue("Success delete message not displayed!", toast.isDisplayed());
        return this;
    }

    public UserPage validateCannotDeleteAdminError() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(8));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(cannotDeleteAdminError));
        assertTrue("Expected error message not displayed!", errorMsg.isDisplayed());
        return this;
    }

    // ====== Actions (View all users) ======

    // ---------- Table ----------
    public boolean isUsersTableDisplayedWithColumns(String name, String email ,String action) {
        waitForElementToBeVisible(usersTable);
        List<WebElement> headers = findElements(tableHeaders);
        List<String> header = new ArrayList<>();
        for (WebElement col : headers) {
            header.add(col.getText().trim());
        }
        return header.contains(name) & header.contains(email) & header.contains(action);
    }

    // ---------- Search ----------
    public UserPage searchUserByEmail(String email) {
        waitForElementToBeVisible(usersTable);
        sendKeys(searchField, email);
        click(searchButton);
        return this;
    }

    public UserPage clearSearchField() {
        waitForElementToBeVisible(usersTable);
        sendKeys(searchField, "");
        click(searchButton);
        return this;
    }

    // ---------- Validations ----------
    public boolean isUserDisplayedByEmail(String email) {
        waitForElementToBeVisible(usersTable);
        List<WebElement> rows = findElements(userRows);
        int count = 0;

        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText.contains(email)) {
                count++;
            }
        }
        return count == 1;
    }

    public boolean areUsersDisplayedByDomain(String domain) {
        waitForElementToBeVisible(usersTable);
        List<WebElement> rows = findElements(userRows);

        List<String> emails = new ArrayList<>();
        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText.contains("@")) {
                emails.add(rowText);
            }
        }
        return emails.stream().allMatch(email -> email.contains(domain));
    }

    public boolean isNoUsersFoundMessageDisplayed(String message) {
        waitForElementToBeVisible(usersTable);
        waitForElementToBeVisible(userRows);
        try {
            WebElement el = findElement(userRows);
            return el.getText().trim().equalsIgnoreCase(message);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areAllUsersDisplayed() {
        waitForElementToBeVisible(usersTable);

        List<WebElement> rows = findElements(userRows);
        boolean hasUsers = rows.size() > 1;

        List<WebElement> paginationElements = findElements(paginationItems);
        boolean hasMultiplePages = paginationElements.size() > 1;

        return hasUsers && hasMultiplePages;
    }
}