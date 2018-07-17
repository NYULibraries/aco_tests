# aco_tests
Tests for ACO site front-end.\
url: dlib.nyu.edu/aco 

Test suites will remotely run internet browsers to conduct searches 
on the site, and assert expected or announce unexpected outcomes. 
The program is conducted with a Maven POM file that manages dependencies, 
such as Selenium API which manipulates WebElements for a search and check.

## Getting Started

This repository is composed of the following: 
* This README 
* All source files: .xml, maven libraries, etc. 
* Java Classes: Query.java, Constants.java 
* Tests Classes: Acceptance.java, SearchTest.java
* Input/Output CSV

### Installing

Install all Maven dependencies from the POM with the following maven command

```
mvn install
```

## Running

Run tests using the following Maven command
```
mvn test
```

### SearchTest.java

The SearchTest.java file requires an input CSV under the file path
```
aco_tests/src/test/resources/search_cases.csv
```
Once the test suite compiles and runs to completion, an output file with a stamp
of the run's Date and Time under the file path
```
aco_tests/src/test/resources/aco-report-YY-MM-dd_HH:mm.csv
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit 4](https://junit.org/junit4/) - Test Driven Development
* [Selenium API](https://seleniumhq.github.io/selenium/docs/api/java/) - WebDriver



## Authors

**Anderson Tsai Wang** - Association of Research Libraries, Fellowship of Digital an Inclusive Excellence, NYU Elmer Holmes Bobst Library, Digital Libraries and Technology Services Intern

## Acknowledgments

* Selenium API: ChromeDriver, GeckoDriver
* Apache Commons CSV
* Association of Research Libraries
* NYU Libraries

