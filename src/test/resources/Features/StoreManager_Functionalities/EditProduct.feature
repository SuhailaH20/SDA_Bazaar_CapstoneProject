@EditProduct  @StoreManager
Feature: Edit Product Functionality

  Background:
    Given Store Manager is on the Login page
    When Store Manager enters valid credentials and clicks login
    And Store Manager clicks on Products button in the menu
    And a product with Name "Mouse" already exists

  @Positive
  Scenario: Verify editing an existing product with valid inputs
    When Store Manager clicks on the Edit button for product with Name "Mouse"
    And Store Manager updates the product with Name "Gaming Mouse", Price "25.99", Stock "55"
    And Store Manager clicks the Submit button
    Then a success message "Product updated successfully" should be displayed


  @Negative
  Scenario: Verify editing an existing product with invalid price values
    When Store Manager clicks on the Edit button for product with Name "Gaming Mouse"
    And Store Manager updates the product with Name "Gaming", Price "-25", Stock "55"
    And Store Manager clicks the Submit button
    Then an error message "The price must be a positive number." should be displayed

  @Negative
  Scenario: Verify editing an existing product with a missing required field
    When Store Manager clicks on the Edit button for product with Name "Gaming"
    And Store Manager updates the product with Name " ", Price "25.99", Stock "55"
    And Store Manager clicks the Submit button
    Then an error message "The name field is required." should be displayed






