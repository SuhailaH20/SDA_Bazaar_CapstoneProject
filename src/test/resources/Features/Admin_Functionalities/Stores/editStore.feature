@try
Feature: Edit Store As Admin Feature



    Background:
      Given user goes to homepage
      When user enters email "admin@sda.com" and password "Password.12345"
      And user clicks login button
      And Admin clicks on Store Link
      And Create a store via API with name "Store Edit"

    Scenario: Edit Store Happy Path
      When Admin click on Edit button for store "Store Edit"



