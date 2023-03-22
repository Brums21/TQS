Feature: BlazeDemo Application
 
  Scenario: Login into the platform

    Given a website with the url "https://blazedemo.com/"

    When select "Portland" as origin and "Berlin" as destination
    
    Then the shown page title is "Flights from Portland to Berlin:"

