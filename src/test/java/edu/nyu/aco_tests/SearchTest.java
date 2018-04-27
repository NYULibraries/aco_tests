package edu.nyu.aco_tests;

//Junits
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

//Selectors

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

@RunWith(Parameterized.class)
public class SearchTest{
    private String browser;
    private WebDriver driver;
    private int timeOut = 10;
    private WebDriverWait wait;

    private int minNumResultsForKitab = 1352;
    private int minNumResultsForNewYorkUniversityLibraries = 1799;
    private int minNumResultsForCornell = 742;

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


    public SearchTest(String browser){
        this.browser = browser;
    }

    @Parameters
    public static Object[] data() {
        return new Object[] { "Chrome", "Firefox", "Safari" };
    }

    public void setUpChrome() throws Exception {
        System.out.println("Set Up Chrome");
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, timeOut);
    }

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

    @Before
    public void setUp() throws Exception{
        if (this.browser.equals("Chrome")){
            setUpChrome();
        }
        else if (this.browser.equals("Firefox")){
            setUpFirefox();
        }
        else if (this.browser.equals("Safari")){
            setUpSafari();
        }
        this.driver.get("http://dlib.nyu.edu/aco/");
    }

    @Test
    public void testKitab() throws Exception {
        try {
            Search(anyField, containsAny, "kitab", minNumResultsForKitab);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testNYU() throws Exception {
        try {
            Search(provider, matches,"New York University Libraries", minNumResultsForNewYorkUniversityLibraries);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testCornell() throws Exception{
        try {
            Search(provider, containsAny, "Cornell", minNumResultsForCornell);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }

    public void Search(String field, String scope, String query, int minNumResults){

        System.out.println("SearchTest: " + query);
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

}

