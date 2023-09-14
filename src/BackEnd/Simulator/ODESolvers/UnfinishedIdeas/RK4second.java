package BackEnd.Simulator.ODESolvers.UnfinishedIdeas;

import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;
import BackEnd.Simulator.ODESolvers.ODESolver;
import BackEnd.Simulator.Physics.PhysicsEnginGeneral;

public class RK4second implements ODESolver {
    private final int Step_Amount = 512; 
    
    @Override
    public void evaluate(double stepSize) {
        Planets.setPlanets(rungeKuttaCall(stepSize, Planets.getPlanets()));
    }

    public Planet[] rungeKuttaCall (double stepsize , Planet[] planetsstate) {
        double height = stepsize;
        
        Planet[] currentstate = planetsstate.clone();
        MyVector[] k1 = getKn(currentstate, height);
        Planet[] planetsstateK2 = getYofKn (currentstate, k1, height, 2);
        MyVector[] k2 = getKn(planetsstateK2, height, k1);
        Planet[] planetsstateK3 = getYofKn(currentstate, k2, height, 3);
        MyVector[] k3 = getKn(planetsstateK3, height, k1);
        Planet[] planetsstateK4 = getYofKn(currentstate, k3, height, 3);
        MyVector[] k4 = getKn(planetsstateK4, height, k1);
        planetsstate = rungaKuttaAdd(currentstate.clone(), k1, k2, k3, k4, height);
        
        return planetsstate;
    }

    public Planet[] rungaKuttaAdd(Planet[] currentstate, MyVector[] k1, MyVector[] k2, MyVector[] k3, MyVector[] k4, double height) {
        Planet[] newPositions = currentstate.clone();
        for (int i = 1; i < currentstate.length; i++) {
            MyVector addedKs = k1[i];
            addedKs = MyVector.add(addedKs, MyVector.mult(k2[i], 2));
            addedKs = MyVector.add(addedKs, MyVector.mult(k3[i], 2));
            addedKs = MyVector.add(addedKs, k4[i]);
            MyVector newPos = MyVector.add(currentstate[i].getPosition().copy(), MyVector.mult(addedKs, height/6));
            newPositions[i].setPosition(newPos);
            newPositions[i].setVelocity(k1[i]);
        }
        return newPositions;
    }

    public Planet[] getYofKn (Planet[] currentstate, MyVector[] knMinusOne, double height, int n) {
        Planet[] yofkn = currentstate.clone();
        for (int j = 1; j < currentstate.length; j++) {
            if (n == 2 || n== 3) {
                MyVector yOfkn = MyVector.add(currentstate[j].getCopy().getPosition().copy(), MyVector.mult(knMinusOne[j].copy(), height/2));
                Planet knJ = currentstate[j].getCopy();
                knJ.setPosition(yOfkn);
                yofkn[j] = knJ;
            } else if (n == 4) {
                MyVector yOfkn = MyVector.add(currentstate[j].getCopy().getPosition().copy(), MyVector.mult(knMinusOne[j].copy(), height));
                Planet knJ = currentstate[j].getCopy();
                knJ.setPosition(yOfkn);
                yofkn[j] = knJ;
            }
        }
        return yofkn;
    }

    public MyVector[] getKn (Planet[] currentstate, double height, MyVector[] k1) {
        MyVector[] kn = new MyVector[currentstate.length];
            for (int i = 1; i < currentstate.length; i++) {
                kn[i] = MyVector.add(k1[i].copy(), MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(currentstate[i].getCopy(), Planets.getCopy(currentstate)),height));
            }
        return kn;
    }

    public MyVector[] getKn (Planet[] currentstate, double height) {
        //System.out.println(currentstate.length);
        MyVector[] kn = new MyVector[currentstate.length];
            for (int i = 1; i < currentstate.length; i++) {
                kn[i] = MyVector.add(currentstate[i].getVelocity().copy(), MyVector.mult(PhysicsEnginGeneral.accelerationThroughForce(currentstate[i].getCopy(), Planets.getCopy(currentstate)),height));
                //System.out.println(i);
            }
        return kn;
    }

    
    
}
