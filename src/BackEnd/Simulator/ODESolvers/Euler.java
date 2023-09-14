package BackEnd.Simulator.ODESolvers;

import java.io.FileWriter;
import java.io.IOException;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import BackEnd.Simulator.TimeManagment.MyTime;
import Utility.Data;

public class Euler implements ODESolver {

    public CelestialObject[] planets;
    MyTime time;
    FileWriter out;
    String s;
    public Euler(CelestialObject[] planets, MyTime time, String s) {
        this.planets = planets;
        this.time = time;
        this.s = s;

        try {
            out = new FileWriter("try.txt");

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void evaluate(double stepSize) {
        if (s.equalsIgnoreCase("b"))
            backwordEuler(stepSize);
        else if (s.equalsIgnoreCase("s"))
            semiEuler(stepSize);
        else
            Planets.setPlanets(eulerCall(stepSize, Planets.getPlanets()));
            //forwardEuler(stepSize);
            //evaluateSemiImplicit(stepSize);

    }


    /**
     * Calls the euler method 
     * @param stepSize or height
     * @param planetsstate array of the states of the planet
     * @return  the state of all the planets after euler's method is executed
     */
    public static Planet[] eulerCall(double stepSize, Planet[] planetsstate) {
        double height = stepSize;
        Planet[] planetscopy = Planets.getCopy(planetsstate);
        for (int j = 1; j < planetsstate.length; j++) {
            planetsstate[j] = eulerMethod(planetsstate[j].getCopy(), height, planetscopy);
        }
       
        return planetsstate;
    }

    /**
     * Executes the euler method
     * @param planet current planet
     * @param stepsize or height
     * @param planetsstate array of the states of the planets
     * @return planet with updated position and velocity
     */
    public static Planet eulerMethod (Planet planet, double stepsize, Planet[] planetsstate) {
        MyVector currentPos = planet.getPosition().copy();
        MyVector currentVelo = planet.getVelocity().copy();
        MyVector currentVeloTimesH = MyVector.mult(currentVelo, stepsize);
        MyVector newPos = MyVector.add(currentPos, currentVeloTimesH);
        planet.setPosition(newPos);
        MyVector newVelo = MyVector.add(currentVelo, MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(planet.getCopy(), Planets.getCopy(planetsstate)),stepsize));
        planet.setVelocity(newVelo);
        return planet;
    }

    public void forwardEuler(double stepSize) {

        for (CelestialObject body : planets) {
            // Update the position using forward Euler method
            MyVector p = body.getPosition().copy();
            MyVector v = body.getVelocity().copy();

            p.add(MyVector.mult(v, stepSize));
            body.setPosition(p);
        }
        for (CelestialObject body : planets) {
            MyVector v = body.getVelocity().copy();
            MyVector a = body.getAccelaration().copy();
            v.add(MyVector.mult(a, stepSize));
            body.setVelocity(v);
        }

        calculateAcceleration();
    }

    public void semiEuler(double stepSize) {
        // backward velocity
        for (CelestialObject body : planets) {
            MyVector v = body.getVelocity();
            MyVector a = body.getAccelaration();
            v.add(MyVector.mult(a, stepSize));
        }
        calculateAcceleration();

        // forward position
        for (CelestialObject body : planets) {
            MyVector p = body.getPosition();
            MyVector v = body.getVelocity();
            p.add(MyVector.mult(v, stepSize));
        }
    }

    void backwordEuler(double stepSize) {

        double subSteps = 1800;
        double dt = stepSize / subSteps;
        for (int i = 1; i < stepSize; i += dt, dt += 100) {

            for (CelestialObject body : planets) {
                // Calculate the new position using backward Euler method
                MyVector v = body.getVelocity().copy();
                v.mult(dt);
                MyVector p = body.getPosition().copy();
                p.add(v);
                MyVector a = body.getAccelaration().copy();
                a.mult(dt * dt);
                p.add(a);
                body.setPosition(p);

                // Calculate the new velocity using backward Euler method
                MyVector vNew = body.getVelocity().copy();
                a = body.getAccelaration().copy();
                a.mult(dt);
                vNew.add(a);
                body.setVelocity(vNew);
            }
            calculateAcceleration();
        }
    }

    private void calculateAcceleration() {
        MyVector[] accelerations = new MyVector[planets.length];
        accelerations[0] = new MyVector(0, 0, 0);
        for (int i = 0; i < planets.length; i++) {

            for (int j = 0; j < planets.length; j++) {
                if (checkName(planets[i].getName(), planets[j].getName())) {
                    continue;
                }
                MyVector pos1 = planets[j].getPosition().copy();
                MyVector pos2 = planets[i].getPosition().copy();

                double dist = pos1.dist(pos2);
                double mass = planets[j].getMass();
                double ForceScalar = Data.G * mass / Math.pow(dist, 3);
                pos1.sub(pos2);
                pos1.mult(ForceScalar);
                if (accelerations[i] == null)
                    accelerations[i] = pos1.copy();
                else
                    accelerations[i].add(pos1);

            }
        }

        for (int i = 0; i < accelerations.length; i++) {
            planets[i].setAccelaration(accelerations[i]);
        }

    }

    private boolean checkName(String name, String name2) {
        return name.equalsIgnoreCase(name2);
    }

}