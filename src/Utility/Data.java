package Utility;

public class Data {

        public final static double[][] positions = {
                        { 0, 0, 0 }, { 7.83e6, 4.49e7, 2.87e6 }, { -2.82e7, 1.04e8, 3.01e6 },
                        { -1.48e8, -2.78e7, 3.37e4 }, { -1.48e8, -2.75e7, 7.02e4 }, { -1.59e8, 1.89e8, 7.87e6 },
                        { 6.93e8, 2.59e8, -1.66e7 }, { 1.25e9, -7.60e8, -3.67e7 }, { 1.25e9, -7.61e8, -3.63e7 },
                        { 4.45e9, -3.98e8, -9.45e7 }, { 1.96e9, 2.19e9, -1.72e7 }, { -1.48e8, -2.78e7+6378, 3.37e4 }
        };

        public final static double[][] velocity = {
                        { 0, 0, 0 }, { -5.75e1, 1.15e1, 6.22e0 }, { -3.40e1, -8.97e0, 1.84e0 },
                        { 5.05e0, -2.94e1, 1.71e-3 }, { 4.34e0, -3.00e1, -1.16e-2 }, { -1.77e1, -1.35e1, 1.52e-1 },
                        { -4.71e0, 1.29e1, 5.22e-2 }, { 4.47e0, 8.24e0, -3.21e-1 }, { 9.00e0, 1.11e1, -2.25e0 },
                        { 4.48e-1, 5.45e0, -1.23e-1 }, { -5.13e0, 4.22e0, 8.21e-2 }, { 4.34e0, -3.00e1, -1.16e-2 }
        };

        public static final double[] MASS = { 1.99e30, 3.30e23, 4.87e24, 5.97e24, 7.35e22, 6.42e23,
                        1.90e27, 5.68e26, 1.35e23, 1.02e26, 8.68e25, 50000
        };
        public static final String[] NAMES = { "Sun", "Mercury", "Venus", "Earth", "Moon", "Mars", "Jupiter",
                        "Saturn", "Titan", "Neptune", "Uranus" ,"Rocket"};

        public static final double G = 6.6743e-20;

        public static final double[] radi = { 696340, 2439.7, 6051.8, 6378, 1735, 3389.5, 69911, 58232, 2574.7, 25362,
                        24622, 5 };

        public static final double[] distanceFromTheSun = { 0, 58000000, 108200000, 149597870, 150000000, 150000000,
                        778500000, 1434000000,
                        1.4012 * Math.pow(10, 9), 2.871 * Math.pow(10, 9), 4.495 * Math.pow(10, 9), 149597870 };

}
