package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;
import junit.framework.TestCase;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import java.net.URL;


public class SimpleTest extends TestCase {
	private WebDriver driver;

	public void setUp() throws Exception {
		System.out.println("Set Up");
		System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.35/bin/chromedriver");
		driver = new ChromeDriver();
	}

	public void testSimple() throws ComparisonFailure{
		System.out.println("Retrieving URL");
		this.driver.get("http://dlib.nyu.edu/aco/");
		assertEquals("Arabic Collections Online", this.driver.getTitle());
	}

	public void tearDown() throws Exception {
		this.driver.quit();
	}
 
}

