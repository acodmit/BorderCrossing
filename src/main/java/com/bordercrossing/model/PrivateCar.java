package com.bordercrossing.model;

import javafx.scene.paint.Color;

import java.util.Random;

public class PrivateCar extends Vehicle{

    private static int CAR_ID = 0;
    public PrivateCar(){
        super("Car" + CAR_ID,new Random().nextInt(5) + 1,Color.YELLOW);
        CAR_ID++;
    }

}
