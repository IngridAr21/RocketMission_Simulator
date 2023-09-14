package BackEnd.Celectial.Creater;

import java.lang.Cloneable;

import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planet;
import Utility.Data;

public interface CelestialObject extends Cloneable {

    default MyVector calculateAcceleration(CelestialObject[] bodies) {
        MyVector a = new MyVector(0, 0, 0);
        for (int j = 0; j < bodies.length; j++) {
            if (this == bodies[j] || this.getName().equalsIgnoreCase("sun")) {
                continue;
            }
            MyVector pos1 = bodies[j].getPosition().copy();
            MyVector pos2 = this.getPosition().copy();

            double dist = pos1.dist(pos2);
            double mass = bodies[j].getMass();
            double ForceScalar = Data.G * mass / Math.pow(dist, 3);
            pos1.sub(pos2);
            pos1.mult(ForceScalar);
            a.add(pos1);
        }
        return a;
    }

    default boolean equals(CelestialObject object) {
        return this.getName().equals(object.getName());
    }

    public String getName();

    public double getMass();

    public MyVector getPosition();

    public MyVector getVelocity();

    public MyVector getAccelaration();

    public void setPosition(MyVector position);

    public void setVelocity(MyVector velocity);

    public void setAccelaration(MyVector accelaration);

    public boolean isFirstPosition();

    public void changeInfoFirstPosition();

    public CelestialObject clone() throws CloneNotSupportedException;

    public Planet getCopy();

    public double getRadius();

}