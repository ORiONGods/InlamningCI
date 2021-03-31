Feature: Feature to test signup at MailChimp
## let's do this!

	@chimps
  Scenario: Signup at MailChimp
    Given I have entered an email
    And I have also entered an username
    And I have also entered a password
    And I do not want to have any spams 
    When I press Sign Up
    Then the result should be at a verification page