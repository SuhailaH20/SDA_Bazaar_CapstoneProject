@Smoke @UI @US04    @Customer
Feature: US04 - Validate product display on customer home page

  Background:
    Given user is logged in as customer

  @US04_TC001
  Scenario: Validate that each product displays Name, Price, Description, and Image
    When user navigates to the customer home page
    Then each product should display "Name", "Price", "Image"
    And product "Laptop" should have a description "<p>Description</p>"
    And other products should have missing descriptions

  @US04_TC002
  Scenario: Validate that product list loads quickly (within 3 seconds)
    When user opens the customer home page
    Then the product list should load in less than or equal to 3 seconds

  @US04_TC003
  Scenario: Validate that product list handles empty inventory
    Given no products exist in the database
    When user opens the customer home page
    Then a message "No products available." should be displayed
