
package BackEnd.Rocket;

import java.util.Random;
import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Simulator.TimeManagment.MyTime;


public class HillClimbing implements Mission {
    private static double MAX_THRUST = 300000;
    private static final double TARGET_DISTANCE = 300000;
    private static final double MIN_DISTANCE = 100000;
    double newDistance;
    MyVector newPos;
    MyVector newVelocity;
    boolean reachedTitan = false;
    boolean reachedEarth = false;
    double currentDistance;
    double previousDistance;
    CelestialObject[] planets;
    MyTime time;
    double fuel = 0;
    

    // New variables for orbiting
    private boolean inOrbit = false;
    private double orbitAltitude;
    private double orbitVelocity;
    private CelestialObject titan;

    public HillClimbing(CelestialObject[] planets) {
        this.planets = planets;
    }

    /**
     * @param stepSize which is the same step size used for the solvers so we can get accurate results
     * @return this method calculates the distance of the rocket from its target and using hill climbing it 
     * desides when its more optimal to use thrust to get closer to where it needs to be, gives the amount of fuel used to reach titan and to
     * get back to earth
     */
    public void hillClimb(double stepSize) {
        Random random = new Random();
        MyVector rocketPos = planets[11].getPosition().copy();
        
        if(!hasReachedTitan()){
            currentDistance = rocketPos.dist(planets[8].getPosition().copy());
            
            // Generate random thrust value
            double thrust = random.nextDouble() * MAX_THRUST;

            if(!isGettingCloser()){
                // Apply changes to the current position and velocity
                newPos = applyChangesForTitan(rocketPos, thrust, stepSize);
                newVelocity = nextVelocityForTitan(thrust, stepSize);
                newDistance = newPos.dist(planets[8].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            else if(currentDistance < 3.5E8 && currentDistance > 310000){
                MAX_THRUST = 3000000;
                // Apply changes to the current position and velocity
                newPos = applyChangesForTitan(rocketPos, thrust, stepSize);
                newVelocity = nextVelocityForTitan(thrust, stepSize);
                newDistance = newPos.dist(planets[8].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            else if(currentDistance < 310000 && currentDistance > 150000){
                MAX_THRUST = 300000;
                newPos = applyChangesForTitan(rocketPos, -30000, stepSize);
                newVelocity = nextVelocityForTitan(-30000, stepSize);
                newDistance = newPos.dist(planets[8].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            
            else{
                // Apply changes to the current position and velocity
                newPos = applyChangesForTitan(rocketPos, 10, stepSize);
                newVelocity = nextVelocityForTitan(10, stepSize);
                newDistance = newPos.dist(planets[8].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            
            if (currentDistance <= TARGET_DISTANCE && currentDistance >= MIN_DISTANCE) {
            System.out.println("Rocket reached Titan successfully!");
            System.out.println("Amount of fuel to reach Titan: " + fuel);
            setHasReachedTitan();
            
            switchToLandingGUI();
            }

            previousDistance = newDistance;
        }

        else if(inOrbit && !hasReachedEarth()){
            // Calculate orbit parameters
            orbitAltitude = 9000;// Desired altitude for orbit around Titan
            titan = planets[8];
            calculateOrbitParameters();
            currentDistance  = rocketPos.dist(planets[3].getPosition().copy());
            // Generate random thrust value
            double thrust = random.nextDouble() * MAX_THRUST;


            // Check if spacecraft is within the desired orbit
            if (currentDistance > orbitAltitude) {
            // Apply changes to the current position and velocity
            newPos = applyChangesForTitan(rocketPos, orbitVelocity, stepSize);
            newVelocity = nextVelocityForTitan(orbitVelocity,stepSize);
            newDistance = newPos.dist(planets[8].getPosition().copy());
            if(!isGettingCloser()){
                // Apply changes to the current position and velocity
                newPos = applyChangesForEarth(rocketPos, thrust);
                newVelocity = nextVelocityForEarth(thrust, stepSize);
                newDistance = newPos.dist(planets[3].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            else{
                // Apply changes to the current position and velocity
                newPos = applyChangesForEarth(rocketPos, 10);
                newVelocity = nextVelocityForEarth(10, stepSize);
                newDistance = newPos.dist(planets[3].getPosition().copy());
                
                //Update rocket position and distance if it gets closer to the target planet
                if (newDistance < currentDistance) {
                    rocketPos = newPos;
                    currentDistance = newDistance;
                    planets[11].setVelocity(newVelocity);  // Update rocket velocity
                }
            }
            
            
            if (currentDistance <= TARGET_DISTANCE && currentDistance >= MIN_DISTANCE) {
            System.out.println("Rocket reached Earth successfully!");
            System.out.println("Amount of fuel to return to Earth: " + fuel);
            setHasReachedEarth();
            }

            previousDistance = newDistance;
        }}
    }
        /**
         * 
         * @return bollean which will be true if the distance to the target is getting closer
         * and false if the distance is getting smaller
         */
    public boolean isGettingCloser(){
        if(currentDistance < previousDistance){
            return true;
        }
        return false;
    }

    public boolean hasReachedTitan(){
        return reachedTitan;
    }

    public void setHasReachedTitan(){
        reachedTitan = true;
    }

    public boolean hasReachedEarth(){
        return reachedEarth;
    }

    public void setHasReachedEarth(){
        reachedEarth = true;
    }

    /**
     * 
     * @param currentState which is the position of rocket
     * @param thrust amount of thrust used to push the rocket towards Titan
     * @return MyVector whisch is the amount the velocity vector will increase
     */
    public MyVector applyChangesForTitan(MyVector currentState, double thrust, double stepSize) {
        MyVector direction = planets[8].getPosition().subt(currentState).norm();
        MyVector thrustVector = direction.multr(thrust);
        return currentState.addR(thrustVector);
    }

    /**
     * 
     * @param thrust amount of thrust used to push the rocket
     * @param stepSize the stepsize we used for the solvers will be the same here aswell
     * @return MyVector wich gives us the new velocity vector of the rocket which is the previous plus the one calculated
     */
    public MyVector nextVelocityForTitan(double thrust, double stepSize) {
        double acceleration = thrust / planets[11].getMass();
        MyVector direction = planets[8].getPosition().subt(planets[11].getPosition()).norm();
        MyVector thrustVector = direction.multr(acceleration * (stepSize));
        fuel += thrustVector.mag();
        return planets[11].getVelocity().addR(thrustVector);
    }

/**
 * 
 * @param currentState which is the position of rocket
 * @param thrust amount of thrust used to push the rocket towards Earth
 * @return MyVector whisch is the amount the velocity vector will increase
 */
    public MyVector applyChangesForEarth(MyVector currentState, double thrust) {
        MyVector direction = planets[3].getPosition().subt(currentState).norm();
        MyVector thrustVector = direction.multr(thrust);
        return currentState.addR(thrustVector);
    }

    /**
     * 
     * @param thrust amount of thrust used to push the rocket
     * @param stepSize the stepsize we used for the solvers will be the same here aswell
     * @return MyVector wich gives us the new velocity vector of the rocket which is the previous plus the one calculated
     */
    public MyVector nextVelocityForEarth(double thrust, double stepSize) {
        double acceleration = thrust / planets[11].getMass();
        MyVector direction = planets[3].getPosition().subt(planets[11].getPosition()).norm();
        MyVector thrustVector = direction.multr(acceleration * stepSize);
        fuel += thrustVector.mag();
        return planets[11].getVelocity().addR(thrustVector);
    }

    private void calculateOrbitParameters() {
        // Assuming circular orbit for simplicity
        double gravitationalConstant = 6.67430e-11;
        double titanMass = titan.getMass();
        double radius = titan.getRadius() + orbitAltitude;

        // Calculate the velocity needed for a circular orbit using the vis-viva equation
        orbitVelocity = Math.sqrt(gravitationalConstant * titanMass / radius);
    }

    public void switchToLandingGUI() {
        // Get the reference to the running instance of the App class
        App appInstance = App.getInstance();

        // Call the switchToLanding() method
        appInstance.switchToLanding();
    }

    
}