package edu.nyu.aco_tests;

import junit.framework.TestCase;

//Selectors
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;

//Waits
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

//Drivers
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
//To-Do : Parameterize the test

public class Search extends TestCase {
    private WebDriver driver;
    private int timeOut = 10;
    private WebDriverWait wait;

    private int minNumResultsForKitab = 1352;
    private int minNumResultsForNewYorkUniversityLibraries = 1799;

    private String anyField = "q";
    private String title = "title";
    private String author = "author";
    private String publisher = "publisher";
    private String placeOfPublication = "pubplace";
    private String provider = "provider";
    private String subject = "subject";

    private String containsAny = "containsAny";
    private String containsAll = "containsAll";
    private String matches = "matches";


    public void setUpChrome() throws Exception {
        System.out.println("Set Up Chrome");
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, timeOut);
    }

    @Before
    public void setUpFirefox() throws Exception {
        System.out.println("Set Up Firefox");
        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, timeOut);
    }

    public void setUpSafari() throws Exception {
        System.out.println("Set Up Safari");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        driver = new SafariDriver();
        wait = new WebDriverWait(driver, timeOut);
    }

    public void setUpBrowser(String browser) throws Exception{
        if (browser.equals("Chrome")){
            setUpChrome();
        }
        else if (browser.equals("Firefox")){
            setUpFirefox();
        }
        else if (browser.equals("Safari")){
            setUpSafari();
        }
        //Default browser will be chrome
        else{
            setUpChrome();
        }

    }

    public void testKitab() throws Exception {
        try {
            setUpChrome();
            driver.get("http://dlib.nyu.edu/aco/");
            Search(anyField, containsAny, "kitab", minNumResultsForKitab);
            driver.quit();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void testNYU() throws Exception {
        try {
            setUpChrome();
            driver.get("http://dlib.nyu.edu/aco/");
            Search(provider, matches,"New York University Libraries", minNumResultsForNewYorkUniversityLibraries);
            driver.quit();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void Search(String field, String scope, String query, int minNumResults){

        System.out.println("Search: " + query);
        System.out.println("Field: " + field);
        System.out.println("Scope: " + scope);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search_holder_advanced")));


        Select fieldBox = new Select(driver.findElement(By.className("field-select")));
        fieldBox.selectByValue(field);

        Select scopeBox = new Select(driver.findElement(By.className("scope-select")));
        scopeBox.selectByValue(scope);

        WebElement searchBox = driver.findElement(By.className("input-hold"));
        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox);
        actions.click();
        //Send query
        actions.sendKeys(query);
        //Hit enter
        actions.sendKeys(Keys.RETURN);
        actions.build().
        perform();

        //Check result count
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("numfound")));
        String numfound = driver.findElement(By.className("numfound")).getText();
        int numberOfResults = Integer.parseInt(numfound);

        boolean greaterThanMin;
        if(numberOfResults >=minNumResults) {
            greaterThanMin = true;
        }
        else {
            greaterThanMin = false;
        }

        assertTrue(greaterThanMin);
}

//
//    public void testSimpleFireFox() throws ComparisonFailure{
//        try {
//            setUpFireFox();
//            System.out.println("Retrieving URL");
//            this.driver.get("http://dlib.nyu.edu/aco/");
//            assertEquals("Arabic Collections Online", this.driver.getTitle());
//            this.driver.quit();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//

//    public void testSimpleSafari() throws ComparisonFailure{
//        try {
//            setUpSafari();
//            System.out.println("Retrieving URL");
//            this.driver.get("http://dlib.nyu.edu/aco/");
//            assertEquals("Arabic Collections Online", this.driver.getTitle());
//            this.driver.quit();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}

