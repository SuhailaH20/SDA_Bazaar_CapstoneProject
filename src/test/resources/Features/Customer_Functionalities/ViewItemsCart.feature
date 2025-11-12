@UI @ViewIّّtemsInCart    @Customer
Feature: View items in the cart as a customer

  Background:
    Given User is logged in

  @Positive  @US08_TC001 @Smoke
  Scenario: Verify viewing items in the popup cart
    Given User has at least one product in the cart
    When User hovers over the cart icon
    Then Popup cart should display product names, prices, and subtotal

  @Positive  @US08_TC002  @Bug
  Scenario: Verify viewing items on the cart page
    Given User has at least one product in the cart
    When User hovers over the cart icon
    And User clicks View Cart button
    Then Cart page should display all added items with their prices and total

  @Positive  @US08_TC003   @BugApi
  Scenario: Verify removing an item from the popup cart
    Given User has at least one product in the cart
    When User hovers over the cart icon
    And User clicks remove button for an item in the popup cart
    Then Item should be removed and cart count should decrease by 1
    And Success message should be displayed
    And The shopping cart should not contain the deleted product in backend

  @Positive  @US08_TC004  @Bug
  Scenario: Verify removing an item from the cart page
    Given User has at least one product in the cart
    When User hovers over the cart icon
    And User clicks View Cart button
    And User clicks remove button next to an item
    Then Item should be removed and total should update correctly
    And The shopping cart should not contain the deleted product in backend

  @Negative  @US08_TC005 @Smoke
  Scenario: Verify empty cart message
    Given User has no items in the cart
    When User hovers over the cart icon
    Then View Cart button should not be displayed
    And Message Your cart is empty should be displayed
    And Cart subtotal should show $0.00