@AdminAddUser   @AdminUser
Feature: Admin Add User Functionality
  As an Admin
  I want to be able to add users from the Add User Page
  So that I can manage users in the system

  Background:
    Given User Goes To Dashboard
    And admin is logged in successfully
    And admin navigates to Add User page


  @Positive
  Scenario: US15_TC001 Verify adding a new user with valid data *
    And admin enters name "Sara Ahmed"
    And admin enters email "sara@test.com"
    And admin enters password "Pass@1234"
    And admin enters password confirmation "Pass@1234"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see success message "User created successfully"
    And verify user exists in API

  @Negative
  Scenario: US15_TC002 Verify error when Email field is empty
    And admin enters name "Sara"
    And admin enters email ""
    And admin enters password "Pass@1234"
    And admin enters password confirmation "Pass@1234"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "The email field is required."
    And verify user with name "Sara" and email "" does not exist in API

  @Negative
  Scenario: US15_TC003 Verify error when Password field is empty
    And admin enters name "Lama"
    And admin enters email "Lama@example.com"
    And admin enters password ""
    And admin enters password confirmation "Pass@1234"
    And admin selects role "Admin"
    And admin clicks Submit button
    Then admin should see error message "The password field is required."
    And verify user with name "Lama" and email "Lama@example.com" does not exist in API


  @Negative
  Scenario: US15_TC004 Verify error for password less than 6 chars
    And admin enters name "Lama"
    And admin enters email "Lama66@example.com"
    And admin enters password "123"
    And admin enters password confirmation "123"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "The password field must be at least 6 characters."
    And verify user with name "Lama" and email "Lama66@example.com" does not exist in API


  @Negative
  Scenario: US15_TC005 Verify error when Role is not selected
    And admin enters name "Lena"
    And admin enters email "Lena@example.com"
    And admin enters password "123456789"
    And admin enters password confirmation "123456789"
    And admin selects role ""
    And admin clicks Submit button
    Then admin should see error message "The role field is required."
    And verify user with name "Lena" and email "Lena@example.com" does not exist in API

  @Negative
  Scenario: US15_TC006 Verify error when passwords don’t match
    And admin enters name "Lena"
    And admin enters email "Lena67@example.com"
    And admin enters password "12345678Ll"
    And admin enters password confirmation "12345678ll"
    And admin selects role "Store Manager"
    And admin clicks Submit button
    Then admin should see error message "The password field confirmation does not match."
    And verify user with name "Lena" and email "Lena67@example.com" does not exist in API

  @Negative
  Scenario: US15_TC007 Verify invalid email without “@” shows error
    And admin enters name "Rahaf"
    And admin enters email "Rahafexample.com"
    And admin enters password "1234567Rr"
    And admin enters password confirmation "1234567Rr"
    And admin selects role "Store Manager"
    And admin clicks Submit button
    Then admin should see error message "Please include an '@' in the email address. 'Rahafexample.com' is missing an '@'."
    And verify user with name "Rahaf" and email "Rahafexample.com" does not exist in API

  @Negative
  Scenario: US15_TC008 Verify error for incomplete email after “@”
    And admin enters name "Dana"
    And admin enters email "Dana@"
    And admin enters password "12Dd35678"
    And admin enters password confirmation "12Dd35678"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "Please enter a part following '@'. 'Dana@' is incomplete."
    And verify user with name "Dana" and email "Dana@" does not exist in API

  @Negative
  Scenario: US15_TC009 Verify error for duplicate email
    And admin enters name "Lama Mahalawi"
    And admin enters email "sara@test.com"
    And admin enters password "12345678Ll"
    And admin enters password confirmation "12345678Ll"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "The email has already been taken."
    And verify user with name "Lama Mahalawi" and email "sara@test.com" does not exist in API

  @Negative
  Scenario: US15_TC010 Verify error when email starts with “@”
    And admin enters name "Rana"
    And admin enters email "@example.com"
    And admin enters password "1234Rana"
    And admin enters password confirmation "1234Rana"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "Please enter a part followed by '@'. '@example.com' is incomplete."
    And verify user with name "Rana" and email "@example.com" does not exist in API

  @Negative
  Scenario: US15_TC011 Verify error when email has multiple “@”
    And admin enters name "Rana"
    And admin enters email "Rana@@example.com"
    And admin enters password "RanaCamell"
    And admin enters password confirmation "RanaCamell"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "A part following '@' should not contain the symbol '@'"
    And verify user with name "Rana" and email "Rana@@example.com" does not exist in API

  @Negative
  Scenario: US15_TC012 Verify error for invalid dot position in email
    And admin enters name "Ali"
    And admin enters email "Ali@example."
    And admin enters password "1234Ali"
    And admin enters password confirmation "1234Ali"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "'.' is used at a wrong position in 'example.'"
    And verify user with name "Ali" and email "Ali@example." does not exist in API



  @Negative
  Scenario: US15_TC013 Verify error for spaces in email
    And admin enters name "Asayl"
    And admin enters email "Asayl @example.com"
    And admin enters password "Aa123123"
    And admin enters password confirmation "Aa123123"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "A part followed by '@' should not contain the symbol ' '."
    And verify user with name "Asayl" and email "Asayl @example.com" does not exist in API


  @Negative
  Scenario: US15_TC014 Verify error for double dots in email
    And admin enters name "Asayl"
    And admin enters email "Asayl@example..com"
    And admin enters password "Aa123123"
    And admin enters password confirmation "Aa123123"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then admin should see error message "'.' is used at a wrong position in 'example..com'"
    And verify user with name "Asayl" and email "Asayl@example..com" does not exist in API


  @Negative
  Scenario: BUG: US15_TC015 Verify system accepts email missing domain *
    And admin enters name "Diyala"
    And admin enters email "Diyala@example"
    And admin enters password "Diyala12"
    And admin enters password confirmation "Diyala12"
    And admin selects role "Store Manager"
    And admin clicks Submit button
    Then BUG: system accepts invalid data instead of showing error "Please include a valid domain (e.g., .com, .net)." with note "System accepts invalid email without domain"
    And verify user exists in API with invalid data


  @Negative
  Scenario: BUG: US15_TC016 Verify system accepts name without letters *
    And admin enters name "@"
    And admin enters email "123@example.com"
    And admin enters password "D1234567"
    And admin enters password confirmation "D1234567"
    And admin selects role "Store Manager"
    And admin clicks Submit button
    Then BUG: system accepts invalid data instead of showing error "Name must contain letters." with note "BUG: System accepted invalid name without alphabetic characters."
    And verify user exists in API with invalid data

  @Negative
  Scenario: BUG: US15_TC017 Verify system accepts uppercase email *
    And admin enters name "Rawan"
    And admin enters email "RAWAN@EXAMPLE.COM"
    And admin enters password "Ra1234567"
    And admin enters password confirmation "Ra1234567"
    And admin selects role "Customer"
    And admin clicks Submit button
    Then BUG: system accepts invalid data instead of showing error "Email must be in lowercase format." with note "BUG: System accepted uppercase email instead of validating lowercase."
    And verify user exists in API with invalid data

  @Negative
  Scenario: BUG: US15_TC018 Verify system accepts password with space *
    And admin enters name "Osama"
    And admin enters email "Osama@example.com"
    And admin enters password "123 4567"
    And admin enters password confirmation "123 4567"
    And admin selects role "Admin"
    And admin clicks Submit button
    Then BUG: system accepts invalid data instead of showing error "Spaces are not allowed in password." with note "BUG: System accepted password containing spaces and allowed user creation."
    And verify user exists in API with invalid data

  @Negative
  Scenario: US15_TC019 Verify error when Name field is empty
    And admin enters name ""
    And admin enters email "Lama@example.com"
    And admin enters password "Pass@1234"
    And admin enters password confirmation "Pass@1234"
    And admin selects role "Admin"
    And admin clicks Submit button
    Then admin should see error message "The name field is required."
    And verify user with name "" and email "Lama@example.com" does not exist in API