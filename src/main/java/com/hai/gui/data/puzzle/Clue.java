package com.hai.gui.data.puzzle;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class Clue {

    private int clueNum;
    private int clueStart;
    private String value;
    private int clueEnd;

    public Clue() {
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getClueStart() {
        return clueStart;
    }

    public void setClueStart(int clueStart) {
        this.clueStart = clueStart;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getClueEnd() {
        return clueEnd;
    }

    public void setClueEnd(int clueEnd) {
        this.clueEnd = clueEnd;
    }

    @Override
    public String toString() {
        return "Clue{" +
                "clueNum=" + clueNum +
                ", clueStart=" + clueStart +
                ", value='" + value + '\'' +
                ", clueEnd=" + clueEnd +
                '}';
    }

    public int getAnswerLength(boolean isAcross)
    {
        if (isAcross) return clueEnd - clueStart + 1;
        else return (clueEnd - clueStart) / 5 + 1;
    }
}
