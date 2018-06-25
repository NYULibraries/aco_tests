package edu.nyu.aco_tests;

import static edu.nyu.aco_tests.Constants.*;

//Java
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
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
    private static CSVWriter writer;
    private int numberOfResults;
    @BeforeClass
    public static void setUpClass(){
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_VALUE);
        System.setProperty(FIREFOX_DRIVER_KEY, FIREFOX_DRIVER_VALUE);
        try{
            writer = new CSVWriter(new FileWriter(new File(String.format("%s%s", OUTPUT, new SimpleDateFormat("yyyy-MM-dd HH:mm'.csv'").format(new Date())))));
            String[] entries = "Result#Field#Scope#Query#Browser#Expected#Actual".split("#");
            writer.writeNext(entries); //first line
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Build searches in query parameters.
    @Parameters
    public static ArrayList<Query> searches(){
//  Reads from csv method
        ArrayList<Query> queries = new ArrayList<Query>();
        try {
            CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
            String[] line;
            reader.readNext(); //These are the column titles in the first row
            while ((line = reader.readNext()) != null) {
                for (String browser : BROWSERS){
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
    public void setUpTest(){
        String browser = this.query.browser;
        System.out.println("SET UP : " + browser);
        if (browser.equals("Chrome")){
            driver = new ChromeDriver();
        }
        else if (browser.equals("Firefox")){
            driver = new FirefoxDriver();
        }
        wait = new WebDriverWait(driver, TIME_OUT);
        driver.get(HOMEPAGE);
    }

    @Test
    public void runTest(){
        String result = "";
        try{
            search();
            result = "Success";
        }catch (Exception e) {
            result = "Failure";
            fail(e.toString());
        }finally{
            String[] log = new String[]{result, query.field, query.scope, query.query, query.browser, String.valueOf(query.minNumResults), String.valueOf(numberOfResults)};
            writer.writeNext(log);
        }
    }

    @After
    public void tearDownTest(){
        System.out.println("TEAR DOWN : " + this.query.browser);
        driver.quit();
    }

    @AfterClass
    public static void tearDownClass(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void search(){
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
//        int numberOfResults;
        String numFound;
        //If there isn't a resultsnum webElement, there are no results. set numberOfResults to 0
        try {
            numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();
            numberOfResults = Integer.parseInt(numFound);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            numberOfResults = 0;
        }
        assertTrue(String.format("Expected number of results to be greater than %d; got %d number of results", query.minNumResults, numberOfResults),numberOfResults >= query.minNumResults);
    }

    //java logging
    //junit report
    //aco-report-{timestamp EST}.csv
    //Failures
    //Test , Browser , Expected , Got
    //Success
}

