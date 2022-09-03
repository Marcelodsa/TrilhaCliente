package com.example.trilha;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Piece extends Circle {

    private Circle circle;
    private ArrayList<Piece> neighbourhood;

    private ArrayList<Piece> horizontalLineNeighbourhood;

    private ArrayList<Piece> verticalLineNeighbourhood;

    private boolean isInAMill;

    public Piece(Circle circle){
        this.circle = circle;
        this.neighbourhood = new ArrayList<>();
        this.horizontalLineNeighbourhood = new ArrayList<>();
        this.verticalLineNeighbourhood = new ArrayList<>();
        isInAMill = false;
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

    public ArrayList<Piece> getHorizontalLineNeighbourhood() {
        return horizontalLineNeighbourhood;
    }

    public void setHorizontalLineNeighbourhood(ArrayList<Piece> horizontalLineNeighbourhood) {
        this.horizontalLineNeighbourhood = horizontalLineNeighbourhood;
    }

    public ArrayList<Piece> getVerticalLineNeighbourhood() {
        return verticalLineNeighbourhood;
    }

    public void setVerticalLineNeighbourhood(ArrayList<Piece> verticalLineNeighbourhood) {
        this.verticalLineNeighbourhood = verticalLineNeighbourhood;
    }

    public boolean isInAMill() {
        return isInAMill;
    }

    public void setInAMill(boolean inAMill) {
        isInAMill = inAMill;
    }
}
