package com.bordercrossing.record;

import com.bordercrossing.controller.Main;
import com.bordercrossing.model.CustomsDocumentation;
import com.bordercrossing.model.Passenger;
import com.bordercrossing.model.Truck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

import static com.bordercrossing.controller.SimulationController.SIMULATION_START_TIME;

public class CustomsDocumentationManager {
    private static final Path CUSTOMS_FILE_PATH = Paths.get("src","main","resources","com","bordercrossing","output",
            SIMULATION_START_TIME + "_customs_docs.txt");
    public static void generateCustomsDocumentation(Truck truck, String issuer) {

        // Generate object containing trucks's customs documentation info
        CustomsDocumentation customsDoc = new CustomsDocumentation(issuer, null, truck.getVehicleName());
        customsDoc.setDeclaredWeight(Integer.toString(truck.getDeclaredWeight()));
        customsDoc.setTruckWeight(Integer.toString(truck.getWeight()));

        // Append generated customs documentation to a file
        appendCustomsDocumentationToFile(customsDoc);
    }

    public static void generateCustomsDocumentation(Passenger passenger, String issuer, String illeagalItem) {

        // Generate object containing passenger's customs documentation info
        CustomsDocumentation customsDoc = new CustomsDocumentation(issuer,
                passenger.getFirstName()+ " " + passenger.getLastName(), null);
        customsDoc.setIllegalItem(illeagalItem);

        // Append generated customs documentation to a file
        appendCustomsDocumentationToFile(customsDoc);
    }

    private static void appendCustomsDocumentationToFile(CustomsDocumentation customsDoc) {

        // If file does not exist, create a new file
        try {
            if (!Files.exists(CUSTOMS_FILE_PATH)) {
                Files.createFile(CUSTOMS_FILE_PATH);
            }
            // Append generated customs documentation to a file
            Files.writeString(CUSTOMS_FILE_PATH, customsDoc.toString() + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            Main.LOGGER.log(Level.SEVERE, "Error appending customs documentation to file", e);
        }
    }
}

