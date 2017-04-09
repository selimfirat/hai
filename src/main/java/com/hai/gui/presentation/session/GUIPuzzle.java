package com.hai.gui.presentation.session;

import com.hai.gui.data.puzzle.Puzzle;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by mrsfy on 27-Mar-17.
 */
public class GUIPuzzle extends StackPane {

    private GraphicsContext gc;
    private GraphicsContext bgc;
    private double size = 565;
    private double singleSize;


    public GUIPuzzle() {

        singleSize = size / 5;

        Canvas canvas = new Canvas(size, size);
        Canvas backCanvas = new Canvas(size, size);


        gc = canvas.getGraphicsContext2D();
        bgc = backCanvas.getGraphicsContext2D();

        clearFields();

        drawGrid();
        this.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, new Insets(0, 0, 0, 0))));
        this.setPrefWidth(size);
        this.setPrefHeight(size);
        this.getChildren().addAll(canvas, backCanvas);

    }

    public void clearFields() {

        gc.clearRect(0, 0, size, size);

    }

    public void fillPuzzleAll(Puzzle p) {

        clearFields();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boolean free = p.getLayout()[j*5+i] == 0;

                if (free) {
                    drawFreeCell(i, j);
                } else {
                    drawCellLetter(p.getAnswers()[j*5+i].charAt(0), i, j);
                }

                // ACROSS
                if (p.getClues().getA().size() > i) {
                    int a = p.getClues().getA().get(i).getClueStart();
                        drawCellNum(p.getClues().getA().get(i).getClueNum(), a % 5, a / 5);
                }

                // DOWN
                if (p.getClues().getD().size() > j) {
                    int b = p.getClues().getD().get(j).getClueStart();
                     drawCellNum(p.getClues().getD().get(j).getClueNum(), b % 5, b / 5);
                }

            }
        }
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
