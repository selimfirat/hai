package com.hai.gui.domain.csp;

import com.hai.gui.data.csp.Assignment;
import com.hai.gui.data.csp.Constraint;
import com.hai.gui.data.csp.Domain;
import com.hai.gui.data.csp.Variable;
import com.hai.rest.Candidate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class CSPSolver {

    public Assignment backtracingSearch(List<Variable> variableList, List<Constraint> constraintList, Map<String, Domain> domains) {
        return recursiveBacktracing(new Assignment(), getVariablesWithConstraintsAndDomains(variableList, constraintList, domains));
    }

    private Variable selectUnassignedVariable(Assignment assignment, Map<String, Variable> variables) {

        for (String varId : variables.keySet()) {
            if (!assignment.isAssigned(varId))
                return variables.get(varId);
        }

        return null;
    }

    private boolean isConsistent(Variable var, String newCandidate, Assignment assignment) {

        // NOTE: this approach is different from CSP Solving Algorithms paper.
        for (Constraint constraint : var.getConstraints()) {
            char c1, c2;
            if (var.getId().startsWith("D")) {
                c1 = newCandidate.charAt(constraint.getDownCharAt());
                c2 = assignment.getFields().get("A" + constraint.getAcrossNum()).charAt(constraint.getAcroosCharAt());
            }else {
                c1 = newCandidate.charAt(constraint.getAcroosCharAt());
                c2 = assignment.getFields().get("D" + constraint.getDownNum()).charAt(constraint.getDownCharAt());
            }
            if (c1 != c2)
                return false;
        }

        return true;
    }


    private Assignment recursiveBacktracing(Assignment assignment, Map<String, Variable> variables) {
        Variable var = selectUnassignedVariable(assignment, variables);

        if (var == null)
            return assignment;

        for (String candidate : var.getDomain().getCandidates().keySet()) {
            if (isConsistent(var, candidate, assignment)) {
                assignment.addField(var.getId(), candidate);

                variables.remove(var.getId());
                if (variables.isEmpty()) {
                    return assignment;
                }else if (recursiveBacktracing(assignment, variables) != null) {
                    return assignment;
                }

            }


        }

        return null;
    }


    private Map<String, Variable> getVariablesWithConstraintsAndDomains(List<Variable> variableList, List<Constraint> constraintList, Map<String, Domain> domains) {
        for (Variable variable : variableList) {
            for (Constraint constraint : constraintList) {
                if (("A" + constraint.getAcrossNum()).equals(variable.getId()) || (("D" + constraint.getDownNum()).equals(variable.getId())))
                    variable.getConstraints().add(constraint);
            }

            variable.setDomain(domains.get(variable.getId()));
        }

        return variableList.stream().collect(Collectors.toMap(Variable::getId, item -> item));
    }
/*
    // returns false if an inconsistency is found and true otherwise
    public boolean AC_3(List<Variable> variableList, List<Constraint> constraintList) {


        Map<String, Variable> variables = getVariablesWithConstraints(variableList, constraintList);
        Queue<Constraint> queue = new LinkedList<>(constraintList);

        while (!queue.isEmpty()) {
            Constraint constraint = queue.remove();
            Variable f = variables.get("A" + constraint.getAcrossNum());
            Variable s = variables.get("D" + constraint.getDownNum());

            if (revise(f, s, constraint)) {
                if (f.getDomain().getCandidates().size() == 0)
                    return false;

                for (Constraint c : f.getConstraints())
                    if (c.getDownNum() != constraint.getDownNum())
                        queue.add(c);
            }
        }

        return true;
    }
*/
    // true iff we revise the domain of Xi
    public boolean revise(Variable f, Variable s, Constraint c) {
        boolean revised = false;
        for (String aWord : f.getDomain().getCandidates().keySet()) {
            boolean found = false;
            for (String dWord : s.getDomain().getCandidates().keySet()) {
                if (aWord.charAt(c.getAcroosCharAt()) == dWord.charAt(c.getDownCharAt())) {
                    found  = true;
                }
            }
            if (!found) {
                revised = true;
                f.getDomain().getCandidates().remove(aWord);
            }

        }


        return revised;
    }
}
