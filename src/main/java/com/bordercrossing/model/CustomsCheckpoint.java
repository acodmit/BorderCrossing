package com.bordercrossing.model;

import com.bordercrossing.controller.Main;
import com.bordercrossing.record.CustomsDocumentationManager;
import com.bordercrossing.simulation.Grid;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class CustomsCheckpoint extends Checkpoint {
    public CustomsCheckpoint(Grid simulationGrid, int row, int column) {

        this.id = COUNT++;
        this.name = "CustomsCheckpoint" + id;
        this.simulationGrid = simulationGrid;
        this.row = row;
        this.column = column;

    }

    protected void processVehicle(Vehicle vehicle) {

        if (vehicle instanceof PrivateCar) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", ex);
            }

        } else if (vehicle instanceof Bus) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", ex);
            }

            List<Passenger> passengers = vehicle.getPassengers();
            for (Passenger passenger : passengers) {

                if (passenger.hasIllegalItem()) {

                    if (vehicle.getDriver().equals(passenger)) {

                        // Set the vehicle's isSuspended flag to true
                        vehicle.setSuspended(true);

                    }

                    // Generate passenger's customs documentation
                    CustomsDocumentationManager.generateCustomsDocumentation(passenger, name, "IllegalItem");
                }

            }
        } else if (vehicle instanceof Truck) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", ex);
            }

            Truck truck = (Truck) vehicle;
            if (truck.getDeclaredWeight() <= truck.getWeight()) {

                // Set the vehicle's isSuspended flag to true
                vehicle.setSuspended(true);

                // Generate truck's customs documentation
                CustomsDocumentationManager.generateCustomsDocumentation(truck, name);


            } else if (new Random().nextDouble() < 0.5) {

                // Generate truck's customs documentation
                CustomsDocumentationManager.generateCustomsDocumentation(truck, name);

            }

        }

        // Set customs processed flat to true
        vehicle.setCustomsProcessed(true);
    }
}
