import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Scanner;


public class FrontendDeveloperTests {


  /**
   * Tests good and bad input for the readFile() method. Specifically tests for input of a file that
   * exists, and one that doesn't
   */
  @Test
  public void testReadFile() {


    try {
      // bad input
      TextUITester tester = new TextUITester("FakeSongs");
      Frontend frontend1 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend1.readFile();
      String output = tester.checkOutput();

      // checks for the right output
      if (!(output.contains("Failed to load file."))) {
        Assertions.fail("readFile() found a csv file to load when one didn't exits");
      }
    } catch (Exception e) {
      Assertions.fail("readFile() threw an exception when finding a file that doesn't exist");
    }


    try {
      // good input
      TextUITester tester = new TextUITester("songs.csv");
      Frontend frontend2 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend2.readFile();
      String output = tester.checkOutput();

      // checks for the correct output
      if (!output.contains("Done reading file.")) {
        Assertions.fail("readFile() didn't successfully load a csv file that existed");
      }

    } catch (Exception e) {
      Assertions.fail("readFile() threw an exception even though a file existed");
    }

    //tests passed
    Assertions.assertTrue(true);

  }


  /**
   * Tests input for the getValues() method, specifically when the min is less than max, when max is
   * greater than or equal to min, or when the input is not in the specified format
   */
  @Test
  public void testGetValues() {

    // checks if csv file is loaded first
    try {
      TextUITester tester = new TextUITester("50 - 40");
      Frontend frontend = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend.getValues();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("A file must be loaded first.")) {
        Assertions.fail("getValues() loaded min and mix when a file wasn't loaded first.");
      }
    } catch (Exception e) {
      Assertions.fail("getValues() threw an exception rather than properly handling it");
    }

