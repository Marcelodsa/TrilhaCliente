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
            int circleIndex = circles.indexOf(circle);

            Piece leftNeighbour = new Piece(circles.get(circleIndex%8 - 1 < 0? circleIndex + 7:circleIndex - 1));
            Piece rightNeighbour = new Piece(circles.get(circleIndex%8 + 1 > 7? circleIndex - 7:circleIndex + 1));

            Piece firstHorizontalLineNeighbour = null;
            Piece secondHorizontalLineNeighbour = null;

            Piece firstVerticalLineNeighbour = null;
            Piece secondVerticalLineNeighbour = null;

            int remainder = circleIndex % 8;


            switch (remainder) {
                case 0 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 2));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex + 6));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex + 7));
                }
                case 1 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 1));

                    List<Circle> n = circles.stream().filter(c -> circles.indexOf(c) % 8 == 1 && c != circle).toList();
                    firstVerticalLineNeighbour = new Piece(n.get(0));
                    secondVerticalLineNeighbour = new Piece(n.get(1));
                }
                case 2 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 2));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex + 1));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex + 2));
                }
                case 3 -> {
                    List<Circle> n = circles.stream().filter(c -> circles.indexOf(c) % 8 == 3 && c != circle).toList();
                    firstHorizontalLineNeighbour = new Piece(n.get(0));
                    secondHorizontalLineNeighbour = new Piece(n.get(1));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex + 1));
                }
                case 4 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 2));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex - 2));
                }
                case 5 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex + 1));

                    List<Circle> n = circles.stream().filter(c -> circles.indexOf(c) % 8 == 5 && c != circle).toList();
                    firstVerticalLineNeighbour = new Piece(n.get(0));
                    secondVerticalLineNeighbour = new Piece(n.get(1));
                }
                case 6 -> {
                    firstHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondHorizontalLineNeighbour = new Piece(circles.get(circleIndex - 2));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex + 1));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex - 6));
                }
                case 7 -> {
                    List<Circle> n = circles.stream().filter(c -> circles.indexOf(c) % 8 == 7 && c != circle).toList();
                    firstHorizontalLineNeighbour = new Piece(n.get(0));
                    secondHorizontalLineNeighbour = new Piece(n.get(1));

                    firstVerticalLineNeighbour = new Piece(circles.get(circleIndex - 1));
                    secondVerticalLineNeighbour = new Piece(circles.get(circleIndex - 7));
                }
            }

            piece.setHorizontalLineNeighbourhood(new ArrayList<>(Arrays.asList(firstHorizontalLineNeighbour, secondHorizontalLineNeighbour)));
            piece.setVerticalLineNeighbourhood(new ArrayList<>(Arrays.asList(firstVerticalLineNeighbour, secondVerticalLineNeighbour)));

            if((circleIndex%8)%2 == 0){
                piece.setNeighbourhood(new ArrayList<>(Arrays.asList(leftNeighbour, rightNeighbour)));
            }
            else{
                //TODO 0 - 0 - 0 o vizinho esquerdo do meio tem index = 0, dai vai dar erro de divisao por 0, ajeitar isso
                List<Piece> nl = Stream.of(piece.getHorizontalLineNeighbourhood().stream().filter(p -> Math.abs(customDiv(8, circles.indexOf(p.getCircle())) - 8 / circleIndex) <= 1).toList(),
                        piece.getVerticalLineNeighbourhood().stream().filter(p -> Math.abs(customDiv(8, circles.indexOf(p.getCircle())) - 8 / circleIndex) <= 1).toList()).flatMap(Collection::stream).toList();
                piece.setNeighbourhood(new ArrayList<>(nl));
            }

            pieces.add(piece);
        }
    }

    private int customDiv(int a, int b){
        if(b == 0){
            return 0;
        }
        return a / b;
    }

    public ArrayList<Piece> getPieces(){
            return pieces;
    }
}
