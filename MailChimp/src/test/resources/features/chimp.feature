Feature: Feature to test signup at MailChimp

  @chimps
  Scenario Outline: Signup at MailChimp
    Given I have entered an "<EMAIL>"
    And I have also entered an "<USERNAME>"
    And I have also entered a "<PASSWORD>"
    And I say no to spam but yes to cookies
    When I press Sign Up
    Then I will check for "<VERIFY>"

    Examples: 
      | EMAIL  | USERNAME | PASSWORD | VERIFY      |
      | valid  | valid    | validPwd | allGood     |
      | valid  | longUser | aQ!23456 | hundredLong |
      | valid  | trump    | aQ!23456 | userExists  |
      | badAt  | valid    | aQ!23456 | noAtSign    |
      | domain | valid    | aQ!23456 | noDomain    |
      | noUser | valid    | aQ!23456 | noUserPart  |
      | valid  |          | aQ!23456 | someEmpty   |
      |        | valid    | aQ!23456 | someEmpty   |
