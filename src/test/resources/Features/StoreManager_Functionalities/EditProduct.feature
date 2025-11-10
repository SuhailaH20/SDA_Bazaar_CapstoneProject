@EditProduct
Feature: Edit Product Functionality
  As a Store Manager
  I want to add new products to the catalog
  I want to edit existing product details
  So that I can keep product information accurate and handle invalid updates.

  Background:
    Given Store Manager is on the Login page
    When Store Manager enters valid credentials and clicks login
    And Store Manager clicks on Products button in the menu

  @Positive
  Scenario: Verify editing an existing product with valid inputs
    When Store Manager clicks on the Edit button for product with Name "Mouse"
    And Store Manager updates the product with Name "Gaming Mouse", Price "25.99", Stock "55"
    And Store Manager clicks the Submit button
    Then a success message "Product updated successfully" should be displayed
#    And the product with SKU "MS1001" should be verified with the updated details via API


  @Negative
  Scenario: Verify editing an existing product with invalid price values
    When Store Manager clicks on the Edit button for product with Name "Gaming Mous"
    And Store Manager updates the product with Name "Gaming", Price "-25", Stock "55"
    And Store Manager clicks the Submit button
    Then an error message "The price must be a positive number." should be displayed
#    And the product with SKU "MS1001" should have its original details verified via API


  @Negative
  Scenario: Verify editing an existing product with a missing required field
    When Store Manager clicks on the Edit button for product with Name "Gaming"
    And Store Manager updates the product with Name " ", Price "25.99", Stock "55"
    And Store Manager clicks the Submit button
    Then an error message "The name field is required." should be displayed
#    And the product with SKU "MS1001" should have its original details verified via API







