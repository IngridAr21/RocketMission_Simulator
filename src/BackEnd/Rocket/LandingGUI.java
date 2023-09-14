package BackEnd.Rocket;
import BackEnd.MyVector;
import java.util.ArrayList;
import java.util.Random;

import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Landing.OpenLoopStrategy;
import FrontEnd.ImprovedGroup.SmartGroup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LandingGUI {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1400;
    Stage primaryStage;
    
    public static Planets planets = new Planets();
    public static Planet[] planetArr = planets.getPlanets();
    static Planet rocket;
    ImageView moonlanderView;
    Pane pane;

    ArrayList<Double> xValues;
    ArrayList<Double> yValues;

    Circle[] stars = new Circle[5000];
    SmartGroup space;
  

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        double centerX = 0;
        double centerY = 2574.7;
        double radius = 2574.7;
        
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Circle(new Random().nextDouble());
            double d = new Random().nextDouble() * new Random().nextInt(1000 * 20);
            double d1 = new Random().nextDouble() * new Random().nextInt(20000 * 20);

            stars[i].setTranslateX(d - 1000);
            stars[i].setTranslateY(-d1 + 500);
            stars[i].setTranslateZ(new Random().nextDouble() * new Random().nextInt(1200));
            stars[i].setFill(Color.WHITE);

        }
        
        Circle titan = new Circle(centerX, centerY, radius);
        titan.setFill(Color.WHITE);

        pane = new Pane();
        pane.getChildren().add(titan);
        pane.getChildren().addAll(stars);
        pane.setTranslateY(700);
        pane.setTranslateX(700);
        
        Scene scene = new Scene(pane, WIDTH, HEIGHT, true);
        scene.setFill(Color.BLACK);


        MyVector position = new MyVector(1000, 100000);
                MyVector velocity = new MyVector(0, 0, 0);
                rocket = planetArr[11];
                xValues = new ArrayList<Double>();
                yValues = new ArrayList<Double>();
                OpenLoopStrategy test = new OpenLoopStrategy(position, velocity, rocket,1 );

                test.land(rocket);

                Image lander = new Image("/sorce/lander.jpg");
                moonlanderView = new ImageView(lander);
                moonlanderView.setFitHeight(30);
                moonlanderView.setFitWidth(30);

                pane.getChildren().add(moonlanderView);

                for (int i = 0; i < test.xSize(); i++) {
                //System.out.println(test.getyValues(i));
                xValues.add(-test.getxValues(i));
                yValues.add(-test.getyValues(i));
                }


        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case A ->
                    pane.setTranslateX(pane.getTranslateX() + 100); // instedad of group --> camera.set
                    
                case D ->
                    pane.setTranslateX(pane.getTranslateX() - 100);
                    
                case S ->
                    pane.setTranslateY(pane.getTranslateY() - 100);
                    
                case W ->
                    pane.setTranslateY(pane.getTranslateY() + 100);
                    
            }
        });

        double frameDuration = 3700.0 / 60.0;

        Timeline timeline = new Timeline();
        for (int i = 0; i < xValues.size(); i++) {
            double x = xValues.get(i);
            double y = yValues.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i/480), event ->{
                moonlanderView.setTranslateX(x);
                moonlanderView.setTranslateY(y);
                pane.setTranslateX(400-x);
                pane.setTranslateY(300-y);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

}
