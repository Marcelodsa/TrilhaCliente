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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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

    private Client client;

    public Piece pieceClass;

    public String clientColor;

    public String opponentColor;

    private boolean isMyTurn;

    private Movements movementMap;

    private int piecesPlaced;

    private int oldSelectedPiece, newSelectedPiece;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        try {
            client = new Client(new Socket("localhost", 1234), this);
            movementMap = new Movements(Arrays.asList(place1,place2,place3,place4,place5,place6,place7,place8,place9,
                    place10,place11,place12,place13,place14,place15,place16,place17,place18,place19,place20,
                    place21,place22,place23,place24));
            isMyTurn = false;
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

    public void mouseClicked (Event event) {

        Circle piece = ((Circle) event.getSource());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (piece.getFill() == Paint.valueOf("BLACK") && piecesPlaced<9) {
                    piece.setFill(Paint.valueOf(clientColor));
                    changeTurn();
                    client.sendMovementMessage(Integer.parseInt(piece.getId()), Integer.parseInt(piece.getId()));
                    piecesPlaced++;
                    // MOV:circleId
                    //MOV:1
                    //mandar msg MOV:piece.getId(), piece.getId()
                }
            }
        });

        // ter uma variavel que armazena a pecao selecionada atual
        // guarda o id da atual e guarda o id da nova
        // manda o movimento

    }

    public void changeTurn(){
        isMyTurn = !isMyTurn;
    }

    public void handleMovement(int oldCircleId, int newCircleId){
        Piece oldSelectedPiece = movementMap.getPieces().get(oldCircleId);
        Piece newSelectedPiece = movementMap.getPieces().get(newCircleId);


        oldSelectedPiece.getCircle().setFill(Paint.valueOf("BLACK"));
        newSelectedPiece.getCircle().setFill(Paint.valueOf("opponentColor.toUpperCase()"));
        changeTurn();
    }
}