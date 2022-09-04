package com.example.trilha;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private Circle place1,place2,place3,place4,place5,place6,place7,place8,place9,place10,
            place11,place12,place13,place14,place15,place16,place17,place18,place19,place20,
            place21,place22,place23,place24,myColor;
    @FXML
    private Pane pane;

    @FXML
    private Label turnInfo;

    private Client client;

    public String clientColor;

    public String opponentColor;

    private boolean isMyTurn;

    private Movements movementMap;

    private int piecesPlaced;

    private int remainingPieces;

    private Piece currentSelectedPiece;

    private boolean createdMill;

    private boolean allPiecesPlaced;

    @FXML
    private Label notificationLabel;

    private int opponentPieces;

    private int opponentPiecesPlaced;

    private boolean gameOver;

    private int movementsToDrawGame;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        try {
            client = new Client(new Socket("localhost", 3456), this);
            movementMap = new Movements(Arrays.asList(place1,place2,place3,place4,place5,place6,place7,place8,place9,
                    place10,place11,place12,place13,place14,place15,place16,place17,place18,place19,place20,
                    place21,place22,place23,place24));
            isMyTurn = false;
            createdMill = false;
            gameOver = false;
            opponentPieces = 0;
            remainingPieces = 0;
            piecesPlaced = 0;
            opponentPiecesPlaced = 0;
            movementsToDrawGame = 10;
            turnInfo.setText("Vez do oponente");
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });
    }

    public void onSendAction (ActionEvent event){

        String messageToSend = tf_message.getText();
        if (!messageToSend.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            vbox_messages.getChildren().add(hBox);

            client.sendChatMessage(messageToSend);
            tf_message.clear();
        }
    }


    public void addLabel(String messageFromClient) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox_messages.getChildren().add(hBox);
            }
        });
    }

    public void setClientColor(String color){
        clientColor= color;
        myColor.setFill(Paint.valueOf(clientColor));
    }

    public void setOpponentColor(String color){
        opponentColor= color;
    }

    private void highlightPiece(Piece piece){
        piece.getCircle().setStroke(Paint.valueOf("YELLOW"));
        piece.getCircle().setStrokeWidth(2);
    }

    private void unhighlightPiece(Piece piece){
        piece.getCircle().setStroke(Paint.valueOf("BLACK"));
        piece.getCircle().setStrokeWidth(1);
    }

    public void mouseClicked (Event event) {
        Circle circleSelected = ((Circle) event.getSource());
        Piece selectedPiece = Optional.of(movementMap.getPieces().stream().filter(p -> p.getCircle() == circleSelected).findFirst().get()).orElse(null);

        if(!gameOver){
            if(isMyTurn){
                if(!allPiecesPlaced) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(!createdMill) {
                                if (selectedPiece.getFill() == Paint.valueOf("BLACK")) {
                                    selectedPiece.setFill(Paint.valueOf(clientColor));
                                    selectedPiece.getCircle().setFill(Paint.valueOf(clientColor));
                                    client.sendPiecePositioningMessage(movementMap.getPieces().indexOf(selectedPiece));
                                    notificationLabel.setText("Voce colocou uma peca");
                                    piecesPlaced++;
                                    remainingPieces++;
                                    if (piecesPlaced == 9) {
                                        allPiecesPlaced = true;
                                    }
                                    if (createdMill(selectedPiece)) {
                                        client.sendMillNotification();
                                        System.out.println("Player created a mill");
                                        notificationLabel.setText("Voce criou um moinho");
                                        createdMill = true;
                                    } else {
                                        changeTurn();
                                    }
                                }
                            }
                            else{
                                if(selectedPiece.getFill() == Paint.valueOf(opponentColor)){
                                    handleMillDecision(selectedPiece);
                                }
                            }
                        }
                    });
                }
                else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (selectedPiece.getFill() == Paint.valueOf(clientColor)) {
                                if(currentSelectedPiece != null){
                                    unhighlightPiece(currentSelectedPiece);
                                }
                                currentSelectedPiece = selectedPiece;
                                highlightPiece(currentSelectedPiece);
                            }
                            else{
                                if (selectedPiece.getFill() == Paint.valueOf("BLACK") && currentSelectedPiece != null) {
                                    List<Circle> horizontalNeighbourhood = currentSelectedPiece.getHorizontalLineNeighbourhood().stream().map(Piece::getCircle).toList();
                                    List<Circle> verticalNeighbourhood = currentSelectedPiece.getVerticalLineNeighbourhood().stream().map(Piece::getCircle).toList();
                                    boolean isMovingToHorizontalNeighbour = horizontalNeighbourhood.contains(selectedPiece.getCircle());
                                    boolean isMovingToVerticalNeighbour = verticalNeighbourhood.contains(selectedPiece.getCircle());
                                    boolean isJumpingOpponentPieceHorizontally = horizontalNeighbourhood.stream().map(Circle::getFill).toList().contains(Paint.valueOf(opponentColor));
                                    boolean isJumpingOpponentPieceVertically = verticalNeighbourhood.stream().map(Circle::getFill).toList().contains(Paint.valueOf(opponentColor));

                                    //TODO nao basta olhar se a vizinhanca tem cor oposta, tem que ver se o vizinho intermediario tem, se nao vai bugar
                                    if (currentSelectedPiece.getNeighbourhood().stream().map(Piece::getCircle).toList().contains(selectedPiece.getCircle()) || remainingPieces == 3) {
                                        client.sendMovementMessage(movementMap.getPieces().indexOf(currentSelectedPiece), movementMap.getPieces().indexOf(selectedPiece));
                                        notificationLabel.setText("Voce moveu uma peca");

                                        currentSelectedPiece.setFill(Paint.valueOf("BLACK"));
                                        currentSelectedPiece.getCircle().setFill(Paint.valueOf("BLACK"));
                                        unhighlightPiece(currentSelectedPiece);
                                        selectedPiece.setFill(Paint.valueOf(clientColor));
                                        selectedPiece.getCircle().setFill(Paint.valueOf(clientColor));

                                        if (currentSelectedPiece.isInAMill()) {
                                            removeFromMill(currentSelectedPiece);
                                        }

                                        if (createdMill(selectedPiece)) {
                                            client.sendMillNotification();
                                            System.out.println("Player created a mill");
                                            notificationLabel.setText("Voce criou um moinho");
                                            createdMill = true;
                                        } else {
                                            changeTurn();
                                        }

                                        currentSelectedPiece = null;

                                        checkIfShouldDrawGame();
                                    }
                                }
                                else{
                                    if(createdMill) {
                                        handleMillDecision(selectedPiece);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public void changeTurn(){
        isMyTurn = !isMyTurn;
        Platform.runLater(() -> {
            if(isMyTurn){
                turnInfo.setText("Sua vez");
            }
            else{
                turnInfo.setText("Vez do oponente");
            }
        });
    }

    public void handleOpponentPiecePositioning(int pieceIndex){
        if(opponentPiecesPlaced < 9){
            Platform.runLater(() -> {notificationLabel.setText("Oponente colocou uma peca");});
            opponentPiecesPlaced++;
            opponentPieces++;

            Piece piecePlaced = movementMap.getPieces().get(pieceIndex);

            piecePlaced.getCircle().setFill(Paint.valueOf(opponentColor));
            piecePlaced.setFill(Paint.valueOf(opponentColor));

            changeTurn();
        }
    }

    public void handleMovement(int oldCircleId, int newCircleId){
        Piece oldSelectedPiece = movementMap.getPieces().get(oldCircleId);
        Piece newSelectedPiece = movementMap.getPieces().get(newCircleId);

        oldSelectedPiece.getCircle().setFill(Paint.valueOf("BLACK"));
        oldSelectedPiece.setFill(Paint.valueOf("BLACK"));
        newSelectedPiece.getCircle().setFill(Paint.valueOf(opponentColor));
        newSelectedPiece.setFill(Paint.valueOf(opponentColor));

        Platform.runLater(() -> {notificationLabel.setText("Oponente moveu uma peca");});

        checkIfShouldDrawGame();

        if(!newSelectedPiece.isInAMill()) {
            changeTurn();
        }
    }

    /*
    detects whether a player has created a called 'mill'
    with 3 pieces in a horizontal or vertical line
     */
    private boolean createdMill(Piece piece){
        // the 2 cells of the horizontal line neighbourhood are filled with player's pieces
        boolean hasHorizontalMill =  piece.getHorizontalLineNeighbourhood().stream().filter(p -> p.getCircle().getFill().equals(Paint.valueOf(clientColor))).toList().size() == 2;

        // the 2 cells of the vertical line neighbourhood are filled with player's pieces
        boolean hasVerticalMill = piece.getVerticalLineNeighbourhood().stream().filter(p -> p.getCircle().getFill().equals(Paint.valueOf(clientColor))).toList().size() == 2;

        return hasHorizontalMill || hasVerticalMill;
    }

    private void removeFromMill(Piece piece){
        boolean hasHorizontalMill =  piece.getHorizontalLineNeighbourhood().stream().filter(p -> p.getCircle().getFill().equals(Paint.valueOf(clientColor))).toList().size() == 2;
        if(hasHorizontalMill){
            for(Piece p : piece.getHorizontalLineNeighbourhood()){
                p.setInAMill(false);
            }
        }
        else{
            for(Piece p : piece.getVerticalLineNeighbourhood()){
                p.setInAMill(false);
            }
        }
        piece.setInAMill(false);
    }

    private void handleMillDecision(Piece selectedPiece){
        if (!selectedPiece.isInAMill()) {
            client.removePieceFromOpponent(movementMap.getPieces().indexOf(selectedPiece));
            selectedPiece.setFill(Paint.valueOf("BLACK"));
            selectedPiece.getCircle().setFill(Paint.valueOf("BLACK"));
            opponentPieces--;
            createdMill = false;
            changeTurn();
            if (opponentPiecesPlaced == 9 && opponentPieces == 2) {
                notificationLabel.setText("Voce venceu");
                gameOver = true;
            }
        }
    }

    public void handlePlayerRemovingPiece(int pieceIndex){
        Piece removedPiece = movementMap.getPieces().get(pieceIndex);
        removedPiece.setFill(Paint.valueOf("BLACK"));
        removedPiece.getCircle().setFill(Paint.valueOf("BLACK"));

        if(removedPiece.isInAMill()){
            removeFromMill(removedPiece);
        }
        remainingPieces--;
        if(allPiecesPlaced && remainingPieces == 2){
            Platform.runLater(() -> {notificationLabel.setText("Voce perdeu");});
            gameOver = true;
        }
        else {

            String notificationText = "Oponente removeu uma peca sua.";

            if (remainingPieces == 3) {
                notificationText += " Modo de movimentacao livre ativado!";
            }

            String finalNotificationText = notificationText;
            Platform.runLater(() -> {
                notificationLabel.setText(finalNotificationText);
            });

            changeTurn();
        }
    }

    public void checkIfShouldDrawGame(){
        if(piecesPlaced == 3 && opponentPieces == 3){
            movementsToDrawGame--;
            if(movementsToDrawGame == 0){
                showDrawNotification();
                client.sendDrawNotification();
                gameOver = true;
            }
        }
    }

    public void showMillNotification(){
        Platform.runLater(() -> {notificationLabel.setText("Jogador criou um moinho e removera uma peca sua");});
        if(isMyTurn){
            changeTurn();
        }
    }

    public void showDrawNotification(){
        Platform.runLater(() ->{notificationLabel.setText("Empate por numero de movimentos restantes igual a 0 com ambos os jogadores com 3 pecas");});
    }
}