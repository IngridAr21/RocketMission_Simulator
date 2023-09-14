package BackEnd.Tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.ODESolvers.AdamMoulton;

/*
 * Testing strategy
 *
 * Partition the inputs as follows:
 *      h: h = 0, h > 0
 *      y: yold > 0, yold = 0, yold < 0,
 *      derivative: derivative < 0, derivative = 0,derivative > 0
 *
 * Full Cartesian Coverage coverage of partitions.
 */
public class AdamMoultonTest {
    
    Planets planets = new Planets();
    MyVector zeroVector = new MyVector(0,0,0);
    MyVector positiveVector1 = new MyVector(1,1,1);
    MyVector positiveVector2 = new MyVector(2,2,2);
    MyVector positiveVector3 = new MyVector(3,3,3);
    MyVector positiveVector4 = new MyVector(4,4,4);
    MyVector positiveVector5 = new MyVector(5,5,5);
    MyVector negativeVector1 = new MyVector(-1,-1,-1); 
    MyVector negativeVector2 = new MyVector(-2,-2,-2); 
    MyVector negativeVector3 = new MyVector(-3,-3,-3); 
    MyVector negativeVector4 = new MyVector(-4,-4,-4); 
    MyVector negativeVector5 = new MyVector(-5,-5,-5);
    MyVector negativeDoubleVector = new MyVector(-2,-2,-2);
    private final double DELTA = 1e-2;

    AdamMoulton adamMoulton = new AdamMoulton();

    @Test 
    public void testPositiveYOldZeroStepsize(){
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getX(), DELTA);
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getY(), DELTA);
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getZ(), DELTA);
    }
    @Test 
    public void testPositiveYOldDiffZeroStepsize(){
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getX(), DELTA);
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getY(), DELTA);
        assertEquals(1,adamMoulton.adamMoultonAdd(positiveVector1,0, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getZ(), DELTA);
    }
    @Test 
    public void testPositiveYOldPositiveStepsize(){
        assertEquals(2,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getX(), DELTA);
        assertEquals(2,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getY(), DELTA);
        assertEquals(2,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector1, positiveVector1, positiveVector1, positiveVector1).getZ(), DELTA);
    }
    @Test 
    public void testPositiveYOldDiffPositiveStepsize(){
        assertEquals(3.5,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getX(), DELTA);
        assertEquals(3.5,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getY(), DELTA);
        assertEquals(3.5,adamMoulton.adamMoultonAdd(positiveVector1,1, positiveVector2, positiveVector3, positiveVector4, positiveVector5).getZ(), DELTA);
    }
    @Test 
    public void testNegativeYOldZeroStepsize(){
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getX(), DELTA);
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getY(), DELTA);
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getZ(), DELTA);
    }
    @Test
    public void testNegativeYOldDiffZeroStepsize(){
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getX(), DELTA);
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getY(), DELTA);
        assertEquals(-1,adamMoulton.adamMoultonAdd(negativeVector1,0, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getZ(), DELTA);
    }
    @Test 
    public void testNegativeYOldPositiveStepsize(){
        assertEquals(-2,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getX(), DELTA);
        assertEquals(-2,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getY(), DELTA);
        assertEquals(-2,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector1, negativeVector1, negativeVector1, negativeVector1).getZ(), DELTA);
    }
    @Test
    public void testNegativeYOldDiffPositiveStepsize(){
        assertEquals(-3.5,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getX(), DELTA);
        assertEquals(-3.5,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getY(), DELTA);
        assertEquals(-3.5,adamMoulton.adamMoultonAdd(negativeVector1,1, negativeVector2, negativeVector3, negativeVector4, negativeVector5).getZ(), DELTA);
    }
    @Test
    public void testZeroYOldZeroStepsize(){
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,0, zeroVector, zeroVector, zeroVector, zeroVector).getX(), DELTA);
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,0, zeroVector, zeroVector, zeroVector, zeroVector).getY(), DELTA);
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,0, zeroVector, zeroVector, zeroVector, zeroVector).getZ(), DELTA);
    }
    @Test
    public void testZeroYOldPositiveStepsize(){
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,1, zeroVector, zeroVector, zeroVector, zeroVector).getX(), DELTA);
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,1, zeroVector, zeroVector, zeroVector, zeroVector).getY(), DELTA);
        assertEquals(0,adamMoulton.adamMoultonAdd(zeroVector,1, zeroVector, zeroVector, zeroVector, zeroVector).getZ(), DELTA);
    }
}