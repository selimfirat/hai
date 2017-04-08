package com.hai.gui.model;

import java.util.List;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class ClueTypeDataModel {

    private List<ClueDataModel> A;
    private List<ClueDataModel> D;

    public ClueTypeDataModel() {
    }

    public List<ClueDataModel> getA() {
        return A;
    }

    public void setA(List<ClueDataModel> a) {
        A = a;
    }

    public List<ClueDataModel> getD() {
        return D;
    }

    public void setD(List<ClueDataModel> d) {
        D = d;
    }

    @Override
    public String toString() {
        return "ClueTypeDataModel{" +
                "A=" + A +
                ", D=" + D +
                '}';
    }
}
