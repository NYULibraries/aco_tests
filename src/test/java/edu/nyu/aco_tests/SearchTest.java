package edu.nyu.aco_tests;

import static edu.nyu.aco_tests.Constants.*;

//Java
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
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
    private static CSVPrinter csvPrinter;
    private int numberOfResults;

    @BeforeClass
    public static void setUpClass(){
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_VALUE);
        System.setProperty(FIREFOX_DRIVER_KEY, FIREFOX_DRIVER_VALUE);
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.format("%s%s", OUTPUT_FILE, new SimpleDateFormat("yyyy-MM-dd HH:mm'.csv'").format(new Date()))));
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader(RESULT, FIELD, SCOPE, QUERY, BROWSER, EXPECTED, ACTUAL));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Build searches in query parameters.
    @Parameters
    public static ArrayList<Query> searches(){
        ArrayList<Query> queries = new ArrayList<Query>();
        try (
            Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim())
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                for (String browser : BROWSERS){
                    queries.add(new Query(csvRecord.get(FIELD), csvRecord.get(SCOPE), csvRecord.get(QUERY), Integer.parseInt(csvRecord.get(MINIMUM_NUMER_OF_RESULTS)), browser));
                }
            }
        }
        catch (IOException e) {
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
            try{
                csvPrinter.printRecord(result, query.field, query.scope, query.query, query.browser, query.minNumResults, numberOfResults);
                csvPrinter.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
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
            csvPrinter.close();
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
        try {
            String numFound = driver.findElement(By.cssSelector("div[class='resultsnum'] span[class='numfound']")).getText();
            numberOfResults = Integer.parseInt(numFound);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            numberOfResults = 0;
        }
        assertTrue(String.format("Expected number of results to be greater than %d; got %d number of results", query.minNumResults, numberOfResults),numberOfResults >= query.minNumResults);
    }
}

