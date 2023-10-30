
Feature: Sign Up

    Scenario Outline: Successful sig up
    Given user in Sign up page
    When  user with usertype"<usertype>" set email with 'ibtihaj@gmail.com' and username with 'ibtihaj' and password with '592002' and confirmpassword with '592002' 
    Then the user should go to login page
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    Scenario Outline: password does not match confirm password
    Given user in Sign up page
    When  user with usertype"<usertype>" set email with 'ibtihaj@gmail.com' and username with 'ibtihaj' and password with '592002' and confirmpassword with '59111' 
    Then the user should recieve warning message and go back to sign page
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    