package com.hai.gui;

import com.hai.gui.model.ClueDataModel;
import com.hai.gui.model.ClueTypeDataModel;
import com.hai.gui.model.PuzzleDataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by mrsfy on 03-Apr-17.
 */
public class SessionController {

    @FXML
    private Puzzle puzzle;

    @FXML
    private GridPane acrossGrid;

    @FXML
    private GridPane downGrid;


    public void fillPuzzle(PuzzleDataModel puzzleDataModel) {
        puzzle.fillPuzzleAll(puzzleDataModel);

        ClueTypeDataModel clues = puzzleDataModel.getClues();

        int i = 1, j = 1;
        for (ClueDataModel clue : clues.getA()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            Label clueValue = new Label(clue.getValue());

            acrossGrid.addRow(i++, clueNum, clueValue);
        }

        for (ClueDataModel clue : clues.getD()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            Label clueValue = new Label(clue.getValue());

            downGrid.addRow(j++, clueNum, clueValue);
        }

    }



}
