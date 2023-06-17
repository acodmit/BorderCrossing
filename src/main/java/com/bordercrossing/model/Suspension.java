package com.bordercrossing.model;

import java.io.Serializable;

public class Suspension implements Serializable {
    private String passengerName;
    private String vehicleName;
    private boolean isDriver;
    private String issuerCheckpoint;
    private String suspensionReason;
    private boolean isVehicleSuspended;

    public Suspension(String passengerName, String vehicleName, boolean isDriver,
                      String issuerCheckpoint, String suspensionReason, boolean isVehicleSuspended) {
        this.passengerName = passengerName;
        this.vehicleName = vehicleName;
        this.isDriver = isDriver;
        this.issuerCheckpoint = issuerCheckpoint;
        this.suspensionReason = suspensionReason;
        this.isVehicleSuspended = isVehicleSuspended;
    }

    // Getters and setters

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getIssuerCheckpoint() {
        return issuerCheckpoint;
    }

    public void setIssuerCheckpoint(String issuerCheckpoint) {
        this.issuerCheckpoint = issuerCheckpoint;
    }

    public String getSuspensionReason() {
        return suspensionReason;
    }

    public void setSuspensionReason(String suspensionReason) {
        this.suspensionReason = suspensionReason;
    }

    public boolean isVehicleSuspended() {
        return isVehicleSuspended;
    }

    public void setVehicleSuspended(boolean vehicleSuspended) {
        isVehicleSuspended = vehicleSuspended;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Passenger: ").append(passengerName).append("\n");
        sb.append("Vehicle: ").append(vehicleName).append("\n");
        sb.append("Driver: ").append(isDriver ? "Yes" : "No").append("\n");
        sb.append("Issuer checkpoint: ").append(issuerCheckpoint).append("\n");
        sb.append("Reason: ").append(suspensionReason).append("\n");
        sb.append("Vehicle Suspended: ").append(isVehicleSuspended ? "Yes" : "No").append("\n");
        return sb.toString();
    }
}

