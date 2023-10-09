@PartnerApp
#@Login

Feature: Login


  @configureDevice
  Scenario: Check if device can be added and configured
#    Given I set the environment for adding and configuring the device
    When I add an access point through the API
    When I click on "hamburger-menu" button
    And I click on "Customers" button
    And I search and select the customer
    And I click on "Access Points tab" button
    And I scroll and select the access point to be configured
    And I click on "configure" button
    Then The device should get configured

  @resetDevice
  Scenario: Check if device can be deleted and detached
#    Given I call the show reset button API
    When I click on "hamburger-menu" button
    And I click on "Customers" button
    And I search and select the customer
    And I click on "Access Points tab" button
    And I scroll and select the access point to be reset
    And I click on the reset device button
    And I search the access point to be deleted and delete it
#    And I click on "Phone - Back" button
#    And I click on "Detached Devices Tab" button
#    And I detach the deleted device
#    And I click on "Phone - Back" button
#    Then the device should get detached

  @lg3
  Scenario: Check if device can be added, configured, deleted and factory rest
    Given I set the environment for adding and configuring the device
    When I add an access point through the API
    When I click on "hamburger-menu" button
    And I click on "Customers" button
    And I search and select the customer
    And I click on "Access Points tab" button
    And I scroll and select the access point to be configured
    And I click on "configure" button
    Then The device should get configured

    And I click on "Phone - Back" button
    And I click on "Phone - Back" button
    And I click on "Phone - Back" button
    And I search the access point to be deleted and delete it
    And I click on "Phone - Back" button
    And I click on "Detached Devices Tab" button
    And I detach the deleted device
    And I click on "Phone - Back" button
    Then the device should get detached