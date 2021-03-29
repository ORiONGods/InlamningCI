Feature: Feature to test signup at MailChimp

	@chimps
  Scenario: Signup at MailChimp
    Given I have entered an email
    And I have also entered a username
    And I have also entered a password
    When I press Sign Up
    Then the result should be at a verification page