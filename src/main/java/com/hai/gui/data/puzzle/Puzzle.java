package com.hai.gui.data.puzzle;

import java.util.Arrays;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class Puzzle {

    private CluesContainer clues;
    private int[] layout;
    private String[] answers;

    public Puzzle() {
    }

    public CluesContainer getClues() {
        return clues;
    }

    public void setClues(CluesContainer clues) {
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
        return "Puzzle{" +
                "clues=" + clues +
                ", layout=" + Arrays.toString(layout) +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
