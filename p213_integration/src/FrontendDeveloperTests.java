import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * class to test methods of Frontend
 */
public class FrontendDeveloperTests extends ApplicationTest 
{
	
	/**
	 * the Frontend object used for testing
	 */
	FrontendInterface frontend = new FrontendPlaceholder();
	
	/**
	 * parent pane passed to methods
	 */
	Pane parent = new Pane();

	/**
	 * launch program to run tests
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setup() throws Exception {
		ApplicationTest.launch(Frontend.class);
	}

	/**
	 * tests that the labels and button created by the createShortestPathControls
	 * method are correct
	 */
	@Test
	public void testCreateShortestPathControls() {
		frontend.createShortestPathControls(parent);
		
		Label starter = lookup("#starter").query();
		//test label with correct text is created
		Assertions.assertEquals(starter.getText(), "Enter Start and End Points:");
		
		Button findButton = (Button) lookup("#findButton").query();
		
		//test that button is created correctly
		Assertions.assertTrue(findButton.isVisible());
		Assertions.assertEquals("Find Shortest Path", findButton.getText());
	}

	/**
	 * tests that the labels and button created by the
	 * createAdditionalFeaturesControls method are correct
	 */
	@Test
	public void testCreateAdditionalFeatureControls() {
		frontend.createAdditionalFeatureControls(parent);

		CheckBox check = lookup("#showTimesBox").query();
		
		//test that checkbox is created correctly
		Assertions.assertTrue(check.isVisible());
		
		Label select = lookup("#locationSelector").query();
		
		//test that label is created correctly
		Assertions.assertEquals(select.getText(), "Find Locations Accessible in Given Time");
	}

	/**
	 * tests that the labels and button created by the createAboutAndQuitControls
	 * method are correct
	 */
	@Test
	public void testCreateAboutAndQuitControls() 
	{
		frontend.createAboutAndQuitControls(parent);
		Button aboutButton = (Button) lookup("#about").query();
		Button quitButton = (Button) lookup("#quit").query();

		//test that quit and about controls are created correctly
		Assertions.assertTrue(aboutButton.isVisible());
		Assertions.assertEquals("About", aboutButton.getText());
		Assertions.assertTrue(quitButton.isVisible());
		Assertions.assertEquals("Quit", quitButton.getText());
	}
	
	/**
	 * integration test for findShortestPath() - test that input is correctly taken and passed to method
	 * with correct output
	 */
	@Test
	public void testShortestPathIntegration()
	{
		try {
		      BackendInterface backend = new Backend(null);
		      frontend.createAllControls(parent);
		      backend.loadGraphData("campus.dot");
		      // creating expected shortest path to compare with real output
		      ArrayList<String> expectedShortestPath = new ArrayList<>();
		      
		      //take simulated input for start point
		      Label input = lookup("#inputStart").query();
		      
		      clickOn("#inputStart");
		      write("Union South");
		      
		      String start = input.getText();
		      
		      //take simulated input for end point
		      Label endput = lookup("#inputEnd").query();
		      
		      clickOn("#inputEnd");
		      write("Atmospheric, Oceanic and Space Sciences");
		      
		      String end = input.getText();
		      
		      //populate shortest path
		      expectedShortestPath.add("Union South");
		      expectedShortestPath.add("Computer Sciences and Statistics");
		      expectedShortestPath.add("Atmospheric, Oceanic and Space Sciences");
		      // tests comparison between expectedShortestPath and real output
		      Assertions.assertTrue(
		          backend.findShortestPath(start, end)
		              .equals(expectedShortestPath));

		      // testing for invalid nodes
		      String invalidPredNode = "invalid node pred";
		      String invalidSuccNode = "invalid node succ 2";
		      Assertions.assertTrue(backend.findShortestPath(invalidPredNode, invalidSuccNode).size() == 0);

		    } catch (Exception e) {
		      Assertions.fail("Error while handling testShortestPath: " + e.getMessage());
		    }
	}

	
	/**
	 * integration test for getTravelTimesOnPath() - test that input is correctly taken and passed to method
	 * with correct output
	 */
	@Test
	public void testTravelTimesIntegration()
	{
		try {
		      BackendInterface backend = new Backend(null);
		      
		      frontend.createAllControls(parent);
		      
		      // load valid graph data file
		      backend.loadGraphData("campus.dot");
		      
		    //take simulated input for start point
		      Label input = lookup("#inputStart").query();
		      
		      clickOn("#inputStart");
		      write("Union South");
		      
		      String start = input.getText();
		      
		      //take simulated input for end point
		      Label endput = lookup("#inputEnd").query();
		      
		      clickOn("#inputEnd");
		      write("Atmospheric, Oceanic and Space Sciences");
		      
		      String end = input.getText();
		      
		      //simulate input for checkbox
		      CheckBox check = lookup("#showTimesBox").query();
		      
		      clickOn("#showTimesBox");
		      
		      //output of running getTravelTimesOnPath()
		      Label output = lookup("output").query();
		      
		      List<Double> times = backend.getTravelTimesOnPath(start, end);
		      
		      String s = "";
		      
		      for (Double t : times) {
					s = s + ", " + t.toString();
				}
		      
		      //compare expected with actual outputs
		      Assertions
		          .assertEquals(output.getText(), s);
		    } catch (Exception e) {
		      Assertions.fail("Error while handling testShortestPath: " + e.getMessage());
		    }

	}
}
