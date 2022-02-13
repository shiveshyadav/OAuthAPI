Feature:

  @OAuth @getuserdetail
  Scenario Outline: Obtain user code details
    Given OAuth service is up and running
    When user requests for user code with <client_id> <audience> <scope>
    Then verify <statusCode>
    Examples:
    |client_id|audience|scope|statusCode|
    |'nZ8JDrV8Hklf3JumewRl2ke3ovPZn5Ho'|'urn:my-videos'|'offline_access+openid+profile'|200|
    |'nZ8JDrV8Hklf3JumewRl2ke3ovPZn5Ho'|'urn:my-videos1'|'offline_access+openid+profile'|400|
    |'nZ8JDrV8Hklf3JumewRl2ke3ovPZn5Ho'|'urn:my-videos'|''|200|
    |'nZ8JDrV8Hklf3JumewRl2ke3ovPZn5H1'|'urn:my-videos'|'offline_access+openid+profile'|403|



  @OAuth @activateuserCode
  Scenario Outline: Auth api redirects to new confirmation url
    Given OAuth service is up and running
    When user requests for user code with <client_id> <audience> <scope>
    And user get response verification uri
    Then user can access verification uri to auth and <statusCode>
    Examples:
      |client_id|audience|scope|statusCode|
      |'nZ8JDrV8Hklf3JumewRl2ke3ovPZn5Ho'|'urn:my-videos'|'offline_access+openid+profile'|302|


