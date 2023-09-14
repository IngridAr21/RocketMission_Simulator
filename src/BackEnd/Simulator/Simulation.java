package BackEnd.Simulator;

import BackEnd.Simulator.Physics.PhysicsEnginGeneral;
import BackEnd.Simulator.TimeManagment.MyTime;

public class Simulation {

    PhysicsEnginGeneral pEngine;
    MyTime time;

    public Simulation(PhysicsEnginGeneral pEngine) {
        this.pEngine = pEngine;
        this.time = pEngine.getTime();
    }

    public void start() {
        double start = time.getStart();
        double end = time.getEnd();
        while (start <= end) {
            pEngine.movePlanets();
            start += time.getTimeStep();
            
            // System.out.println(pEngine.getCelectialObjects()[3].getPosition());
        }
    }

    public PhysicsEnginGeneral getpEngine() {
        return pEngine;
    }

    public void setpEngine(PhysicsEnginGeneral pEngine) {
        this.pEngine = pEngine;
    }

    public MyTime getTime() {
        return time;
    }

    public void setTime(MyTime time) {
        this.time = time;
    }

}
