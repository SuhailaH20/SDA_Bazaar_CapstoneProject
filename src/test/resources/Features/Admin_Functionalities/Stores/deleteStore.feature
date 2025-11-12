@DeleteStoreAsAdmin   @AdminStore
Feature: Delete Store As Admin Feature



 Background:
 Given user goes to homepage
 When admin is logged in successfully
 And Admin clicks on Store Link
 And Admin clicks on Add store button
 And Admin Full Add Store Form with "Store delete", "Location", 3, "Description" to edit
 Then Admin should see success message for adding store


  @Smoke
  Scenario: veify Confirmation Dialog appers
    When Admin Click on Delete Button for "Store delete"
    Then Verify the Confirmation Dialog appers
    #delet after complet verify
     And Admin Clicks on Confirm Delete Button



  @Smoke
  Scenario: Verify Delete Button
    When Admin Click on Delete Button for "Store delete"
    And Admin Clicks on Confirm Delete Button
    Then Admin should see success message for delete store
    And Verify the store deleted successfully via API

  @Smoke
  Scenario: Verify Cancel Button
    When Admin Click on Delete Button for "Store delete"
    And  Admin Click on Cancel Button
    Then Verify store in the table
   #delet after complet verify
  When Admin Click on Delete Button for "Store delete"
  And Admin Clicks on Confirm Delete Button

  @Smoke
  Scenario: Verify When the admin Clicks outside the Confirmation Dialog The Store remains in the table
    When Admin Click on Delete Button for "Store delete"
    And Admin Click on Backdrop
    Then Verify store in the table






