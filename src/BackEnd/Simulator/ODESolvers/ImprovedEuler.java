package BackEnd.Simulator.ODESolvers;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;


public class ImprovedEuler implements ODESolver {

    public CelestialObject[] planets;
    public Planets allPlanets;

    public ImprovedEuler(Planets allPlanets) {
        this.allPlanets = allPlanets;
        this.planets = allPlanets.getPlanets();
    }

    @Override
    public void evaluate(double stepSize) {
        Planets.setPlanets(improvedEulerCall(stepSize, Planets.getPlanets()));
    }

    /**
     * Calls improved Euler method
     * @param stepSize or height
     * @param planetsstate array of planet's state
     * @return  returns planet's state updated (after the execution of improved euler method)
     */
    public static Planet[] improvedEulerCall (double stepSize, Planet[] planetsstate) {
        double height = stepSize;
        Planet[] planetscopy = Planets.getCopy(planetsstate);
        planetsstate = improvedEulerMethod(height,planetscopy);
        
        return planetsstate;
    }

    /**
     * Executes the improved Euler method
     * @param stepSize or height
     * @param planetsstate array of the state of the planets
     * @return array of planet's state updated 
     */
    public static Planet[] improvedEulerMethod(double  stepSize, Planet[] planetsstate) {
        Planet[] nextState = Euler.eulerCall(stepSize, planetsstate.clone());
        for (int i = 1; i < planetsstate.length; i++) {
            MyVector currentPos = planetsstate [i].getPosition().copy();
            MyVector currentVelo = planetsstate [i].getVelocity().copy();
            MyVector approxNextVelo = nextState[i].getVelocity().copy();
            MyVector fxofx1 = MyVector.div1( MyVector.add(currentVelo, approxNextVelo),2);
            if (!planetsstate[i].isFirstPosition()) {
                MyVector newVelo = MyVector.add(currentVelo, MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(planetsstate[i].getCopy(), Planets.getCopy(planetsstate)),stepSize));
                planetsstate[i].setVelocity(newVelo);
            }

            MyVector nextPos = MyVector.add(currentPos, MyVector.mult(fxofx1, stepSize));
            planetsstate[i].setPosition(nextPos);
            planetsstate[i].changeInfoFirstPosition();
        }
        return planetsstate;
    }
    

}