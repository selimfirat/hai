package com.hai.gui.presentation.main;

import com.hai.gui.presentation.GUITransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Created by mrsfy on 03-Apr-17.
 */
public class MainController {

    @FXML
    BorderPane mainBorderPane;

    @FXML
    DatePicker datePicker;

    public void startSession() {
        mainBorderPane.setCenter(GUITransition.getInstance().getSessionPane());
    }

    public void setDateChangeListener(ChangeListener<LocalDate> changeListener) {
        datePicker.valueProperty().addListener(changeListener);
    }

    public void setDayCellFactory(Callback<DatePicker, DateCell> dayCellFactory) {
        datePicker.setDayCellFactory(dayCellFactory);
    }

}