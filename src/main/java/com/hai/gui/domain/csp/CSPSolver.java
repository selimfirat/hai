package com.hai.gui.domain.csp;

import com.hai.gui.data.csp.Constraint;
import com.hai.gui.data.csp.Variable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class CSPSolver {

    // returns false if an inconsistency is found and true otherwise
    public boolean AC_3(List<Variable> variableList, List<Constraint> constraintList) {
        Queue<Constraint> queue = new LinkedList<>(constraintList);
        Map<String, Variable> variables = variableList.stream().collect(Collectors.toMap(Variable::getId, item -> item));

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

    // true iff we revise the domain of Xi
    public boolean revise(Variable f, Variable s, Constraint c) {
        boolean revised = false;
        for (String aWord : f.getDomain().getCandidates().keySet()) {
            // TODO: do something about score here.
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
