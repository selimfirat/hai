package com.hai.gui.domain.merger.rest_client;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Candidate {
    private String word;
    private double score;

    public Candidate(String word, double score) {
        this.word = word;
        this.score = score;
    }

    public Candidate() {
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
