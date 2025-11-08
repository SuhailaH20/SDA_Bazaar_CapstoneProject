@UI @AddToCart
  Feature: Add Product to Cart Functionality
  As a customer
  I want to add products to my cart
  So that I can review them before purchasing

    Background:
      Given User is logged in and on the product page

    @POSITIVE @US06_TC001
  Scenario: Verify adding a single product to the cart
    When User clicks Add to Cart button for product "Flower"
    Then Product "Flower" should be added successfully and success message is displayed
    And cart count increases by 1

    @POSITIVE @US06_TC002
  Scenario: Verify adding multiple products to the cart
    When User clicks Add to Cart button for product "Laptop"
    And User clicks Add to Cart button for product "E-Book Reader"
    Then Both products "Laptop" and "E-Book Reader" should appear in the cart
    And cart count increases by 2
    And Cart subtotal should be correct

    @POSITIVE  @US06_TC003 @Bug
  Scenario: Verify adding the same product multiple times
    When User clicks Add to Cart button for product "Jeans"
    And User clicks Add to Cart button for product "Jeans"
    And User clicks Add to Cart button for product "Jeans"
    Then Product "Jeans" quantity should increase correctly
    And Cart subtotal should be correct
    And cart count increases by 3

    @Negative @US06_TC004 @Bug
  Scenario: Verify adding an out of stock product
    Given there is a product "pen_outOfStock" that is out of stock
    When User clicks Add to Cart button for product "pen_outOfStock"
    Then System should display error message "pen_outOfStock" is out of stock
    And cart count should not change
