package edu.nyu.aco_tests;

//Drivers
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

//Waits
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

//Java util
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

//Junits
import static org.junit.Assert.*;
import org.junit.BeforeClass;
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

//Query Class
class Query{
    protected String browser;
    protected String field;
    protected String scope;
    protected String query;
    protected int minNumResults;

    Query(String field, String scope, String query, int minNumResults){
        this.field = field;
        this.scope = scope;
        this.query = query;
        this.minNumResults = minNumResults;
    }

    //An updated Query with browser included
    Query(Query withoutBrowser, String browser){
        this.field = withoutBrowser.field;
        this.scope = withoutBrowser.scope;
        this.query = withoutBrowser.query;
        this.minNumResults = withoutBrowser.minNumResults;
        this.browser = browser;
    }

    @Override
    public String toString(){
        return String.format("SearchTest: %s\nField: %s\nScope: %s\nNumber: %d", this.query, this.field, this.scope, this.minNumResults);
    }
}

//Test Class
@RunWith(Parameterized.class)
public class SearchTest{
    private Query query;
    private WebDriver driver;
    private int timeOut = 10;
    private WebDriverWait wait;

    @BeforeClass
    public static void settingSystemProperties(){
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
    }

    //Build searches in the parameters. Uses a jagged two-dimensional array
    @Parameters
    public static ArrayList<Query> query_data() {
        int minNumResultsForKitab = 1352;
        int minNumResultsForNewYorkUniversityLibraries = 1799;
        int minNumResultsForCornell = 742;
        int minNumResultsForColumbia = 2303;
        int minNumResultsForPrinceton = 873;

        String anyField = "q";
        String title = "title";
        String author = "author";
        String publisher = "publisher";
        String placeOfPublication = "pubplace";
        String provider = "provider";
        String subject = "subject";

        String containsAny = "containsAny";
        String containsAll = "containsAll";
        String matches = "matches";

        //List of queries
        List<Query> queries = Arrays.asList(
                new Query(anyField, containsAny, "kitab", minNumResultsForKitab),
                new Query(provider, matches,"New York University Libraries", minNumResultsForNewYorkUniversityLibraries),
                new Query(provider, containsAny, "Cornell", minNumResultsForCornell),
                new Query(provider, matches, "Columbia University Libraries", minNumResultsForColumbia),
                new Query(provider, matches, "Princeton Unviersity", minNumResultsForPrinceton)
        );

        ArrayList<Query> browsersByQueries = new ArrayList<Query>();
        for(Query query : queries) {
            browsersByQueries.add(new Query(query, "Chrome"));
            browsersByQueries.add(new Query(query, "Firefox"));
            browsersByQueries.add(new Query(query, "Safari"));
        }
        return browsersByQueries;
    }

    public SearchTest(Query query_data){
        this.query = query_data;
    }

    @Before
    public void setUp() throws Exception{
        String browser = this.query.browser;
        System.out.println("Set Up " + browser);
        if (browser.equals("Chrome")){
            driver = new ChromeDriver();
        }
        else if (browser.equals("Firefox")){
            driver = new FirefoxDriver();
        }
        else if (browser.equals("Safari")){
            driver = new SafariDriver();
        }
        wait = new WebDriverWait(driver, timeOut);
        driver.get("http://dlib.nyu.edu/aco/");
        System.out.println("        Home Page Set! ");
    }

    @Test
    public void runTest() throws Exception{
        try{
            Search();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @After
    public void tearDown() throws Exception{
        System.out.println("TEAR DOWN : " + this.query.browser);
        driver.quit();
    }

    public void Search(){
        wait.until(ExpectedConditions.elementToBeClickable(By.className("submit-hold")));
        Select fieldBox = new Select(driver.findElement(By.className("field-select")));
        fieldBox.selectByValue(query.field);

        Select scopeBox = new Select(driver.findElement(By.className("scope-select")));
        scopeBox.selectByValue(query.scope);

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className("input-hold")));
        actions.click();
        actions.sendKeys(query.query);
        actions.moveToElement(driver.findElement(By.className("submit-hold")));
        actions.click();
        actions.build().perform();

        System.out.println("       Searching ");
        System.out.println(query);
        //Check result count
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("resultsnum")));
        String numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();

        int numberOfResults = Integer.parseInt(numFound);
        System.out.print(numberOfResults);
        assertTrue(numberOfResults >= query.minNumResults);
        System.out.println("     PASS");
    }
}

