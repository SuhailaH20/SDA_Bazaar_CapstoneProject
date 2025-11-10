
@AdminEditUser
Feature: Admin Edit User Functionality
  As an Admin
  I want to edit existing users and validate updates and validations via UI and API

  Background:
    Given user goes to Dashboard
    And admin is logged in successfully
    And admin navigates to Users list page


  @Positive
  Scenario: US16_TC001 Verify editing user name
    And admin clicks edit for the user with email "RAWAN@EXAMPLE.COM"
    And admin updates name to "Sara Mohamad"
    And admin enters Password "Ra1234567"
    And admin enters Password Confirmation "Ra1234567"
    And Admin Clicks Submit Button
    Then admin should see update success message "User updated successfully"
    And verify user with name "Sara Mohamad" and email "RAWAN@EXAMPLE.COM" updated in API

  @Positive
  Scenario: US16_TC002 Verify editing user email
    And admin clicks edit for the user with email "RAWAN@EXAMPLE.COM"
    And admin updates email to "sara12@test.com"
    And admin enters Password "Ra1234567"
    And admin enters Password Confirmation "Ra1234567"
    And Admin Clicks Submit Button
    Then admin should see update success message "User updated successfully"
    And verify user with name "Sara Mohamad" and email "sara12@test.com" updated in API

  @Positive
  Scenario: US16_TC003 Verify editing user password
    And admin clicks edit for the user with email "sara12@test.com"
    And admin updates password to "Pass@123"
    And admin enters Password Confirmation "Pass@123"
    And Admin Clicks Submit Button
    Then admin should see update success message "User updated successfully"
    And verify user with name "Sara Mohamad" and email "sara12@test.com" updated in API

  @Positive
  Scenario: US16_TC004 Verify editing user role
    And admin clicks edit for the user with email "sara12@test.com"
    And admin updates role to "Store Manager"
    And admin enters Password "Pass@123"
    And admin enters Password Confirmation "Pass@123"
    And Admin Clicks Submit Button
    Then admin should see update success message "User updated successfully"
    And verify user with email "sara12@test.com" updated role "Store Manager" in API


  @Negative
  Scenario: BUG: US16_TC005 Verify error when name is empty
    And admin clicks edit for the user with email "Diyala@example"
    And admin updates name to ""
    And admin enters Password "Diyala12"
    And admin enters Password Confirmation "Diyala12"
    And Admin Clicks Submit Button
    Then BUG: system restores old value instead of showing error "The name field is required."
    And admin updates name to ""
    And Admin Clicks Submit Button
    Then system displayed a backend error message instead of "The name field is required."
    And verify user with name "Diyala" and email "Diyala@example" is not updated in API


  @Negative
  Scenario: BUG: US16_TC006 Verify error when email is empty
    And admin clicks edit for the user with email "Diyala@example"
    And admin updates email to ""
    And admin enters Password "Diyala12"
    And admin enters Password Confirmation "Diyala12"
    And Admin Clicks Submit Button
    Then BUG: system restores old value instead of showing error "The email field is required."
    And admin updates email to ""
    And Admin Clicks Submit Button
    Then system displayed a backend error message instead of "The email field is required."
    And verify user with name "Diyala" and email "Diyala@example" is not updated in API


  @Negative
  Scenario: BUG: US16_TC007 Verify system accepts empty password
    And admin clicks edit for the user with email "Diyala@example"
    And admin enters Password ""
    And admin enters Password Confirmation "Diyala12"
    And Admin Clicks Submit Button
    Then BUG: System accepts invalid data instead of showing error "The password field is required" with note "BUG: System accepted empty password"
    And Verify user update "Diyala@example" in API with invalid data

  @Negative
  Scenario: BUG: US16_TC008 Verify system accepts empty confirm password
    And admin clicks edit for the user with email "Diyala@example"
    And admin enters Password "Diyala12"
    And admin enters Password Confirmation ""
    And Admin Clicks Submit Button
    Then BUG: System accepts invalid data instead of showing error "Password confirmation does not match." with note "BUG: System accepted empty Password Confirmation"
    And Verify user update "Diyala@example" in API with invalid data


  @Negative
  Scenario: BUG: US16_TC009 Verify error when role is null
    And admin clicks edit for the user with email "Diyala@example"
    And admin updates role to "Select a Role"
    And admin enters Password "Diyala12"
    And admin enters Password Confirmation "Diyala12"
    And Admin Clicks Submit Button
    Then BUG: system restores old value instead of showing error "The role field is required."
    And admin updates role to "Select a Role"
    And Admin Clicks Submit Button
    Then system displayed a backend error message instead of "The role field is required."
    And verify user with email "Diyala@example" and role "Select a Role" is not updated in API


  @Negative
  Scenario: BUG: US16_TC010 Verify system accepts weak password
    And admin clicks edit for the user with email "sara@test.com"
    And admin enters Password "123"
    And admin enters Password Confirmation "123"
    And Admin Clicks Submit Button
    Then BUG: System accepts invalid data instead of showing error "The password must be at least 6 characters." with note "BUG: System accepted weak password"
    And Verify user update "sara@test.com" in API with invalid data


  @Negative
  Scenario: BUG: US16_TC011 Verify error for duplicate email
    And admin clicks edit for the user with email "sara@test.com"
    And admin updates email to "sara12@test.com"
    And admin enters Password "123"
    And admin enters Password Confirmation "123"
    And Admin Clicks Submit Button
    Then BUG: system restores old value instead of showing error "The email has already been taken."
    And admin updates email to "sara12@test.com"
    And Admin Clicks Submit Button
    Then system displayed a backend error message instead of "The email has already been taken."
    And verify user with name "Sara Ahmed" and email "sara@test.com" is not updated in API