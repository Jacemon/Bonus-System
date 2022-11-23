package by.jcompany.bonus_system.server;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.FunctionManager;
import jakarta.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.AccessDeniedException;

public class ClientHandler implements Runnable {
    private final int clientNumber;
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    
    @Getter @Setter
    private User clientUser = null;
    
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
                    String command = clientRequest.getRequestType();
                    String requestString = clientRequest.getRequestString();
                    
                    System.out.println("client #" + clientNumber + " -> server: ");
                    System.out.println(clientRequest);
                    
                    if (command.equals("QUIT")) {
                        break;
                    }
                    
                    Object response = FunctionManager.executeFunction(command, requestString, clientUser);
                    serverResponse = new Response("OK", response);
                    
                    if (command.equals("LOGIN")) {
                        clientUser = (User) response;
                    }
                    if (command.equals("LOGOUT")) {
                        clientUser = null;
                    }
                } catch (AccessDeniedException exception) {
                    serverResponse = new Response("ERROR", "Forbidden!");
                } catch (NullPointerException exception) {
                    serverResponse = new Response("ERROR", "Command not exist!");
                } catch (PersistenceException exception) {
                    serverResponse = new Response("ERROR", "Could not create entity!");
                } catch (Exception exception) {
                    serverResponse = new Response("ERROR", "Not defined error!");
                    exception.printStackTrace();
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