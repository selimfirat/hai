package com.hai.gui.data.csp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsfy on 17-Apr-17.
 */
public class Variable {

    private String id;
    private List<Constraint> constraints = new ArrayList<>();
    private Domain domain = new Domain();

    public Variable() {
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

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
}
