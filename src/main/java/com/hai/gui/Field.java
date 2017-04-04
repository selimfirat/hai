package com.hai.gui;

/**
 * Created by mrsfy on 04-Apr-17.
 */
public class Field {

    private int startIndex;
    private int endIndex;
    private String clue;
    private Word word;
    private int fieldIndex;
    private FieldType fieldType;
    private int clueNum;

    public Field() {
    }

    public Field(int startIndex, int endIndex, String clue, Word word, int fieldIndex, FieldType fieldType, int clueNum) {
        this.startIndex = startIndex;
        this.clue = clue;
        this.word = word;
        this.fieldIndex = fieldIndex;
        this.fieldType = fieldType;
        this.clueNum = clueNum;
        this.endIndex = endIndex;
    }

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
