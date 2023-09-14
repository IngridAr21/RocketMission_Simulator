package BackEnd.Simulator.ODESolvers;

public interface ODESolver {

     /**
     * evaluate method for each ODE solver to implement for testing
     * @param stepSize stepSize to use for the solver
     */
    public void evaluate(double stepSize);

}
