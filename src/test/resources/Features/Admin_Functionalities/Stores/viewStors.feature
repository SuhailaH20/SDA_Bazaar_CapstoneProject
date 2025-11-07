
@ViewStorsAsAdmin
Feature:View Store as Admin

  Background:
    Given user goes to homepage
    When user enters email "admin@sda.com" and password "Password.12345"
    And user clicks login button



  Scenario: Verify Store option is available in sidebar menu
    When Admin views the sidebar menu
    Then Admin should see Store option
    And the Store option should be clickable


  @viewStorePage
    Scenario: Verify Admin navigates to Store Page
      When Admin clicks on Store Link
      Then Admin should be navigated to Store page


   @viewStoreTable
    Scenario: Verify Store Table Information Display
        When Admin clicks on Store Link
        Then Admin should be navigated to Store page
        And Table should display at least one store entry
        And Table should display information: NAME, DESCRIPTION, ADMIN NAME,ACTIONS





