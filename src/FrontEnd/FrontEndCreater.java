package FrontEnd;

import java.util.Random;
import BackEnd.Simulator.Simulation;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import FrontEnd.ImprovedGroup.SmartGroup;
import FrontEnd.KeyBoardAndMouse.MyKeyBoard;
import FrontEnd.KeyBoardAndMouse.MyMouse;
import FrontEnd.Shapes.ShapesCreater;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

public class FrontEndCreater {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 1400;

    Camera camera;
    ShapesCreater creater;
    SmartGroup space;
    Node[] celecShapes;
    Simulation s;
    PhysicsEnginGeneral pEnginGeneral;
    Stage primaryStage;

    public void start(Stage primaryStage, PhysicsEnginGeneral pEnginGeneral) throws InterruptedException {
        this.pEnginGeneral = pEnginGeneral;
        this.primaryStage = primaryStage;
        s = new Simulation(pEnginGeneral);

        Circle[] stars = new Circle[900];
        PhongMaterial m = new PhongMaterial();
        m.setSpecularColor(Color.WHITE);

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Circle(new Random().nextDouble());
            double d = new Random().nextDouble() * new Random().nextInt(WIDTH * 2);
            double d1 = new Random().nextDouble() * new Random().nextInt(HEIGHT * 2);

            stars[i].setTranslateX(d);
            stars[i].setTranslateY(d1);
            stars[i].setTranslateZ(new Random().nextDouble() * new Random().nextInt(1200));
            stars[i].setFill(Color.WHITE);

        }

        creater = new ShapesCreater(pEnginGeneral.getAllPlanets());

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setAxis(new Point3D(0, 1, 1));
        //task = creater.getTask();
        SmartGroup group = new SmartGroup();
        celecShapes = creater.getSpheres();
        group.getChildren().addAll(celecShapes);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-1200);
        space = new SmartGroup();
        space.getChildren().add(group);
        space.getChildren().addAll(stars);
        group.toFront();

        group.getChildren().add(LightSource());
        creater.rotatePlanets();
        camera = new PerspectiveCamera();
        Scene scene = new Scene(space, WIDTH, HEIGHT);

        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        camera.setNearClip(0);
        primaryStage.setTitle("Title");

        primaryStage.setScene(scene);
        primaryStage.show();

        MyMouse mouseControler = new MyMouse(group, scene, camera);
        MyKeyBoard kyboard = new MyKeyBoard(group, scene, camera);

        mouseControler.initMouse();
        kyboard.initKeyboard();

        animation(group);

    }

    private void animation(SmartGroup group) {
        AnimationTimer time = new AnimationTimer() {
            
            double start = pEnginGeneral.getTime().getStart();
            double end = pEnginGeneral.getTime().getEnd();
            double timeStep = pEnginGeneral.getTime().getTimeStep();

            @Override
            public void handle(long now) {
                creater.rotatePlanets();
                pEnginGeneral.movePlanets();
                pEnginGeneral.trajectory();
                for (int i = 1; i < celecShapes.length; i++) {
                    Circle line = new Circle(0.1);
                    line.setTranslateX(group.getChildren().get(i).getTranslateX());
                    line.setTranslateX(group.getChildren().get(i).getTranslateY());
                    line.setTranslateX(group.getChildren().get(i).getTranslateZ());
                    line.setFill(Color.WHITE);
                    space.getChildren().add(line);
                }

                creater.updatePosition();
                start += timeStep;

                if (start > end) {
                    primaryStage.close();
                }
            }
        };
        time.start();

    }

    public Line create3DLine(double startX, double startY, double startZ,
            double endX, double endY, double endZ) {
        Line line = new Line(startX, startY, endX, endY);
        line.setTranslateZ(startZ);
        line.setStrokeWidth(20);
        line.setStroke(Color.WHITE);
        return line;
    }

    private Node LightSource() {
        PointLight light = new PointLight();
        light.getTransforms().add(new Translate(0, 0, 0));
        return light;
    }

    
}