@AddProduct   @StoreManager
Feature: Add Product Functionality
  As a Store Manager
  I want to add new products to the catalog
  So that I can manage the store's inventory and sales

  Background:
    Given Store Manager is on the Login page
    When Store Manager enters valid credentials and clicks login
    And Store Manager clicks on Products button in the menu
    And Store Manager clicks on ADD PRODUCT button

@Test001
Scenario: Successfully add a product with All required fields
  And Store Manager fills in product details with Name "Mouse", Price "29.99", Stock "50", and SKU "MS01001"
  And Store Manager clicks the Submit button
  Then a success message "Product created successfully" should be displayed
  And the product with SKU "MS1001" should be verified via API response

  @Test2
  Scenario: Attempt to add a product with a missing Name
    And Store Manager fills in product details with Name "", Price "29.99", Stock "50", and SKU "MS11021"
    And Store Manager clicks the Submit button
    Then an error message "The name field is required." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test3
  Scenario: Attempt to add a product with a missing Price
    And Store Manager fills in product details with Name "Mouse", Price "", Stock "50", and SKU "MS100091"
    And Store Manager clicks the Submit button
    Then an error message "The price field is required." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test4
  Scenario: Attempt to add a product with a missing Stock
    And Store Manager fills in product details with Name "Mouse", Price "29.99", Stock "", and SKU "MS1004451"
    And Store Manager clicks the Submit button
    Then an error message "The stock field is required." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test5
  Scenario: Attempt to add a product with a missing SKU
    And Store Manager fills in product details with Name "Mouse", Price "29.99", Stock "50", and SKU ""
    And Store Manager clicks the Submit button
    Then an error message "The sku field is required." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test6
  Scenario: Attempt to add a product with a duplicate SKU
    Given a product with SKU "MS01001" already exists
    And Store Manager fills in product details with Name "Mouse", Price "66.99", Stock "58", and SKU "MS01001"
    And Store Manager clicks the Submit button
    Then an error message "The SKU has already been taken." should be displayed
#    And verify product list via API response (Only the original product should exist)

  @Test7
  Scenario: Attempt to add a product with non-numeric Price
    And Store Manager fills in product details with Name "Mouse", Price "ABC", Stock "50", and SKU "MS109801"
    And Store Manager clicks the Submit button
    Then an error message "The price field is required." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test8
  Scenario: Attempt to add a product with negative Price
    And Store Manager fills in product details with Name "Mouse", Price "-10", Stock "50", and SKU "MS10301"
    And Store Manager clicks the Submit button
    Then an error message "The price must be a positive number." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test9
  Scenario: Attempt to add a product with negative Stock
    And Store Manager fills in product details with Name "Mouse", Price "29.99", Stock "-5", and SKU "MS107701"
    And Store Manager clicks the Submit button
    Then an error message "The stock must be a positive number." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test10
  Scenario: Attempt to add a product with zero Stock
    And Store Manager fills in product details with Name "Mouse", Price "29.99", Stock "0", and SKU "MS10201"
    And Store Manager clicks the Submit button
    Then an error message "The stock must be greater than zero." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test11
  Scenario: Attempt to add a product with Price set to zero
    And Store Manager fills in product details with Name "Mouse", Price "0", Stock "50", and SKU "MS10101"
    And Store Manager clicks the Submit button
    Then an error message "The price must be greater than zero." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test12
  Scenario: Attempt to add a product with a Name of exactly 255 characters
    And Store Manager fills in product details with Name "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Price "99.99", Stock "10", and SKU "NAME255"
    And Store Manager clicks the Submit button
    Then a success message "Product created successfully" should be displayed
#    And verify product list via API response (Product should be created)

  @Test13
  Scenario: Attempt to add a product with a Name of 256 characters
    And Store Manager fills in product details with Name "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Price "99.99", Stock "10", and SKU "NAME256A"
    And Store Manager clicks the Submit button
    Then an error message "The name field must not be greater than 255 characters." should be displayed
#    And verify product list via API response (Product should not be created)

  @Test14
  Scenario: Attempt to add a product with a Name of 254 characters
    And Store Manager fills in product details with Name "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Price "99.99", Stock "10", and SKU "NAME254"
    And Store Manager clicks the Submit button
    Then a success message "Product created successfully" should be displayed
#    And verify product list via API response (Product should be created)


