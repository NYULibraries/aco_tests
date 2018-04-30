package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

public class Acceptance extends TestCase {
    private WebDriver driver;

    public void setUpChrome() throws Exception {
        System.out.println("Set Up Chrome");
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
        driver = new ChromeDriver();
    }

    public void testSimpleChrome() throws ComparisonFailure{
        try {
            setUpChrome();
            System.out.println("Retrieving URL");
            this.driver.get("http://dlib.nyu.edu/aco/");
            assertEquals("Arabic Collections Online", this.driver.getTitle());
            this.driver.quit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setUpFireFox() throws Exception{
        System.out.println("Set Up FireFox");
        //	System.setProperty("webdriver.firefox.bin","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.19.1/bin/geckodriver");
        driver = new FirefoxDriver();
    }

    public void testSimpleFireFox() throws ComparisonFailure{
        try {
            setUpFireFox();
            System.out.println("Retrieving URL");
            this.driver.get("http://dlib.nyu.edu/aco/");
            assertEquals("Arabic Collections Online", this.driver.getTitle());
            this.driver.quit();
         } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setUpSafari() throws Exception {
        System.out.println("Set Up Safari");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        driver = new SafariDriver();
    }

    public void testSimpleSafari() throws ComparisonFailure{
        try {
            setUpSafari();
            System.out.println("Retrieving URL");
            this.driver.get("http://dlib.nyu.edu/aco/");
            assertEquals("Arabic Collections Online", this.driver.getTitle());
            this.driver.quit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

