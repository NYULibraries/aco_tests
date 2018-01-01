package edu.nyu.aco_tests;

import junit.framework.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
     try {
      SimpleTest testSimple = new SimpleTest();
      testSimple.setUp();
      testSimple.testSimple();
      testSimple.tearDown();
     } catch (Exception e) {
         System.out.println("Exception occurred:"+e.toString());
      }

    }
}

