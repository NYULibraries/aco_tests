package edu.nyu.aco_tests;

import junit.framework.TestCase;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import java.net.URL;


public class SimpleTest extends TestCase {
	private WebDriver driver;

	public void setUp() throws Exception {
            System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.34/bin/chromedriver");
            driver = new ChromeDriver();
	}

	public void testSimple() throws Exception {
		this.driver.get("http://www.google.com");
		assertEquals("Google", this.driver.getTitle());
	}

	public void tearDown() throws Exception {
		this.driver.quit();
	}
 
    }

