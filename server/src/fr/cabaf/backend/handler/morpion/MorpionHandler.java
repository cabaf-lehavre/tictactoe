package fr.cabaf.backend.handler.morpion;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.handler.GameClientHandler;

public class MorpionHandler extends GameClientHandler<MorpionHandlerContext> {

    private ModeleMorpion morpion;
    private ModeleMorpion.Etat nextJoueurId = ModeleMorpion.Etat.CROIX;

    @Override
    protected int getGameNrPlayers() {
        return 2;
    }

    @Override
    protected MorpionHandlerContext onPlayerConnection(Client client) {
        ModeleMorpion.Etat joueurId = nextJoueurId;
        nextJoueurId = nextJoueurId.next();

        client.sendLine("joueur_id," + nextJoueurId.ordinal());

        return new MorpionHandlerContext(joueurId);
    }

    @Override
    protected MorpionHandlerContext onPlayerReconnection(Client client, MorpionHandlerContext ctx, MorpionHandlerContext oldCtx) {
        return new MorpionHandlerContext(oldCtx.getJoueurId());
    }

    @Override
    protected void onPlayerDisconnection(Client client, MorpionHandlerContext ctx) {

    }

    @Override
    protected void onPlayerEntering(Client client, MorpionHandlerContext ctx) {

    }

    @Override
    protected void onPlayerQuitting(Client client, MorpionHandlerContext ctx) {

    }

    @Override
    protected void onGameStarting() {
        morpion = new ModeleMorpionSimple();

        for (Client client : getLobby()) {
            client.sendLine("game_start");
        }
    }

    @Override
    protected void onGameEnding() {
        morpion = null;
    }

    @Override
    protected boolean isGameValid() {
        return !morpion.estTerminee();
    }

    @Override
    protected void onGameTurnStarting(Client client, MorpionHandlerContext ctx) {

    }

    @Override
    protected void onGameTurnEnding(Client client, MorpionHandlerContext ctx) {

    }
}
