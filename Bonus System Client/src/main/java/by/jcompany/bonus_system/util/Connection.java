package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements AutoCloseable {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 4040;
    
    private final Socket clientSocket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    
    // todo попробовать java.nio
    public Connection() throws IOException {
        clientSocket = new Socket(IP_ADDRESS, PORT);
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }
    
    public void makeRequest(Request request) throws IOException {
        objectOutputStream.writeObject(request);
    }
    
    public Response getResponse() throws IOException, ClassNotFoundException {
        return (Response) objectInputStream.readObject();
    }
    
    @Override
    public void close() throws Exception {
        objectOutputStream.close();
        objectInputStream.close();
        clientSocket.close();
    }
}
