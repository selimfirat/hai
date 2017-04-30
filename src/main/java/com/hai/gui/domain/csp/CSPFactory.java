package com.hai.gui.domain.csp;

import com.hai.gui.data.csp.Constraint;
import com.hai.gui.data.csp.Variable;
import com.hai.gui.data.cytoscape_graph.Edge;
import com.hai.gui.data.cytoscape_graph.Element;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsfy on 17-Apr-17.
 */
public class CSPFactory {

    private Puzzle puzzle;
    public CSPFactory(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public List<Constraint> generateConstraints() {

        List<Constraint> res = new ArrayList<>();

        for (Clue clueD : puzzle.getClues().getD()) {
            for (int i = clueD.getClueStart(); i <= clueD.getClueEnd(); i += 5) {
                for (Clue clueA : puzzle.getClues().getA()) {
                    if (clueA.getClueStart() <= i && clueA.getClueEnd() >= i) {
                        Constraint constraint = new Constraint(clueA.getClueNum(), clueD.getClueNum(), i % 5 - clueA.getClueStart() % 5, i / 5 - clueD.getClueStart() / 5);

                        res.add(constraint);

                        break;
                    }
                }
            }
        }

        return res;
    }

    public List<Variable> generateVariables() {

        List<Variable> result = new ArrayList<>();

        for (Clue clue : puzzle.getClues().getA())
            result.add(new Variable("A" + clue.getClueNum()));

        for (Clue clue : puzzle.getClues().getD())
            result.add(new Variable("D" + clue.getClueNum()));

        return result;
    }
}