    // min is less than max - bad input
    try {
      TextUITester tester = new TextUITester("songs.csv\n50 - 40");
      Frontend frontend1 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend1.readFile();
      frontend1.getValues();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("The range must be in the specified format.")) {
        Assertions.fail("getValues() did not reject the illegal input for min and max");
      }
    } catch (Exception e) {
      Assertions.fail("getValues() threw an exception rather than properly handling it");
    }

    // min is equal to max - good input
    try {
      TextUITester tester = new TextUITester("songs.csv\n50 - 50");
      Frontend frontend2 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend2.readFile();
      frontend2.getValues();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("songs found between 50 - 50"))
        Assertions.fail("getValues() did not get songs for a valid min/max");

    } catch (Exception e) {
      Assertions.fail("getValues() threw an exception for good input");
    }

    // min is less than max - good input
    try {
      TextUITester tester = new TextUITester("songs.csv\n40 - 50");
      Frontend frontend3 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend3.readFile();
      frontend3.getValues();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("songs found between 40 - 50"))
        Assertions.fail("getValues() did not get songs for a valid min/max");

    } catch (Exception e) {
      Assertions.fail("getValues() threw an exception for good input");
    }

    // incorrect format - bad input
    try {
      TextUITester tester = new TextUITester("songs.csv\n40 50");
      Frontend frontend4 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend4.readFile();
      frontend4.getValues();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("The range must be in the specified format.")) {
        Assertions.fail("getValues() did mot reject the illegal input for min and max");
      }
    } catch (Exception e) {
      Assertions.fail("getValues() threw an exception rather than properly handling it");
    }
    // all tests passed
    Assertions.assertTrue(true);
  }

  /**
   * Checks for input on the setFilter() method Checks cases where the range is not yet set, and
   * when range is initialized
   */
  @Test
  public void testSetFilter() {


    // filter has not been set yet
    try {
      TextUITester tester = new TextUITester("songs.csv\n35");
      Frontend frontend1 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend1.readFile();
      frontend1.setFilter();
      String output = tester.checkOutput();

      //checks output
      if (!output.contains("The range must be set first."))
        Assertions.fail("setFilter() did not reject input when the range was not set.");
    } catch (Exception e) {
      Assertions.fail("setFilter() threw an exception instead of properly handling it.");
    }

    // filter is set, passes in min speed
    try {
      TextUITester tester = new TextUITester("songs.csv\n40 - 50\n45");
      Frontend frontend2 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend2.readFile();
      frontend2.getValues();
      frontend2.setFilter();
      String output = tester.checkOutput();

      // checks output
      if (!output.contains("songs found between 40 - 50 with min speed 45"))
        Assertions.fail("setFilter() rejected the input when the range was set");

    } catch (Exception e) {
      Assertions.fail("setFilter() threw an exception for valid input");
    }

    // all tests passed
    Assertions.assertTrue(true);


  }

  /**
   * Checks input for topFive() Checks cases where range and min speed are not set, and when they
   * are properly set
   */
  @Test
  public void testTopFive() {


    // filter and min have not been set
    try {
      TextUITester tester = new TextUITester("");
      Frontend frontend1 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend1.topFive();
      String output = tester.checkOutput();

      //checks output
      if (!output.contains("The speed must be set first."))
        Assertions.fail(
            "topFive() did not reject input when the range and min speed were not set.");
    } catch (Exception e) {
      Assertions.fail("topFive() threw an exception instead of properly handling it.");
    }

    // filter is set, min is not
    try {
      // initializes the range and gets top 5
      TextUITester tester2 = new TextUITester("songs.csv\n40 - 50");
      Frontend frontend2 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend2.readFile();
      frontend2.getValues();
      frontend2.topFive();
      String output2 = tester2.checkOutput();

      if (!output2.contains("The speed must be set first."))
        Assertions.fail("topFive() did not reject input when the min speed was not set.");

    } catch (Exception e) {
      Assertions.fail("topFive() threw an exception instead of properly handling it.");
    }

    // filter and min are set
    try {
      // initializes the range and min speed
      TextUITester tester3 = new TextUITester("songs.csv\n40 - 50\n45");
      Frontend frontend3 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
      frontend3.readFile();
      frontend3.getValues();
      frontend3.setFilter();

      // gets top 5
      frontend3.topFive();
      String output3 = tester3.checkOutput();

      if (!output3.contains("Top Five songs found between 40 - 50 with a min speed 45:"))
        Assertions.fail(
            "topFive() did not give the expected output when the range and min speed were set.");

    } catch (Exception e) {
      Assertions.fail("topFive() threw an exception instead of properly handling it.");
    }

    // all tests passed
    Assertions.assertTrue(true);
  }

  /**
   * Checks if the main menu properly updates for when filters and min speeds are set
   */
  @Test
  public void testMainMenu() {
    TextUITester tester1 = new TextUITester("");
    Frontend frontend1 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));

    // filters and min speed not set

    frontend1.displayMainMenu();
    String output = tester1.checkOutput();

    if (!output.contains("(by Min Speed: none)") || !output.contains("[min - max]"))
      Assertions.fail("main menu did not display the correct information");

    // range is set, min speed is not
    TextUITester tester2 = new TextUITester("songs.csv\n40 - 50");
    Frontend frontend2 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
    frontend2.readFile();
    frontend2.getValues();

    // gets main menu and checks output
    frontend2.displayMainMenu();
    output = tester2.checkOutput();

    if (!output.contains("(by Min Speed: none)") || !output.contains("[40 - 50]"))
      Assertions.fail("main menu did not display the correct information");



    // range and min are set
    TextUITester tester3 = new TextUITester("songs.csv\n40 - 50\n 45");
    Frontend frontend3 = new Frontend(new Scanner(System.in), new BackendPlaceholder(null));
    frontend3.readFile();
    frontend3.getValues();
    frontend3.setFilter();

    frontend3.displayMainMenu();
    output = tester3.checkOutput();

    if (!output.contains("(by Min Speed: 45)") || !output.contains("[40 - 50]"))
      Assertions.fail("main menu did not display the correct information");

    // all tests passed
    Assertions.assertTrue(true);
  }

}	
