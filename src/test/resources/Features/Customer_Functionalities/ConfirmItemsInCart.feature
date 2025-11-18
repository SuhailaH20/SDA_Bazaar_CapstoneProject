@UI @ConfirmItemsInCart   @Customer
Feature: Confirm items in the cart as a customer

  Background:
    Given User is logged in

  @Positive  @US09_TC001  @Bug
  Scenario: Verify confirming cart with valid items
    Given User has at least one product in the cart
    When User hovers over the cart icon
    And User clicks View Cart button
    And User clicks Confirm Cart button
    Then System should display order summary with items and total price
    And System should display success popup message "Your order has been received successfully."

  @Negative  @US09_TC002 @Smoke
  Scenario: Verify confirming empty cart
    Given User has no items in the cart
    When User hovers over the cart icon
    Then View Cart button should not be displayed
    And User should not be able to proceed to confirmation

  @Negative  @US09_TC003
  Scenario: Verify handling network error during confirmation
    Given User has at least one product in the cart
    When User hovers over the cart icon
    And User clicks View Cart button
    And Network connection is lost before clicking Confirm Cart button
    And User clicks Confirm Cart button
    Then System should display error message "Error! AxiosError: Network Error"
