package com.hai.gui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;

/**
 * Created by mrsfy on 03-Apr-17.
 */
public class MainController {

    @FXML
    BorderPane mainBorderPane;

    @FXML
    DatePicker datePicker;

    public void startSession(BorderPane session) {
        mainBorderPane.setCenter(session);
    }

}