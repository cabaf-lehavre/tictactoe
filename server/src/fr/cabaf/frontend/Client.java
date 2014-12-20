package fr.cabaf.frontend;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final BufferedWriter writer;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public Client(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    public Scanner createScanner() {
        try {
            return new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public BufferedReader createBufferedReader() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void println(String line) {
        try {
            writer.write(line);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void print(String data) {
        try {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
