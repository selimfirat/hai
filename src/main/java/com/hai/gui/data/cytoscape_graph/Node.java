package com.hai.gui.data.cytoscape_graph;

/**
 * Created by mrsfy on 16-Apr-17.
 */
public class Node implements ElementData {

    private String id;

    public Node() {
    }

    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
