package com.hai.gui;

import com.hai.gui.Puzzle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

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

        BorderPane main = (BorderPane) loader.load();

        MainController mainController =
                loader.<MainController>getController();

        /*
        BorderPane session = FXMLLoader.load(this.getClass().getResource("/session.fxml"));


        mainController.getMainBorderPane().setCenter(session);

        */


        Scene scene = new Scene(main);


        primaryStage.setTitle("Hai Crossword Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
