package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;

//Selectors
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


public class Search extends TestCase {
    private WebDriver driver;

    public void setUpChrome() throws Exception {
        System.out.println("Set Up Chrome");
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        driver = new ChromeDriver();
    }

    public void testChrome() throws ComparisonFailure{
        try {
            setUpChrome();
            System.out.println("Retrieving URL");
            this.driver.get("http://dlib.nyu.edu/aco/");

            System.out.println("Searching 'kitab'");
            //Search Kitab
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("input-hold")));

            WebElement searchBox = this.driver.findElement(By.className("input-hold"));
            Actions actions = new Actions(driver);
            actions.moveToElement(searchBox);
            actions.click();
            //Send query
            actions.sendKeys("kitab");
            //Hit enter
            actions.sendKeys(Keys.RETURN);
            actions.build().perform();
//            searchBox.sendKeys("kitab");
//            searchBox.submit();

            //Check result count
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("numfound")));
            assertEquals("1352",this.driver.findElement(By.className("numfound")).getText());
            this.driver.quit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//
//    public void setUpFireFox() throws Exception{
//        System.out.println("Set Up FireFox");
//        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
//        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
//        driver = new FirefoxDriver();
//    }
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
//    public void setUpSafari() throws Exception {
//        System.out.println("Set Up Safari");
//        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
//        driver = new SafariDriver();
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

