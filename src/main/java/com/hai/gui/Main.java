package com.hai.gui;

import com.hai.gui.model.PuzzleDataModel;
import com.hai.gui.model.PuzzleModel;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Created by mrsfy on 27-Mar-17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/main.fxml"
                )
        );
        FXMLLoader sessionLoader = new FXMLLoader(
                getClass().getResource(
                        "/session.fxml"
                )
        );

        BorderPane session = sessionLoader.load();
        BorderPane main =  loader.load();

        MainController mainController =
                loader.<MainController>getController();
        SessionController sessionController = sessionLoader.<SessionController>getController();


        Jongo jongo = new Jongo(new MongoClient(new MongoClientURI("mongodb://huseyin:huseyin123@ds153239.mlab.com:53239/huseyin")).getDB("huseyin"));

        MongoCollection puzzlesCol = jongo.getCollection("puzzles");
        MongoCursor<PuzzleModel> puzzles = puzzlesCol.find().as(PuzzleModel.class);

        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());
        System.out.println(puzzles.next().getPuzzle_data());

        PuzzleDataModel puzzleDataModel = puzzles.next().getPuzzle_data();
        System.out.println(puzzleDataModel);

        sessionController.fillPuzzle(puzzleDataModel);
        mainController.startSession(session);


        Scene scene = new Scene(main);


        primaryStage.setTitle("Hai Crossword Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void fillPuzzle() {

    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
