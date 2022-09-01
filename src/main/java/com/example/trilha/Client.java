package com.example.trilha;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
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
            case "TXT":
                controller.addLabel(messageContent);
                break;
            case "MYCOL":
                controller.setClientColor(messageContent);
                break;
            case "OPCOL":
                controller.setOpponentColor(messageContent);
                break;
            case "TURN":
                controller.changeTurn();
                break;
            case "MOV":
                //"1,2"
                int oldPiece = Integer.parseInt(messageContent.split(",")[0]);
                int newPiece = Integer.parseInt(messageContent.split(",")[1]);
                controller.handleMovement(oldPiece, newPiece);
                break;
            default:
                break;
        }
    }

    public void sendChatMessage(String message) {
        sendMessageToServer("TXT:"+message);
    }

    public void sendMovementMessage(int oldCircleId, int newCircleId) {
        List list = Arrays.asList(oldCircleId, newCircleId);
        String movement= "MOV"+list;
        sendMovementToServer(movement);
    }
    public void sendMovementToServer(String movement){
        try {
            outputStream.writeObject(movement);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the client.");
            //closeEverything(socket, inputStream, outputStream);
        }
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
}
