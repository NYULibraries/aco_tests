package edu.nyu.aco_tests;

//Java
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.io.FileReader;
//Drivers
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//Waits
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
//Selectors
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
//Junits
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

//Query Class
class Query{
    protected String browser;
    protected String field;
    protected String scope;
    protected String query;
    protected int minNumResults;

    Query(String field, String scope, String query, int minNumResults, String browser){
        this.field = field;
        this.scope = scope;
        this.query = query;
        this.minNumResults = minNumResults;
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
    public static void setProperties(){
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
    }

    //Build searches in query parameters.
    @Parameters
    public static ArrayList<Query> searches() throws IOException {
//  Reads from csv method
        String csvFile = "src/test/resources/search_cases.csv";
        ArrayList<Query> queries = new ArrayList<Query>();
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            reader.readNext(); //These are the column names in the first row
            while ((line = reader.readNext()) != null) {
                queries.add(new Query(line[1],line[2],line[3],Integer.valueOf(line[4]), "Chrome"));
                queries.add(new Query(line[1],line[2],line[3],Integer.valueOf(line[4]), "Firefox"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queries;
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
        wait = new WebDriverWait(driver, timeOut);
        driver.get("http://dlib.nyu.edu/aco/");
    }

    @Test
    public void runTest() throws Exception{
        try{
            Search();
        }catch (Exception e) {
            fail(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception{
        System.out.println("TEAR DOWN : " + this.query.browser);
        driver.quit();
    }

    public void Search(){
        System.out.println(query);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("submit-hold")));
        //field
        Select fieldBox = new Select(driver.findElement(By.className("field-select")));
        fieldBox.selectByValue(query.field);
        //scope
        Select scopeBox = new Select(driver.findElement(By.className("scope-select")));
        scopeBox.selectByValue(query.scope);

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className("input-hold")));
        actions.click();
        actions.sendKeys(query.query);
        actions.moveToElement(driver.findElement(By.className("submit-hold")));
        actions.click();
        actions.build().perform();

        //Check result
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("results-header")));
        int numberOfResults;
        //If there isn't a resultsnum webElement, there are no results. set numberOfResults to 0
        if (driver.findElement(By.cssSelector("div[class='resultsnum']")) == null){
            numberOfResults = 0;
        }
        else {
            String numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();
            numberOfResults = Integer.parseInt(numFound);
        }
        assertTrue("Expected number of results to be greater than " + query.minNumResults +
                "; got " + numberOfResults + " number of results",numberOfResults >= query.minNumResults);
    }
}

