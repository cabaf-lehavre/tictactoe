package fr.cabaf.backend;

import fr.cabaf.backend.handler.morpion.MorpionHandler;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        Server server = new Server(new ClientThreadFactory(), 5555);
        server.setDefaultHandler(new MorpionHandler());
        Server.setInstance(server);
//        Thread thread = new Thread(server);
//        thread.setDaemon(true);
//        thread.start();
        server.run();
    }
}
