package BackEnd.Celectial.PlanetsCreater;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;

public class Planets {
        iPlanetsFactory planetsFactory;
        private static Planet[] planets;

        MyVector[] posVec;

        public Planets() {
                planetsFactory = new PlanetFactory();
                planets = planetsFactory.creatPlanet();
                posVec = getPositionsList();
        }

        public MyVector[] getPositionsList() {
                MyVector[] positionsList = new MyVector[planets.length];
                for (int i = 0; i < positionsList.length; i++) {
                        positionsList[i] = new MyVector(planets[i].getPosition().getX(),
                                        planets[i].getPosition().getY(), planets[i].getPosition().getZ());

                }
                return positionsList;
        }

        public static void setPlanets(CelestialObject[] newPlanets) {
                Planet[] neweststate = (Planet[]) (newPlanets);
                planets = neweststate;
        }

        public static Planet[] getPlanets() {
                return planets;
        }
        
        public static Planet[] getCopy (Planet[] planetsToBeCopied) {
                Planet[] copy = new Planet[planetsToBeCopied.length]; 
                for (int i = 0; i < planetsToBeCopied.length; i++) {
                        copy[i] = planetsToBeCopied[i].getCopy();
                }
                return copy;
        }

        public static void setPlanets(Planet[] newPlanets) {
               planets = newPlanets;
        }



}
