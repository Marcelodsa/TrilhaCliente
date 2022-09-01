package com.example.trilha;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Piece extends Circle {

    private Circle circle;
    public ArrayList<Piece> neighbourhood;

    public Piece(Circle circle){
        this.circle = circle;
        this.neighbourhood = new ArrayList<>();
    }

    public void drawPiece (Double numberX, Double numberY, Double radius, String color) {

        circle = new Circle(numberX, numberY, radius);
        circle.setStroke(Paint.valueOf(color));
        circle.setFill(Paint.valueOf(color));
        circle.toFront();

    }

    public Circle getCircle() {
        return circle;
    }

    public void setNeighbourhood(ArrayList<Piece> neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public ArrayList<Piece> getNeighbourhood() {
        return neighbourhood;
    }

}
