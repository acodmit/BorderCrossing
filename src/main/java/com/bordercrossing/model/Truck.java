package com.bordercrossing.model;

import javafx.scene.paint.Color;

import java.util.Random;

public class Truck extends Vehicle{
    private static int TRUCK_ID = 0;
    private int declaredWeight;
    private int weight;

    public Truck() {
        super("Truck" + TRUCK_ID, new Random().nextInt(3) + 1, Color.RED);
        declaredWeight = generateDeclaredWeight();
        weight = calculateWeight(declaredWeight);
        TRUCK_ID++;
    }

    public int getDeclaredWeight() {
        return declaredWeight;
    }

    public void setDeclaredWeight(int declaredWeight) {
        this.declaredWeight = declaredWeight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int generateDeclaredWeight() {

        int[] declaredWeights = {3500, 7500, 20_000, 40_000};
        return declaredWeights[new Random().nextInt(declaredWeights.length)];

    }

    private int calculateWeight(int declaredWeight) {

        // Calculate value of maximal possible overweight
        int maxOverDeclared = (int) (0.3 * declaredWeight); // Maximum 30% over declared weight

        if (new Random().nextDouble() <= 0.2) {
            // Possibility of 20% that the truck is up to 30% over declared weight
            int overDeclared = new Random().nextInt(maxOverDeclared + 1);
            return declaredWeight + overDeclared;
        } else {
            // Other trucks have regular weight
            int underDeclared = new Random().nextInt((declaredWeight - 1000) + 1);
            return declaredWeight - underDeclared;
        }
    }

    public void moveToBorderCheckpoint(){

        if(!simulation.simulationGrid.isOccupied(6,12)){

            move(simulation.simulationGrid,DirectionEnum.RIGHT);

            // Move vehicle for two steps down
            for (int step = 0; step < 2; step++) {
                move(simulation.simulationGrid,DirectionEnum.DOWN);
            }

            // Move vehicle for two steps right
            for (int step = 0; step < 2; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }

        }

    }

    public void moveToCustomsCheckpoint(){

        if((currentRow == 6 && currentColumn == 12) && !simulation.simulationGrid.isOccupied(6,15)){

            // Move vehicle for four steps right
            for (int step = 0; step < 3; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }

        }



    }
}
