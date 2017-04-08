package com.hai.gui.data.puzzle;

import java.util.List;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class CluesContainer {

    private List<Clue> A;
    private List<Clue> D;

    public CluesContainer() {
    }

    public List<Clue> getA() {
        return A;
    }

    public void setA(List<Clue> a) {
        A = a;
    }

    public List<Clue> getD() {
        return D;
    }

    public void setD(List<Clue> d) {
        D = d;
    }

    @Override
    public String toString() {
        return "CluesContainer{" +
                "A=" + A +
                ", D=" + D +
                '}';
    }
}
