# IMDB PROJECT

IMDB Project Testing is a project to training on QA Automation using the following:
	Java
	JUnit
	Selenium
	Cucumber
	Extent Report
_____________________________________


## Run All tests

`mvn clean test`

## Run Login Feature tests

`mvn clean test -Dcucumber.options=' --tags @SignIn'`

### Run Concrete error scenarios only

`mvn clean test -Dcucumber.options=' --tags @LoginIncorrect'`

##  Reporting

execution reports: target/cucumber-reports/report.html
