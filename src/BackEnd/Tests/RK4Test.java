package BackEnd.Tests;
import BackEnd.*;
import BackEnd.Celectial.Creater.CelestialObject;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.ODESolvers.RK4;

import org.junit.Test;
import static org.junit.Assert.*;

/*
 * Testing strategy
 *
 * Partition the inputs as follows:
 *      h: h = 0, h>0
 *      y: yold > 0 , yold = 0, yold < 0,
 *      derivative: derivative < 0,derivative = 0,derivative > 0
 *
 * Full Cartesian Coverage coverage of partitions.
 */

public class RK4Test {

    MyVector zeroVector = new MyVector(0,0,0);
    MyVector positiveVector = new MyVector(1,1,1);
    MyVector negativeVector = new MyVector(-1,-1,-1); 
    MyVector doubleVector = new MyVector(2,2,2);
    MyVector negativeDoubleVector = new MyVector(-2,-2,-2);
    private final double DELTA = 1e-2;

    @Test 
    public void testPositiveYOldZeroStepsizePositiveDerivative(){
        assertEquals(positiveVector.getX(),RK4.eulerMethod(positiveVector, 0, positiveVector).getX(),DELTA);
        assertEquals(positiveVector.getY(),RK4.eulerMethod(positiveVector, 0, positiveVector).getY(),DELTA);
        assertEquals(positiveVector.getZ(),RK4.eulerMethod(positiveVector, 0, positiveVector).getZ(),DELTA);
    }

    @Test public void testPositiveYOldZeroStepsizeZeroDerivative()
    {
        assertEquals(positiveVector.getX(),RK4.eulerMethod(positiveVector, 0, zeroVector).getX(),DELTA);
        assertEquals(positiveVector.getY(),RK4.eulerMethod(positiveVector, 0, zeroVector).getY(),DELTA);
        assertEquals(positiveVector.getZ(),RK4.eulerMethod(positiveVector, 0, zeroVector).getZ(),DELTA);
    }

    @Test public void testPositiveYOldZeroStepsizeNegativeDerivative()
    {
        assertEquals(positiveVector.getX(),RK4.eulerMethod(positiveVector, 0, negativeVector).getX(),DELTA);
        assertEquals(positiveVector.getY(),RK4.eulerMethod(positiveVector, 0, negativeVector).getY(),DELTA);
        assertEquals(positiveVector.getZ(),RK4.eulerMethod(positiveVector, 0, negativeVector).getZ(),DELTA);
    }

