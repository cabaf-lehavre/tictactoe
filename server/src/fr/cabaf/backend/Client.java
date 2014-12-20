package fr.cabaf.backend;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class Client {
    private final int id;
    private final Socket socket;

    private final BufferedWriter writer;

    private ClientHandler handler;

    public Client(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;

        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler getHandler() {
        return handler;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }

    public void sendLine(String line, boolean nl) {
        System.out.println("SEND " + line);

        try {
            writer.write(line);
            if (nl) {
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void sendLine(String line) {
        sendLine(line, true);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
