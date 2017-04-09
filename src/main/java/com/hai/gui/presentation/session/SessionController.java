package com.hai.gui.presentation.session;

import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.CluesContainer;
import com.hai.gui.data.puzzle.Puzzle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by mrsfy on 03-Apr-17.
 */
public class SessionController {

    @FXML
    private com.hai.gui.presentation.session.GUIPuzzle GUIPuzzle;

    @FXML
    private GridPane acrossGrid;

    @FXML
    private GridPane downGrid;


    public void fillPuzzle(Puzzle puzzle) {
        acrossGrid.getChildren().remove(1, acrossGrid.getChildren().size());
        downGrid.getChildren().remove(1, downGrid.getChildren().size());

        GUIPuzzle.fillPuzzleAll(puzzle);

        CluesContainer clues = puzzle.getClues();

        int i = 2, j = 2;
        for (Clue clue : clues.getA()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            clueNum.setStyle("-fx-font-weight: bold; -fx-padding: 0.5em 0 0 1em;");

            Label clueValue = new Label(clue.getValue());


            acrossGrid.addRow(i++, clueNum, clueValue);
        }

        for (Clue clue : clues.getD()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            clueNum.setStyle("-fx-font-weight: bold; -fx-padding: 0.5em 0 0 1em");
            Label clueValue = new Label(clue.getValue());

            downGrid.addRow(j++, clueNum, clueValue);
        }

    }



}
