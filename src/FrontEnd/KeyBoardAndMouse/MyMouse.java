package FrontEnd.KeyBoardAndMouse;

import FrontEnd.ImprovedGroup.SmartGroup;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;

public class MyMouse {
    private double anchorX, anchorY;
    private double anchorAngelX, anchorAngelY;
    private DoubleProperty angelX = new SimpleDoubleProperty(0);
    private DoubleProperty angelY = new SimpleDoubleProperty(0);

    SmartGroup group;
    Scene scene;
    Camera camera;

    public MyMouse(SmartGroup group, Scene scene, Camera camera) {
        this.group = group;
        this.scene = scene;
        this.camera = camera;
    }

    public void initMouse() {
        Rotate xRotate, yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS));
        xRotate.angleProperty().bind(angelX);
        yRotate.angleProperty().bind(angelY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngelX = angelX.get();
            anchorAngelY = angelY.get();
        });

        scene.setOnMouseDragged(event -> {

            angelX.set(anchorAngelX - (anchorY - event.getSceneY()));
            angelY.set(anchorAngelY + anchorX - event.getSceneX());
        });
        scene.addEventHandler(ScrollEvent.SCROLL, event -> {

            double delta = event.getDeltaY();

            delta *= 100;

            camera.setTranslateZ(camera.getTranslateZ() + delta);

        });
    }

}
