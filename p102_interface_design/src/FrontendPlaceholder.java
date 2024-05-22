import java.util.Scanner;

/**
 * FrontendPlaceholder - CS400 Project 1: iSongify
 *
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Frontend class that will be implemented in a later week.
 */
public class FrontendPlaceholder implements FrontendInterface{

    public FrontendPlaceholder(Scanner in, BackendInterface backend) {}

    public void runCommandLoop() {
	displayMainMenu();
	System.out.println("R"); // user entered command
	readFile();

	displayMainMenu();
	System.out.println("G"); // user entered command
	getValues();
	getYearSongs();
	getEnergySongs();

	// add steps to make use of each of your two new features here

    	displayMainMenu();
	System.out.println("Q"); // user entered command
    }

    public void displayMainMenu() {
	
	// update this menu to 1) specify the attributes songs are retrieved by
	// and 2) to include extra options to access each each of the extra
	// features/capabilities that you have added to your PlaceholderBackend
	
	String menu = """
	    
	    ~~~ Command Menu ~~~
	        [R]ead Data
	        [G]et Songs by Duration Range
	        [Y]get Songs from a specified year
	        [E]get Songs with a specified energy range
	        [Q]uit
	    Choose command:""";
	System.out.print(menu + " ");
    }

    public void readFile() {
	System.out.print("Enter path to csv file to load: ");
	
	System.out.println("mySongData.csv"); // user entered command

	System.out.println("Done reading file.");
    }
    
    public void getValues() {
	System.out.print("Enter range of values (MIN - MAX): ");

	System.out.println("80 - 90"); // user entered command
	
	System.out.println("""
			   5 songs found between 80 - 90:
			       Baby
			       Dynamite
			       Secrets
			       Empire State of Mind (Part II) Broken Down
			       Only Girl (In The World)""");
    }

    // Add methods to access each of the two capabilities that you designed
    // and added to your PlaceholderBackend.java class.  Ensure that they
    // demonstrate examples of the user input needed to make use of them.
    
    public void getYearSongs() {
    	System.out.print("Enter year: ");

    	System.out.println("2010"); // user entered command
    	
    	System.out.println("""
    			   5 songs found in 2010:
    			       Baby
    			       Dynamite
    			       Secrets
    			       Empire State of Mind (Part II) Broken Down
    			       Only Girl (In The World)""");
    }
    
    public void getEnergySongs() {
    	System.out.print("Enter range of energy values (MIN - MAX): ");

    	System.out.println("80 - 90"); // user entered command
    	
    	System.out.println("""
    			   5 songs found between 80 - 90:
    			       Baby
    			       Dynamite
    			       Secrets
    			       Empire State of Mind (Part II) Broken Down
    			       Only Girl (In The World)""");
    }
}
