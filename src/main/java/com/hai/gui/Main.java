package com.hai.gui;

import com.hai.gui.data.DB;
import com.hai.gui.data.csp.Assignment;
import com.hai.gui.data.csp.Constraint;
import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.csp.Variable;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.data.puzzle.nyt_puzzle.NYTPuzzlesRepository;
import com.hai.gui.domain.csp.CSPFactory;
import com.hai.gui.domain.csp.CSPSolver;
import com.hai.gui.domain.merger.Merger;
import com.hai.gui.presentation.GUITransition;
import com.hai.gui.presentation.MrsHai;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.*;


/**
 * Created by mrsfy on 27-Mar-17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Logger LOG = Logger.getLogger(Main.class.getName());
    private NYTPuzzlesRepository nytPuzzlesRepository = NYTPuzzlesRepository.getInstance();
    private MainController mainController = GUITransition.getInstance().getMainController();
    private SessionController sessionController = GUITransition.getInstance().getSessionController();
    private MrsHai mrsHai = new MrsHai();

    @Override
    public void start(Stage primaryStage) throws Exception {

        //HTTPServer.getInstance().start();
        Jongo jongo = new Jongo(new MongoClient(new MongoClientURI("mongodb://huseyin:huseyin123@ds153239.mlab.com:53239/huseyin")).getDB("huseyin"));
        NYTPuzzlesRepository.getInstance().setJongo(jongo);

        GUITransition.getInstance().getMainController().setDateChangeListener(new ChangeListener<LocalDate>() {
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                startSession(newValue);
            }
        });

        DB.initConnection("jdbc:sqlserver://haibilkenthai.cwomk2hiytzy.us-east-1.rds.amazonaws.com:1433;user=bilkenthai;password=bilkenthai;database=bilkenthai");

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


        Logger.getLogger("").addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {

                if (record.getLevel() == MrsHai.LEVEL) {
                    final LogRecord finalRecord = record;
                    new Thread(() -> mrsHai.speak(finalRecord.getMessage())).start();
                }
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        });

        LOG.log(MrsHai.LEVEL, "Hello! I am Mrs Hai.");
        LOG.log(MrsHai.LEVEL, "I will tell you the story of my crossword solving adventure while I am solving it.");
        LOG.log(MrsHai.LEVEL, "Please pick a date of New York Times mini puzzle to be solved.");
    }

    private void startSession(LocalDate date) {

        Puzzle puzzle = nytPuzzlesRepository.findPuzzle(date);
        System.out.println(puzzle);

        sessionController.fillPuzzle(puzzle);

        CSPFactory cspFactory = new CSPFactory(puzzle);
        List<Variable> variableList = cspFactory.generateVariables();
        List<Constraint> constraintList = cspFactory.generateConstraints();

        sessionController.initCSPGraph(variableList, constraintList);

        mainController.startSession();

        new Thread(() -> {
            Merger merger = new Merger(puzzle, date);
            Map<String, Domain> domains = merger.getDomains();

            CSPSolver cspSolver = new CSPSolver();

            Assignment assignment = cspSolver.solve(cspSolver.getVariablesWithConstraintsAndDomains(variableList, constraintList, domains), new Assignment());
            System.out.println(assignment);

        }).start();

    }

}
