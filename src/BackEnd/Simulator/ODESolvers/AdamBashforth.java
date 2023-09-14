package BackEnd.Simulator.ODESolvers;

import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import BackEnd.Simulator.TimeManagment.MyTime;

public class AdamBashforth implements ODESolver{

    // array storing the four last velocities of each planet
    MyVector[][] lastVelocities = new MyVector[Planets.getPlanets().length][4];;
    int bootstrappStepAmount = 4;

    @Override
    public void evaluate(double stepSize) {
        if(MyTime.getStart() < stepSize * 3){
            bootstrapp(stepSize, Planets.getPlanets().clone());
        } else {
            adamBashfordCall(stepSize, Planets.getPlanets().clone());
        } 
    }

    /**
     * Adam Bashforth method for updating the state of the planets
     * @param stepSize step size
     * @param planetsstate array with state of the planets
     * @return array with state of planets
     */
    public Planet[] adamBashfordCall ( double stepSize, Planet[] planetsstate) {
            Planet[] currentstate = planetsstate.clone();
            for (int j = 1; j < planetsstate.length; j++) {
                MyVector currentVelo = MyVector.add(currentstate[j].getVelocity().copy(), MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(currentstate[j].getCopy(), Planets.getCopy(planetsstate)),stepSize));
                planetsstate[j].setVelocity(currentVelo);
                planetsstate[j].setPosition(adamBashfordAdd(planetsstate[j].getPosition().copy(), stepSize, currentVelo, lastVelocities[j][2], lastVelocities[j][1], lastVelocities[j][0])); 
                lastVelocities[j][0] = lastVelocities[j][1];
                lastVelocities[j][1] = lastVelocities[j][2];
                lastVelocities[j][2] = currentVelo;
            }
        return planetsstate;
    } 
    
    /**
     * Adam Bashforth method for updating the position 
     * @param position vector position 
     * @param stepSize step size
     * @param currentVelocity vector velocity v4
     * @param lastVelocity vector old velocity v3
     * @param preLastVelocity vector old velocity v2
     * @param prePreLastVelocity vector old velocity v4
     * @return new vector position
     */
    public MyVector adamBashfordAdd (MyVector position, double stepSize, MyVector currentVelocity, MyVector lastVelocity, MyVector preLastVelocity, MyVector prePreLastVelocity) {
        MyVector addedVelocities = MyVector.mult(currentVelocity, 55);
        addedVelocities = MyVector.add(addedVelocities, MyVector.mult(lastVelocity,-59));
        addedVelocities = MyVector.add(addedVelocities, MyVector.mult(preLastVelocity,37));
        addedVelocities = MyVector.add(addedVelocities, MyVector.mult(prePreLastVelocity,-9));
        MyVector addedVelocitesTimesH = MyVector.mult(addedVelocities, Math.pow(24, -1)*stepSize);
        MyVector newPosition = MyVector.add(position, addedVelocitesTimesH);
        return newPosition;
    }


     /**
     * bootstrap method to get the velocities
     * @param stepSize step size
     * @param planetsstate array of planets
     * @return array of planets with initialized velocities
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