package BackEnd.Celectial.PlanetsCreater;

import BackEnd.MyVector;
import BackEnd.Celectial.Creater.CelestialObject;
import static Utility.Data.*;

public class PlanetFactory implements iPlanetsFactory {

    CelestialObject[] celestialObjects;
    Planet[] planets;

    @Override
    public Planet[] creatPlanet() {
        initializePlanets();
        return planets;

    };

    @Override
    public CelestialObject[] creatCelestialObjects() {
        initializePlanets();
        return this.celestialObjects;
    }

    private void initializePlanets() {
        CelestialObject[] celestialObjects = new Planet[positions.length];

        MyVector[] velVectors = initializeVelocityVectors();
        MyVector[] posVectors = initializePositionVectors();

        for (int i = 0; i < celestialObjects.length; i++) {

            celestialObjects[i] = new Planet.PlanetBuilder().name(NAMES[i])
                    .mass(MASS[i])
                    .radius(radi[i])
                    .distanceFromSun(distanceFromTheSun[i])
                    .velocity(velVectors[i])
                    .position(posVectors[i])
                    .build();
        }
        accelerationThroughForce(celestialObjects);
        this.celestialObjects = celestialObjects;
        this.planets = (Planet[]) celestialObjects;
    }

    private void accelerationThroughForce(CelestialObject[] celestialObjects) {

        for (CelestialObject currentSpaceObject : celestialObjects) {

            for (CelestialObject planet : celestialObjects) {
                if (planet.getName().equals(currentSpaceObject.getName())) {
                    continue;
                }
                double dist = planet.getPosition().dist(currentSpaceObject.getPosition());
                double mass = planet.getMass();
                MyVector accVector = planet.getPosition().copy();

                accVector.sub(currentSpaceObject.getPosition());
                accVector.mult(G * mass / Math.pow(dist, 3));
                currentSpaceObject.setAccelaration(accVector);
            }
        }
    }

    private MyVector[] initializePositionVectors() {
        MyVector[] positionsList = new MyVector[positions.length];
        for (int i = 0; i < positionsList.length; i++) {
            MyVector position = new MyVector(positions[i][0], positions[i][1], positions[i][2]);
            positionsList[i] = position;
        }
        return positionsList.clone();
    }

    private static MyVector[] initializeVelocityVectors() {
        MyVector[] velocitiesList = new MyVector[velocity.length];
        for (int i = 0; i < velocitiesList.length; i++) {
            MyVector position = new MyVector(velocity[i][0], velocity[i][1], velocity[i][2]);
            velocitiesList[i] = position;
        }
        return velocitiesList.clone();
    }

}
