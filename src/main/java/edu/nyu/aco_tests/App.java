package edu.nyu.aco_tests;

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
     try {
      testSimple.setUp();
      testSimple.testSimple();
     } catch (Exception e) {
         System.out.println("Exception occurred:"+e.toString());
         testSimple.tearDown();
      }
      testSimple.tearDown();
     } catch (Exception e) {
         System.out.println("Exception occurred:"+e.toString());
      }

    }
}

