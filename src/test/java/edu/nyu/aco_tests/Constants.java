package edu.nyu.aco_tests;

final class Constants {
    private Constants() {
        throw new Error();
    }

    //Drivers
    static final String CHROME = "Chrome";
    static final String FIREFOX = "Firefox";
    static final String[] BROWSERS = {CHROME,FIREFOX};
    static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    static final String CHROME_DRIVER_VALUE = "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver";
    static final String FIREFOX_DRIVER_KEY = "webdriver.gecko.driver";
    static final String FIREFOX_DRIVER_VALUE = "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver";
    static final Integer TIME_OUT = 10;
    static final String HOMEPAGE = "http://dlib.nyu.edu/aco/";
    //Input CSV and header names
    static final String CSV_FILE = "src/test/resources/search_cases.csv";
    static final String FIELD = "Field";
    static final String SCOPE = "Scope";
    static final String QUERY = "Query";
    static final String MINIMUM_NUMER_OF_RESULTS = "Minimum Number of Results";
    //Output CSV and header names
    static final String OUTPUT_FILE = "src/test/resources/aco-report-";
    static final String RESULT = "Result";
    static final String BROWSER = "Browser";
    static final String EXPECTED = "Expected";
    static final String ACTUAL = "Actual";
    static final String PASS = "Success";
    static final String FAIL = "Failure";
}
