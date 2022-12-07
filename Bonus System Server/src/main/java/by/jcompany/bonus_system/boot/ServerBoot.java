package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.boot.server.Server;

import java.io.IOException;

public class ServerBoot {
    public static void main(String[] args) throws IOException {
        Server.start();
    }
}
