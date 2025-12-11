@smoke
Feature: User API tests

  Scenario: Create a new user
    Given the API is available
    And I load the input file "createUser.json"
    When I send "POST" request to "/users"
    Then the response status should be 201
    Then the response should match "createUser.json" ignoring fields
      | id |
      | createdAt |

#  Scenario: Create a new user
#    Given the API is available
#    And I load the input file "createUser1.json"
#    When I send "POST" request to "/users"
#    Then the response status should be 201
#    Then the response should match "createUser1.json" ignoring fields
