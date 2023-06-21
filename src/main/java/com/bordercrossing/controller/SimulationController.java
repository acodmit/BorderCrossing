package com.bordercrossing.controller;

import com.bordercrossing.model.Vehicle;
import com.bordercrossing.simulation.Simulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class SimulationController implements Initializable {
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox additionalHBox;
    private GridPane gridPane;
    @FXML
    private VBox gridVBox;
    @FXML
    private Button pauseButton;
    @FXML
    private Label timeLabel;
    @FXML
    private Label successLabel;
    private Timeline timerTimeline;
    private boolean paused = false;
    private java.time.Duration executionTime = java.time.Duration.ZERO;
    private static final int NUM_ROWS = 9;
    private static final int NUM_COLS = 21;
    private Simulation simulation;
    public static final String SIMULATION_START_TIME;

    static {
        LocalDateTime startTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        SIMULATION_START_TIME = startTime.format(formatter);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Generate simulation grid
         gridPane = generateSimulationGrid();

        // Set alignment of gridPane
        VBox.setVgrow(gridPane, Priority.ALWAYS);

        // Add gridPane to main VBox
        gridVBox.getChildren().add(gridPane);

        // Set on action method for pause button
        pauseButton.setOnAction(event -> handlePause());

        // Generate timer updating timeline
        Timeline timerTimeline = generateTimerTimeline();
        timerTimeline.play();

        // Generate success label updating timeline
        Timeline successTimeline = generateSuccessTimeline();
        successTimeline.play();

    }

    public void updateGridPane(){

        // Generate grid updating timeline
        Timeline gridTimeline = GridUpdater.generateGridTimeline(gridPane,simulation.simulationGrid,50);
        gridTimeline.play();

    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    private GridPane generateSimulationGrid() {

        GridPane gridPane = new GridPane();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(37.0);
                rectangle.setHeight(37.0);

                Label label = new Label();

                if ((row == 2 || row == 4 || row == 6) && col == 13) {
                    rectangle.setFill(Color.BLUE); // police checkpoint

                    // Set label text
                    label.setText("PC" + (row / 2));

                } else if ((row == 4 || row == 6) && col == 16) {
                    rectangle.setFill(Color.GREEN); // customs checkpoints

                    // Set label text
                    label.setText("CC" + ((row - 2)  / 2));

                } else if ((row >= 2 && row <= 6) && col == 10) {
                    rectangle.setFill(Color.GRAY);
                } else if ((row == 2 && col >= 10 && col <= 15) || (row == 6 && col >= 10 )) {
                    rectangle.setFill(Color.GRAY);
                } else if (row == 3 && col == 15 ) {
                    rectangle.setFill(Color.GRAY);
                } else {
                    rectangle.setFill(row == 4 ? Color.GRAY : Color.LIGHTGRAY);
                }

                // Add Rectangle to the GridPane
                gridPane.add(rectangle, col, row);

                // Set the alignment of the stack pane to center in the grid cell
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);

                // Set label text color
                //label.setStyle("-fx-text-fill: white;");

                // Add Label to the GridPane
                gridPane.add(label, col, row);
            }
        }
        gridPane.setGridLinesVisible(true);

        return gridPane;
    }
    @FXML
    void handlePause() {

        if (!Simulation.PAUSED) {

            // Pause the simulation
            timerTimeline.pause();
            Simulation.PAUSED = true;

        } else {

            // Resume the simulation
            timerTimeline.play();
            Simulation.PAUSED = false;

            synchronized (Vehicle.getLOCK()) {
                Vehicle.getLOCK().notifyAll();
            }
        }

    }

    // Method generating timer updating timeline
    private Timeline generateTimerTimeline() {

        timerTimeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> updateTimerLabel()));
        timerTimeline.setCycleCount(Animation.INDEFINITE);

        return timerTimeline;
    }

    private void updateTimerLabel() {

        executionTime = executionTime.plus(Duration.ofSeconds(1));
        String formattedTime = formatTime(executionTime);
        timeLabel.setText(timeLabel.getText().substring(0,24) + formattedTime);

    }

    public Timeline generateSuccessTimeline() {

        // Create a KeyFrame with the desired refresh duration
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.seconds(1), event -> updateSuccessLabel());

        // Create a Timeline and set the KeyFrame
        Timeline timeline = new Timeline(keyFrame);

        // Set the number of cycles for the Timeline
        timeline.setCycleCount(Animation.INDEFINITE);

        return timeline;

    }

    public void updateSuccessLabel() {

        // Update the label based on the provided vehicle
        Vehicle successVehicle = Vehicle.getSUCCESS_VEHICLE();

        if( successVehicle!= null){
            successLabel.setText("Successfully processed vehicle: \n" + successVehicle);
        }

    }

    private static String formatTime(Duration duration) {

        double hours = duration.toHours();
        double minutes = duration.toMinutesPart();
        double seconds = duration.toSecondsPart();

        DecimalFormat decimalFormat = new DecimalFormat("00");
        String formattedHours = decimalFormat.format(hours);
        String formattedMinutes = decimalFormat.format(minutes);
        String formattedSeconds = decimalFormat.format(seconds);

        return String.format(" %s:%s:%s", formattedHours, formattedMinutes, formattedSeconds);

    }

    @FXML
    void handleSuspended(ActionEvent event) {

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("suspended-view.fxml"));
            Parent root = loader.load();

            // Create a new stage and set the scene
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Suspended Passengers");
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            Main.LOGGER.log(Level.SEVERE, "Exception occurred during handling suspended passengers", e);
        }
    }

}
