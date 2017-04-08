package com.hai.gui.model;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class PuzzleModel {

    private PuzzleDataModel puzzle_data;

    public PuzzleModel() {
    }

    public PuzzleDataModel getPuzzle_data() {
        return puzzle_data;
    }

    public void setPuzzle_data(PuzzleDataModel puzzle_data) {
        this.puzzle_data = puzzle_data;
    }

    @Override
    public String toString() {
        return "PuzzleModel{" +
                "puzzle_data=" + puzzle_data +
                '}';
    }
}
