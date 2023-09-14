package BackEnd.Simulator.ODESolvers.OneDimension;

public class OneDODE {

    /**
     * differential equation function
     * @param t t
     * @param y y
     * @return y(tEnd)
     */
    public double dydx(double t, double y){
        double dydx = Math.exp(-t)-Math.pow(y, 2);
        return dydx;
    }

    /**
     * euler method
     * @param t t
     * @param tEnd t end
     * @param y y 
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double euler(double t, double tEnd, double y, double stepSize){
        while (t < tEnd){
            y = y + stepSize * dydx(t, y);
            t += stepSize;
            System.out.println(y + " at euler " + t);
        }
        return y;
    }
    
    /**
     * improved Euler
     * @param t t
     * @param tEnd t end
     * @param y y 
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double improvedEuler(double t, double tEnd, double y, double stepSize){
        while (t < tEnd){
            y = y + (0.5 * stepSize) * (dydx(t, y) + dydx(t + stepSize, y + stepSize * dydx(t, y)));
            t += stepSize;
            System.out.println(y + " at improved Euler" + t);
        }
        return y;
    }

    /**
     * Runge Kutta fourth order method
     * @param t t
     * @param tEnd t end
     * @param y y 
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double RK4(double t, double tEnd, double y, double stepSize){
        double k1 = 0;
        double k2 = 0;
        double k3 = 0;
        double k4 = 0;

        while (t < tEnd){
            k1 = stepSize * dydx(t, y);
            k2 = stepSize * dydx(t + (1/3.0) *  stepSize, y + (1/3.0) * k1);
            k3 = stepSize * dydx(t + (2/3.0) * stepSize, y -(1/3.0) * k1 + k2);
            k4 = stepSize * dydx(t + stepSize, y + k1 - k2 + k3);
            
            y = y + (1/8.0) * (k1 + 3 * k2 + 3 * k3 + k4);
            t += stepSize;
            System.out.println(y + " at RK4 " +t);
        }
        return y;
    }

    /**
     * Adam Bashforth fourth order method using RK4 as bootstrap
     * @param t t
     * @param tEnd t end
     * @param y y
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double AB4(double t, double tEnd, double y, double stepSize){
        double t1 = t + stepSize, t2 = t1 + stepSize, t3 = t2 + stepSize; 
        double y1 = RK4(t, t1, y, stepSize); 
        double y2 = RK4(t1, t2, y1, stepSize);
        double y3 = RK4(t2, t3, y2, stepSize);
        double y4 = 0;
        while (t3 < tEnd){
            y4 = y3 + (stepSize / 24.0) * (55 * dydx(t3, y3) - 59 * dydx(t2, y2) + 37 * dydx(t1, y1) - 9 * dydx(t, y));

            t = t1;
            t1 = t2;
            t2 = t3;
            t3 += stepSize;
            
            y = y1;
            y1 = y2;
            y2 = y3;
            y3 = y4;
            System.out.println(y4 + " at AB4 " + t3);
        }
        return y4;
    }

    /**
     * Adam Moutlon third order method using RK4 as bootstrap 
     * @param t t
     * @param tEnd t end
     * @param y y
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double AM3(double t, double tEnd, double y, double stepSize){
        double t1 = t + stepSize, t2 = t1 + stepSize, t3 = t2 + stepSize; 
        double y1 = RK4(t, t1, y, stepSize); 
        double y2 = RK4(t1, t2, y1, stepSize);
        double y3 = RK4(t2, t3, y2, stepSize);

        while (t3 < tEnd){
            y3 = y2 + (stepSize / 24.0) * (9 * dydx(t3, y3) + 19 * dydx(t2, y2) - 5 * dydx(t1, y1) + dydx(t, y));
            
            t = t1;
            t1 = t2;
            t2 = t3;
            t3 += stepSize;
            
            y = y1;
            y1 = y2;
            y2 = y3;
            y3 = RK4(t2, t3, y2, stepSize);
            System.out.println(y3 + " at AM3 " + t3);
        }
        return y3;
    }

    /**
     * Adam Moulton third order method using Adam Bashforth third order as bootstrap
     * @param t t
     * @param tEnd t end
     * @param y y 
     * @param stepSize step size
     * @return y(tEnd)
     */
    public double AM3wAB3(double t, double tEnd, double y, double stepSize){
        double t1 = t + stepSize, t2 = t1 + stepSize, t3 = t2 + stepSize;
        double y1 = RK4(t, t1, y, stepSize); 
        double y2 = RK4(t1, t2, y1, stepSize);
        double y3 = AB3boostrap(t, t1, t2, y, y1, y2, stepSize);
        
        while (t3 < tEnd){
            y3 = y2 + (stepSize / 24.0) * (9 * dydx(t3, y3) + 19 * dydx(t2, y2) - 5 * dydx(t1, y1) + dydx(t, y));

            t = t1;
            t1 = t2;
            t2 = t3;
            t3 += stepSize;
            
            y = y1;
            y1 = y2;
            y2 = y3;
            
            y3 = AB3boostrap(t, t1, t2, y, y1, y2, stepSize);
            System.out.println(y3 + " at AM32 second " + t3);
        }
        return y3;
    }

    /**
     * Adam Bashforth third order method used for Adam Moulton boostrap 
     * @param t t
     * @param t1 t1
     * @param t2 t2
     * @param y y
     * @param y1 y1
     * @param y2 y2
     * @param stepSize step size
     * @return y3
     */
    public double AB3boostrap(double t, double t1, double t2, double y, double y1, double y2, double stepSize){
        double y3 = y2 + (stepSize/12.0) * (23 * dydx(t2, y2) - 16 * dydx(t1, y1) + 5 * dydx(t, y));
        return y3;
    }
    
    public static void main(String[]args){
        
    }
}