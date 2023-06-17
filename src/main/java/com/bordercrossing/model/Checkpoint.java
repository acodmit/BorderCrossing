package com.bordercrossing.model;

import com.bordercrossing.controller.Main;
import com.bordercrossing.simulation.Grid;
import com.bordercrossing.simulation.Simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

public abstract class Checkpoint extends Thread{
    protected static int COUNT = 0;
    protected int id;
    protected String name;
    protected int row;
    protected int column;
    protected Grid simulationGrid;

    public String getCheckpointName() {
        return name;
    }
    public void setCheckpointName(String name) {
        this.name = name;
    }

    @Override
    public void run(){

        while(true) {

            if(Simulation.FINISHED){
                break;
            }

            if (getStatus()) {

                Vehicle vehicle = null;
                synchronized (Vehicle.getLOCK()) {

                    if (simulationGrid.isOccupied(row, column - 1)) {

                        vehicle = simulationGrid.getVehicle(row, column - 1);

                    }

                }

                if (vehicle != null) {

                    processVehicle(vehicle);

                }
                synchronized (Vehicle.getLOCK()) {

                    Vehicle.getLOCK().notifyAll();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Main.LOGGER.log(Level.SEVERE, "Error occurred during thread sleep", ex);
                }

        }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Main.LOGGER.log(Level.SEVERE, "Error occurred during thread sleep", ex);
                }
            }
        }
    }

    private boolean getStatus() {

        try {
            // Read the content of the file
            Path filePath = Paths.get("src","main","resources","com","bordercrossing","config","status.txt");
            List<String> lines = Files.readAllLines(filePath);

            // Get the status of the checkpoint at the specified index
            String line = lines.get(id);
            return Boolean.parseBoolean(line.trim());

        } catch (IOException ex) {
            Main.LOGGER.log(Level.SEVERE, "Error reading status file", ex);
            return false; // Default to false if there's an error
        }

    }

    protected void processVehicle(Vehicle vehicle) {
        // Process vehicle logic
        // ...
    }

}
