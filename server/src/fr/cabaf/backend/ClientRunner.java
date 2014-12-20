package fr.cabaf.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientRunner implements Runnable {
    private final Client client;

    public ClientRunner(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Socket socket = client.getSocket();
        client.getHandler().onConnect(client);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!socket.isClosed()) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                try {
                    client.getHandler().onReceive(client, line);
                } catch (Exception e) {
                    client.getHandler().onException(e);
                }
            }
        } catch (SocketException ignored) {
            // likely to be a closed connection
        } catch (IOException ioe) {
            throw new Error(ioe);
        } finally {
            try { socket.close(); }catch(IOException ignored){}
            client.getHandler().onDisconnect(client);
            Server.getInstance().removeClient(client);
        }
    }
}
