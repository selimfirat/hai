package com.hai.gui;

/**
 * Created by mrsfy on 04-Apr-17.
 */
public class Word {

    private String word;
    private double score;

    public Word(String word, double score) {
        this.word = word;
        this.score = score;
    }

    public Word(String word) {
        this.word = word;
        this.score = 0;
    }

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
