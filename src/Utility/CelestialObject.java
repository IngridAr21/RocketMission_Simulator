package Utility;

import BackEnd.MyVector;
import java.lang.Cloneable;

public interface CelestialObject extends Cloneable {

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

}
