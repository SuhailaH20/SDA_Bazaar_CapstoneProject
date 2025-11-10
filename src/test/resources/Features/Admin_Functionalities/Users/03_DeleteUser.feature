@AdminDeleteUser    @AdminUser
Feature: Admin Delete User Functionality
  As an Admin
  I want to delete users and validate confirmation and API behavior

  Background:
    Given user goes to Dashboard
    And admin is logged in successfully
    And admin navigates to Users list page

  @Positive
  Scenario: BUG: US17_TC001 Verify admin can delete a Customer user
    And admin clicks delete for the user with email "sara@test.com"
    And admin confirms delete action
    Then BUG: System displayed "Error: You cant delete a admin role users!" instead of showing 'User deleted successfully.'
    And BUG: User "sara@test.com" remains in API

  @Positive
  Scenario: BUG: US17_TC002 Verify admin can delete a Store Manager user
    And admin clicks delete for the user with email "123@example.com"
    And admin confirms delete action
    Then BUG: System displayed "Error: You cant delete a admin role users!" instead of showing 'User deleted successfully.'
    And BUG: User "123@example.com" remains in API

  @Negative
  Scenario: US17_TC003 Verify admin cannot delete Admin user
    And admin clicks delete for the user with email "Osama@example.com"
    And admin confirms delete action
    Then admin should see error message cannot delete admin user
    Then verify user "Osama@example.com" still exists in API

  @Negative
  Scenario: US17_TC004 Verify canceling delete action
    And admin clicks delete for the user with email "123@example.com"
    And admin cancels delete action
    Then verify user "123@example.com" still exists in API
