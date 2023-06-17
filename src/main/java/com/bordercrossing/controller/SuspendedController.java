package com.bordercrossing.controller;

import com.bordercrossing.model.Suspension;
import com.bordercrossing.record.SuspensionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SuspendedController implements Initializable {
    @FXML
    private ListView<String> suspendedListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> suspendedEntries = new ArrayList<>();

        List<Suspension> suspensions = SuspensionManager.getSuspensions();

        for(Suspension suspension : suspensions)
            suspendedEntries.add(suspension.toString());

        if(!suspendedEntries.isEmpty()){

            // Set the list of suspended entries to the ListView
            suspendedListView.getItems().addAll(suspendedEntries);

        }
    }

}


