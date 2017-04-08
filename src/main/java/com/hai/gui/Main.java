package com.hai.gui;

import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.puzzle.nyt_puzzle.NYTPuzzlesRepository;
import com.hai.gui.presentation.GUITransition;
import com.hai.gui.presentation.main.MainController;
import com.hai.gui.presentation.session.SessionController;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jongo.Jongo;

import java.time.Instant;
import java.time.LocalDate;
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

        startSession(LocalDate.now().plusDays(-1));

        GUITransition.getInstance().initPrimaryStage(primaryStage);
    }

    private void startSession(LocalDate date) {

        Puzzle puzzle = nytPuzzlesRepository.findPuzzle(date);
        System.out.println(puzzle);

        sessionController.fillPuzzle(puzzle);
        mainController.startSession();
    }

}
