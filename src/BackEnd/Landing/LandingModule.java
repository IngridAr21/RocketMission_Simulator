package BackEnd.Landing;
import BackEnd.Celectial.PlanetsCreater.Planet;


public class LandingModule 
{
    private iLandingStrategy landingStrategy;
    public void land(Planet rocket)
    {
        landingStrategy.land(rocket);
    }
    
}