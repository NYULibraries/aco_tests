package edu.nyu.aco_tests;

import static edu.nyu.aco_tests.Constants.*;

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

//Test Class
@RunWith(Parameterized.class)
public class SearchTest{
    private Query query;
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public static void setProperties(){
        System.setProperty(chromeDriverKey, chromeDriverValue);
        System.setProperty(firefoxDriverKey, firefoxDriverValue);
    }

    //Build searches in query parameters.
    @Parameters
    public static ArrayList<Query> searches(){
//  Reads from csv method

        ArrayList<Query> queries = new ArrayList<Query>();
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            reader.readNext(); //These are the column names in the first row
            while ((line = reader.readNext()) != null) {
                for (String browser : browsers){
                    queries.add(new Query(line[1].trim(),line[2].trim(),line[3].trim(),Integer.valueOf(line[4].trim()), browser));
                }
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
    public void setUp(){
        String browser = this.query.browser;
        System.out.println("SET UP : " + browser);
        if (browser.equals("Chrome")){
            driver = new ChromeDriver();
        }
        else if (browser.equals("Firefox")){
            driver = new FirefoxDriver();
        }
        wait = new WebDriverWait(driver, timeOut);
        driver.get(homepage);
    }

    @Test
    public void runTest(){
        try{
            Search();
        }catch (Exception e) {
            fail(e.toString());
        }
    }

    @After
    public void tearDown(){
        System.out.println("TEAR DOWN : " + this.query.browser);
        driver.quit();
    }

    private void Search(){
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
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='widget items']")));
        int numberOfResults;
        String numFound;
        //If there isn't a resultsnum webElement, there are no results. set numberOfResults to 0
        try {
            numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();
            numberOfResults = Integer.parseInt(numFound);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            numberOfResults = 0;
        }
        assertTrue("Expected number of results to be greater than " + query.minNumResults +
                "; got " + numberOfResults + " number of results",numberOfResults >= query.minNumResults);
    }

    //java logging
    //junit report
    //aco-report-{timestamp EST}.csv
    //Failures
    //Test , Browser , Expected , Got
    //Success
}

