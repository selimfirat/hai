package com.hai.gui.data.cytoscape_graph;

/**
 * Created by mrsfy on 16-Apr-17.
 */
public class Edge implements ElementData {

    private String id;
    private String source;
    private String target;

    public Edge() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
