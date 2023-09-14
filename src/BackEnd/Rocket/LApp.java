package BackEnd.Rocket;
import javafx.application.Application;
import javafx.stage.Stage;

public class LApp extends Application{

    LandingGUI landing;

    public void start(Stage arg0) throws Exception {
        landing = new LandingGUI();
        //landing.landingGUI(arg0);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
