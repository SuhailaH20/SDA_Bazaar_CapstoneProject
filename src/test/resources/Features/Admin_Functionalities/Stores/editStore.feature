@EditStoreAsAdmin   @AdminStore
Feature: Edit Store As Admin Feature



    Background:
      Given user goes to homepage
      When admin is logged in successfully
      And Admin clicks on Store Link


       @HappyPath
        Scenario: Edit Store Name Happy Path
       When Admin clicks on Add store button
       And Admin Full Add Store Form with "Store Edit", "Location", 2, "Description" to edit
       Then Admin should see success message for adding store
       When Admin click on Edit button for store "Store Edit"
       And Admin enter store name
       And Admin clicks on Submit button
      Then Admin should see success message for update store
       And Verify the store update successfully via API "Name"



       @HappyPath
       Scenario: Edit Store Location Happy Path
         When Admin clicks on Add store button
         And Admin Full Add Store Form with "Store Edit", "Location", 2, "Description" to edit
         Then Admin should see success message for adding store
         When Admin click on Edit button for store "Store Edit"
         And Admin enter store location
         And Admin clicks on Submit button
         Then Admin should see success message for update store
         And Verify the store update successfully via API "Location"
          #delet after complet verify
         When Admin Click on Delete Button for "Store Edit"
         And Admin Clicks on Confirm Delete Button


       @HappyPath
       Scenario: Edit Store Description Happy Path
         When Admin clicks on Add store button
         And Admin Full Add Store Form with "Store Edit", "Location", 2, "Description" to edit
         Then Admin should see success message for adding store
         When Admin click on Edit button for store "Store Edit"
         And Admin enter store description
         And Admin clicks on Submit button
         Then Admin should see success message for update store
         And Verify the store update successfully via API "description"
          #delet after complet verify
         When Admin Click on Delete Button for "Store Edit"
         And Admin Clicks on Confirm Delete Button


        @HappyPath
        Scenario: Edit Store Admin Happy Path
          When Admin clicks on Add store button
          And Admin Full Add Store Form with "Store Edit", "Location", 2, "Description" to edit
          Then Admin should see success message for adding store
          When Admin click on Edit button for store "Store Edit"
          And Admin select store admin"Store Manager"
          And Admin clicks on Submit button
          Then Admin should see success message for update store
           #delet after complet verify
          When Admin Click on Delete Button for "Store Edit"
          And Admin Clicks on Confirm Delete Button



         @NegativeTests
         Scenario: verify error message when Name is Missing during Edit
           When Admin clicks on Add store button
           And Admin Full Add Store Form with "Store Edit", "Location", 2, "Description" to edit
           Then Admin should see success message for adding store
           When Admin click on Edit button for store "Store Edit"
           And Admin Leave the "name" field empty
           And Admin clicks on Submit button
           Then Admin should see Error message for "name" field






        @NegativeTests
        Scenario: verify error message when Location is Missing during Edit
          When Admin click on Edit button for store "Store Edit"
          And Admin Leave the "location" field empty
          And Admin clicks on Submit button
          Then Admin should see Error message for "location" field




        @NegativeTests @Bug
        Scenario: verify error message when Store Admin is Missing during Edit
          When Admin click on Edit button for store "Store Edit"
          And Admin Leave the "admin" field empty
          And Admin clicks on Submit button
          Then Admin should see Error message for "admin" field





        @NegativeTestsEditDescription
        Scenario: verify error message when Description is Missing during Edit
          When Admin click on Edit button for store "Store Edit"
          And Admin Leave the "description" field empty
          And Admin clicks on Submit button
          Then Admin should see Error message for "description" field


          @Bug
        Scenario: verify multiple error message when missing All feild
          When Admin click on Edit button for store "Store Edit"
          And Admin Leave the "All field" field empty
            And Admin clicks on Submit button
          Then Admin should see Error message for "All field" field



        @updateMiltipl
        Scenario: Edit all information
          When Admin click on Edit button for store "Store Edit"
          And Admin Full Add Store Form with "Name", "Location", "Store Manager", "Description"
          Then Admin should see success message for update store


















