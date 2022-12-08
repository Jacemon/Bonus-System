package by.jcompany.bonus_system.boot.server;

import by.jcompany.bonus_system.boot.server.init.InitCommands;
import by.jcompany.bonus_system.boot.server.init.InitDatabaseRows;
import by.jcompany.bonus_system.boot.server.init.InitSavedValues;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static final int SERVER_PORT = 4040;
    public static boolean exit = false;
    private static ServerSocket serverSocket = null;
    private static final List<Socket> clientSockets = new ArrayList<>();
    
    public static void start() {
        Thread serverThread = new Thread(() -> {
            InitCommands.create();
            InitDatabaseRows.tryInitAdmin();
            InitDatabaseRows.tryInitUndefinedRole();
            InitDatabaseRows.tryInitCommonRole();
            InitSavedValues.tryInitTaskPointCost();
            
            int clientCount = 0;
    
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
        
                while (true) {
                    clientSockets.removeIf(Socket::isClosed);
                    
                    Socket clientSocket = serverSocket.accept();
                    clientSockets.add(clientSocket);
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, clientCount++);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                }
            } catch (SocketException ignored) {
            } catch (Exception exception) {
                exception.printStackTrace();
            }  finally {
                try {
                    assert serverSocket != null;
                    serverSocket.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        serverThread.start();
        
        System.out.println("server starting...");
        System.out.println("for exit input \"exit\"");
        System.out.println("for database recreating input \"recreate\"");
        
        try {
            while (!exit) {
                Scanner in = new Scanner(System.in);
                String serverCommand = in.nextLine();
                switch (serverCommand) {
                    case "exit" -> {
                        exit = true;
                        serverThread.interrupt();
                        for (Socket clientSocket : clientSockets) {
                            clientSocket.close();
                        }
                        serverSocket.close();
                    }
                    case "recreate" -> {
                        System.out.println("existing database will be deleted, are you sure? (y/n)");
                        if (in.nextLine().equals("y")) {
                            //todo startScript.sql
                            System.out.println("data was recreated");
                        } else {
                            System.out.println("database was not recreated");
                        }
                    }
                }
            }
        } catch (IOException ignored) {
        } finally {
            System.out.println("server closed...");
        }
    }
}