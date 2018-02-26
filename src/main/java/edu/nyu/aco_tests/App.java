package edu.nyu.aco_tests;

import junit.framework.ComparisonFailure;

/**
 * 
 * First Test
 */
public class App 
{
    public static void main( String[] args )
    {
     try {
      SimpleTest testSimple = new SimpleTest();
      testSimple.setUpChrome();
      testSimple.testSimpleChrome();
      System.out.println("Chrome: Success! The title matches the URL's!");
      testSimple.tearDown();
      testSimple.setUpFireFox();
      testSimple.testSimpleFireFox();
      System.out.println("Firefox: Success! The title matches the URL's!");
      testSimple.tearDown();
     } catch (Exception e) {
         System.out.println("Exception occurred:"+e.toString());
      }
      catch (ComparisonFailure err){
          System.out.println("Failure: " + err.getMessage());
      }
    }
}

