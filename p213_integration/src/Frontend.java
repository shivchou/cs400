import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * create the user-facing interface for the shortest path app
 */
public class Frontend extends Application implements FrontendInterface 
{
	/**
	 * the backend object whose functions are called for shortest path functionality
	 */
	private static BackendInterface back;

	/**
	 * the starting point
	 */
	private String start;

	/**
	 * the end destination
	 */
	private String end;

	/**
	 * a list of the locations on the shortest path
	 */
	private List<String> shortestPath;

	/**
	 * the button pressed to call the shortest path method from backend
	 */
	private Button findButton;

	/**
	 * text field to take user input for starting point
	 */
	private TextField inputStart;

	/**
	 * text field to take user input for end destination
	 */
	private TextField inputEnd;

	
	/**
	 * instantiate a backend object to use the shortest path functionalities
	 * 
	 * @param back - the backend object to use
	 */
	public static void setBackend(BackendInterface back) 
	{
		Frontend.back = back;
	}

	
	/**
	 * starts the program (extended from Application)
	 * 
	 * @param stage - the stage
	 */
	public void start(Stage stage) {
		Pane root = new Pane();

		createAllControls(root);

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Shortest Path Finder");
		stage.show();
	}

	/**
	 * Creates all controls in the GUI.
	 * 
	 * @param parent the parent pane that contains all controls
	 */
	@Override
	public void createAllControls(Pane parent) {
		createShortestPathControls(parent);
		createPathListDisplay(parent);
		createAdditionalFeatureControls(parent);
		createAboutAndQuitControls(parent);

	}

	/**
	 * Creates the controls for the shortest path search.
	 * 
	 * @param parent the parent pane that contains all controls
	 */
	@Override
	public void createShortestPathControls(Pane parent) 
	{
		//create a label that shows where to input start and end points
		Label starter = new Label("Enter Start and End Points:");
		starter.setId("starter");
		starter.setLayoutX(32);
		starter.setLayoutY(16);
		parent.getChildren().add(starter);

		//create a text field to take user input for the starting point
		inputStart = new TextField();
		inputStart.setId("inputStart");
		inputStart.setPromptText("Enter starting point");
		inputStart.setLayoutX(250);
		inputStart.setLayoutY(16);
		parent.getChildren().add(inputStart);

		//create a text field to take user input for the end destination
		inputEnd = new TextField();
		inputEnd.setId("inputEnd");
		inputEnd.setPromptText("Enter end destination:");
		inputEnd.setLayoutX(250);
		inputEnd.setLayoutY(48);
		parent.getChildren().add(inputEnd);

		//create a button to call findShortestPath() from backend when pressed
		findButton = new Button("Find Shortest Path");
		findButton.setId("findButton");
		findButton.setLayoutX(32);
		findButton.setLayoutY(80);
		parent.getChildren().add(findButton);

	}

	/**
	 * Creates the controls for displaying the shortest path returned by the search.
	 * 
	 * @param the parent pane that contains all controls
	 */
	@Override
	public void createPathListDisplay(Pane parent) 
	{
		//call findShortestPath() and output the result
		findButton.setOnAction(event -> 
		{
			//assign the text entered to start and end
			start = inputStart.getText();
			end = inputEnd.getText();

			//call findShortestPath()
			shortestPath = back.findShortestPath(start, end);
			
			//iterate through the returned list and output as a string in a label
			String out = "shortest path: ";
			for (String s : shortestPath) 
			{
				out = out + ", " + s;
			}
			
			//create the output label and use it to display the returned places from findShortestPath()
			Label output = new Label(out);
			output.setId("output");
			output.setLayoutX(32);
			output.setLayoutY(250);
			parent.getChildren().add(output);
		});

	}

	/**
	 * Creates controls for the two features in addition to the shortest path
	 * search.
	 * 
	 * @param parent parent pane that contains all controls
	 */
	@Override
	public void createAdditionalFeatureControls(Pane parent) {
		this.createTravelTimesBox(parent);
		this.createFindReachableControls(parent);
	}

