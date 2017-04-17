package com.hai.gui.data.csp;

/**
 * Created by mrsfy on 17-Apr-17.
 */
public class Constraint {

    private int acrossNum;
    private int downNum;
    private int acroosCharAt;
    private int downCharAt;

    public Constraint(int acrossNum, int downNum, int acroosCharAt, int downCharAt) {
        this.acrossNum = acrossNum;
        this.downNum = downNum;
        this.acroosCharAt = acroosCharAt;
        this.downCharAt = downCharAt;
    }

    public int getAcrossNum() {
        return acrossNum;
    }

    public void setAcrossNum(int acrossNum) {
        this.acrossNum = acrossNum;
    }

    public int getDownNum() {
        return downNum;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }

    public int getAcroosCharAt() {
        return acroosCharAt;
    }

    public void setAcroosCharAt(int acroosCharAt) {
        this.acroosCharAt = acroosCharAt;
    }

    public int getDownCharAt() {
        return downCharAt;
    }

    public void setDownCharAt(int downCharAt) {
        this.downCharAt = downCharAt;
    }
}
