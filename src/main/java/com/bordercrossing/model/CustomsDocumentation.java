package com.bordercrossing.model;

import java.io.Serializable;

public class CustomsDocumentation implements Serializable {
    private String issuer;
    private String passenger;
    private String vehicle;
    private String declaredWeight;
    private String truckWeight;
    private String illegalItem;

    public CustomsDocumentation(String issuer, String passenger, String vehicle) {
        this.issuer = issuer;
        this.passenger = passenger;
        this.vehicle = vehicle;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    public String getDeclaredWeight() {
        return declaredWeight;
    }

    public void setDeclaredWeight(String declaredWeight) {
        this.declaredWeight = declaredWeight;
    }

    public String getTruckWeight() {
        return truckWeight;
    }

    public void setTruckWeight(String truckWeight) {
        this.truckWeight = truckWeight;
    }

    public String getIllegalItem() {
        return illegalItem;
    }

    public void setIllegalItem(String illegalItem) {
        this.illegalItem = illegalItem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Customs Documentation:\n");
        sb.append("Issuer: ").append(issuer).append("\n");

        if (passenger != null) {
            sb.append("Passenger: ").append(passenger).append("\n");
            sb.append("Illegal Item: ").append(illegalItem).append("\n");
        } else if (vehicle != null) {
            sb.append("Vehicle: ").append(vehicle).append("\n");
            sb.append("Declared weight: ").append(declaredWeight).append("\n");
            sb.append("Truck Weight: ").append(truckWeight).append("\n");
        }

        return sb.toString();
    }
}

