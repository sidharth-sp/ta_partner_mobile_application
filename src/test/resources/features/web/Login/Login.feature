@PartnerApp
@Login

Feature: Login

  @loginPartner
  Scenario: Login The User
    Given I enter the username
    And I enter the password
    And I click on "login" button

  @loginSaams
  Scenario: Login User To Saams App
    Given I login to SAAMS

  @returnToHomePage
  Scenario: Return To HomePage
    Given I return to the Homepage

  @selectOrganisationSaams
  Scenario: I select a organisation in saams
    Given I select the organisation

  @selectPartner
  Scenario: Select a specific partner
    When I click on "hamburger-menu" button
    And I click on "Select-Partner-Dropdown" button
    And I select the partner