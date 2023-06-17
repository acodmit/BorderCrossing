package com.bordercrossing.record;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.bordercrossing.controller.Main;
import com.bordercrossing.model.*;

import static com.bordercrossing.controller.SimulationController.SIMULATION_START_TIME;

public class SuspensionManager {
    private static Path SUSPENDED_FILE_PATH = Paths.get("src","main","resources","com","bordercrossing","output",
            SIMULATION_START_TIME + "_suspended");

    public static void generateSuspension(Passenger passenger, Vehicle vehicle, Checkpoint checkpoint, String suspensionReason) {

        // Flag showing if a passenger is a driver, initially set to false
        boolean isDriver = false;

        // Set to true if passenger really is a driver
        if(vehicle.getDriver().equals(passenger))
            isDriver = true;

        // Generate suspension to a passenger
        Suspension suspension = new Suspension(passenger.getFirstName() + " " + passenger.getLastName(),
                vehicle.getVehicleName(),isDriver,checkpoint.getCheckpointName(),suspensionReason,vehicle.isSuspended());

        // Load suspensions from a file, append new one and save it as a file again
        List<Suspension> suspensionList = loadSuspensionsFromFile();
        suspensionList.add(suspension);
        saveSuspensionsToFile(suspensionList);
    }

    public static List<Suspension> getSuspensions() {
        return loadSuspensionsFromFile();
    }

    private static List<Suspension> loadSuspensionsFromFile() {

        // Check if file really exist, if not then new suspended file is created
        if (!Files.exists(SUSPENDED_FILE_PATH)) {
            try {
                Files.createFile(SUSPENDED_FILE_PATH);
            } catch (IOException e) {
                Main.LOGGER.log(Level.SEVERE, "Error creating suspended file", e);
            }
        }

        // Deserialize a list of suspensions from a file
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(SUSPENDED_FILE_PATH))) {
            return (List<Suspension>) ois.readObject();
        } catch (EOFException e) {
            // Handle the case when the file is empty
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            Main.LOGGER.log(Level.SEVERE, "Error loading suspensions from file", e);
        }

        // Handle other exceptions
        return new ArrayList<>();
    }


    private static void saveSuspensionsToFile(List<Suspension> suspensionList) {

        // Serialize a list of suspensions to a suspended file
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(SUSPENDED_FILE_PATH))) {
            oos.writeObject(suspensionList);
        } catch (IOException e) {
            Main.LOGGER.log(Level.SEVERE, "Error saving suspensions to file", e);
        }
    }
}
