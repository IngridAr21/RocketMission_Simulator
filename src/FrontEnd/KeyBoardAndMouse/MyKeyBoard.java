package FrontEnd.KeyBoardAndMouse;

import FrontEnd.ImprovedGroup.SmartGroup;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.transform.Translate;

public class MyKeyBoard {
    SmartGroup group;
    Scene scene;
    Camera camera;

    public MyKeyBoard(SmartGroup group, Scene scene, Camera camera) {
        this.group = group;
        this.scene = scene;
        this.camera = camera;

    }

    private int selectedObjectIndex = 0;

    public void initKeyboard() {
        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case A ->
                    group.setTranslateX(group.getTranslateX() + 100); // instedad of group --> camera.set
                    
                case D ->
                    group.setTranslateX(group.getTranslateX() - 100);
                    
                case S ->
                    group.setTranslateY(group.getTranslateY() - 100);
                    
                case W ->
                    group.setTranslateY(group.getTranslateY() + 100);
                    
                case F1 ->
                    selectedObjectIndex = 0;
                    
                case F2 ->
                    selectedObjectIndex = 1;
                    
                case F3 ->
                    selectedObjectIndex = 2;
                    
                case F4 ->
                    selectedObjectIndex = 3;
                    
                case F5 ->
                    selectedObjectIndex = 4;
                    
                case F6 ->
                    selectedObjectIndex = 5;
                    
                case F7 ->
                    selectedObjectIndex = 6;
                    
                case F8 ->
                    selectedObjectIndex = 7;
                    
                case F9 ->
                    selectedObjectIndex = 8;
                    
                case F10 ->
                    selectedObjectIndex = 9;
                    
                case F11 ->
                    selectedObjectIndex = 10;
                    
                case F12 ->
                    selectedObjectIndex = 11;
                    
                case R ->
                    selectedObjectIndex = 12;
                    
                case X ->
                    group.translateZProperty().set(group.getTranslateZ() + 20);
                    
                case Z ->
                    group.translateZProperty().set(group.getTranslateZ() - 20);
                    
            }
            Translate cameraTranslate = new Translate(
                    group.getChildren().get(selectedObjectIndex).getTranslateX(),
                    group.getChildren().get(selectedObjectIndex).getTranslateY(),
                    group.getChildren().get(selectedObjectIndex).getTranslateZ() + 240);

            camera.getTransforms().clear();
            camera.getTransforms().addAll(cameraTranslate);

            group.getChildren().get(selectedObjectIndex).translateXProperty().addListener((obs, oldVal, newVal) -> {
                cameraTranslate.setX(newVal.doubleValue());
            });

            group.getChildren().get(selectedObjectIndex).translateYProperty().addListener((obs, oldVal, newVal) -> {
                cameraTranslate.setY(newVal.doubleValue());
            });

            group.getChildren().get(selectedObjectIndex).translateZProperty().addListener((obs, oldVal, newVal) -> {
                cameraTranslate.setZ(newVal.doubleValue() + 290);
            });
        });
    }

}
