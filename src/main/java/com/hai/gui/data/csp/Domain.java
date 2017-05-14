package com.hai.gui.data.csp;


import com.hai.gui.data.candidate.Candidate;
import javafx.collections.transformation.SortedList;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Domain {

    // word -> score
    private TreeSet<Candidate> candidates = new TreeSet<>((o1, o2) -> o1.getScore() - o2.getScore() >= 0 ? 1 : -1);

    public Domain() {

    }

    public Domain(Map<String, Double> candidates) {
        candidates.forEach((word, score) -> {
            this.candidates.add(new Candidate(word, score));
        });
    }

    public TreeSet<Candidate> getCandidates() {
        return candidates;
    }


    public Domain copy() {
        Domain domain = new Domain();
        domain.getCandidates().addAll(candidates);

        return domain;
    }
}
