Feature: Feature to test signup at MailChimp

  @chimps
  Scenario Outline: Signup at MailChimp
    Given I have entered an "<email>"
    And I have also entered an "<username>"
    And I have also entered a "<password>"
    And I do not want to have any spams
    When I press Sign Up
    Then I will "<verify>"

    Examples: 
      | email  | username  | password | verify      |
      | nobody | ORiONGods | aQ!23456 | allgood     |
      | nobody | longUser  | aQ!23456 | uptohundred |
      | nobody | trump     | aQ!23456 | exists      |
      |        | ORiONGods | aQ!23456 | empty       |
