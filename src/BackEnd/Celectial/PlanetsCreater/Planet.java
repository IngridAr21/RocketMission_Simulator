package BackEnd.Celectial.PlanetsCreater;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;

public class Planet implements CelestialObject {

    public static class PlanetBuilder {
        private double radius;
        private double distanceFromSun;
        private String name;
        private double mass;
        private MyVector position;
        private MyVector velocity;

        public PlanetBuilder() {

        }

        public PlanetBuilder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public PlanetBuilder distanceFromSun(double distanceFromSun) {
            this.distanceFromSun = distanceFromSun;
            return this;
        }

        public PlanetBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlanetBuilder mass(double mass) {
            this.mass = mass;
            return this;
        }

        public PlanetBuilder position(MyVector position) {
            this.position = position;
            return this;
        }

        public PlanetBuilder velocity(MyVector velocity) {
            this.velocity = velocity;
            return this;
        }

        Planet p;

        public BackEnd.Celectial.Creater.CelestialObject build() {
            p = new Planet(name, mass, radius, distanceFromSun, velocity, position);
            return p;
        }
    }

    private double radius;
    private double distance;
    private double sunSize = 1391400;
    private double mass;
    private MyVector position;
    private String name;
    private MyVector velocity;
    private MyVector accelaration;

    private boolean isFirstPosition = true;

    private Planet(String name, double mass, double radius, double distance, MyVector velocity, MyVector position) {
        this.position = position;
        this.velocity = velocity;
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        accelaration = new MyVector(1, 1, 1);

    }

    private Planet(String name, double mass, MyVector velocity, MyVector position, boolean isFirstPosition) {
        this.position = position;
        this.velocity = velocity;
        this.name = name;
        this.mass = mass;
        this.accelaration = new MyVector(0, 0, 0);
        this.isFirstPosition = isFirstPosition;

    }

    public Planet getCopy(){
        Planet planet = new Planet (name, mass, velocity.copy(), position.copy(), isFirstPosition);
        return planet;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSunSize() {
        return sunSize;
    }

    public void setSunSize(double sunSize) {
        this.sunSize = sunSize;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public MyVector getPosition() {
        return position;
    }

    public void setPosition(MyVector position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyVector getVelocity() {
        return velocity;
    }

    public void setVelocity(MyVector velocity) {
        this.velocity = velocity;
    }

    public MyVector getAccelaration() {
        return accelaration;
    }

    public void setAccelaration(MyVector accelaration) {
        this.accelaration = accelaration;
    }

    @Override
    public boolean isFirstPosition() {
        return isFirstPosition;
    }

    @Override
    public void changeInfoFirstPosition() {
        this.isFirstPosition = false;
    }

    @Override
    public CelestialObject clone() throws CloneNotSupportedException {
        CelestialObject clone = new Planet(this.name, this.mass, this.radius, this.distance,
                this.velocity.copy(),
                this.position.copy());
        ((Planet) clone).isFirstPosition = this.isFirstPosition;
        return clone;
    }

}