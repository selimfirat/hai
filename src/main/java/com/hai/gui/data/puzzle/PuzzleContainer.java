package com.hai.gui.data.puzzle;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class PuzzleContainer {

    private Puzzle puzzle_data;

    public PuzzleContainer() {
    }

    public Puzzle getPuzzle_data() {
        return puzzle_data;
    }

    public void setPuzzle_data(Puzzle puzzle_data) {
        this.puzzle_data = puzzle_data;
    }

    @Override
    public String toString() {
        return "PuzzleContainer{" +
                "puzzle_data=" + puzzle_data +
                '}';
    }
}
