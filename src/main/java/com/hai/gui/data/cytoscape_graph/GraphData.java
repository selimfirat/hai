package com.hai.gui.data.cytoscape_graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsfy on 16-Apr-17.
 */
public class GraphData {

    private List<Element> nodes = new ArrayList<>();
    private List<Element> edges = new ArrayList<>();


    public List<Element> getNodes() {
        return nodes;
    }

    public void setNodes(List<Element> nodes) {
        this.nodes = nodes;
    }

    public List<Element> getEdges() {
        return edges;
    }

    public void setEdges(List<Element> edges) {
        this.edges = edges;
    }
}
