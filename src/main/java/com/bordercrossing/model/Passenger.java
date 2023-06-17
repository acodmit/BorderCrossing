package com.bordercrossing.model;

import java.util.Random;

public class Passenger {
    private static int PASSENGER_ID = 0;
    private String firstName;
    private String lastName;
    private boolean hasValidDocuments;
    private boolean hasLaguage;
    private boolean hasUnallowedItem;
    private boolean isSuspended;

    public Passenger(){
        this.firstName = "FirstName" + PASSENGER_ID;
        this.lastName = "LastName" + PASSENGER_ID;

        Random random = new Random();
        if(random.nextDouble() < 0.97)
            this.hasValidDocuments = true;
        else
            this.hasValidDocuments = false;

        if(random.nextDouble() < 0.7){
            this.hasLaguage = true;
            if(random.nextDouble() < 0.1)
                this.hasUnallowedItem = true;
            else
                this.hasUnallowedItem = false;
        }else{
            this.hasLaguage = false;
            this.hasUnallowedItem = false;
        }
        PASSENGER_ID++;

        this.isSuspended = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean hasValidDocuments() {
        return hasValidDocuments;
    }

    public void setHasValidDocuments(boolean hasValidDocuments) {
        this.hasValidDocuments = hasValidDocuments;
    }

    public boolean hasLaguage() {
        return hasLaguage;
    }

    public void setHasLaguage(boolean hasLaguage) {
        this.hasLaguage = hasLaguage;
    }

    public boolean hasIllegalItem() {
        return hasUnallowedItem;
    }

    public void setHasUnallowedItem(boolean hasUnallowedItem) {
        this.hasUnallowedItem = hasUnallowedItem;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }
}
