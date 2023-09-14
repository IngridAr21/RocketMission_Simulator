package BackEnd.Simulator.TimeManagment;

public class MyTime {

    static double start;
    double end;
    double timeStep;

    public MyTime(double start, double end, double timeStep) {
        this.start = start;
        this.timeStep = timeStep;
        this.end = end;
    }

    public double getHalf() {
        return Math.abs(start - end) / 2;
    }

    public static double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

}
