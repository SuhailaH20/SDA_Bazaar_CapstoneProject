@Registration
Feature: Registration Feature

    #[US01_TC001]
  @HappyPathRegistration
  Scenario: Registration Happy Path
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up "faker"
    And user enters full name for sign up "John Doe"
    And user enters password for sign up
    And user enters confirm password for sign up
    And user clicks the sing up button
    Then user should see success message for registration
    And assert the registration via API


    #[US01_TC002] execution for BZRT3-78
  @NegativeRegistration @Smoke
  Scenario: Registration with empty name and email
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up ""
    And user enters full name for sign up ""
    And user enters password for sign up
    And user enters confirm password for sign up
    And user clicks the sing up button
    Then user should see required field error messages

      #[US01_TC003]
  @NegativeRegistration
  Scenario: Registration Negative
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up "invalid_email.com"
    And user enters full name for sign up "John Doe"
    And user enters password for sign up
    And user enters confirm password for sign up
    And user clicks the sing up button
    Then user should see invalid email error message
    And assert the negative registration via API using email "invalid_email.com"


  #[US01_TC004]
  @NegativeRegistration
  Scenario: Registration with short password
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up "john.doe@example.com"
    And user enters full name for sign up "John Doe"
    And user enters password for sign up "Abc1"
    And user enters confirm password for sign up "Abc1"
    And user clicks the sing up button
    Then user should see password length error message

    #[US01_TC005]
  @NegativeRegistration
  Scenario: Validate Confirm Password mismatch
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up "john.doe@example.com"
    And user enters full name for sign up "John Doe"
    And user enters password for sign up "Abc123"
    And user enters confirm password for sign up "Abc133"
    And user clicks the sing up button
    Then user should see Confirm Password mismatch error message

   #[US01_TC006]
@NegativeRegistration
Scenario: Validate existing email.
  Given user goes to homepage
  When user clicks registration link
  And user enters email for sign up "john.doe@example.com"
  And user enters full name for sign up "John Doe"
  And user enters password for sign up
  And user enters confirm password for sign up
  And user clicks the sing up button
  Then user should see email taken error message

  # Known Bug: The system allows numbers in Name field. Expected to restrict invalid characters.
   #[US01_TC007]
  @NegativeRegistration @Bug
  Scenario: Validate invalid characters in Name.
    Given user goes to homepage
    When user clicks registration link
    And user enters email for sign up "faker"
    And user enters full name for sign up "John 123"
    And user enters password for sign up
    And user enters confirm password for sign up
    And user clicks the sing up button
    Then user should see invalid characters in Name error message
