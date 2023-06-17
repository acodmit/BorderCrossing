package com.bordercrossing.simulation;

import com.bordercrossing.model.Vehicle;

public class Grid {
    private Vehicle[][] grid;
    private int rowCount;
    private int colCount;

    public Grid(int rows, int columns) {
        this.grid = new Vehicle[rows][columns];
        this.rowCount = rows;
        this.colCount = columns;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public boolean isOccupied(int row, int col) {

        return grid[row][col] != null;

    }

    public void setVehicle(Vehicle vehicle, int row, int column) {

        // Set vehicle to specified position
        if (isPositionValid(row, column)) {
            grid[row][column] = vehicle;
        }

    }

    public Vehicle getVehicle(int row, int column) {

        // Get vehicle reference if occupied
        if (isPositionValid(row, column)) {
            return grid[row][column];
        } else {
            return null;
        }

    }

    public void removeVehicle(int row, int column) {

        // Remove vehicle from position if occupied by setting null reference
        if (isPositionValid(row, column)) {
            grid[row][column] = null;
        }

    }

    private boolean isPositionValid(int row, int column) {

        // Check if requested position is valid, avoiding ArrayIndexOutOfBoundsException
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;

    }
}

