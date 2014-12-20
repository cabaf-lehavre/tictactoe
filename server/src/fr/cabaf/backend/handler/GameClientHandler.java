package fr.cabaf.backend.handler;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.ClientHandler;

import java.util.*;

public abstract class GameClientHandler<Ctx extends BaseGameContext> implements ClientHandler {
    protected abstract Ctx onPlayerConnection(Client client);
    protected abstract Ctx onPlayerReconnection(Client client, Ctx ctx, Ctx oldCtx);
    protected abstract void onPlayerDisconnection(Client client, Ctx ctx);
    protected abstract void onPlayerEntering(Client client, Ctx ctx);
    protected abstract void onPlayerQuitting(Client client, Ctx ctx);

    protected abstract void onGameStarting();
    protected abstract void onGameEnding();
    protected abstract boolean isGameValid();
    protected abstract int getGameNrPlayers();

    protected abstract void onGameTurnStarting(Client client, Ctx ctx);
    protected abstract void onGameTurnEnding(Client client, Ctx ctx);

    // contexts are remembered from sessions to sessions
    // they're only removed when reconnecting
    // one might implement a size limit (ie. remember at most 100 sessions)
    // or a LRU eviction
    private final Map<Integer, Ctx> contexts = new HashMap<Integer, Ctx>();

    // a lobby is where players play or wait for the game to start
    private final LinkedList<Client> lobby = new LinkedList<Client>();

    // true if game is running, false otherwise
    private boolean running;
    private Client current;

    protected final List<Client> getLobby() {
        return Collections.unmodifiableList(lobby);
    }

    protected final boolean isLobbyFull() {
        return lobby.size() >= getGameNrPlayers();
    }

    @Override
    public final void onConnect(Client client) {
        Ctx ctx = onPlayerConnection(client);
        contexts.put(client.getId(), ctx);

        client.sendLine("cur_id," + client.getId());
    }

    @Override
    public final void onDisconnect(Client client) {
        Ctx ctx = contexts.get(client.getId());

        if (ctx.isPlaying()) {
            onPlayerQuitting(client, ctx);
        }

        lobby.remove(client);

        onPlayerDisconnection(client, ctx);
    }

    @Override
    public final void onReceive(Client client, String str) {
        Ctx ctx = contexts.get(client.getId());

        if (str.startsWith("reconnect")) {
            int oldId = Integer.parseInt(str.substring("reconnect".length()));
            reconnect(client, ctx, oldId);
        } else if (str.startsWith("enter")) {
            enter(client, ctx);
        } else if (str.startsWith("lobby_status")) {
            lobbyStatus(client);
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
        onGameStarting();

        doStartGameTurn();
    }

    private void doStartGameTurn() {
        Client client = lobby.pollFirst();
        Ctx ctx = contexts.get(client.getId());

        this.current = client;
        onGameTurnStarting(client, ctx);
    }

    private void doEndGameTurn() {
        Client client = this.current;
        Ctx ctx = contexts.get(client.getId());
        onGameTurnEnding(client, ctx);

        lobby.offerLast(this.current);
        this.current = null;

        if (!isGameValid()) {
            doEndGame();
        }
    }

    private void doEndGame() {
        running = false;
        onGameEnding();
    }
}
