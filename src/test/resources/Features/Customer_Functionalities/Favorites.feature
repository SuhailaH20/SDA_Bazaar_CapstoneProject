@Smoke @UI @US07
Feature: US07 - Validate adding and managing favorites list

  Background:
    Given user is logged in

  @US07_TC001
  Scenario: Verify adding product to favorites works correctly
    When user clicks the heart icon on product "Flower"
    Then product should appear under "My Favorites" list
    And heart icon should not turn red on Home page
    But heart icon should turn red on Favorites page

  @US07_TC002
  Scenario: Verify message when adding same product twice to favorites
    Given product "Flower" is already in favorites
    When user clicks heart icon again
    Then system should display message "Error! Product is already in favorites."

  @US07_TC003
  Scenario: Validate that clicking heart icon adds product to favorites
    When user clicks the heart icon on product "Jeans"
    Then product should be added to favorites and heart icon should turn red

  @US07_TC004
  Scenario: Validate that 'My Favorites' page shows added products
    Given user has favorite products
    When user navigates to "My Favorites" page
    Then all added products should be displayed correctly

  @US07_TC005
  Scenario: Validate same product cannot be added twice to favorites
    Given product "Jeans" is already in favorites
    When user clicks heart icon again
    Then system should prevent adding duplicate product
