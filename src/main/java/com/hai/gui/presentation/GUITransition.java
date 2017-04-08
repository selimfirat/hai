package com.hai.gui.presentation;

import com.hai.gui.presentation.main.MainController;
import com.hai.gui.presentation.session.SessionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by mrsfy on 08-Apr-17.
 */
public class GUITransition {

    private static GUITransition _instance;

    private MainController mainController;
    private SessionController sessionController;

    private BorderPane mainPane;
    private BorderPane sessionPane;

    private GUITransition() {
        FXMLLoader mainLoader = initFXML("/main.fxml");
        FXMLLoader sessionLoader = initFXML("/session.fxml");

        try {
            mainPane = mainLoader.load();
            sessionPane = sessionLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainController = mainLoader.<MainController>getController();
        sessionController = sessionLoader.<SessionController>getController();
    }

    public void initPrimaryStage(Stage primaryStage) {
        Scene scene = new Scene(mainPane);

        primaryStage.setTitle("Hai Crossword Solver");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static GUITransition getInstance() {
        if (_instance == null)
            _instance = new GUITransition();

        return _instance;
    }

    private FXMLLoader initFXML(String fileURL) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        fileURL
                )
        );

        return loader;
    }

    public MainController getMainController() {
        return mainController;
    }

    public SessionController getSessionController() {
        return sessionController;
    }



    public BorderPane getMainPane() {
        return mainPane;
    }

    public BorderPane getSessionPane() {
        return sessionPane;
    }
}
