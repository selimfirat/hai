package com.hai.gui.data.cytoscape_graph;

/**
 * Created by mrsfy on 16-Apr-17.
 */
public class Element {

    private ElementData data;

    public Element() {
    }

    public Element(ElementData data) {
        this.data = data;
    }

    public ElementData getData() {
        return data;
    }

    public void setData(ElementData data) {
        this.data = data;
    }
}
