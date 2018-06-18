package edu.nyu.aco_tests;

public final class Constants {
    private Constants() {
        throw new Error();
    }

    public static final int timeOut = 10;
    public static final String homepage = "http://dlib.nyu.edu/aco/";
    public static final String csvFile = "src/test/resources/search_cases.csv";

    public static final String[] browsers = {"Chrome","Firefox"};

    public static final String chromeDriverKey = "webdriver.chrome.driver";
    public static final String chromeDriverValue = "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver";
    public static final String firefoxDriverKey = "webdriver.gecko.driver";
    public static final String firefoxDriverValue  = "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver";
}
