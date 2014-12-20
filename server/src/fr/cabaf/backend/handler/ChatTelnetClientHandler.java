package fr.cabaf.backend.handler;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.ClientHandler;
import fr.cabaf.backend.Server;

public class ChatTelnetClientHandler implements ClientHandler {
    private final Server server;

    public ChatTelnetClientHandler(Server server) {
        this.server = server;
    }

    @Override
    public void onConnect(Client client) {
        server.broadcast("Le client #%d vient de se connecter !", client.getId());
    }

    @Override
    public void onDisconnect(Client client) {
        server.broadcast("Le client #%d vient de se déconnecter !", client.getId());
    }

    @Override
    public void onReceive(Client client, String str) {
        if (str.equalsIgnoreCase("/quit")) {
            client.close();
            return;
        }

        server.broadcast("Client %d: %s", client.getId(), str);
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}
