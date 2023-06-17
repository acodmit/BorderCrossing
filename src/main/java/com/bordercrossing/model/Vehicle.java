package com.bordercrossing.model;

import com.bordercrossing.controller.Main;
import com.bordercrossing.simulation.Grid;
import com.bordercrossing.simulation.Simulation;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Vehicle extends Thread{
    protected static final Object LOCK = new Object();
    protected static int PROCESSED_VEHICLES = 0;
    protected static int REMAINING_VEHICLES = 45;
    protected static Vehicle SUCCESS_VEHICLE = null;
    private String name;
    private int passengersCount;
    private List<Passenger> passengers;
    private Passenger driver;
    protected Simulation simulation;
    protected int currentRow = -1;
    protected int currentColumn = -1;
    private boolean inSimulationGrid = false;
    private boolean inRemainingGrid = false;
    private boolean policeProcessed = false;
    private boolean customsProcessed = false;
    protected Color color;
    protected boolean isSuspended = false;

    public Vehicle(String name, int passengersCount, Color color) {
        this.name = name;
        this.passengersCount = passengersCount;
        this.passengers = generatePassengers(passengersCount);
        this.driver = this.passengers.get(0);
        this.simulation = null;
        this.color = color;
    }
    public static Object getLOCK() {
        return LOCK;
    }

    public static Vehicle getSUCCESS_VEHICLE() {
        return SUCCESS_VEHICLE;
    }



    public static int getREMAINING_VEHICLES() {
        return REMAINING_VEHICLES;
    }

    public String getVehicleName() {
        return name;
    }

    public void setVehicleName(String name) {
        this.name = name;
    }

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }

    public List<Passenger> getPassengers(){
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Passenger getDriver() {
        return driver;
    }

    public void setDriver(Passenger driver) {
        this.driver = driver;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public boolean isInSimulationGrid() {
        return inSimulationGrid;
    }

    public void setInSimulationGrid(boolean inSimulationGrid) {
        this.inSimulationGrid = inSimulationGrid;
    }

    public boolean isInRemainingGrid() {
        return inRemainingGrid;
    }

    public void setInRemainingGrid(boolean inRemainingGrid) {
        this.inRemainingGrid = inRemainingGrid;
    }

    public boolean isPoliceProcessed() {
        return policeProcessed;
    }

    public void setPoliceProcessed(boolean policeProcessed) {
        this.policeProcessed = policeProcessed;
    }

    public boolean isCustomsProcessed() {
        return customsProcessed;
    }

    public void setCustomsProcessed(boolean customsProcessed) {
        this.customsProcessed = customsProcessed;
    }

    public Color getColor() {
        return color;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    @Override
    public String toString() {
        return "Name = " + name + "\n" +
                "Passengers = " + passengersCount + "\n" +
                "Driver = " + driver.getFirstName() + " " + driver.getLastName();
    }

    private List<Passenger> generatePassengers(int count) {
        List<Passenger> generatedPassengers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Passenger passenger = new Passenger();
            generatedPassengers.add(passenger);
        }
        return generatedPassengers;
    }

    @Override
    public void run() {

        while (true) {

            while(Simulation.PAUSED){

                synchronized (LOCK){
                    try{
                        LOCK.wait();
                    }catch (InterruptedException ex){
                        Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", ex);
                    }
                }
            }

            synchronized (LOCK) {

                if(isSuspended){

                    // Notify waiting vehicles
                    LOCK.notifyAll();

                    break;
                }

                if(inRemainingGrid) {

                    if(currentRow == 0 && currentColumn == 0){

                        moveToSimulationGrid();

                        // Notify waiting vehicles
                        LOCK.notifyAll();

                    } else if(currentRow % 4 == 0 && currentColumn == 0){

                         if(!simulation.remainingGrid.isOccupied(currentRow - 2,currentColumn)){

                            // Move vehicle two steps up
                            for (int step = 0; step < 2; step++) {

                                move(simulation.remainingGrid, DirectionEnum.UP);

                            }

                         }

                        // Notify waiting vehicles
                        LOCK.notifyAll();

                    } else if( currentRow % 4 != 0 && currentColumn == 18){

                        if(!simulation.remainingGrid.isOccupied(currentRow - 2,currentColumn)){

                            // Move vehicle two steps up
                            for (int step = 0; step < 2; step++) {

                                move(simulation.remainingGrid, DirectionEnum.UP);

                            }

                        }

                        // Notify waiting vehicles
                        LOCK.notifyAll();

                    }else{

                        if( currentRow % 4 == 0){

                            if(!simulation.remainingGrid.isOccupied(currentRow ,currentColumn - 2)) {

                                // Move vehicle two steps left
                                for (int step = 0; step < 2; step++) {

                                    move(simulation.remainingGrid, DirectionEnum.LEFT);

                                }

                                // Notify waiting vehicles
                                LOCK.notifyAll();
                            }
                        }else if( currentRow % 4 != 0){

                            if(!simulation.remainingGrid.isOccupied(currentRow,currentColumn + 2)){

                                // Move vehicle two steps right
                                for (int step = 0; step < 2; step++) {

                                    move(simulation.remainingGrid, DirectionEnum.RIGHT);

                                }

                            }

                            // Notify waiting vehicles
                            LOCK.notifyAll();
                        }
                    }

                } else if (inSimulationGrid) {

                    // If vehicle is not police processed
                    if (!policeProcessed) {

                        // If vehicle is currently waiting in line to be processed
                        if ((currentRow == 4 && currentColumn < 9) && !simulation.simulationGrid.isOccupied(currentRow, currentColumn + 2)) {

                            // Move vehicle two steps right
                            for (int step = 0; step < 2; step++) {

                                move(simulation.simulationGrid, DirectionEnum.RIGHT);

                            }

                            // Notify waiting vehicles
                            LOCK.notifyAll();


                        } else if (currentRow == 4 && currentColumn == 9) {

                            moveToBorderCheckpoint();

                            // Notify waiting vehicles
                            LOCK.notifyAll();

                        }

                    } else if (policeProcessed && !customsProcessed) {

                        moveToCustomsCheckpoint();

                        // Notify waiting vehicles
                        LOCK.notifyAll();

                    } else if (customsProcessed) {

                        // Move vehicle two steps right
                        for (int step = 0; step < 4; step++) {
                            move(simulation.simulationGrid, DirectionEnum.RIGHT);
                        }

                        SUCCESS_VEHICLE = this;

                        // Notify waiting vehicles
                        LOCK.notifyAll();

                        break;

                    }

                }
                // Notify waiting vehicles
                LOCK.notifyAll();

                try {
                    // Wait until the next destination is available
                    LOCK.wait();
                } catch (InterruptedException e) {
                    Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", e);
                }

            }
        }

        setInSimulationGrid(false);
        simulation.simulationGrid.removeVehicle(currentRow, currentColumn);
        processed();
    }

    protected void moveToSimulationGrid(){

        // Move vehicle to the simulation grid if possible
        if (!simulation.simulationGrid.isOccupied(4, 1)) {

            // Remove the vehicle from the current position
            simulation.remainingGrid.removeVehicle(currentRow, currentColumn);
            setInRemainingGrid(false);

            // Set simulation grid entry position
            currentRow = 4;
            currentColumn = 1;

            // Update the vehicle's position
            simulation.simulationGrid.setVehicle(this, currentRow, currentColumn);
            setInSimulationGrid(true);

            REMAINING_VEHICLES--;

        }
    }

    protected void moveToBorderCheckpoint(){

        if(!simulation.simulationGrid.isOccupied(2,12)){

            move(simulation.simulationGrid,DirectionEnum.RIGHT);

            // Move vehicle two steps up
            for (int step = 0; step < 2; step++) {
                move(simulation.simulationGrid,DirectionEnum.UP);
            }

            // Move vehicle two steps right
            for (int step = 0; step < 2; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }

        }else if(!simulation.simulationGrid.isOccupied(4,12)){

            // Move vehicle three steps right
            for (int step = 0; step < 3; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }
        }

    }

    protected void moveToCustomsCheckpoint(){

        if((currentRow == 2 && currentColumn == 12) && !simulation.simulationGrid.isOccupied(4,15)){

            // Move vehicle three steps right
            for (int step = 0; step < 3; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }

            // Move vehicle two steps down
            for (int step = 0; step < 2; step++) {
                move(simulation.simulationGrid,DirectionEnum.DOWN);
            }

        }else if ((currentRow == 4 && currentColumn == 12) && !simulation.simulationGrid.isOccupied(4,15)){

            // Move vehicle four steps right
            for (int step = 0; step < 3; step++) {
                move(simulation.simulationGrid,DirectionEnum.RIGHT);
            }

        }

    }

    protected void move(Grid grid, DirectionEnum direction) {

        // Remove the vehicle from the current position
        grid.removeVehicle(currentRow, currentColumn);

        // Get destination depending on direction
        if(direction.equals(DirectionEnum.RIGHT)){
            currentColumn++;
        } else if(direction.equals(DirectionEnum.LEFT)){
            currentColumn--;
        }else if(direction.equals(DirectionEnum.UP)){
            currentRow--;
        }else {
            currentRow++;
        }

        // Update the vehicle's position
        grid.setVehicle(this, currentRow, currentColumn);

        // Wait for 0.1 seconds before moving to the next step
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Main.LOGGER.log(Level.SEVERE, "InterruptedException occurred during vehicle processing", e);
        }

    }

    private static void processed(){

        PROCESSED_VEHICLES++;

        if(PROCESSED_VEHICLES >= 50){

            Simulation.FINISHED = true;

        }
    }

}
