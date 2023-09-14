package BackEnd.Rocket;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import BackEnd.Simulator.TimeManagment.MyTime;
import FrontEnd.FrontEndCreater;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static App instance;
    public static void main(String[] args) {
        launch(args);
    }

    FrontEndCreater frontEnd;
    LandingGUI landing;
    Stage primaryStage;

    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        frontEnd = new FrontEndCreater();
        landing = new LandingGUI();

        Planets planet = new Planets();

        double hour = 3600;
        double day = 24 * hour;
        double year = (day * 365);
        MyTime time = new MyTime(0, year, hour);
        PhysicsEnginGeneral pEnginGeneral = new PhysicsEnginGeneral(planet, "RK4", time);

        frontEnd.start(primaryStage, pEnginGeneral);
    }

    public void switchToLanding() {
        // Close the existing stage
        primaryStage.close();

        // Start the Landing GUI in a new stage
        Stage landingStage = new Stage();
        landing.start(landingStage);
    }

    @Override
    public void stop() {
    }

    public static App getInstance() {
        return instance;
    }
}
