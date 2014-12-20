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
    protected boolean isGameValid() {
        return !morpion.estTerminee();
    }

    @Override
    protected MorpionHandlerContext onPlayerConnection(Client client) {
        ModeleMorpion.Etat joueurId = nextJoueurId;
        nextJoueurId = nextJoueurId.next();

        System.out.println("nouveau client " + joueurId);

        client.sendLine("joueur_id," + joueurId.ordinal());

        return new MorpionHandlerContext(joueurId);
    }

    @Override
    protected MorpionHandlerContext onPlayerReconnection(Client client, MorpionHandlerContext ctx, MorpionHandlerContext oldCtx) {
        return new MorpionHandlerContext(oldCtx.getJoueurId());
    }

    @Override
    protected void onPlayerReceive(Client client, MorpionHandlerContext ctx, String msg) {
        if (msg.startsWith("cocher")) {
            String[] args = msg.split(",");

            int x = Integer.parseInt(args[1]),
                y = Integer.parseInt(args[2]);

            morpion.cocher(x, y, ctx.getJoueurId());
            broadcast("cocher," + x + "," + y + "," + ctx.getJoueurId().ordinal());
            passTurn();
        }
    }

    @Override
    protected void onGameStarting() {
        morpion = new ModeleMorpionSimple();
        broadcast("start_game");
    }

    @Override
    protected void onGameEnding() {
        broadcast("end_game," + morpion.getGagnant().ordinal());
    }

    @Override
    protected void onGameTurnStarting(Client client, MorpionHandlerContext ctx) {
        broadcast("start_turn," + ctx.getJoueurId().ordinal());
    }

    @Override
    protected void onGameTurnEnding(Client client, MorpionHandlerContext ctx) {
        broadcast("end_turn," + ctx.getJoueurId().ordinal());
    }
}
