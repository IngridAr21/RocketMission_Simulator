package BackEnd.Tests;
import BackEnd.*;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.ODESolvers.Euler;

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
public class EulerTest {
    Planets allPlanets = new Planets();
    private final double DELTA = 1e-2;

    @Test
    public void stepSizeZero(){
        Planet[] expected = Planets.getPlanets();
        Euler.eulerCall(0, Planets.getPlanets());
        for(int i = 0; i < expected.length ; i++ ){
            assertEquals(expected[i].getPosition().getX(),Planets.getPlanets()[i].getPosition().getX(), DELTA);
            assertEquals(expected[i].getPosition().getY(),Planets.getPlanets()[i].getPosition().getY(), DELTA);
            assertEquals(expected[i].getPosition().getZ(),Planets.getPlanets()[i].getPosition().getZ(), DELTA);
        }
        
    }

    @Test
    public void positiveStepSize(){
        MyVector[] expected = { new MyVector(0.0, 0.0, 0.0 ), 
            new MyVector(7623000.0, 4.49414E7, 2892392.0),
            new MyVector(-2.83224E7, 1.03967708E8, 3016624.0 ), 
            new MyVector( -1.4798182E8, -2.790584E7, 33706.156  ),
            new MyVector(  -1.47984376E8, -2.7608E7, 70158.240),
            new MyVector(  -1.5906372E8, 1.889514E8, 7870547.2 ),
            new MyVector( 6.92983044E8, 2.5904644E8, -1.659981208E7),
            new MyVector(  1.250016092E9, -7.59970336E8, -3.67011556E7),
            new MyVector(1.2500324E9, -7.6096004E8, -3.63081E7 ),
            new MyVector( 4.4500016128E9, -3.9798038E8, -9.45004428E7), 
            new MyVector(  1.959981532E9, 2.190015192E9, -1.719970444E7),
            new MyVector(-1.48E8, -2.7793622E7, 33700.0)
        };
        Euler.eulerCall(3600, Planets.getPlanets());
        for(int i = 0; i < expected.length ; i++ ){
            assertEquals(expected[i].getX(),Planets.getPlanets()[i].getPosition().getX(), DELTA);
            assertEquals(expected[i].getY(),Planets.getPlanets()[i].getPosition().getY(), DELTA);
            assertEquals(expected[i].getZ(),Planets.getPlanets()[i].getPosition().getZ(), DELTA);
        }
        
    }
}