package com.example.trilha;

import javafx.scene.shape.Circle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Movements {
    private ArrayList<Piece> pieces;

    public Movements(List<Circle> circles){
        pieces = new ArrayList<>();
        createPiecesAndNeighbourhood(circles);
    }

    private void createPiecesAndNeighbourhood(List<Circle> circles){
        for(Circle circle : circles){
            Piece piece = new Piece(circle);

            pieces.add(piece);
        }
    }

    public ArrayList<Piece> getPieces(){
            return pieces;
    }
}
