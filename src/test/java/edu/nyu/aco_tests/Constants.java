package edu.nyu.aco_tests;

final class Constants {
    private Constants() {
        throw new Error();
    }

    static final int TIME_OUT = 10;
    static final String HOMEPAGE = "http://dlib.nyu.edu/aco/";
    static final String CSV_FILE = "src/test/resources/search_cases.csv";
    static final String OUTPUT = "src/test/resources/aco-report-";
    static final String[] BROWSERS = {"Chrome","Firefox"};

    static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    static final String CHROME_DRIVER_VALUE = "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver";
    static final String FIREFOX_DRIVER_KEY = "webdriver.gecko.driver";
    static final String FIREFOX_DRIVER_VALUE = "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver";
}
