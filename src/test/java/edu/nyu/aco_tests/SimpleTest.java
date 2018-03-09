package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import java.net.URL;


public class SimpleTest extends TestCase {
	private WebDriver driver;

	public void setUpChrome() throws Exception {
		System.out.println("Set Up Chrome");
		System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.34/bin/chromedriver");
		driver = new ChromeDriver();
	}

	public void testSimpleChrome() throws ComparisonFailure{
                try {
                setUpChrome();
		System.out.println("Retrieving URL");
		this.driver.get("http://dlib.nyu.edu/aco/");
		assertEquals("Arabic Collections Online", this.driver.getTitle());
                } catch (Exception e) {
                    System.out.println("Things went bad");
                }               
	}



}

