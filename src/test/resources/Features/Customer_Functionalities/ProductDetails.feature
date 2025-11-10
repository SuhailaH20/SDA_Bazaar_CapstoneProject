@Smoke @UI @US05    @Customer
Feature: US05 - Validate product details page functionality

  Background:
    Given product list is displayed

  @US05_TC001
  Scenario: Validate that product detail page opens when a product is clicked
    When user clicks on any product
    Then product detail page should open successfully

  @US05_TC002
  Scenario: Validate that product Name, Price, Description, and Image appear correctly
    When user clicks on a product
    Then Name, Price, and Image should be visible
    And Description should be displayed if available

  @US05_TC003
  Scenario: Validate system product description
    When user clicks on product "Laptop"
    Then description should be displayed as "<p>Description</p>"

  @US05_TC004
  Scenario: Validate system behavior when product has missing description
    When user clicks on product "Flower"
    Then system should show placeholder text "Description not available"
