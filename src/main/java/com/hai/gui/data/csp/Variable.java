package com.hai.gui.data.csp;

import com.hai.gui.data.candidate.Candidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrsfy on 17-Apr-17.
 */
public class Variable {

    private String id;
    private Map<String, Constraint> constraints = new HashMap<>();
    private Domain domain = new Domain();

    public Variable() {
    }

    public Variable copy() {

        Variable newVariable = new Variable();
        newVariable.setDomain(domain.copy());
        newVariable.setConstraints(new HashMap<>(constraints));

        return newVariable;
    }

    public Variable(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public void setConstraints(Map<String,Constraint> constraints) {
        this.constraints = constraints;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }
}
