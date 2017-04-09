package com.hai.gui;

import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.puzzle.nyt_puzzle.NYTPuzzlesRepository;
import com.hai.gui.presentation.GUITransition;
import com.hai.gui.presentation.main.MainController;
import com.hai.gui.presentation.session.SessionController;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jongo.Jongo;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Date;


/**
 * Created by mrsfy on 27-Mar-17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    NYTPuzzlesRepository nytPuzzlesRepository = NYTPuzzlesRepository.getInstance();
    MainController mainController = GUITransition.getInstance().getMainController();
    SessionController sessionController = GUITransition.getInstance().getSessionController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Jongo jongo = new Jongo(new MongoClient(new MongoClientURI("mongodb://huseyin:huseyin123@ds153239.mlab.com:53239/huseyin")).getDB("huseyin"));
        NYTPuzzlesRepository.getInstance().setJongo(jongo);

        GUITransition.getInstance().getMainController().setDateChangeListener(new ChangeListener<LocalDate>() {
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                startSession(newValue);
            }
        });

        GUITransition.getInstance().getMainController().setDayCellFactory(new Callback<DatePicker, DateCell>() {

            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable non scraped puzzles' days.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY
                                || item.isBefore(LocalDate.of(2017, 2, 14))
                                || item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }

                    ;
                };
            }
        });

        GUITransition.getInstance().initPrimaryStage(primaryStage);
    }

    private void startSession(LocalDate date) {

        Puzzle puzzle = nytPuzzlesRepository.findPuzzle(date);
        System.out.println(puzzle);

        sessionController.fillPuzzle(puzzle);
        mainController.startSession();
    }

}
