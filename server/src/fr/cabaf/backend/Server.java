package fr.cabaf.backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class Server implements Runnable {
    private static Server instance;

    public static Server getInstance() {
        return instance;
    }

    public static void setInstance(Server instance) {
        Server.instance = instance;
    }

    private final ThreadFactory threadFactory;
    private final ServerSocket listener;

    private final Map<Integer, Client> clients = new HashMap<Integer, Client>();

    private ClientHandler defaultHandler;
    private int clientId;

    public Server(ThreadFactory threadFactory, ServerSocket listener) {
        this.threadFactory = threadFactory;
        this.listener = listener;
    }

    public Server(ThreadFactory threadFactory, int port) throws IOException {
        this(threadFactory, new ServerSocket(port));
    }

    public ClientHandler getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(ClientHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public Client findClient(int id) {
        return clients.get(id);
    }

    public void broadcast(String message) {
        for (Client client : clients.values()) {
            client.sendLine(message);
        }
    }

    public void broadcast(String format, Object... args) {
        String message = String.format(format, args);
        broadcast(message);
    }

    public void removeClient(Client client) {
        clients.remove(client.getId());
    }

    @Override
    public void run() {
        System.out.printf("LSTN %d\n", listener.getLocalPort());
        try {
            while (!listener.isClosed()) {
                Socket socket = listener.accept();

                int id = ++clientId;
                Client client = new Client(id, socket);
                client.setHandler(defaultHandler);

                clients.put(id, client);

                Thread thread = threadFactory.newThread(new ClientRunner(client));
                thread.start();
            }
        } catch (IOException ioe) {
            throw new Error(ioe);
        }
    }
}
