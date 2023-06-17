package com.bordercrossing.simulation;

import com.bordercrossing.model.*;

import java.util.*;

public class Simulation {

    public static boolean FINISHED = false;
    public static boolean PAUSED = false;
    public List<Vehicle> vehicleList;
    public List<Checkpoint> checkpointList;
    public Grid simulationGrid;
    public Grid remainingGrid;
    public Simulation(){

        // Generate simulation and remaining grids
        this.simulationGrid = new Grid(9,21);
        this.remainingGrid = new Grid(10,19);

        // Generate checkpoints
        this.checkpointList = generateCheckpoints();

        // Generate and place vehicles
        this.vehicleList = generateVehicles();
        placeSimulationVehicles();
        placeRemainingVehicles();

    }

    public List<Vehicle> generateVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        // Generate 35 private cars
        for (int i = 0; i < 35; i++) {
            vehicles.add(new PrivateCar());
        }

        // Generate 10 buses
        for (int i = 0; i < 10; i++) {
            vehicles.add(new Bus());
        }

        // Generate 5 trucks
        for (int i = 0; i < 5; i++) {
            vehicles.add(new Truck());
        }

        // Shuffle vehicle list
        Collections.shuffle(vehicles);

        return vehicles;
    }

    public List<Checkpoint> generateCheckpoints() {
        List<Checkpoint> checkpoints = new LinkedList<>();

        // Generate 3 police checkpoints
        for (int i = 0; i < 3; i++) {
            checkpoints.add(new PoliceCheckpoint(simulationGrid,2 + (i * 2),13));
        }

        // Generate 2 customs checkpoints
        for (int i = 0; i < 2; i++) {
            checkpoints.add(new CustomsCheckpoint(simulationGrid,4 + (i * 2), 16));
        }

        return checkpoints;
    }

    public void placeRemainingVehicles() {

        // Place remaining vehicles from vehicle queue to remaining grid in a snake shape with a blank space

        int row = 0;
        int column = 0;
        int direction = 2; // 2 represents moving right, -2 represents moving left

        for (int i = 5; i < 50; i++) {

            if(!remainingGrid.isOccupied(row,column)){
                Vehicle vehicle = vehicleList.get(i);
                if (vehicle != null ) {
                    remainingGrid.setVehicle(vehicle, row, column);
                    vehicle.setSimulation(this);
                    vehicle.setCurrentRow(row);
                    vehicle.setCurrentColumn(column);
                    vehicle.setInRemainingGrid(true);
                }
            }

            // Update the row and column based on the current direction
            column += direction;


            // Check if we have reached the last column or the first column
            if ((column >= remainingGrid.getColCount() || column < 0)) {
                // Move to the second next row
                row += 2;
                // Reverse the direction
                direction *= -1;
                // Adjust the column based on the direction
                column += direction;

            }
        }
    }


    public void placeSimulationVehicles(){

        // Place first five vehicles from vehicle queue to simulation grid
        for (int i = 0, row = 4, column = 9; i < 5; i++) {
            Vehicle vehicle = vehicleList.get(i);
            if (vehicle != null) {
                simulationGrid.setVehicle(vehicle, row, column);
                vehicle.setSimulation(this);
                vehicle.setCurrentRow(row);
                vehicle.setCurrentColumn(column);
                vehicle.setInSimulationGrid(true);
                column -= 2; // Move to the next column with a step of 2
            }
        }

    }

    public void launch(){

        // Start vehicle execution
        for(Vehicle vehicle : vehicleList){
            vehicle.start();
        }

        // Start checkpoints execution
        for(Checkpoint checkpoint : checkpointList){
            checkpoint.start();
        }


    }

}
