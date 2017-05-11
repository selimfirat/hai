package com.hai.gui.presentation.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hai.gui.data.csp.Constraint;
import com.hai.gui.data.csp.Variable;
import com.hai.gui.data.cytoscape_graph.Edge;
import com.hai.gui.data.cytoscape_graph.Element;
import com.hai.gui.data.cytoscape_graph.GraphData;
import com.hai.gui.data.cytoscape_graph.Node;
import com.hai.gui.data.puzzle.Clue;
import com.hai.gui.data.puzzle.CluesContainer;
import com.hai.gui.data.puzzle.Puzzle;
import com.hai.gui.presentation.session.csp.CSPGraph;
import com.hai.gui.presentation.session.puzzle.GUIPuzzle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Created by mrsfy on 03-Apr-17.
 */
public class SessionController {

    @FXML
    private GUIPuzzle GUIPuzzle;

    @FXML
    private GUIPuzzle GUIPuzzle2;

    @FXML
    private GridPane acrossGrid;

    @FXML
    private GridPane downGrid;

    @FXML
    private GridPane acrossGrid2;

    @FXML
    private GridPane downGrid2;

    @FXML
    private CSPGraph cspGraph;


    public void fillPuzzle(Puzzle puzzle) {
        acrossGrid.getChildren().remove(1, acrossGrid.getChildren().size());
        downGrid.getChildren().remove(1, downGrid.getChildren().size());

        GUIPuzzle.fillPuzzleAll(puzzle);

        CluesContainer clues = puzzle.getClues();

        int i = 2, j = 2;
        for (Clue clue : clues.getA()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            clueNum.setStyle("-fx-font-weight: bold; -fx-padding: 0.5em 0 0 1em;");

            Label clueValue = new Label(clue.getValue());


            acrossGrid.addRow(i++, clueNum, clueValue);
        }

        for (Clue clue : clues.getD()) {

            Label clueNum = new Label(String.valueOf(clue.getClueNum()));
            clueNum.setStyle("-fx-font-weight: bold; -fx-padding: 0.5em 0 0 1em");
            Label clueValue = new Label(clue.getValue());

            downGrid.addRow(j++, clueNum, clueValue);
        }

    }


    public void initCSPGraph(List<Variable> variables, List<Constraint> constraints) {
        GraphData graphData = new GraphData();

        for (Variable var : variables) {
            Node node = new Node();
            node.setId(var.getId());
            graphData.getNodes().add(new Element(node));
        }

        for (Constraint constraint : constraints) {
            Edge edge = new Edge();
            edge.setSource("D" + constraint.getDownNum());
            edge.setTarget("A" + constraint.getAcrossNum());
            edge.setId(edge.getSource() + "[" + constraint.getDownCharAt() + "]:" + edge.getTarget() + "[" + constraint.getAcroosCharAt() + "]");
            graphData.getEdges().add(new Element(edge));
        }


        ObjectMapper objectMapper = new ObjectMapper();

        String elesJson = "null";
        try {
            elesJson = objectMapper.writeValueAsString(graphData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(elesJson);

        cspGraph.invokeJS("initGraph(" + elesJson + ")");
    }
}
