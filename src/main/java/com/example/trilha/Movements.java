package com.example.trilha;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Movements {
    private ArrayList<Piece> possibleCirclesMovement;
    private ArrayList<Piece> pieces;
    private HashMap<String, Piece> piecesMap;

    public Movements(List<Circle> circles){
        initializeCircles(circles);
    }

    private void initializeCircles(List<Circle> circles) {
        possibleCirclesMovement = new ArrayList<>();

        Piece circlePiece1 = new Piece(circles.get(0));
        Piece circlePiece2 = new Piece(circles.get(1));
        Piece circlePiece3 = new Piece(circles.get(2));
        Piece circlePiece4 = new Piece(circles.get(3));
        Piece circlePiece5 = new Piece(circles.get(4));
        Piece circlePiece6 = new Piece(circles.get(5));
        Piece circlePiece7 = new Piece(circles.get(6));
        Piece circlePiece8 = new Piece(circles.get(7));
        Piece circlePiece9 = new Piece(circles.get(8));
        Piece circlePiece10 = new Piece(circles.get(9));
        Piece circlePiece11 = new Piece(circles.get(10));
        Piece circlePiece12 = new Piece(circles.get(11));
        Piece circlePiece13 = new Piece(circles.get(12));
        Piece circlePiece14 = new Piece(circles.get(13));
        Piece circlePiece15 = new Piece(circles.get(14));
        Piece circlePiece16 = new Piece(circles.get(15));
        Piece circlePiece17 = new Piece(circles.get(16));
        Piece circlePiece18 = new Piece(circles.get(17));
        Piece circlePiece19 = new Piece(circles.get(18));
        Piece circlePiece20 = new Piece(circles.get(19));
        Piece circlePiece21 = new Piece(circles.get(20));
        Piece circlePiece22 = new Piece(circles.get(21));
        Piece circlePiece23 = new Piece(circles.get(22));
        Piece circlePiece24 = new Piece(circles.get(23));

        ArrayList<Piece> place1Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece2, circlePiece10

        ));
        circlePiece1.setNeighbourhood(place1Neighbour);

        ArrayList<Piece> place2Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece1, circlePiece3

        ));
        circlePiece2.setNeighbourhood(place2Neighbour);

        ArrayList<Piece> place3Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece2, circlePiece15

        ));
        circlePiece3.setNeighbourhood(place3Neighbour);

        ArrayList<Piece> place4Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece5, circlePiece11

        ));
        circlePiece4.setNeighbourhood(place4Neighbour);

        ArrayList<Piece> place5Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece2, circlePiece4, circlePiece6, circlePiece8

        ));
        circlePiece5.setNeighbourhood(place5Neighbour);

        ArrayList<Piece> place6Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece5, circlePiece14

        ));
        circlePiece6.setNeighbourhood(place6Neighbour);

        ArrayList<Piece> place7Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece8, circlePiece12

        ));
        circlePiece7.setNeighbourhood(place7Neighbour);

        ArrayList<Piece> place8Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece7, circlePiece9

        ));
        circlePiece8.setNeighbourhood(place8Neighbour);

        ArrayList<Piece> place9Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece8, circlePiece13

        ));
        circlePiece9.setNeighbourhood(place9Neighbour);

        ArrayList<Piece> place10Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece1, circlePiece11, circlePiece22

        ));
        circlePiece10.setNeighbourhood(place10Neighbour);

        ArrayList<Piece> place11Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece4, circlePiece10, circlePiece12, circlePiece19

        ));
        circlePiece11.setNeighbourhood(place11Neighbour);

        ArrayList<Piece> place12Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece7, circlePiece11, circlePiece16

        ));
        circlePiece12.setNeighbourhood(place12Neighbour);

        ArrayList<Piece> place13Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece9, circlePiece14, circlePiece18

        ));
        circlePiece13.setNeighbourhood(place13Neighbour);

        ArrayList<Piece> place14Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece6, circlePiece13, circlePiece15, circlePiece21

        ));
        circlePiece14.setNeighbourhood(place14Neighbour);

        ArrayList<Piece> place15Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece3, circlePiece14, circlePiece24

        ));
        circlePiece15.setNeighbourhood(place15Neighbour);

        ArrayList<Piece> place16Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece12, circlePiece17

        ));
        circlePiece16.setNeighbourhood(place16Neighbour);

        ArrayList<Piece> place17Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece16, circlePiece18, circlePiece20

        ));
        circlePiece17.setNeighbourhood(place17Neighbour);

        ArrayList<Piece> place18Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece13, circlePiece17

        ));
        circlePiece18.setNeighbourhood(place18Neighbour);

        ArrayList<Piece> place19Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece11, circlePiece20

        ));
        circlePiece19.setNeighbourhood(place19Neighbour);

        ArrayList<Piece> place20Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece17, circlePiece19, circlePiece21, circlePiece23

        ));
        circlePiece20.setNeighbourhood(place20Neighbour);

        ArrayList<Piece> place21Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece14, circlePiece20

        ));
        circlePiece21.setNeighbourhood(place21Neighbour);

        ArrayList<Piece> place22Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece10, circlePiece23

        ));
        circlePiece22.setNeighbourhood(place22Neighbour);

        ArrayList<Piece> place23Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece20, circlePiece22, circlePiece24

        ));
        circlePiece23.setNeighbourhood(place23Neighbour);

        ArrayList<Piece> place24Neighbour = new ArrayList<>(Arrays.asList(
                circlePiece15, circlePiece23

        ));
        circlePiece24.setNeighbourhood(place24Neighbour);

        pieces = new ArrayList<>(Arrays.asList(
                circlePiece1,circlePiece2,circlePiece3,circlePiece4,circlePiece5,circlePiece6,circlePiece7,circlePiece8,circlePiece9,circlePiece10,
                circlePiece12,circlePiece13,circlePiece14,circlePiece15,circlePiece16,circlePiece17,circlePiece18,circlePiece19,circlePiece20,circlePiece21,
                circlePiece22,circlePiece23,circlePiece24
        ));
        piecesMap = new HashMap<>();

        for (Piece piece: pieces){
            piecesMap.put(piece.getCircle().getId(), piece);
        }

    }

    public ArrayList<Piece> getPieces(){
            return pieces;
    }
}
