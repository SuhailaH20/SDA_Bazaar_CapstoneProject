@Logout   @Login
Feature: Logout functionality

  Background:
    Given user goes to homepage
    And user enters email "customer@sda.com" and password "Password.12345"
    And user clicks login button
#user enters valid customer credentials
  #[US03_TC01]
  @PositiveLogout @CustomerLogout
  Scenario: Validate successful logout
    When user clicks profile
    And user clicks logout
    Then user should be redirected to login page


      #[US03_TC02]
  @PositiveLogout @SessionTermination
  Scenario: Validate session termination after logout
    When user clicks profile
    And user clicks logout
    And user tries to access the store URL directly
    Then user should be redirected to login page

    #[US03_TC03]
  @PositiveLogout @AutoLogout @Bug
  Scenario: Auto logout after 10 minutes of inactivity
    When user remains idle for 3 minutes
    Then user should be redirected to login page

