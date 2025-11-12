@AdminLogout  @Login
Feature: Logout functionality

  Background:
    Given admin is logged in successfully

  #[US03_TC01]
  @AdminPositiveLogout @Smoke
  Scenario: Validate successful logout
    When admin clicks profile
    And admin clicks logout
    Then user should be redirected to login page

   #[US03_TC02]
  @PositiveLogout @AdminSessionTermination @Smoke
  Scenario: Validate session termination after logout
    When admin clicks profile
    And admin clicks logout
    And user tries to access the store URL directly
    Then user should be redirected to login page

    #[US03_TC03]
  @PositiveLogout @AutoLogout @Bug
  Scenario: Auto logout after 10 minutes of inactivity
    When user remains idle for 3 minutes
    Then user should be redirected to login page

