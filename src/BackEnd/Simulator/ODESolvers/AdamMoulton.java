package BackEnd.Simulator.ODESolvers;

import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import BackEnd.Simulator.TimeManagment.MyTime;

public class AdamMoulton implements ODESolver {

    // array storing the three last velocities of each planet 
    MyVector[][] lastVelocities = new MyVector[Planets.getPlanets().length][3];
    
    @Override
    public void evaluate(double stepSize) {
        if(MyTime.getStart() < stepSize*3){
            bootstrapp(stepSize, Planets.getPlanets().clone());
        } else {
            adamMoultonCall(stepSize, Planets.getPlanets().clone());
        } 
    }


    /**
     * Adam Moulton method to update the state of the planets.
     * @param stepSize step size
     * @param planetsstate array with state of planets
     * @return new state of planets
     */
    public Planet[] adamMoultonCall (double stepSize, Planet[] planetsstate) {
            Planet[] currentstate = planetsstate.clone();
            Planet[] nextstate = Euler.eulerCall( stepSize, planetsstate.clone()).clone();
            for (int j = 1; j < planetsstate.length; j++) {
                MyVector nextVelo = nextstate[j].getVelocity().copy();
                MyVector currentVelo = MyVector.add(currentstate[j].getVelocity().copy(), MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(currentstate[j].getCopy(), Planets.getCopy(currentstate)),stepSize));
                planetsstate[j].setVelocity(currentVelo);
                planetsstate[j].setPosition(adamMoultonAdd(currentstate[j].getPosition().copy(), stepSize , nextVelo, currentVelo, lastVelocities[j][1], lastVelocities[j][0])); 
                lastVelocities[j][0] = lastVelocities[j][1];
                lastVelocities[j][1] = currentVelo;
            
        }   
        return planetsstate;
    }

    /**
     * calculates the new position using Adam Moultons 
     * @param position vector position
     * @param stepSize step size
     * @param nextVelocity vector next velocity v3
     * @param currentVelocity vector current velocity v2
     * @param lastVelocity vector old velocity V1
     * @param preLastVelocity vector older velocity v0
     * @return new vector position
     */
    public MyVector adamMoultonAdd (MyVector position, double height, MyVector nextVelocity, MyVector currentVelocity, MyVector lastVelocity, MyVector preLastVelocity) {
        MyVector addedVelocities = MyVector.mult(nextVelocity, 9);
        addedVelocities = MyVector.add(addedVelocities, MyVector.mult(currentVelocity,19));
        addedVelocities = MyVector.add(addedVelocities, MyVector.mult(lastVelocity,-5));
        addedVelocities = MyVector.add(addedVelocities, preLastVelocity);
        MyVector addedVelocitesTimesH = MyVector.mult(addedVelocities, Math.pow(24, -1)*height);
        MyVector newPosition = MyVector.add(position, addedVelocitesTimesH);
        return newPosition;
    }
    
    /**
     * bootstrap method to get the velocities
     * @param stepSize step size
     * @param planetsstate array with state of planets
     * @return array with the states of the planets
     */
    public Planet[] bootstrapp (double stepSize, Planet[] planetsstate) {
        Planet[] nextstate = Euler.eulerCall(stepSize, planetsstate.clone());
        for (int j = 1; j < planetsstate.length; j++) {
        if (MyTime.getStart() == 0){
            lastVelocities[j][0] = planetsstate[j].getVelocity().copy();
        }
        lastVelocities[(int)(MyTime.getStart()/stepSize)][j] = nextstate[j].getVelocity().copy();
            
        }
        return planetsstate;
    }
    
}