	/**
	 * Creates the check box to add travel times in the result display.
	 * 
	 * @param parent parent pane that contains all controls
	 */
	@Override
	public void createTravelTimesBox(Pane parent) 
	{
		//create a checkbox that user checks to show times
		CheckBox showTimesBox = new CheckBox("Show Walking Times");
		showTimesBox.setId("showTimesBox");
		showTimesBox.setLayoutX(200);
		showTimesBox.setLayoutY(80);
		
		//call getTravelTimesOnPath() from backend if box is checked
		showTimesBox.setOnAction(event -> 
		{
			if (showTimesBox.isSelected())
			{
				List<Double> travelTimes = back.getTravelTimesOnPath("startLocation", "endLocation");
				
				//create a string from returned list
				String out = "";
				for (Double s : travelTimes) {
					out = out + ", " + s.toString();
				}
				
				//pass created string to label to display returned list
				Label output = new Label(out);
				output.setId("output");
				output.setLayoutX(32);
				output.setLayoutY(290);
				parent.getChildren().add(output);
			}
		});
		parent.getChildren().add(showTimesBox);

	}

	/**
	 * Creates controls to search for all destinations reachable in a given time.
	 * 
	 * @param parent parent pane that contains all controls
	 */
	@Override
	public void createFindReachableControls(Pane parent) 
	{
		//create a label to show where to enter time	
		Label locationSelector = new Label("Find Locations Accessible in Given Time");
		locationSelector.setId("locationSelector");
		locationSelector.setLayoutX(500);
		locationSelector.setLayoutY(16);
		parent.getChildren().add(locationSelector);

		//create a text field to take user input of time in seconds
		TextField timeSelector = new TextField();
		timeSelector.setId("timeSelector");
		timeSelector.setPromptText("Enter time in seconds (eg. 5 min = 300)");
		timeSelector.setLayoutX(500);
		timeSelector.setLayoutY(48);
		parent.getChildren().add(timeSelector);

		//create a button that calls getReachableLocations() when pressed
		Button findLocations = new Button("Find Locations");
		findLocations.setId("findLocations");
		
		//call getReachableLocations() and display result
		findLocations.setOnAction(event -> 
		{
			//store user input as string and cast to double, pass to backend method
			String time = timeSelector.getText();
			List<String> locations = back.getReachableLocations(start, Double.valueOf(time));
			
			//create string to be displayed
			String out = "";
			for (String s : locations) 
			{
				out = out + ", " + s;
			}
			
			//create a label to display results of getReachableLocations()
			Label reachable = new Label(out);
			reachable.setLayoutX(32);
			reachable.setLayoutY(340);
			parent.getChildren().add(reachable);
		});
		findLocations.setLayoutX(500);
		findLocations.setLayoutY(80);
		parent.getChildren().add(findLocations);
	}

	/**
	 * Creates an about and quit button.
	 * 
	 * @param parent parent pane that contains all controls
	 */
	@Override
	public void createAboutAndQuitControls(Pane parent) 
	{
		//create the about button and display instructions when pressed
		Button about = new Button("About");
		about.setId("about");
		about.setLayoutX(32);
		about.setLayoutY(560);
		
		//make a label that displays instructions on using the app when 'about' button is pressed
		about.setOnAction(event -> 
		{
			Label aboutPrint = new Label("To use this app, enter the start and end destinations "
					+ "then hit the 'Find Shortest Path' button."
					+ "\n If you'd like to see the time it takes to get there, "
					+ "check the 'Show Walking Times' button.");
			aboutPrint.setLayoutX(32);
			aboutPrint.setLayoutY(400);
			parent.getChildren().add(aboutPrint);
		});
		parent.getChildren().add(about);

		//create a button that quits the program and closes the window when pressed
		Button quit = new Button("Quit");
		quit.setId("quit");
		quit.setLayoutX(96);
		quit.setLayoutY(560);
		
		//exit program and close window when button is pressed
		quit.setOnAction(event -> 
		{
			Platform.exit();
		});
		parent.getChildren().add(quit);
	}
}
