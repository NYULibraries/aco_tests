package edu.nyu.aco_tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import java.net.URL;


/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Acceptance.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//                System.out.println("Set Up Chrome");
//                System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.34/bin/chromedriver");
//                ChromeDriver driver = new ChromeDriver();
//
//                System.out.println("Retrieving URL");
//                driver.get("http://dlib.nyu.edu/aco/");
//                assertEquals("Arabic Collections Online", driver.getTitle());
          //assertTrue( true );
    }
}
