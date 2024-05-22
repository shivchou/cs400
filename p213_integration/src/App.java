import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    System.out.println("v0.1");
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Application.launch(Frontend.class, args);
  }
}
