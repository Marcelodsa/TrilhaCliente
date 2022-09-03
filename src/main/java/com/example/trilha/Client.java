package com.example.trilha;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class Client {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Controller controller;

    public Client(Socket socket, Controller controller) {
        try {
            this.socket = socket;
            this.controller= controller;
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
            receiveMessageFromClient();
        } catch (IOException e) {
            System.out.println("Error creating client.");
            e.printStackTrace();
        }
    }

    public void delegateActions(String message){
        String type = message.split(":")[0];
        String messageContent = message.split(":")[1];

        switch (type) {
            case "TXT" -> controller.addLabel(messageContent);
            case "MYCOL" -> controller.setClientColor(messageContent);
            case "OPCOL" -> controller.setOpponentColor(messageContent);
            case "TURN" -> controller.changeTurn();
            case "MOV" -> {
                int oldPiece = Integer.parseInt(messageContent.split(",")[0]);
                int newPiece = Integer.parseInt(messageContent.split(",")[1]);
                controller.handleMovement(oldPiece, newPiece);
            }
            case "REM" ->{
                controller.handlePlayerRemovingPiece(Integer.parseInt(messageContent));
            }
            case "MILL" ->{
                controller.showMillNotification();
            }
            case "DRAW" ->{
                controller.showDrawNotification();
            }
            case "POS" ->{
                controller.handleOpponentPiecePositioning(Integer.parseInt(messageContent));
            }
        }
    }

    public void sendChatMessage(String message) {
        sendMessageToServer("TXT:"+message);
    }

    public void sendMovementMessage(int oldCircleId, int newCircleId) {
        String movement= "MOV:" + oldCircleId + "," + newCircleId;
        sendMessageToServer(movement);
    }

    public void sendMessageToServer (String messageToClient) {
        try {
            outputStream.writeObject(messageToClient);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the client.");
            //closeEverything(socket, inputStream, outputStream);
        }
    }

    private void receiveMessageFromClient () {
        new Thread(()->{
            while (socket.isConnected()) {
                try {
                    String messageFromClient = (String) inputStream.readObject();
                    if (messageFromClient != null) {
                        //System.out.println(MessageFormat.format("Received message from server: {}", messageFromClient));
                        System.out.println(messageFromClient);
                        delegateActions(messageFromClient);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from client.");
                    //closeEverything(socket, inputStream, outputStream);
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void removePieceFromOpponent(int pieceIndex){
        String msg = "REM:" + pieceIndex;

        sendMessageToServer(msg);
    }

    public void closeEverything (Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMillNotification(){
        String msg = "MILL:true";

        sendMessageToServer(msg);
    }

    public void sendDrawNotification(){
        String msg = "DRAW:true";

        sendMessageToServer(msg);
    }

    public void sendPiecePositioningMessage(int pieceIndex){
        String msg = "POS:" + pieceIndex;

        sendMessageToServer(msg);
    }
}
