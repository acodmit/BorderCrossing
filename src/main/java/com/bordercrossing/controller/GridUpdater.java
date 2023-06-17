package com.bordercrossing.controller;

import com.bordercrossing.simulation.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridUpdater {

    public static Timeline generateGridTimeline(GridPane gridPane, Grid grid, int interval) {

        // Generate a timeline that triggers the grid updating method
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(interval), event -> {
            updateGrid(gridPane, grid);
        }));

        // Set the cycle count to INDEFINITE so the timeline keeps running indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);

        return timeline;
    }

    public static void updateGrid(GridPane gridPane, Grid grid) {

        for (Node node : gridPane.getChildren()) {

            if(node instanceof  Rectangle){

                int row = GridPane.getRowIndex(node);
                int column = GridPane.getColumnIndex(node);

                Rectangle rectangle = (Rectangle) node;

                // Check the corresponding position in the grid
                if (grid.getVehicle(row, column) != null) {

                    if (rectangle.getFill().equals(Color.GRAY)) {

                        // Paint the rectangle with the vehicle's color
                        rectangle.setFill(grid.getVehicle(row, column).getColor());

                    }

                } else if(grid.getVehicle(row,column) == null &&
                        (rectangle.getFill().equals(Color.YELLOW) ||
                        rectangle.getFill().equals(Color.ORANGE) ||
                        rectangle.getFill().equals(Color.RED))){

                    // Reset the rectangle color to gray when no vehicle is present
                    rectangle.setFill(Color.GRAY);
                }
            }
        }
    }

}
