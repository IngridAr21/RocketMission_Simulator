package BackEnd.Landing;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planet;
import java.lang.Math;
import java.util.ArrayList;

import BackEnd.MyVector;

public class OpenLoopStrategy implements iLandingStrategy
{
    //IMPORTANT NOTICES:
    //as per the project manual, tha landing happens in 2d: it is essentially a separate model - once the rocket
    //enters Titan's orbit, we switch to a two dimensional model, only considering Titan and the rocket. 
    //As the switch occurs, another important thing to notices is that the coordinate system will change: 
    // the new origin becomes the target.

    //BEWARE: This class uses METERS as the unit of distance, so the parameters passed into it need to be converted accordingly
    
    //first, lets define the constants
    private final double G_TITAN = 1.352;
    private final double U_MAX = 10.0*G_TITAN;
    private final double V_MAX = 1.0;
    private final double Y_TARGET = 0.0;
    private final double DELTA_X = 0.1;
    private final double DELTA_THETA = 0.02; //IMPORTANT: this is for theta mod 2pi, so keep this in mind later on
    private final double DELTA_X_VELOCITY = 0.1;
    private final double DELTA_Y_VELOCITY = 0.1;
    private final double DELTA_THETA_VELOCITY = 0.01;
    

    //now, lets define the fields
    private double xCoordinate;

    private double yCoordinate;

    ArrayList<Double> xValues = new ArrayList<Double>();

    public double getxValues(int i) {
        return xValues.get(i);
    }

    public int xSize(){
        return xValues.size();
    }

    ArrayList<Double> yValues = new ArrayList<Double>();

    public double getyValues(int i) {
        return yValues.get(i);
    }

    private double theta = -1*Math.PI/12 ; // angle of rotation

    private double xVelocity;
    private double yVelocity;
    private double thetaVelocity = 0.0; // angular velocity

    private double xAcceleration;
    private double yAcceleration;
    private double thetaAcceleration;

    public CelestialObject rocket;

    private double u = 0.0; // acceleration of thruster
    private double v = 0.0; // total torque of thrusters
    private double stepSize; 
    public boolean hasLanded = false;

    /**
     * open loop strategy constructor
     * @param position position of the spacecraft
     * @param velocity velocity of the spacecraft
     * @param rocket spacraft object
     * @param stepSize step size
     */
    public OpenLoopStrategy(MyVector position, MyVector velocity, CelestialObject rocket, double stepSize)  
    {
        this.xCoordinate = position.getX();
        this.yCoordinate = position.getY();
        this.xVelocity = velocity.getX();
        this.yVelocity = velocity.getY();
        theta = -1*Math.signum(xCoordinate)*Math.PI/(6*(xCoordinate/100));
        this.rocket = rocket;
        rocket.setPosition(new MyVector(xCoordinate,yCoordinate,0));
        rocket.setVelocity(new MyVector(xVelocity,yVelocity,0));
        rocket.setAccelaration(new MyVector(0,G_TITAN,0));
        this.stepSize = stepSize;
    }

    /**
     * resets the velocity
     * @param xVelocity x velcocity
     */
    public void resetVelocity(double xVelocity)
    {
        u = (-1*xVelocity)/(stepSize*Math.sin(theta));
    }
   

    // checks if the spacecraft has landed
    public void checkIfLanded()
    {
        if(Math.abs(xCoordinate)<DELTA_X && Math.abs(yCoordinate-Y_TARGET)<1E-12 &&
        Math.abs(xCoordinate) <= DELTA_X && Math.abs(theta%(2*Math.PI)) <= DELTA_THETA &&
        Math.abs(xVelocity) <= DELTA_X_VELOCITY && Math.abs(yVelocity) <= DELTA_Y_VELOCITY && 
        Math.abs(thetaVelocity)==DELTA_THETA_VELOCITY)
        {
            System.out.println("Landed succesfully !");
            hasLanded = true;
        }
    }

    /**
     * update the position, velocity and acceeration of the rocket 
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     * @param xVelocity x velocity
     * @param yVelocity y velocity
     * @param xAcceleration x acceleration
     * @param yAcceleration y acceleration
     */
    public void updateRocket(double xCoordinate, double yCoordinate, double xVelocity, double yVelocity, double xAcceleration, double yAcceleration)
    {
        rocket.setPosition(new MyVector(xCoordinate,yCoordinate,0));
        rocket.setVelocity(new MyVector(xVelocity,yVelocity,0));
        rocket.setAccelaration(new MyVector(xAcceleration,yAcceleration,0));
    }

    // update position using euler
    public void updatePosition()
    {
        xCoordinate += xVelocity * stepSize;
        yCoordinate += yVelocity * stepSize;
        theta += thetaVelocity * stepSize;
    }

    // update velocity
    public void updateVelocity()
    {
        xVelocity += stepSize * xAcceleration;
        yVelocity += stepSize * yAcceleration;
        thetaVelocity += stepSize * thetaAcceleration;
    }

