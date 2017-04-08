package com.hai.gui.model;

import java.util.Arrays;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class PuzzleDataModel {

    private ClueTypeDataModel clues;
    private int[] layout;
    private String[] answers;

    public PuzzleDataModel() {
    }

    public ClueTypeDataModel getClues() {
        return clues;
    }

    public void setClues(ClueTypeDataModel clues) {
        this.clues = clues;
    }

    public int[] getLayout() {
        return layout;
    }

    public void setLayout(int[] layout) {
        this.layout = layout;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "PuzzleDataModel{" +
                "clues=" + clues +
                ", layout=" + Arrays.toString(layout) +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
