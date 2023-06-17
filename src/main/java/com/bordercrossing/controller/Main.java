package com.bordercrossing.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static Logger LOGGER = Logger.getLogger("LOGGER");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to BorderCrossing Simulation App!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try{
            LOGGER.addHandler(new FileHandler("logs.log",false));
        }catch (IOException ex){
            LOGGER.log(Level.WARNING, ex.fillInStackTrace().toString(), ex);
        }
        launch();
    }
}