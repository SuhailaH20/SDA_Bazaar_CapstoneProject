@DeleteProduct    @StoreManager
Feature: Delete Product Functionality

  Background:
    Given Store Manager is on the Login page
    When Store Manager enters valid credentials and clicks login
    And Store Manager clicks on Products button in the menu
    And a product with Name "Gaming Mouse" already exists

  @Smoke
  Scenario: Successfully delete a product
    When Store Manager clicks the Delete button for product with Name "Gaming Mouse"
    And Store Manager confirms the deletion
    Then a success message "Product deleted successfully" should be displayed
    And the product with Name "Gaming Mouse" should no longer be visible in the product list


  Scenario: Cancel a product deletion
    When Store Manager clicks the Delete button for product with Name "Mouse"
    And Store Manager cancels the deletion
    Then the product with Name "Mouse" should still be visible in the product list
