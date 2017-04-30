package com.hai.gui.data.csp;


import com.hai.gui.domain.merger.rest_client.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Domain {

    // word -> score
    private Map<String, Double> candidates = new HashMap<>();

    public Domain() {
    }

    public Domain(List<Candidate> candidates) {

    }

    public Map<String, Double> getCandidates() {
        return candidates;
    }

    public void setCandidates(Map<String, Double> candidates) {
        this.candidates = candidates;
    }
}
