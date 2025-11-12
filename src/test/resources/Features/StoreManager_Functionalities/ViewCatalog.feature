@StoreManager
Feature: Store Manager Product Catalog

  @StoreManager @T01Catalog @BugApi
  Scenario: Verify Store Manager can view the catalog of all products
    Given Store Manager is on the Login page
    When Store Manager enters valid credentials and clicks login
    And Store Manager clicks on Products button in the menu
    Then the catalog table should be displayed with all products

