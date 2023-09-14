package FrontEnd.Shapes;

import BackEnd.Celectial.PlanetsCreater.Planets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class ShapesCreater {

        Sphere[] spheres;
        Node[] nodes;
        Planets planets;
        double scalerDist = 10000;
        double scalerMass = 1e25;
        //MyTask task;

        public ShapesCreater(Planets planets) {

                this.planets = planets;
                spheres = new Sphere[12];

                updatePosition(planets);

                nodes = new Node[13];

                for (int i = 0; i < spheres.length; i++) {
                        nodes[i] = spheres[i];
                }

        }

        private void updatePosition(Planets planets) {
                Image[] images = new Image[] {
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/sun.gif")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/mercury.png")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/venus.png")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/earth.jpg")),

                                new Image(getClass()
                                                .getResourceAsStream("/sorce/moon.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/mars.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/jupiter.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/saturn.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/titan.jpg")),

                                new Image(getClass()
                                                .getResourceAsStream("/sorce/neptun.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/uranus.jpg")),
                                new Image(getClass()
                                                .getResourceAsStream("/sorce/OIP.jpg")),

                };

                for (int i = 0; i < spheres.length; i++) {
                        double radius = planets.getPlanets()[i].getMass();
                        if (i == 0)
                                radius /= 1E29;
                        else
                                radius /= scalerMass;
                        
                        if (radius < 1)
                                radius = 1;
                        
                        spheres[i] = new Sphere(radius);
                        spheres[i].setTranslateX(planets.getPlanets()[i].getPosition().x / scalerDist);
                        spheres[i].setTranslateY(planets.getPlanets()[i].getPosition().y / scalerDist);
                        spheres[i].setTranslateZ(planets.getPlanets()[i].getPosition().z / scalerDist);

                        PhongMaterial material = new PhongMaterial();
                        material.setDiffuseMap(images[i]);
                        spheres[i].setMaterial(material);
                        if(i==0)
                        material.setSelfIlluminationMap(images[0]);

                }
        }

        public void updatePosition() {
                for (int i = 0; i < spheres.length; i++) {

                        spheres[i].setTranslateX(planets.getPlanets()[i].getPosition().x / scalerDist);
                        spheres[i].setTranslateY(planets.getPlanets()[i].getPosition().y / scalerDist);
                        spheres[i].setTranslateZ(planets.getPlanets()[i].getPosition().z / scalerDist);
                }
        }

        // public Node getNodes() {
        //         return rocketShap.getSpaceship();
        // }

        public Sphere[] getSpheres() {

                return spheres;
        }

        public void rotatePlanets() {
                double day = 1;
                spheres[0].setRotationAxis(Rotate.Y_AXIS);
                spheres[0].setRotate(spheres[0].getRotate() + day / 27);

                spheres[1].setRotationAxis(Rotate.Y_AXIS);
                spheres[1].setRotate(spheres[1].getRotate() + day / 59);

                spheres[2].setRotationAxis(Rotate.Y_AXIS);
                spheres[2].setRotate(spheres[2].getRotate() + day / 243);

                spheres[3].setRotationAxis(Rotate.Y_AXIS);
                spheres[3].setRotate(spheres[3].getRotate() + day);

                spheres[4].setRotationAxis(Rotate.Y_AXIS);
                spheres[4].setRotate(spheres[4].getRotate() + day);

                spheres[5].setRotationAxis(Rotate.Y_AXIS);
                spheres[5].setRotate(spheres[5].getRotate() + day - 0.8);

                spheres[6].setRotationAxis(Rotate.Y_AXIS);
                spheres[6].setRotate(spheres[6].getRotate() + day - 0.6);

                spheres[7].setRotationAxis(Rotate.Y_AXIS);
                spheres[7].setRotate(spheres[7].getRotate() + day / 2);

                spheres[8].setRotationAxis(Rotate.Y_AXIS);
                spheres[8].setRotate(spheres[8].getRotate() + day / 2);

                spheres[9].setRotationAxis(Rotate.Y_AXIS);
                spheres[9].setRotate(spheres[9].getRotate() + day / 2);

                spheres[10].setRotationAxis(Rotate.Y_AXIS);
                spheres[10].setRotate(spheres[10].getRotate() + day / 2);
        }

}
