@SignIn
Feature: IMDB Login
  As a user
  I want to verify login Page with IMDB
  
@ViewSignIN  
Scenario: Verify IMDB SignIn is showed
	Given IMDB home page is showed
	When I Click on SignIN
	Then IMDB SignIN page is showed
	
@ViewSignInIMDB  
 Scenario: Verify IMDB SignIn with IMDB Page
	Given IMDB home page is showed
	When I Click on SignIN
	Then IMDB SignIN page is showed
	When I click on Sign In with IMDB 
	Then IMDB SignIn with IMDB Page is showed

@LoginIncorrect 
Scenario Outline: Login Incorrect - user not exist
	Given IMDB home page is showed
	When I Click on SignIN
	Then IMDB SignIN page is showed
	When I click on Sign In with IMDB 
	Then IMDB SignIn with IMDB Page is showed
	When I type <email>, <password> and submit
	Then an error message to user not found is displayed
 Examples:
    | 	email 						| password	| 
    | 	juan@noexiste.com | 1234567 	|
    | 	Luis@noexiste.com | 5 				|