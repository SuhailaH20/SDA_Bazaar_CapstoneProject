@DeleteProduct
Feature: Delete Product Functionality
  As a Store Manager
  I want to delete products from the catalog
  So that I can remove items that are no longer for sale.

  Background:
    Given Store Manager is on the "Products" page
    And a product with Name "Mouse" already exists


  Scenario: Successfully delete a product
    When Store Manager clicks the "Delete" button for product with Name "Mouse"
    And Store Manager confirms the deletion
    Then the product with Name "Mouse" should no longer be visible in the product list
    And the product with Name "Mouse" should be verified as deleted via API


  Scenario: Cancel a product deletion
    When Store Manager clicks the "Delete" button for product with Name "Mouse"
    And Store Manager cancels the deletion
    Then the product with Name "Mouse" should still be visible in the product list
    And the product with Name "Mouse" should be verified as not deleted via API
