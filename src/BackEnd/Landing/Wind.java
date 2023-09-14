package BackEnd.Landing;

import java.util.Random;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;

public class Wind {

    //Add wind instance in "Spacecraft"
    private final double coefficientAir = 0.6;

    private MyVector dragForce;
    private MyVector windSpeed;
    private MyVector accByWind;
    private double titanAtmosphereDensity = 0; //kg/m^3

    double rocketArea = 4*Math.PI*Math.pow(10,2); 

    double windSpeedX;
    double windSpeedY;
    Random random =  new Random();

    public Wind(){
        windSpeed = new MyVector(0,0,0);
        dragForce = new MyVector(0,0,0);
        titanAtmosphereDensity = 0;
    }

    public void calculateWindSpeed(double altitude, CelestialObject rocket){
        //if(altitude <= 120){
            windSpeedX = 0.9975*altitude + 0.3;
            windSpeedY = -Math.pow(windSpeedX,2) + 1;
            windSpeedY = windSpeedY * (random.nextInt(3)-1); //Stochastic procedure
            
        //}
        windSpeed.set(windSpeedX, windSpeedY, 0);
        windSpeed.sub(rocket.getVelocity());
        System.out.println(windSpeed);
        
    
    }


    /*
     * altitude = 0  atmDensity = 5.3
     * altitude = 600  atmDensity = 10^-7
     */
    public void changeAtmDensity(double altitude){
        double slope = ((5.3 - Math.pow(10,-6)) /600);
        titanAtmosphereDensity = slope*altitude + 5.3;
    }


    public MyVector dragForce(CelestialObject rocket){
        windSpeed.mult(windSpeed);
        double product = 0.5*coefficientAir * titanAtmosphereDensity *rocketArea;
        windSpeed.mult(product);
        dragForce = windSpeed;
        return dragForce;
    }

    public MyVector accByWind(CelestialObject rocket, double altitude){
        changeAtmDensity(altitude);
        calculateWindSpeed(altitude,rocket);
        dragForce(rocket);
        dragForce.div(rocket.getMass());
        accByWind = dragForce;

        return accByWind;
    }

    public MyVector getWindSpeed(){
        return windSpeed;
    }

    public double getAirDensity(){
        return titanAtmosphereDensity;
    }

    public MyVector getAccByWind(){
        return accByWind;
    }

    public MyVector getDragForce(){
        return dragForce;
    }
}