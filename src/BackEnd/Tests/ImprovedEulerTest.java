package BackEnd.Tests;
import BackEnd.*;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.ODESolvers.ImprovedEuler;

import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Testing strategy
 *
 * Partition the inputs as follows:
 *      h: h = 0, h > 0
 *
 * Full Cartesian Coverage coverage of partitions.
 */

public class ImprovedEulerTest {

    MyVector velocity = new MyVector(0,0,0);
    MyVector currentpos = new MyVector(0, 0, 0);
    CelestialObject[] planets;
    Planets allPlanets = new Planets();
    
    private final double DELTA = 1e-2;

    
    
    
    @Test
    public void stepSizeZero(){
        Planet[] expected = Planets.getPlanets();
        ImprovedEuler.improvedEulerCall(0.0, Planets.getPlanets());
       
        
        for(int i = 0; i < expected.length ; i++ ){
            //double expecteD = expected[i].getPosition().getX();
            //double actualD = constructor.planets[i].getPosition().getX();
            //String expecteD = expected[i].getPosition().toString();
            //String actualD = constructor.planets[i].getPosition().toString();
            assertEquals(expected[i].getPosition().getX(),Planets.getPlanets()[i].getPosition().getX(),DELTA );
            assertEquals(expected[i].getPosition().getY(),Planets.getPlanets()[i].getPosition().getY(),DELTA );
            assertEquals(expected[i].getPosition().getZ(),Planets.getPlanets()[i].getPosition().getZ(),DELTA );
        }
    }


    @Test
    public void positiveStepSize(){
        
        ImprovedEuler.improvedEulerCall(3600, Planets.getPlanets());
        MyVector[] expected = { new MyVector(0.0, 0.0, 0.0 ), 
            new MyVector(7830000.0, 4.49E7, 2870000.0),
            new MyVector(-2.82E7, 1.04E8, 3010000.0), 
            new MyVector(-1.48E8, -2.78E7, 33700.0  ),
            new MyVector( -1.48E8, -2.75E7, 70200.0),
            new MyVector( -1.59E8, 1.89E8, 7870000.0),
            new MyVector(  6.93E8, 2.59E8, -1.66E7),
            new MyVector( 1.25E9, -7.6E8, -3.67E7),
            new MyVector(1.25E9, -7.61E8, -3.63E7),
            new MyVector(4.45E9, -3.98E8, -9.45E7 ), 
            new MyVector( 1.96E9, 2.19E9, -1.72E7 ),
            new MyVector(-1.48E8, -2.7793622E7, 33700.0)
        };
        
        for(int i = 0; i < expected.length ; i++ ){
            assertEquals(expected[i].getX(), Planets.getPlanets()[i].getPosition().getX(), DELTA );
            assertEquals(expected[i].getY(), Planets.getPlanets()[i].getPosition().getY(),DELTA );
            assertEquals(expected[i].getZ(), Planets.getPlanets()[i].getPosition().getZ(),DELTA );
        }
    }
    
    
}