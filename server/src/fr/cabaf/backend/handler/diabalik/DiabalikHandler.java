package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.handler.GameClientHandler;

/**
 * Created by Brieuc de Tappie on 10/01/2015.
 */
public class DiabalikHandler extends GameClientHandler<DiabalikHandlerContext> {

    ModeleDiabalikSimple diabalik;
    int nextJoueurId=1;
    int moveJoueurCurrent = 0;
    boolean aPasserCurrent = false;

    @Override
    protected DiabalikHandlerContext onPlayerConnection(Client client) {
        int joueurId = client.getId();

        System.out.println("nouveau client " + joueurId);

        client.sendLine("joueur_id," + joueurId);

        return new DiabalikHandlerContext(joueurId);
    }

    @Override
    protected boolean isGameValid() {
        return !diabalik.estTerminee();
    }

    @Override
    protected int getGameNrPlayers() {
        return 2;
    }

    @Override
    public void onPlayerReceive(Client client, DiabalikHandlerContext ctx, String msg) {
        if (msg.startsWith("deplacer")) {
            if (moveJoueurCurrent < 2) {

                String[] args = msg.split(",");

                int x = Integer.parseInt(args[1]),
                        y = Integer.parseInt(args[2]),
                        xD = Integer.parseInt(args[3]),
                        yD = Integer.parseInt(args[4]);
                if (diabalik.deplacer(x, y, xD, yD, ctx.getJoueurId())) {
                    moveJoueurCurrent++;
                    broadcast("deplacement," + x + "," + y + "," + xD + "," + yD + "," + ctx.getJoueurId());
                }
            }
        } else if (msg.startsWith("passer")) {
            if (!aPasserCurrent) {

                String[] args = msg.split(",");

                int x = Integer.parseInt(args[1]),
                        y = Integer.parseInt(args[2]),
                        xD = Integer.parseInt(args[3]),
                        yD = Integer.parseInt(args[4]);
                if (diabalik.passe(x, y, xD, yD, ctx.getJoueurId())) {
                    aPasserCurrent = true;
                    broadcast("passe," + x + "," + y + "," + xD + "," + yD + "," + ctx.getJoueurId());
                }
            }
        }
        if (msg.startsWith("end_tour") || moveJoueurCurrent == 2 && aPasserCurrent == true) {
            moveJoueurCurrent = 0;
            aPasserCurrent = false;
            passTurn();
        } else {
            // he still can play, notifies it then :)
            client.sendLine("play");
        }
    }

    @Override
    protected void onGameStarting() {
        broadcast("start_game");
    }

    protected void onGameEnding() {
            broadcast("end_game," + diabalik.getGagnant());
        }

        @Override
        protected void onGameTurnStarting(Client client, DiabalikHandlerContext ctx) {
            broadcast("start_turn," + ctx.getJoueurId());

            // notifies the current player that he can play
            client.sendLine("play");
        }

    @Override
    public void onGameTurnEnding(Client client, DiabalikHandlerContext ctx) {
        broadcast("end_turn," + ctx.getJoueurId());
    }
}
