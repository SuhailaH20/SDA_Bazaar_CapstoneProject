package com.bazaarstores.pages;


import com.bazaarstores.utilities.Driver;
import org.openqa.selenium.By;
import static org.junit.Assert.assertEquals;

public class RegistrationPage extends BasePage {


    private final By email = By.name("email");
    private final By name = By.name("name");
    private final By password = By.name("password");
    private final By password_confirmation = By.name("password_confirmation");
    private final By signUp = By.xpath("//button[.='Sign Up']");
    private final By invalidEmailMessage = By.xpath("//li[.='The email field must be a valid email address.']");
    private final By nameRequiredMessage = By.xpath("//li[.='The name field is required.']");
    private final By emailRequiredMessage = By.xpath("//li[.='The email field is required.']");
    private final By passwordLengthMessage = By.xpath("//li[contains(normalize-space(.), 'password')]");
    private final By passwordMismatchMessage = By.xpath("//li[contains(normalize-space(.), 'confirmation')]");
    private final By emailTakenMessage = By.xpath("//li[contains(normalize-space(.), 'email has already been taken')]");
    private final By invalidCharactersMessage = By.xpath("//li[contains(normalize-space(.), 'email has already been taken')]");

    public RegistrationPage enterEmail(String email) {
        Driver.getDriver().findElement(this.email).sendKeys(email);
        return this;
    }

    public RegistrationPage enterName(String name) {
        Driver.getDriver().findElement(this.name).sendKeys(name);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        Driver.getDriver().findElement(this.password).sendKeys(password);
        return this;
    }

    public RegistrationPage enterPasswordConfirmation(String confirmPassword) {
        Driver.getDriver().findElement(this.password_confirmation).sendKeys(confirmPassword);
        return this;
    }

    public RegistrationPage clickSignUp() {
        Driver.getDriver().findElement(signUp).click();
        return this;
    }

    public RegistrationPage validateInvalidEmail() {
        assertEquals(
                "The email field must be a valid email address.",
                Driver.getDriver().findElement(invalidEmailMessage).getText()
        );
        return this;
    }

    public RegistrationPage validateRequiredFieldErrors() {
        String actualNameError = Driver.getDriver().findElement(nameRequiredMessage).getText();
        String actualEmailError = Driver.getDriver().findElement(emailRequiredMessage).getText();

        assertEquals("The name field is required.", actualNameError);
        assertEquals("The email field is required.", actualEmailError);

        return this;
    }


    public RegistrationPage validatePasswordLengthError() {
        String actualMessage = Driver.getDriver()
                .findElement(passwordLengthMessage)
                .getText()
                .trim();

        System.out.println("Actual text received: [" + actualMessage + "]");

        assertEquals("The password field must be at least 6 characters.", actualMessage);
        return this;
    }

    public RegistrationPage validatePasswordMismatchError() {
        String actualMessage = Driver.getDriver()
                .findElement(passwordMismatchMessage)
                .getText()
                .trim();

        System.out.println("Actual text received: [" + actualMessage + "]");

        assertEquals("The password field confirmation does not match.", actualMessage);
        return this;
    }

    public RegistrationPage validateEmailTakenError() {
        String actualMessage = Driver.getDriver()
                .findElement(emailTakenMessage)
                .getText()
                .trim();

        System.out.println("Actual text received: [" + actualMessage + "]");

        assertEquals("The email has already been taken.", actualMessage);
        return this;
    }

    public RegistrationPage invalidCharacterError() {
        String actualMessage = Driver.getDriver()
                .findElement(invalidCharactersMessage)
                .getText()
                .trim();

        System.out.println("Actual text received: [" + actualMessage + "]");

        assertEquals("Name must contain only letters.", actualMessage);
        return this;
    }

}