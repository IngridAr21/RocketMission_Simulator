package BackEnd.Simulator.ODESolvers;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import Utility.Data;

public class Adam implements ODESolver {

    double dydx;

    CelestialObject[] planets;
    MyVector[] positions;
    MyVector[] velocities;

    public Adam(CelestialObject[] planets) {
        this.planets = planets;
        positions = bootstrappingEuler(1)[0];
        velocities = bootstrappingEuler(1)[1];
    }

    // the function derivative
    private MyVector dydx(CelestialObject celestialObject, String s) {
        if (s.equalsIgnoreCase("position"))
            return celestialObject.getVelocity();
        if (s.equalsIgnoreCase("velocity"))
            return celestialObject.getAccelaration();
        return calculateAcceleration(celestialObject);
    }

     private void adamBashforthsVectorSolver(double stepSize){
        //velocities
        for (int i = 0; i < planets.length; i++){ 
            MyVector newPosition = positions[i].copy(); //y1
            MyVector derivative1 =  dydx(planets[i],"position");
            derivative1.mult(3);
            MyVector derivative0 = dydx(planets[i], "position");
            derivative1.sub(derivative0);
            derivative1.mult(stepSize);
            newPosition.add(derivative1);
            positions[i].add(newPosition); // y2 = positions
        }
        //velocity
        for (int i = 0; i < planets.length; i++){
            MyVector newVelocity = velocities[i].copy(); //y1
            MyVector derivative1 =  dydx(planets[i],"velocity");
            derivative1.mult(3);
            MyVector derivative0 = dydx(planets[i], "velocity");
            derivative1.sub(derivative0);
            derivative1.mult(stepSize);
            newVelocity.add(derivative1);
            velocities[i].add(newVelocity); // y2 = velocities
        }
    
    }

    
    public MyVector[][] bootstrappingEuler(double stepSize) {
        MyVector[] positions = new MyVector[planets.length];
        MyVector[] velocities = new MyVector[planets.length];
        int j = 0;
        int r = 0;
        for (CelestialObject body : planets) {
            // Update the position using forward Euler method
            MyVector p = body.getPosition().copy();
            MyVector v = body.getVelocity().copy();

            p.add(MyVector.mult(v, stepSize));
            body.setPosition(p);
            positions[j] = p.copy();
            j++;
        }
        for (CelestialObject body : planets) {
            MyVector v = body.getVelocity().copy();
            MyVector a = body.getAccelaration().copy();
            v.add(MyVector.mult(a, stepSize));
            body.setVelocity(v);
            velocities[r] = v.copy();
            r++;
        }  
        MyVector[][] x = new MyVector[][]{positions, velocities};
        return x;
    }

    private void calculateAcceleration() {
        MyVector[] accelerations = new MyVector[planets.length];
        accelerations[0] = new MyVector(0, 0, 0);
        for (int i = 0; i < planets.length; i++) {

            for (int j = 0; j < planets.length; j++) {
                if (planets[j].equals(planets[i])) {
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

    private MyVector calculateAcceleration(CelestialObject body) {
        MyVector acceleration = new MyVector(0, 0, 0);

        for (int j = 0; j < planets.length; j++) {
            if (planets[j].equals(body)) {
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

    @Override
    public void evaluate(double stepSize) {
        adamBashforthsVectorSolver(stepSize);
    }

}