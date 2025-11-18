@AdminDeleteUser
Feature: Admin Delete User Functionality
  As an Admin
  I want to delete users and validate confirmation and API behavior

  Background:
    Given user goes to Dashboard
    And admin is logged in successfully
    And admin navigates to Users list page

#  @Positive @BUG
#  Scenario: BUG: US17_TC001 Verify admin can delete a Customer user
#    And admin clicks delete for the user with email "sara@test.com"
#    And admin confirms delete action
#    Then Admin should see success message "User deleted successfully."
#    And  verify user "sara@test.com" not exists in API
#
#  @Positive @BUG
#  Scenario: BUG: US17_TC002 Verify admin can delete a Store Manager user
#    And admin clicks delete for the user with email "123@example.com"
#    And admin confirms delete action
#    Then Admin should see success message "User deleted successfully."
#    And  verify user "123@example.com" not exists in API


  @Negative  @Smoke
  Scenario: US17_TC003 Verify admin cannot delete Admin user
    And admin clicks delete for the user with email "sara@test.com"
    And admin confirms delete action
    Then admin should see error message cannot delete admin user
    Then verify user "sara@test.com" still exists in API

#  @Negative
#  Scenario: US17_TC004 Verify canceling delete action
#    And admin clicks delete for the user with email "123@example.com"
#    And admin cancels delete action
#    Then verify user "123@example.com" still exists in API
