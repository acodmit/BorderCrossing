package com.bordercrossing.model;

import com.bordercrossing.controller.Main;
import com.bordercrossing.record.SuspensionManager;
import com.bordercrossing.simulation.Grid;

import java.util.List;
import java.util.logging.Level;


public class PoliceCheckpoint extends Checkpoint{

    public PoliceCheckpoint(Grid simulationGrid, int row, int column){

        this.id = COUNT++;
        this.name = "PoliceCheckpoint" + id;
        this.simulationGrid = simulationGrid;
        this.row = row;
        this.column = column;

    }

    protected void processVehicle(Vehicle vehicle) {

        List<Passenger> passengers = vehicle.getPassengers();

        for (Passenger passenger : passengers) {

            if(!(vehicle instanceof Bus)) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during sleep", ex);
                }

            }else{

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during sleep", ex);
                }

            }

            if (!passenger.hasValidDocuments()) {

                if(vehicle.getDriver().equals(passenger)){

                    // Set the vehicle's isSuspended flag to true
                    vehicle.setSuspended(true);

                    // Set the driver`s flag isSuspended to true
                    passenger.setSuspended(true);
                }
                // Create a suspension for the passenger
                SuspensionManager.generateSuspension(passenger,vehicle,this,"Invalid documents.");


            }else if( vehicle.getDriver().isSuspended()){

                // Create a suspension for the passenger
                SuspensionManager.generateSuspension(passenger,vehicle,this,"Vehicle`s driver is suspended.");

            }
        }

        vehicle.setPoliceProcessed(true);
    }
}
