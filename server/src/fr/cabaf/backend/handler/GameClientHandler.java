package fr.cabaf.backend.handler;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.ClientHandler;

import java.util.*;

public abstract class GameClientHandler<Ctx extends BaseGameContext> implements ClientHandler {
    protected abstract Ctx onPlayerConnection(Client client);
    protected Ctx onPlayerReconnection(Client client, Ctx ctx, Ctx oldCtx) {
        return ctx;
    }
    protected void onPlayerDisconnection(Client client, Ctx ctx) {}
    protected void onPlayerEntering(Client client, Ctx ctx) {}
    protected void onPlayerQuitting(Client client, Ctx ctx) {}
    protected void onPlayerReceive(Client client, Ctx ctx, String msg) {}

    protected void onGameStarting() {}
    protected void onGameEnding() {}
    protected abstract boolean isGameValid();
    protected abstract int getGameNrPlayers();

    protected void onGameTurnStarting(Client client, Ctx ctx) {}
    protected void onGameTurnEnding(Client client, Ctx ctx) {}

    // contexts are remembered from sessions to sessions
    // they're only removed when reconnecting
    // one might implement a size limit (ie. remember at most 100 sessions)
    // or a LRU eviction
    private final Map<Integer, Ctx> contexts = new HashMap<Integer, Ctx>();

    // a lobby is where players play or wait for the game to start
    private final LinkedList<Client> lobby = new LinkedList<Client>();

    // true if game is running, false otherwise
    private boolean running;
    private LinkedList<Client> turnList = new LinkedList<Client>();
    private Client current;

    protected final List<Client> getLobby() {
        return Collections.unmodifiableList(lobby);
    }

    protected final boolean isLobbyFull() {
        return lobby.size() >= getGameNrPlayers();
    }

    protected final void broadcast(String line) {
        for (Client client : getLobby()) {
            client.sendLine(line);
        }
    }

    @Override
    public final void onConnect(Client client) {
        System.out.println("CONN " + client.getId());

        Ctx ctx = onPlayerConnection(client);
        contexts.put(client.getId(), ctx);

        client.sendLine("cur_id," + client.getId());
    }

    @Override
    public final void onDisconnect(Client client) {
        System.out.println("DCNN " + client.getId());

        Ctx ctx = contexts.get(client.getId());

        if (ctx.isPlaying()) {
            onPlayerQuitting(client, ctx);
        }

        lobby.remove(client);

        onPlayerDisconnection(client, ctx);
    }

    @Override
    public final void onReceive(Client client, String str) {
        System.out.println("RECV " + str);

        Ctx ctx = contexts.get(client.getId());

        if (str.startsWith("reconnect")) {
            int oldId = Integer.parseInt(str.substring("reconnect".length()));
            reconnect(client, ctx, oldId);
        } else if (str.startsWith("enter")) {
            enter(client, ctx);
        } else if (str.startsWith("lobby_status")) {
            lobbyStatus(client);
        } else if (client == this.current) {
            onPlayerReceive(client, ctx, str);
        }
    }

    @Override
    public final void onException(Exception e) {

    }

    protected final void passTurn() {
        doEndGameTurn();
    }

    private void reconnect(Client client, Ctx ctx, int oldId) {
        Ctx oldCtx = contexts.remove(oldId);
        if (oldCtx == null) {
            // TODO reconnect request failure
            throw new Error("TODO: not implemented");
        } else {
            Ctx newCtx = onPlayerReconnection(client, ctx, oldCtx);
            contexts.put(client.getId(), newCtx);
        }
    }

    private void enter(Client client, Ctx ctx) {
        if (ctx.isPlaying()) {
            // TODO already playing
            throw new Error("TODO: not implemented");
        }

        if (running) {
            client.sendLine("full");
            return;
        }

        lobby.add(client);
        onPlayerEntering(client, ctx);

        if (isLobbyFull()) {
            doStartGame();
        }
    }

    private void lobbyStatus(Client client) {
        int remainingPlayers = getGameNrPlayers() - lobby.size();
        if (remainingPlayers < 0) {
            remainingPlayers = 0;
        }

        client.sendLine("lobby_status," + remainingPlayers);
    }

    private void doStartGame() {
        if (running) {
            throw new IllegalStateException("game is already running");
        }
        running = true;
        turnList.addAll(lobby);
        onGameStarting();

        doStartGameTurn();
    }

    private void doStartGameTurn() {
        Client client = turnList.pollFirst();
        Ctx ctx = contexts.get(client.getId());

        this.current = client;
        onGameTurnStarting(client, ctx);
    }

    private void doEndGameTurn() {
        Client client = this.current;
        Ctx ctx = contexts.get(client.getId());
        onGameTurnEnding(client, ctx);

        turnList.offerLast(this.current);
        this.current = null;

        if (isGameValid()) {
            doStartGameTurn();
        } else {
            doEndGame();
        }
    }

    private void doEndGame() {
        running = false;
        turnList.clear();
        onGameEnding();
    }
}
