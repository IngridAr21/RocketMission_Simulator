Introduction:
This simulation program is designed to demonstrate the trajectory of a rocket on its journey to Saturn's moon, Titan, using JavaFX and the Euler Method. The program simulates the gravitational forces of Saturn and Titan and uses the Euler Method to calculate the rocket's trajectory.

Prerequisites:

Java 8 or higher version installed
JavaFX 8 or higher version installed
Installation:

Download or clone the repository to your local machine
Open the project in Visual Studio Code
Open the launch.json file located in the .vscode folder in the project directory
In the "configurations" section, add the following VM arguments to the "args" field:
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
Note: Replace /path/to/javafx/lib with the path to the JavaFX lib directory on your system
Save the changes to the launch.json file
Run the program using the main method in the App.java class
To change between different solvers change the "RK4" in App to whichever
solver you want to use
If you want to run all the solvers by themselfs without the GUI to test them against the real data then just run MyTest.java
Usage:


Notes:

The simulation assumes a simplified model of the gravitational forces of Saturn and Titan
The Euler Method is used to calculate the trajectory of the rocket, which may not be as accurate as other numerical methods
The simulation is intended for educational purposes and not for real-world applications