    @Test public void testZeroYOldZeroStepsizePositiveDerivative()
    {
        assertEquals(zeroVector.getX(),RK4.eulerMethod(zeroVector, 0,positiveVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(zeroVector, 0,positiveVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(zeroVector, 0,positiveVector).getZ(),DELTA);
    } 

    @Test public void testZeroYOldZeroStepsizeZeroDerivative()
    {
        assertEquals(zeroVector.getX(),RK4.eulerMethod(zeroVector, 0,zeroVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(zeroVector, 0,zeroVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(zeroVector, 0,zeroVector).getZ(),DELTA);
    } 
    
    @Test public void testZeroYOldZeroStepsizeNegativeDerivative()
    {
        assertEquals(zeroVector.getX(),RK4.eulerMethod(zeroVector, 0,negativeVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(zeroVector, 0,negativeVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(zeroVector, 0,negativeVector).getZ(),DELTA);
    }

    @Test public void testNegativeYOldZeroStepsizePositiveDerivative()
    {
        assertEquals(negativeVector.getX(),RK4.eulerMethod(negativeVector, 0,positiveVector).getX(),DELTA);
        assertEquals(negativeVector.getY(),RK4.eulerMethod(negativeVector, 0,positiveVector).getY(),DELTA);
        assertEquals(negativeVector.getZ(),RK4.eulerMethod(negativeVector, 0,positiveVector).getZ(),DELTA);
    }

    @Test public void testNegativeYOldZeroStepsizeZeroDerivative()
    {
        assertEquals(negativeVector.getX(),RK4.eulerMethod(negativeVector, 0,zeroVector).getX(),DELTA);
        assertEquals(negativeVector.getY(),RK4.eulerMethod(negativeVector, 0,zeroVector).getY(),DELTA);
        assertEquals(negativeVector.getZ(),RK4.eulerMethod(negativeVector, 0,zeroVector).getZ(),DELTA);
    } 

    @Test public void testNegativeYOldZeroStepsizeDeriNegativeDerivative()
    {
        assertEquals(negativeVector.getX(),RK4.eulerMethod(negativeVector, 0,negativeVector).getX(),DELTA);
        assertEquals(negativeVector.getY(),RK4.eulerMethod(negativeVector, 0,negativeVector).getY(),DELTA);
        assertEquals(negativeVector.getZ(),RK4.eulerMethod(negativeVector, 0,negativeVector).getZ(),DELTA);
    }

    @Test public void testPositiveYOldPositiveStepsizePositveDerivative()
    {
        assertEquals(doubleVector.getX(),RK4.eulerMethod(positiveVector, 1,positiveVector).getX(),DELTA);
        assertEquals(doubleVector.getY(),RK4.eulerMethod(positiveVector, 1,positiveVector).getY(),DELTA);
        assertEquals(doubleVector.getZ(),RK4.eulerMethod(positiveVector, 1,positiveVector).getZ(),DELTA);
    }

    @Test public void testPositiveYOldPositiveStepsizeZeroDerivative()
    {
        assertEquals(positiveVector.getX(),RK4.eulerMethod(positiveVector, 1,zeroVector).getX(),DELTA);
        assertEquals(positiveVector.getY(),RK4.eulerMethod(positiveVector, 1,zeroVector).getY(),DELTA);
        assertEquals(positiveVector.getZ(),RK4.eulerMethod(positiveVector, 1,zeroVector).getZ(),DELTA);
    }

    @Test public void testPositiveYOldPositiveStepsizeNegativeDerivative()
    {
        
        
        assertEquals(zeroVector.getX(),RK4.eulerMethod(positiveVector, 1,negativeVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(positiveVector, 1,negativeVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(positiveVector, 1,negativeVector).getZ(),DELTA);
    }

    @Test public void testZeroYOldPositiveStepsizePositveDerivative()
    {
        
        
        assertEquals(positiveVector.getX(),RK4.eulerMethod(zeroVector, 1,positiveVector).getX(),DELTA);
        assertEquals(positiveVector.getY(),RK4.eulerMethod(zeroVector, 1,positiveVector).getY(),DELTA);
        assertEquals(positiveVector.getZ(),RK4.eulerMethod(zeroVector, 1,positiveVector).getZ(),DELTA);
    }

    @Test public void testZeroYOldPositiveStepsizeZeroDerivative()
    {
       
        
        assertEquals(zeroVector.getX(),RK4.eulerMethod(zeroVector, 1, zeroVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(zeroVector, 1,zeroVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(zeroVector, 1,zeroVector).getZ(),DELTA);
    }

    @Test public void testZeroYOldPositiveStepsizeNegtiveDerivative()
    {
        assertEquals(negativeVector.getX(),RK4.eulerMethod(zeroVector, 1, negativeVector).getX(),DELTA);
        assertEquals(negativeVector.getY(),RK4.eulerMethod(zeroVector, 1, negativeVector).getY(),DELTA);
        assertEquals(negativeVector.getZ(),RK4.eulerMethod(zeroVector, 1, negativeVector).getZ(),DELTA);
    }

    @Test public void testNegativeYOldPositiveStepsizePositveDerivative()
    {
        assertEquals(zeroVector.getX(),RK4.eulerMethod(negativeVector, 1, positiveVector).getX(),DELTA);
        assertEquals(zeroVector.getY(),RK4.eulerMethod(negativeVector, 1, positiveVector).getY(),DELTA);
        assertEquals(zeroVector.getZ(),RK4.eulerMethod(negativeVector, 1, positiveVector).getZ(),DELTA);
    }

    @Test public void testNegativeYOldPositiveStepsizeZeroDerivative()
    {
        assertEquals(negativeVector.getX(),RK4.eulerMethod(negativeVector, 1, zeroVector).getX(),DELTA);
        assertEquals(negativeVector.getY(),RK4.eulerMethod(negativeVector, 1, zeroVector).getY(),DELTA);
        assertEquals(negativeVector.getZ(),RK4.eulerMethod(negativeVector, 1, zeroVector).getZ(),DELTA);
    }
    
    @Test public void testNegativeYOldPositiveStepsizeNegtiveDerivative()
    {
        assertEquals(negativeDoubleVector.getX(),RK4.eulerMethod(negativeVector, 1, negativeVector).getX(),DELTA);
        assertEquals(negativeDoubleVector.getY(),RK4.eulerMethod(negativeVector, 1, negativeVector).getY(),DELTA);
        assertEquals(negativeDoubleVector.getZ(),RK4.eulerMethod(negativeVector, 1, negativeVector).getZ(),DELTA);
    }
    Planets initializer = new Planets();
    CelestialObject[] planets = Planets.getPlanets();
    
    @Test
    public void derivativeAtPointTest(){
        for(int i = 0; i < planets.length; i++){
            CelestialObject body = planets[i];

            MyVector acceleration = new RK4(planets).derivativeAtPoint(body, planets, 0.0);
            MyVector[] expectedAcceleration = {new MyVector(2.0933768797507176E-10, 1.0694678773277753E-10, -4.1788557179370955E-12 ),
            new MyVector(-1.091892347412938E-5, -6.261402286772607E-5, -4.002291882012752E-6),
            new MyVector( 2.9903076631182678E-6, -1.1027194038243189E-5, -3.1916003332636494E-7),
            new MyVector( 5.7565168767611655E-6, 1.1346315590438034E-6, 5.173198560194192E-9),
            new MyVector( 5.7628381521680425E-6, -3.259950249346461E-6, -5.296468294379805E-7 ),
            new MyVector( 1.3997151405923644E-6, -1.6635877091180557E-6, -6.927782975302572E-8),
            new MyVector(-2.271248718710821E-7, -8.491419122531874E-8, 5.440361459372946E-9),
            new MyVector(-5.3024058038499306E-8, 2.5082912156036262E-8, 4.441883775321512E-9),
            new MyVector(-5.296864412960293E-8, 3.037590142468176E-5, -1.2135900675282364E-5),
            new MyVector(-6.6355983156476086E-9, 5.937695068904838E-10, 1.4090049241485336E-10),
            new MyVector(-1.0267751220596587E-8, -1.1481656556474816E-8, 8.99534454468702E-11),
            new MyVector( 5.756651890623656E-6, -0.009794011813061302, 5.5985074495018135E-9)
            };
            String expecteD = expectedAcceleration.toString();
            String actualD = acceleration.toString();
            //assertEquals(expectedAcceleration.getX(), acceleration.getX(),DELTA);
            //assertEquals(expectedAcceleration.getY(), acceleration.getY(),DELTA);
            //assertEquals(expectedAcceleration.getZ(), acceleration.getZ(),DELTA);

            assertEquals(expectedAcceleration[i].toString(), acceleration.toString());
        }
    }
    

    @Test
    public void moveByhTest() throws CloneNotSupportedException{
        CelestialObject[] updatedPlanets = new RK4(planets).moveByH(0.1, planets);

        MyVector expectedPosition = new MyVector(0.0, 0.0, 0.0);
        assertEquals(expectedPosition.getX(), updatedPlanets[0].getPosition().getX(), DELTA);
        assertEquals(expectedPosition.getY(), updatedPlanets[0].getPosition().getY(), DELTA);
        assertEquals(expectedPosition.getZ(), updatedPlanets[0].getPosition().getZ(), DELTA);
        // Add more assertions for other celestial objects and properties
    }
}