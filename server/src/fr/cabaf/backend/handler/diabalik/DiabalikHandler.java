package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.handler.GameClientHandler;

/**
 * Created by Brieuc de Tappie on 10/01/2015.
 */
public class DiabalikHandler extends GameClientHandler<DiabalikHandlerContext> implements ModeleDiabalikHandler{

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
    public void onPlayerReceive(fr.cabaf.frontend.Client client, DiabalikHandlerContext ctx, String msg) {
        if (msg.startsWith("deplacement")) {
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
        } else if (msg.startsWith("passe")) {
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
        }

    @Override
    public void onGameTurnEnding(fr.cabaf.frontend.Client client, DiabalikHandlerContext ctx) {
        broadcast("end_turn," + ctx.getJoueurId());
    }
}
