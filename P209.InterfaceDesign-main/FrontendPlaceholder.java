import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.*;

public class FrontendPlaceholder extends Application implements FrontendInterface{
  private static BackendInterface back;

  public static void setBackend(BackendInterface back) {
    FrontendPlaceholder.back = back;
  }

  public void start(Stage stage) {
    Pane root = new Pane();

    createAllControls(root);

    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.setTitle("P2: Prototype");
    stage.show();
  }

  public void createAllControls(Pane parent) {
    createShortestPathControls(parent);
    createPathListDisplay(parent);
    createAdditionalFeatureControls(parent);
    createAboutAndQuitControls(parent);
  }

  public void createShortestPathControls(Pane parent) {
    Label src = new Label("Path Start Selector:  Memorial Union");
    src.setLayoutX(32);
    src.setLayoutY(16);
    parent.getChildren().add(src);

    Label dst = new Label("Path End Selector: Computer Science");
    dst.setLayoutX(32);
    dst.setLayoutY(48);
    parent.getChildren().add(dst);

    Button find = new Button("Submit/Find Button");
    find.setLayoutX(32);
    find.setLayoutY(80);
    parent.getChildren().add(find);
  }

  public void createPathListDisplay(Pane parent) {
    Label path =
        new Label(
            "Results List: \n\tMemorial Union\n\tSciene Hall\n\tPyschology\n\tComputerScience");
    path.setLayoutX(32);
    path.setLayoutY(112);
    parent.getChildren().add(path);
  }

  public void createAdditionalFeatureControls(Pane parent) {
	createButtonForPathExists(parent);
	createThreeClosestList(parent);
  }

  public void createAboutAndQuitControls(Pane parent) {
    Button about = new Button("About");
    about.setLayoutX(32);
    about.setLayoutY(560);
    parent.getChildren().add(about);

    Button quit = new Button("Quit");
    quit.setLayoutX(96);
    quit.setLayoutY(560);
    parent.getChildren().add(quit);
  }


  public void createButtonForPathExists(Pane parent){
	String s = "check if path exists: " + back.pathExists("a", "b");
	Button pathExists = new Button(s);
	pathExists.setLayoutX(32);
	pathExists.setLayoutY(360);
	parent.getChildren().add(pathExists);
}

  public void createThreeClosestList(Pane parent){
	List<String> a = back.threeClosestPlaces("a");
	String s = "";
	for(String p : a)
	{
		s += a;
	}
	Label threeClosest = new Label("three closest places: "+ s);
	threeClosest.setLayoutX(32);
	threeClosest.setLayoutY(250);
	parent.getChildren().add(threeClosest);

  }
}

