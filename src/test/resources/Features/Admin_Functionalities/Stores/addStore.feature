@AddStoreAsAdmin
Feature: Add Store as Admin Feature


      Background:
      Given user goes to homepage
      When user enters email "admin@sda.com" and password "Password.12345"
      And user clicks login button
      And Admin clicks on Store Link


  @HappyPath
  Scenario: Add Store Happy Path
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin enter store description " This is a test store"
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""

  @Bug1
  Scenario: Add Store with Mandatory Fields Only
    When Admin clicks on Add store button
    And Admin enter store name ""
    And Admin enter store location ""
    And Admin enter store description ""
    And Admin clicks on Submit button
    Then Admin should see success message for adding store

  @MultipleStores
  Scenario: Add Multiple Storses Sequentially
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""


  @NegativeTests1
  Scenario: verify error message when Name is missing
    When Admin clicks on Add store button
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin enter store description " This is a test store"
    And Admin clicks on Submit button
    Then Admin should see Error message for "name" field


  @NegativeTests2
  Scenario: verify error message when Location is missing
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin select store admin"Store Manager"
    And Admin enter store description ""
    And Admin clicks on Submit button
    Then Admin should see Error message for "location" field



  @NegativeTests3 @Bug2
  Scenario: verify error message when Store Admin is missing
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin enter store description " This is a test store"
    And Admin clicks on Submit button
    Then Admin should see Error message for "admin" field



  @NegativeTests4
  Scenario: verify error message when Description is missing
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin clicks on Submit button
    Then Admin should see Error message for "description" field


  @BoundaryTests1  @Bug
  Scenario: Add Store with Descriptin Less than 255 characters
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length less" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""

  @BoundaryTests2
  Scenario: Add Store with Descriptin more than 255 characters
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length more" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see error message for description length store




  @BoundaryTests3 @Bug
  Scenario: Add Store with Descriptin exactly 255 characters
    When Admin clicks on Add store button
    And Admin enter store name "Bazaar Store 1"
    And Admin enter store location " New York"
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length equal" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name ""




