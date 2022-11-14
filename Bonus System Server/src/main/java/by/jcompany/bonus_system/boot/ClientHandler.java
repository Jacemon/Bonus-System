package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

class ClientHandler implements Runnable {
    private final int clientNumber;
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    ClientHandler(Socket clientSocket, int clientNumber) throws IOException {
        this.clientNumber = clientNumber;
        System.out.println("connection established with client #" + clientNumber + "...");
        
        socket = clientSocket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    
    @Override
    public void run() {
        try {
            Response serverResponse;
            Request clientRequest;
            
            do {
                clientRequest = (Request) objectInputStream.readObject();
                try {
                    String requestType = clientRequest.getRequestType();
                    String requestString = clientRequest.getRequestString();
                    
                    System.out.println("client #" + clientNumber + " -> server: ");
                    System.out.println(clientRequest);
                    
                    if (requestType.equals("QUIT")) {
                        break;
                    }
                    
                    switch (requestType) {
                        case "CREATE_USER" -> {
                            User user = gson.fromJson(requestString, User.class);
                            ClientFunctions.createUser(user);
                            serverResponse = new Response("OK", "User created");
                        }
                        case "READ_ALL_USERS" -> {
                            List<User> users = ClientFunctions.readAllUsers();
                            serverResponse = new Response("OK", users);
                        }
                        default -> serverResponse =
                            new Response("WARNING", "Not defined error!");
                    }
                } catch (Exception exception) {
                    serverResponse = new Response("ERROR", "Not defined error!");
                }
                
                System.out.println("server -> client #" + clientNumber + ": ");
                System.out.println(serverResponse);
                objectOutputStream.writeObject(serverResponse);
            } while (true);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                System.out.println("connection closed with client #" + clientNumber + "...");
            }
        }
    }
}