    // differential equation for the landing module
    public void updateAcceleration()
    {
        xAcceleration = u * Math.sin(theta);
        yAcceleration = u * Math.cos(theta) - G_TITAN;
        thetaAcceleration = v;
    }

    
    /**
     * distance to target
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     * @param xTarget x target 
     * @param yTarget y target
     * @return the distance from the spacecraft to the target
     */
    public double calculateDistance(double xCoordinate, double yCoordinate, double xTarget, double yTarget)
    {
        double distance = Math.sqrt(Math.pow(xCoordinate-xTarget, 2) + Math.pow(yCoordinate-yTarget,2));
        return distance;
    }

    /**
     * calculate angle to target
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     * @param xTarget x target
     * @param yTarget y target
     * @return the angle there is to target
     */
    public double calculateAngleToTarget(double xCoordinate, double yCoordinate, double xTarget, double yTarget)
    {
        double angle = Math.atan2(yCoordinate-yTarget,xCoordinate-xTarget);
        return angle;
    }
    
    /**
     * calculate thurst y 
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     */
    public void calculateThrustY(double xCoordinate, double yCoordinate)
    {
        double thrust = 0;
        if (yCoordinate<=0.06*stepSize)
        {
            thrust = (G_TITAN*stepSize-yVelocity-yCoordinate)/(stepSize*Math.cos(theta));
        }
        else if (Math.abs(yVelocity*stepSize)>yCoordinate+0.05*stepSize)
        {
            thrust = (0.05*stepSize+G_TITAN*stepSize*stepSize-yVelocity*stepSize-yCoordinate)/(stepSize*stepSize*Math.cos(theta));
        }
        else if (Math.abs(yVelocity) > 2)
        {
            //originally was just G_TITAN
            thrust = (G_TITAN*stepSize-2.1-yVelocity)/(stepSize*Math.cos(theta));
        }
        else
        {
            thrust = 0;
        }
        u = Math.min(thrust, U_MAX);
    }

    /**
     * calculate thrust and torque
     * @param xCoordinate x coordinante
     * @param yCoordinate y coordinate
     */
    public void calculateThrustAndTorque(double xCoordinate, double yCoordinate)
    {
        double thrust = 0;
        double torque = 0;
        if (xCoordinate<=1E-14*stepSize)
        {
            thrust = (-xVelocity-xCoordinate)/(stepSize*Math.sin(theta));
            //torque = (-thetaVelocity)/stepSize;
            torque = (-thetaVelocity*stepSize-theta)/(stepSize*stepSize);
        }
        else if (Math.abs(xVelocity*stepSize)>xCoordinate+1E-15*stepSize)
        {
            thrust = (1E-15*stepSize-xVelocity*stepSize-xCoordinate)/(stepSize*stepSize*Math.sin(theta));
        }
        else
        {
            thrust = G_TITAN/Math.cos(theta);
        }
        u = Math.min(thrust, U_MAX);
        v = Math.min(torque,V_MAX);
    }
    
    /**
     * calculate torque method
     * @param xCoordinate x coordinate
     * @param yCoordinate y coordinate
     */
    public void calculateTorque(double xCoordinate,double yCoordinate)
    {
        double torque = 0.0;
        //insert code here
        if (1E-15>Math.abs(xCoordinate))
        {
            if (1E-15<Math.abs(theta))
            {
                torque = (-thetaVelocity*stepSize-theta)/(stepSize*stepSize);
            }
            else if (1E-15<Math.abs(thetaVelocity))
            {
                torque= (-1*thetaVelocity)/(stepSize);
            }
            else
            {
                torque = 0;
            }
        }
        else if (yCoordinate<=0.06*stepSize)
        {
            torque = 0.01/stepSize;
        }
        else
        {
            torque = 0;
        }

        v = Math.min(torque, V_MAX);

    }
    
    @Override
    public void land(Planet rocket)
    {
        checkIfLanded();
        resetVelocity(xVelocity);
        updateAcceleration();
        updateVelocity();
        updatePosition();
        while(!hasLanded)
        {
            if (xCoordinate>1e-15)
            {
                calculateThrustAndTorque(xCoordinate, yCoordinate);
            }
            else
            {
                calculateThrustY(xCoordinate,yCoordinate);
                calculateTorque(xCoordinate, yCoordinate);
            }
            updateAcceleration();
            updateVelocity();
            updatePosition();  
            updateRocket(xCoordinate,yCoordinate,xVelocity,yVelocity,xAcceleration,yAcceleration);
            checkIfLanded();
            
            xValues.add(xCoordinate);
            yValues.add(yCoordinate);
        }
        System.out.println("Final position:" + xCoordinate + ", " + yCoordinate);
        System.out.println("Final velocity:" + xVelocity + ", " + yVelocity );
        System.out.println("Final angle:" + theta + "Final angular velocity:" + thetaVelocity);
    }
}
