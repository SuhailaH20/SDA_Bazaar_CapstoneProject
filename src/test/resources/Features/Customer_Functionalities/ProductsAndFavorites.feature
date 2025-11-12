@UI @Customer
Feature: Customer views products and manages favorites

  Background:
    Given User is logged in and on the customer product page

  # US04 - Product List Display
  @US04 @Positive @US04_TC001
  Scenario: Validate product list displays name, price, and image
    Then All products should display name, price, and image

  @US04 @Positive @US04_TC002
  Scenario: Validate product list loads quickly
    Then Product list should load within 3 seconds

 # US05 - Product List Display Details
  @US05 @Positive @US05_TC001
  Scenario: Validate only Laptop product should display a description
    Then Each product may or may not have a description, but description field should be visible

  # US07 - Favorites
  @US07 @Positive @US07_TC001 @Smoke
  Scenario: Validate adding product to favorites
    When User clicks heart icon for product "Flower"
    Then Product "Flower" should appear in favorites list

  @US07 @Negative @US07_TC002 @Smoke
  Scenario: Validate error message when adding same product twice
    When User clicks heart icon for product "Flower"
    And User clicks heart icon for product "Flower" again
    Then Error message should be displayed "Error! Product is already in favorites."

  @US07 @Positive @US07_TC003 @Smoke
  Scenario: Validate removing product from favorites
    Given Product "Flower" is in favorites
    When User removes product "Flower" from favorites
    Then Product "Flower" should not be in favorites list
