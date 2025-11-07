package com.bazaarstores.stepDefinitions;

import com.bazaarstores.pages.AllPages;
import com.bazaarstores.utilities.ConfigReader;
import com.github.javafaker.Faker;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationSteps {

    AllPages pages = new AllPages();
    public static String email;
    public static String fullName;

    @When("user clicks registration link")
    public void user_clicks_registration_link() {
        pages.getLoginPage().clickRegisterLink();
    }

    @And("user enters email for sign up {string}")
    public void userEntersEmailForSignUp(String email) {
        RegistrationSteps.email = Faker.instance().internet().emailAddress();
        if (email.equals("faker")) {
            pages.getRegistrationPage().enterEmail(RegistrationSteps.email);
        } else {
            pages.getRegistrationPage().enterEmail(email);
        }
    }

    @And("user enters full name for sign up {string}")
    public void userEntersFullNameForSignUp(String fullName) {
        RegistrationSteps.fullName = fullName;
        pages.getRegistrationPage().enterName(fullName);
    }


    @And("user enters password for sign up")
    public void userEntersPasswordForSignUp() {
        pages.getRegistrationPage().enterPassword(ConfigReader.getDefaultPassword());
    }

    @And("user enters confirm password for sign up")
    public void userEntersConfirmPasswordForSignUp() {
        pages.getRegistrationPage().enterPasswordConfirmation(ConfigReader.getDefaultPassword());
    }

    @And("user clicks the sing up button")
    public void userClicksTheSingUpButton() {
        pages.getRegistrationPage().clickSignUp();
    }

    @Then("user should see success message for registration")
    public void userShouldSeeSuccessMessageForRegistration() {
        //This is a bug! It is already reported!!!
        assert false;
    }

    @Then("user should see invalid email error message")
    public void userShouldSeeInvalidEmailErrorMessage() {
        pages.getRegistrationPage().validateInvalidEmail();
    }

    @Then("user should see required field error messages")
    public void userShouldSeeRequiredFieldErrorMessages() {
        pages.getRegistrationPage().validateRequiredFieldErrors();
    }

    //--------------- [US01_TC004]  & [US01_TC005] ----------------------
    @And("user enters password for sign up {string}")
    public void user_enters_password_for_sign_up(String password) {
        pages.getRegistrationPage().enterPassword(password);
    }
    @And("user enters confirm password for sign up {string}")
    public void user_enters_confirm_password_for_sign_up(String confiPass) {
        pages.getRegistrationPage().enterPasswordConfirmation(confiPass);
    }
    @Then("user should see password length error message")
    public void userShouldSeePasswordLengthErrorMessage() {
        pages.getRegistrationPage().validatePasswordLengthError();
    }

    @Then("user should see Confirm Password mismatch error message")
    public void user_should_see_confirm_password_mismatch_error_message() {
        pages.getRegistrationPage().validatePasswordMismatchError();
    }

    //--------------- [US01_TC006] ----------------------
    @Then("user should see email taken error message")
    public void user_should_see_email_taken_error_message() {
        pages.getRegistrationPage().validateEmailTakenError();
    }

    //--------------- [US01_TC007] ----------------------
    @Then("user should see invalid characters in Name error message")
    public void user_should_see_invalid_characters_in_name_error_message() {
        pages.getRegistrationPage().invalidCharacterError();
    }
}
