package BackEnd.Celectial.PlanetsCreater;

import BackEnd.Celectial.Creater.CelestialObject;

public interface iPlanetsFactory {
    
    public CelestialObject[] creatCelestialObjects();

    public Planet[] creatPlanet();
}
