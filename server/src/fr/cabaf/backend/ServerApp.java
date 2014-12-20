package fr.cabaf.backend;

import fr.cabaf.backend.handler.morpion.MorpionHandler;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        int port = 5555;
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.printf("%s isnt a valid port number\n", args[0]);
                return;
            }
        }

        Server server = new Server(new ClientThreadFactory(), port);
        server.setDefaultHandler(new MorpionHandler());
        Server.setInstance(server);
//        Thread thread = new Thread(server);
//        thread.setDaemon(true);
//        thread.start();
        server.run();
    }
}
