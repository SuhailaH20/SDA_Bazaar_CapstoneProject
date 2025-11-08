@UI @Admin @ViewAllUsers
Feature: View all users in the system as an Admin

  Background:
    Given Admin is logged in and on the Dashboard page

  @Positive   @US14_TC001
  Scenario: Verify that admin can view all users in the system
    When Admin navigates to the Users page
    Then All users should be displayed with columns "NAME", "EMAIL", and "ACTIONS"

  @Positive   @US14_TC002
  Scenario: Verify search functionality with a valid email
    When Admin navigates to the Users page
    And Admin searches for user by email "admin@sda.com"
    Then User details with email "admin@sda.com" should be displayed

  @Positive   @US14_TC003
  Scenario: Verify search functionality with a valid domain
    When Admin navigates to the Users page
    And Admin searches for users with domain "@sda.com"
    Then All users with email domain "@sda.com" should be displayed

  @Negative   @US14_TC004
  Scenario: Verify search functionality with a non-existing email
    When Admin navigates to the Users page
    And Admin searches for user by email "test12@test.com"
    Then System should display message "No users found."

  @Negative   @US14_TC005
  Scenario: Verify search functionality with invalid email format
    When Admin navigates to the Users page
    And Admin searches for user by email "admin.sda.com"
    Then System should display message "No users found."

  @Negative   @US14_TC006
  Scenario: Verify behavior when search field is empty
    When Admin navigates to the Users page
    And Admin leaves the search field empty And clicks the search button
    Then All users should remain displayed with no changes

