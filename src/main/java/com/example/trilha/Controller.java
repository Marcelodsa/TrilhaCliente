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
            place21,place22,place23,place24,place25,place26,place27,place28,place29,place30,
            place31,place32,place33,place34,place35,place36,myColor;
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
                    place21,place22,place23,place24,place25,place26,place27,place28,place29,place30,
                    place31,place32,place33,place34,place35,place36));
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
                                    if (piecesPlaced == 8) {
                                        allPiecesPlaced = true;
                                        Platform.runLater(() ->{notificationLabel.setText("Você venceu o jogo!");});
                                    } else {
                                        changeTurn();
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
        opponentPiecesPlaced++;
        opponentPieces++;

        Piece piecePlaced = movementMap.getPieces().get(pieceIndex);

        piecePlaced.getCircle().setFill(Paint.valueOf(opponentColor));
        piecePlaced.setFill(Paint.valueOf(opponentColor));

        if(opponentPiecesPlaced < 8){
            Platform.runLater(() -> {notificationLabel.setText("Oponente colocou uma peca");});
            changeTurn();
        } else {
            Platform.runLater(() -> {
                notificationLabel.setText("O seu oponente venceu o jogo!");
            });
        }
    }
}