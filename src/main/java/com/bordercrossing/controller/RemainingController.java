package com.bordercrossing.controller;

import com.bordercrossing.model.Vehicle;
import com.bordercrossing.simulation.Simulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class RemainingController implements Initializable {
    private final static int NUM_ROWS = 10;
    private final static int NUM_COLS = 19;
    private final double RECT_SIZE = 30.0;
    @FXML
    private HBox gridHBox;
    @FXML
    private Label remainingLabel;
    @FXML
    private VBox mainVBox;
    private GridPane gridPane;
    private Simulation simulation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Generate remaining grid
        gridPane = generateRemainingGrid();
        gridHBox.getChildren().add(gridPane);

        // Generate remaining label timeline
        Timeline remainingLabelTimeline = generateRemainingLabelTimeline();
        remainingLabelTimeline.play();

    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    private GridPane generateRemainingGrid() {

        GridPane gridPane = new GridPane();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Rectangle rectangle = new Rectangle(RECT_SIZE, RECT_SIZE);
                Label label = new Label();

                // Set the fill color based on the row and column positions
                if (row % 2 == 0) {
                        rectangle.setFill(Color.GRAY);
                } else {
                    if ((row % 4 == 3 && col == 0) || (row % 4 == 1 && col == 18)) {
                        rectangle.setFill(Color.GRAY);
                    } else {
                        rectangle.setFill(Color.LIGHTGRAY);
                    }
                }

                // Add Rectangle to the GridPane
                gridPane.add(rectangle, col, row);

                // Set the alignment of the stack pane to center in the grid cell
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);

                // Add Label to the GridPane
                gridPane.add(label, col, row);
            }
        }

        gridPane.setGridLinesVisible(true);
        return gridPane;

    }


    public void updateGrid(){

        // Generate a timeline to update remaining grid
        Timeline gridTimeline = GridUpdater.generateGridTimeline(gridPane,
                simulation.remainingGrid,50);
        gridTimeline.play();

    }


    public Timeline generateRemainingLabelTimeline() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), event -> {
                    updateRemainingLabel();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;

    }

    private void updateRemainingLabel() {

        // Generate an updated label text showing number of remaining vehicles
        String labelText = "Vehicles waiting to be processed: " + Vehicle.getREMAINING_VEHICLES();

        // Update the label text with the number of remaining vehicles
        remainingLabel.setText(labelText);
    }

}

