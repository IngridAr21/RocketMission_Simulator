package BackEnd.Simulator.Physics;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Rocket.HillClimbing;
import BackEnd.Rocket.Mission;
import BackEnd.Simulator.ODESolvers.AdamBashforth;
import BackEnd.Simulator.ODESolvers.AdamMoulton;
import BackEnd.Simulator.ODESolvers.Euler;
import BackEnd.Simulator.ODESolvers.ImprovedEuler;
import BackEnd.Simulator.ODESolvers.ODESolver;
import BackEnd.Simulator.ODESolvers.RK4;
import BackEnd.Simulator.TimeManagment.MyTime;
import Utility.Data;

public class PhysicsEnginGeneral {

    CelestialObject[] planets;
    ODESolver solver;
    Mission climb;
    MyTime time;
    Planets allPlanets;
    

    public PhysicsEnginGeneral(Planets allPlanets,String solverName, MyTime time) {
        this.allPlanets = allPlanets;
        this.planets = allPlanets.getPlanets();
        this.time = time;
        
        climb = new HillClimbing(planets);
        

        if (solverName.equalsIgnoreCase("Euler") || solverName.equalsIgnoreCase("E")) {
            solver = new Euler(planets, time, "e");
        }
        if (solverName.equalsIgnoreCase("SEMI Euler") || solverName.equalsIgnoreCase("S-E")) {
            solver = new Euler(planets, time, "s");
        }
        if (solverName.equalsIgnoreCase("BACKWARD EULER") || solverName.equalsIgnoreCase("B")) {
            solver = new Euler(planets, time, "B");
        }
        if (solverName.equalsIgnoreCase("IMPROVEDEuler") || solverName.equalsIgnoreCase("IE")) {
            solver = new ImprovedEuler(allPlanets);
        }

        if (solverName.equalsIgnoreCase("RUNGE_KUtta4") || solverName.equalsIgnoreCase("RK4")) {
            solver = new RK4(planets);
        }


        if (solverName.equalsIgnoreCase("AdamBashforth")) {
            solver = new AdamBashforth();
        }

        if (solverName.equalsIgnoreCase("AdamMoulton")) {
            solver = new AdamMoulton();
        }
        

    }

    public void movePlanets() {
        solver.evaluate(time.getTimeStep());
    }

    public void trajectory(){
        climb.hillClimb(time.getTimeStep());
    }

    public MyTime getTime() {
        return time;
    }

    public CelestialObject[] getCelectialObjects() {
        return planets;
    }

    public Planets getAllPlanets() {
        return allPlanets;
    }

    public static MyVector accelerationThroughForce(Planet planetX, Planet[] planets) {
        MyVector zero = new MyVector(0, 0, 0);
        planetX.setAccelaration(zero);
        for (Planet planet : planets) {
            if (planet.getName().equals(planetX.getName())) {
                continue;
            }
            double dist = MyVector.dist(planetX.getPosition(), planet.getPosition());
            double mass = planet.getMass();
            double ForceScalar = Data.G * mass / Math.pow(dist, 3);
            MyVector connectVector = MyVector.sub(planet.getPosition(), planetX.getPosition());
            MyVector forceVector = new MyVector(MyVector.mult(connectVector, ForceScalar));
            planetX.setAccelaration(MyVector.add(planetX.getAccelaration(), forceVector));
        }
        return planetX.getAccelaration();
    }
}
