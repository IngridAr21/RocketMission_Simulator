package FrontEnd.Shapes;


import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Rocket.HillClimbing;
import FrontEnd.Multithreding.MyTask;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

public class RocketShap {

    CelestialObject[] planets;
    Planets allPlanets;
    Node spaceship;
    boolean done = false;
    MyTask task;
    HillClimbing climb;
    private double scaler = 10000;

    public RocketShap(HillClimbing climb) {
        this.climb = climb;
        spaceship = new MeshView();
        task = new MyTask("src/sorce/12217_rocket_v1_l1.obj", (MeshView) spaceship);
        Thread thread = new Thread(task);

        thread.start();

    }

    void updatePosition() {
        if (((MeshView) spaceship).getMaterial() != null) {
            System.out.println(planets[11].getPosition());
            spaceship.setTranslateX(planets[11].getPosition().x / scaler);
            spaceship.setTranslateX(planets[11].getPosition().y / scaler);
            spaceship.setTranslateX(planets[11].getPosition().z / scaler);
        }
    }

    public MyTask getTask() {
        return task;
    }

    public Node getSpaceship() {
        spaceship.setScaleX(0.05);
        spaceship.setScaleY(0.05);
        spaceship.setScaleZ(0.05);
        spaceship.setTranslateX(planets[11].getPosition().x / scaler);
        spaceship.setTranslateX(planets[11].getPosition().y / scaler);
        spaceship.setTranslateX(planets[11].getPosition().z / scaler);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass()
                .getResourceAsStream("/sorce/rocket.jpg")));

        ((MeshView) spaceship).setMaterial(material);
        return spaceship;
    }
}
