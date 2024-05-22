import java.util.Scanner;
import java.util.List;

/**
 * Frontend - CS400 Project 1: iSongify
 */
public class Frontend implements FrontendInterface {

  /**
   * tracks the minimum danceability
   */
  private String min = "min";

  /**
   * tracks the maximum danceability
   */
  private String max = "max";

  /**
   * tracks the minimum speed
   */
  private String speed = "none";

  /**
   * tracks to see if a file has been loaded in yet.
   */
  private boolean fileRead = false;

  /**
   * the scanner to read user input
   */
  private Scanner scanner;

  /**
   * the backend to process input
   */
  private BackendInterface backend;

  /**
   * Instantiates the frontend, linking in the backend and the scanner for user input.
   * @param in the scanner used for user input
   * @param backend the backend used to process input
   */
  public Frontend(Scanner in, BackendInterface backend) {

    scanner = in;
    this.backend = backend;
  }

  /**
   * Repeated gives the user an opportunity to issue new commands until they select Q to quit.
   */
  public void runCommandLoop() {

    // displays menu and gets input
    displayMainMenu();
    String input = scanner.nextLine();

    // runs the command based on the input
    switch (input) {
      case "R":
        readFile();
        break;
      case "G":
        getValues();
        break;
      case "F":
        setFilter();
        break;
      case "D":
        topFive();
        break;
      case "Q":
        return;
        // no command is found
      default:
        System.out.println("Invalid Command");
        break;
    }

    // repeats the command loop after a command is ran
    runCommandLoop();
  }

  /**
   * Displays the menu of command options to the user.
   */
  public void displayMainMenu() {

    String menu = """
        	    
        ~~~ Command Menu ~~~
            [R]ead Data
            [G]et Songs by Danceability [min - max]
            [F]ilter Fast Songs (by Min Speed: none)
            [D]isplay Five Most Energetic
            [Q]uit
        Choose command:\s""";
    menu = menu.replace("min", min).replace("max", max).replace("none", speed);
    System.out.print(menu);
  }

  /**
   * Provides text-based user interface and error handling for the [R]ead Data command.
   */
  public void readFile() {
    if (backend == null) {
      System.out.println("Backend is null.");
      return;
    }

    System.out.print("Enter path to csv file to load: ");
    String csvFile = scanner.nextLine();

    // reads file through backend
    try {
      backend.readData(csvFile);
      System.out.println("\nDone reading file.");
      // tracks that the file has been read.
      fileRead = true;
    } catch (Exception e) {
      System.out.println("\nFailed to load file.");
    }
  }

  /**
   * Provides text-based user interface and error handling for the [G]et Songs by Danceability
   * command.
   */
  public void getValues() {
    // checks if a file has been loaded yet
    if (!fileRead) {
      System.out.println("A file must be loaded first.");
      return;
    }
    // gets the input and checks format
    System.out.print("Enter range of values (MIN - MAX): ");
    String userInput = scanner.nextLine();
    String[] input = userInput.split("-");

    // checks if proper format
    if (input.length != 2) {
      System.out.println("The range must be in the specified format.");
      return;
    }

    int currMin;
    int currMax;

    try {
      currMin = Integer.parseInt(input[0].strip());
      currMax = Integer.parseInt(input[1].strip());
    } catch (Exception e) {
      System.out.println("The range must be in the specified format.");
      return;
    }


    // checks if min > max
    if (currMin > currMax) {
      System.out.println("The range must be in the specified format.");
      return;
    }

    min = "" + currMin;
    max = "" + currMax;

    // gets the range
    List<String> list;
    try {
      list = backend.getRange(Integer.parseInt(min), Integer.parseInt(max));
    } catch (Exception e) {
      System.out.println("An unexpected error occurred.");
      return;
    }

    // prints the songs
    System.out.println("" + list.size() + " songs found between " + min + " - " + max + ":");
    for (String s : list) {
      System.out.println("	" + s);
    }
  }

  /**
   * Provides text-based user interface and error handling for the [F]ilter Fast Songs (by Min
   * Speed) command.
   */
  public void setFilter() {
    // checks if the range has been set
    if (min.equals("min") || max.equals("max")) {
      System.out.println("The range must be set first.");
      return;
    }


    int currSpeed;
    System.out.print("Enter minimum speed: ");
    // tries to get input
    try {
      String s = scanner.nextLine();
      currSpeed = Integer.parseInt(s);
    } catch (Exception e) {
      System.out.println("");
      return;
    }

    speed = "" + currSpeed;

    // gets list of songs
    List<String> list;
    try {
      list = backend.filterFastSongs(currSpeed);
    } catch (Exception e) {
      System.out.println("An unexpected error occured.");
      return;
    }
    // prints the songs
    System.out.println(
        list.size() + " songs found between " + min + " - " + max + " with min speed " + speed + ":");

    for (String s : list) {
      System.out.println(s);
    }
  }

  /**
   * Provides text-based user interface and error handling for the [D]isplay Five Most Energetic
   * command.
   */
  public void topFive() {
    // checks if range has been set
    if (min.equals("min")) {
      System.out.println("The range must be set first.");
      return;
    }

    List<String> list;
    try {
      // gets list
      list = backend.fiveMostEnergetic();
    } catch (Exception e) {
      System.out.println("An error has occurred.");
      return;
    }

    // prints list
    System.out.println(
        "Top Five songs found between " + min + " - " + max + " with a min speed " + speed + ":");
    for (String s : list) {
      System.out.println(s);
    }

  }

}
