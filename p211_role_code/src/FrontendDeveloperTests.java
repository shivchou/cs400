import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

}
