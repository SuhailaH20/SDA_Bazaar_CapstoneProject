@AddStoreAsAdmin
Feature: Add Store as Admin Feature


      Background:
      Given user goes to homepage
      When admin is logged in successfully
      And Admin clicks on Store Link


  @HappyPath @Smoke
  Scenario: Add Store Happy Path
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin enter store description
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name

  @Bug
  Scenario: Add Store with Mandatory Fields Only
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin enter store description
    And Admin clicks on Submit button
    Then Admin should see success message for adding store

  @MultipleStores
  Scenario: Add Multiple Storses Sequentially
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name
    When Admin clicks on Add store button
    And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name


  @NegativeTests
  Scenario: verify error message when Name is missing
    When Admin clicks on Add store button
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin enter store description
    And Admin clicks on Submit button
    Then Admin should see Error message for "name" field


  @NegativeTests
  Scenario: verify error message when Location is missing
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin select store admin"Store Manager"
    And Admin enter store description
    And Admin clicks on Submit button
    Then Admin should see Error message for "location" field



  @NegativeTests @Bug
  Scenario: verify error message when Store Admin is missing
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin enter store description
    And Admin clicks on Submit button
    Then Admin should see Error message for "admin" field



  @NegativeTests
  Scenario: verify error message when Description is missing
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin clicks on Submit button
    Then Admin should see Error message for "description" field


  @BoundaryTests @Bug
  Scenario: Add Store with Descriptin Less than 255 characters
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length less" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name

  @BoundaryTests
  Scenario: Add Store with Description more than 255 characters
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length more" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see error message for description length store




   @Bug
  Scenario: Add Store with Description exactly 255 characters
    When Admin clicks on Add store button
    And Admin enter store name
    And Admin enter store location
    And Admin select store admin"Store Manager"
    And Admin enter store description with "length equal" than 255 characters
    And Admin clicks on Submit button
    Then Admin should see success message for adding store
    And Verify the store addition successfully via API with store name


   @Bug
    Scenario: Add Store with Formatted Description
      When Admin clicks on Add store button
      And Admin enter store name
      And Admin enter store location
      And Admin select store admin"Store Manager"
      And Admin enter store description with "Formatted" than 254 characters
      And Admin clicks on Submit button
      Then Admin should see success message for adding store
      And Verify the store addition successfully via API with store name




