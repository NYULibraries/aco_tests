package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;

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
//To-Do : Parameterize the test

public class Search extends TestCase {
    private WebDriver driver;
    private int time_out = 20;
    private WebDriverWait wait;


    private int min_num_results_for_kitab = 1352;
    private int min_num_results_for_new_york_university_libraries = 1799;

    private String AnyField = "q";
    private String Title = "title";
    private String Author = "author";
    private String Publisher = "publisher";
    private String Place_of_Publication = "pubplace";
    private String Provider = "provider";
    private String Subject = "subject";

    private String ContainsAny = "containsAny";
    private String ContainsAll = "containsAll";
    private String Matches = "matches";

    public void setUpChrome() throws Exception {
        System.out.println("Set Up Chrome");
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, time_out);
    }

    public void setUpFireFox() throws Exception {
        System.out.println("Set Up FireFox");
        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, time_out);
    }

    public void setUpSafari() throws Exception {
        System.out.println("Set Up Safari");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        driver = new SafariDriver();
        wait = new WebDriverWait(driver, time_out);
    }

    public void testChrome() throws Exception {
        try {
            setUpChrome();
            driver.get("http://dlib.nyu.edu/aco/");
            Search(AnyField, ContainsAny, "kitab", min_num_results_for_kitab);
            driver.quit();

            setUpChrome();
            driver.get("http://dlib.nyu.edu/aco/");
            Search(Provider, Matches,"New York University Libraries", min_num_results_for_new_york_university_libraries);
            driver.quit();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void Search(String field, String scope, String query, int min_num_results){

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
        int number_of_results = Integer.parseInt(numfound);

        boolean greater_than_min;
        if(number_of_results >=min_num_results) {
            greater_than_min = true;
        }
        else {
            greater_than_min = false;
        }

        assertTrue(greater_than_min);
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

