package com.hai.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * Created by mrsfy on 27-Mar-17.
 */
public class Puzzle extends StackPane {

    private GraphicsContext gc;
    private GraphicsContext bgc;
    private double size = 558;
    private double singleSize;


    public Puzzle() {

        singleSize = size / 5;

        Canvas canvas = new Canvas(size, size);
        Canvas backCanvas = new Canvas(size, size);


        gc = canvas.getGraphicsContext2D();
        bgc = backCanvas.getGraphicsContext2D();

        drawGrid();
        this.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, new Insets(0, 0, 0, 0))));
        this.setPrefWidth(400);
        this.setPrefHeight(400);
        this.getChildren().addAll(canvas, backCanvas);

        Field[] fields = new Field[]{
                new Field(2, 4, "In support of", new Word("FOR"), 0, FieldType.ACROSS, 1),
                new Field(1, 4, "Either component of today's date", new Word("FOUR"), 1, FieldType.ACROSS, 4),
                new Field(0, 4, "Shouts after errant golf shots", new Word("FORES"), 2, FieldType.DOWN, 1),
                new Field(1, 4, "Author Jonathan Safran ___", new Word("FOER"), 1, FieldType.DOWN, 4),
                new Field(0, 4, "Ins and ___", new Word("OUTS"), 3, FieldType.DOWN, 2)
        };

        for (Field field : fields)
            fillField(field);

        // clearFields();
    }

    private void clearFields() {

        gc.clearRect(0, 0, size, size);

    }


    private void fillField(Field field) {
        drawFreeCells(field);

        if (field.getFieldType() == FieldType.DOWN)
            drawCellNum(field.getClueNum(), field.getFieldIndex(), field.getStartIndex());
        else
            drawCellNum(field.getClueNum(), field.getStartIndex(), field.getFieldIndex());

        String word = field.getWord().getWord();

        for (int i = 0; i < word.length(); i++)
            if (field.getFieldType() == FieldType.DOWN)
                drawCellLetter(word.charAt(i), field.getFieldIndex(), field.getStartIndex() + i);
            else
                drawCellLetter(word.charAt(i), field.getStartIndex() + i, field.getFieldIndex());
    }

    private void drawFreeCells(Field field) {
        String word = field.getWord().getWord();

        for (int i = 0; i < field.getStartIndex(); i++)
            if (field.getFieldType() == FieldType.DOWN)
                drawFreeCell(field.getFieldIndex(), i);
            else
                drawFreeCell(i, field.getFieldIndex());

        for (int i = word.length() + field.getStartIndex(); i < field.getEndIndex(); i++)
            if (field.getFieldType() == FieldType.DOWN)
                drawFreeCell(field.getFieldIndex(), i);
            else
                drawFreeCell(i, field.getFieldIndex());
    }


    private void drawFreeCell(int i, int j) {
        gc.setFill(Color.DIMGRAY);
        gc.fillRect(i*singleSize, j*singleSize, singleSize, singleSize);
    }

    private void drawCellLetter(char str, int i, int j) {

        gc.setFill(Color.web("#0858AA"));
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, singleSize/2));
        gc.fillText("" + str, singleSize/3 + i * singleSize, singleSize*2/3 + j * singleSize);
    }

    private void drawCellNum(int num, int i, int j){

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Helvetica", FontWeight.EXTRA_LIGHT, singleSize/5));
        gc.fillText("" + num, singleSize/20 + i * singleSize, singleSize*2/9 + j * singleSize);
    }

    private void drawGrid() {
        for (int i = 0; i < 6; i++) {

            bgc.setLineWidth(i == 0 || i == 5 ? 2.4 : 0.6);
            bgc.setStroke(Color.DIMGRAY);

            bgc.strokeLine(0, i * singleSize, size, i * singleSize);
            bgc.strokeLine(i * singleSize, 0, i * singleSize, size);
        }
    }

}
