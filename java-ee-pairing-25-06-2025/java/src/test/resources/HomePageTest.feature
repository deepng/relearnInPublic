Feature: Test the various items in the Juice shop landing page

  Scenario: [welcome_test_1] Check if we get the welcome screen and I can dismiss it
    Given open Juice shop application
    Then see the welcome screen
    And welcome screen has a welcome message "Welcome to OWASP Juice Shop!"
    Then can dismiss the welcome screen


  Scenario: [welcome_test_2] Check if we get the welcome screen and I can launch the tutorial
    Given open Juice shop application
    Then see the welcome screen
    And welcome screen has a welcome message "Welcome to OWASP Juice Shop!"
    Then can launch the tutorial from the welcome screen
    And can dismiss the welcome screen after launching the tutorial
    Then get hacking instructions to "find the javascript code in dev tools"
    When click on the hacking instructions
    Then get hacking instruction to "Look through client side javascript"
    Then can dismiss the welcome screen after going through the tutorial
