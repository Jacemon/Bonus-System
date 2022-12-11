package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.function.Functions;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connection implements AutoCloseable {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 4040;
    
    private Socket clientSocket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    
    // todo попробовать java.nio
    public Connection() {
        try {
            clientSocket = new Socket(IP_ADDRESS, PORT);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public void makeRequest(Request request) throws IOException {
        try {
            objectOutputStream.writeObject(request);
        } catch (SocketException | NullPointerException exception) {
            Functions.reconnect();
            System.out.println("reconnecting...");
            throw exception;
        }
    }
    
    public Response getResponse() throws IOException, ClassNotFoundException {
        try {
            return (Response) objectInputStream.readObject();
        } catch (SocketException | NullPointerException exception) {
            Functions.reconnect();
            System.out.println("reconnecting...");
            throw exception;
        }
    }
    
    public boolean isOpened() {
        return objectOutputStream != null && objectInputStream != null && clientSocket != null;
    }
    
    @Override
    public void close() throws Exception {
        objectOutputStream.close();
        objectInputStream.close();
        clientSocket.close();
    }
}
