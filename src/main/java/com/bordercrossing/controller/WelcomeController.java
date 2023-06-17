package com.bordercrossing.controller;

import com.bordercrossing.simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class WelcomeController {

    @FXML
    private ImageView imageView;

    @FXML
    private Button launchButton;

    @FXML
    void handleLaunch(ActionEvent event) {

        // Create a Simulation instance
        Simulation simulation = new Simulation();

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation-view.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            SimulationController controller = loader.getController();

            // Set the Simulation instance on the controller
            controller.setSimulation(simulation);

            // Start updating graphical user interface
            controller.updateGridPane();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Simulation");
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) launchButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            Main.LOGGER.log(Level.SEVERE, "Error occurred during loading simulation window", ex);
        }

        try{
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("remaining-view.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            RemainingController controller = loader.getController();

            // Set the Simulation instance on the controller
            controller.setSimulation(simulation);

            // Start updating graphical user interface
            controller.updateGrid();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Remaining Vehicles");
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();

        }catch (IOException ex){
            Main.LOGGER.log(Level.SEVERE, "Error occurred during loading remaining window", ex);
        }

        // Launch simulation
        simulation.launch();

    }
}
