package by.jcompany.bonus_system;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 4040;
    
    private static Socket clientSocket;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    
    private static Connection connection;
    
    public static Connection getConnection() {
        if (connection == null) {
            connection = new Connection();
        }
        return connection;
    }
    
    private Connection() {
        try {
            clientSocket = new Socket(IP_ADDRESS, PORT);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void makeRequest(Request request) throws IOException {
        objectOutputStream.writeObject(request);
    }
    
    public Response getResponse() throws IOException, ClassNotFoundException {
        return (Response) objectInputStream.readObject();
    }
}
