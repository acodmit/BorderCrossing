package com.bordercrossing.model;

import javafx.scene.paint.Color;

import java.util.Random;

public class Bus extends Vehicle{
    private static int BUS_ID = 0;
    public Bus() {
        super("Bus" + BUS_ID,new Random().nextInt(52) + 1,Color.ORANGE);
        BUS_ID++;
    }

}
