@ViewStorsAsAdmin   @AdminStore
Feature:View Store as Admin Feature

    Background:
     Given user goes to homepage
     When admin is logged in successfully



    Scenario: Verify Store option is available in sidebar menu
     When Admin views the sidebar menu
     Then Admin should see Store option
     And the Store option should be clickable



    Scenario: Verify Admin navigates to Store Page
      When Admin clicks on Store Link
      Then Admin should be navigated to Store page

@11
    Scenario: Verify Store Table Information Display
     When Admin clicks on Store Link
     Then Admin should be navigated to Store page
     And Table should display at least one store entry
     And Table should display information: NAME, DESCRIPTION, ADMIN NAME,ACTIONS





