package edu.nyu.aco_tests;

//Java
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

//Drivers
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

//Waits
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

//Selectors
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
    public static void setProperties(){
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
    }

    //Build searches in the parameters. Uses a jagged two-dimensional array
    @Parameters
    public static ArrayList<Query> searches() throws IOException {
//  Reads from csv method
        String csvFile = "src/test/resources/search_cases.csv";
        ArrayList<Query> queries = new ArrayList<Query>();
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            reader.readNext(); //These are the column names in the first row
            //while ((line = reader.readNext()) != null) {
                line = reader.readNext();
                queries.add(new Query(line[1],line[2],line[3],Integer.valueOf(line[4]), "Chrome"));
            //    queries.add(new Query(line[1],line[2],line[3],Integer.valueOf(line[4]), "Firefox"));
            //    queries.add(new Query(line[1],line[2],line[3],Integer.valueOf(line[4]), "Safari"));
            //}
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
        System.out.println(" Selected " + query.field);
        //scope
        Select scopeBox = new Select(driver.findElement(By.className("scope-select")));
        scopeBox.selectByVisibleText("Matches / يساوي");
        //Won't select by value.
        //Tried with query.scope="matches". Got org.openqa.selenium.NoSuchElementException: Unable to locate element: "matches".
        //scopeBox.selectByValue(query.scope);
        System.out.println(" Selected " + query.scope);
        //query
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className("input-hold")));
        actions.click();
        actions.sendKeys(query.query);
        actions.moveToElement(driver.findElement(By.className("submit-hold")));
        actions.click();
        actions.build().perform();

        System.out.println("       Searching ");
        System.out.println(query);
        //Check result
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("resultsnum")));
        String numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();

        int numberOfResults = Integer.parseInt(numFound);
        System.out.print(numberOfResults);
        assertTrue(numberOfResults >= query.minNumResults);
        System.out.println("     PASS");
    }
}

