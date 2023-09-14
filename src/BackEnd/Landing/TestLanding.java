package BackEnd.Landing;

import BackEnd.MyVector;
import BackEnd.Celectial.PlanetsCreater.Planet;
import BackEnd.Celectial.PlanetsCreater.Planets;


public class TestLanding 
{
    public static Planets planets = new Planets();

    public static Planet[] planetArr = planets.getPlanets();

    static Planet rocket;
    
    public static void main(String[] args) 
    {
        MyVector position = new MyVector(1000, 100000);
        MyVector velocity = new MyVector(0, 0, 0);
        rocket = planetArr[11];
        OpenLoopStrategy test = new OpenLoopStrategy(position, velocity, rocket,1 );
        while (!test.hasLanded)
        {
            test.land(rocket);
            // x = test.getxCoordinate();
            // System.out.println(x);
            // y = test.getyCoordinate();
            // lander.setLayoutX(x);
            // lander.setLayoutY(y);
        }
    }
}