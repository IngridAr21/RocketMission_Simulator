package BackEnd.Simulator.ODESolvers;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import Utility.Data;

public class RK4 implements ODESolver {

    CelestialObject[] planets;

    public RK4(CelestialObject[] planets) {
        this.planets = planets;
    }

    /***
     * euler method 
     * @param oldVector oldVector w0
     * @param stepSize step sizes
     * @param derivative takes in the acceleration of k1 for k2, k2 for k3, k3 for k4
     * @return newVector w1
     */
    public static MyVector eulerMethod(MyVector oldVector, double stepSize, MyVector derivative) {
        MyVector newVector = derivative.copy();
        newVector.mult(stepSize);
        newVector.add(oldVector);
        return newVector;
    }

    
    @Override
    public void evaluate(double stepSize) {
        try {
            rungeKutta(stepSize, planets);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Runge Kutta method
     * @param stepsize step size
     * @param planets array of Celestial objects
     * @throws CloneNotSupportedException
     */
    public void rungeKutta(double stepsize, CelestialObject[] planets) throws CloneNotSupportedException
    {
        for (CelestialObject body:planets)
        {
            MyVector velocityk1 = body.getVelocity().copy();
            MyVector accelerationk1 = body.getAccelaration().copy();

            MyVector k1copy = accelerationk1.copy();
            k1copy.mult(1/3);
        
            MyVector velocityk2 = eulerMethod(body.getVelocity().copy(), stepsize/3, k1copy.copy());
            MyVector accelerationk2 = derivativeAtPoint(body, copyPlanets(planets), stepsize/3);

            MyVector k2copy = accelerationk2.copy();
            k2copy.sub(k1copy);
            
            MyVector velocityk3 = eulerMethod(body.getVelocity().copy(), 2*stepsize/3,k2copy.copy());
            MyVector accelerationk3 = derivativeAtPoint(body, copyPlanets(planets), 2*stepsize/3);

            MyVector k3copy = accelerationk3.copy();
            k2copy = accelerationk2.copy();
            k1copy = accelerationk1.copy();
            k3copy.sub(k2copy);
            k3copy.add(k1copy);

            MyVector velocityk4 = eulerMethod(body.getVelocity().copy(), stepsize, body.getAccelaration().copy());
            MyVector accelerationk4 = derivativeAtPoint(body, copyPlanets(planets), stepsize);

            velocityk1.mult(stepsize);
            velocityk2.mult(stepsize);
            velocityk3.mult(stepsize);
            velocityk4.mult(stepsize);

            velocityk2.mult(3);
            velocityk3.mult(3);

            MyVector velocityk = velocityk1;
            velocityk.add(velocityk2);
            velocityk.add(velocityk3);
            velocityk.add(velocityk4);
            velocityk.div(8);

            accelerationk1.mult(stepsize);
            accelerationk2.mult(3*stepsize);
            accelerationk3.mult(3*stepsize);
            accelerationk4.mult(stepsize);

            MyVector accelerationk = accelerationk1;
            accelerationk.add(accelerationk2);
            accelerationk.add(accelerationk3);
            accelerationk.add(accelerationk4);
            accelerationk.div(8);


            MyVector newPosition = body.getPosition().copy();
            newPosition.add(velocityk);
            MyVector newVelocity = body.getVelocity().copy();
            newVelocity.add(accelerationk);

            body.setPosition(newPosition);
            body.setVelocity(newVelocity);
        }
        updateAcceleration(planets);

    }

    /**
     * moves the planets by a step to copy them and update the acceleration
     * @param stepSize step size
     * @param planets array of celestial objects
     * @return array of the new celestial objects
     */
    public CelestialObject[] moveByH(double stepSize, CelestialObject[] planets) {
        for (CelestialObject planet : planets) {
            MyVector position = planet.getPosition();
            MyVector velocity = planet.getVelocity();
            MyVector acceleration = planet.getAccelaration();
            planet.setPosition(eulerMethod(position, stepSize, velocity));
            planet.setVelocity(eulerMethod(velocity, stepSize, acceleration));
        }
        updateAcceleration(planets);
        return planets;
    }

    /**
     * gets the derivative at point to get the acceleration 
     * @param body celestial object
     * @param planets array of celestial objects
     * @param stepsize step size
     * @return the acceleration vector
     */
    public MyVector derivativeAtPoint(CelestialObject body, CelestialObject[] planets, double stepsize)
    {
        CelestialObject[] newPlanets = moveByH(stepsize, planets);
        int index = -1;
        for(int i = 0; i < planets.length; i++)
        {
            if(checkName(newPlanets[i].getName(), body.getName()))
            {
                index = i;
                break;
            }
        }
        
        MyVector acceleration = newPlanets[index].getAccelaration();
        return acceleration;
    }

    /**
     * copy the planets to apply the move the planets
     * @param planets array of celestial objects
     * @return array of celestial objects
     * @throws CloneNotSupportedException
     */
    public CelestialObject[] copyPlanets(CelestialObject[]planets) throws CloneNotSupportedException
    {
        int size = planets.length;
        CelestialObject[] planetsCopy = new CelestialObject[size];
        for (int i = 0; i < planets.length; i++) {
            CelestialObject copy = planets[i].clone();
            planetsCopy[i] = copy;
        }
        return planetsCopy;
    }

    /**
     * calculates the acceleration of planetes using Newton's formulas
     * @param body celestial object
     * @param planets array of celestial objects
     * @return acceleration vector
     */
    private MyVector calculateAcceleration(CelestialObject body, CelestialObject[] planets) {
        MyVector acceleration = new MyVector(0, 0, 0);

        for (int j = 0; j < planets.length; j++) {
            if (checkName(planets[j].getName(), body.getName())) {
                continue;
            }
            MyVector pos1 = planets[j].getPosition().copy();
            MyVector pos2 = body.getPosition().copy();

            double dist = pos1.dist(pos2);
            double mass = planets[j].getMass();
            double ForceScalar = Data.G * mass / Math.pow(dist, 3);
            pos1.sub(pos2);
            pos1.mult(ForceScalar);
            acceleration.add(pos1);
        }

        return acceleration;
    }
    /**
     * update the acceleration of planets
     * @param planets array of clestial objects
     */
    public void updateAcceleration(CelestialObject[] planets) {
        for (CelestialObject planet : planets) {
            planet.setAccelaration(calculateAcceleration(planet, planets));
        }
    }



    /**
     * to check if we are using the correct celestial bodies ignoring case
     * @param name name of planets in array of celestial bodies
     * @param name2 name of celestial body
     * @return true if names are equal ignoring case else false
     */
    private boolean checkName(String name, String name2) {
        return name.equalsIgnoreCase(name2);
    }

}