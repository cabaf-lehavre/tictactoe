package fr.cabaf.backend;

public interface ClientHandler {
    void onConnect(Client client);
    void onDisconnect(Client client);
    void onReceive(Client client, String str);
    void onException(Exception e);
}
