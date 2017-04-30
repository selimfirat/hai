package com.hai.gui.data.csp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrsfy on 30-Apr-17.
 */
public class Assignment {

    private Map<String, String> fields = new HashMap<>();
    private int fieldCount = 0;

    public Map<String, String> getFields() {
        return fields;
    }

    public Assignment() {
    }

    public void addField(String id, String candidate) {
        fields.put(id, candidate);
        fieldCount++;
    }

    public void removeField(String id) {
        fields.remove(id);
        fieldCount--;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public boolean isAssigned(String id) {
        return fields.containsKey(id);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "fields=" + fields +
                ", fieldCount=" + fieldCount +
                '}';
    }
}
