@CommonAppFunctions
@StartStop

  Feature: Start and Stop App

    @startApp
    Scenario: I start the mentioned App
      Given I start the app

    @stopApp
    Scenario: I Stop the mentioned App
      Given I stop the app