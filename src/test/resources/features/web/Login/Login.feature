@PartnerApp
@Login

Feature: Login

  @LoginPartner
  Scenario: Login The User
    Given I enter the username
    And I enter the password
    And I click on "login" button

  @returnToHomePage
  Scenario: Return To HomePage
    Given I return to the Homepage
