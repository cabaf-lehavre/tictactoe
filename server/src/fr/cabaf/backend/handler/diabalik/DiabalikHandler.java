package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.handler.GameClientHandler;

/**
 * Created by Brieuc de Tappie on 10/01/2015.
 */
public class DiabalikHandler extends GameClientHandler<DiabalikHandlerContext> implements ModeleDiabalikHandler{

    ModeleDiabalikSimple diabalik;
    int nextJoueurId=1;
    @Override
    protected DiabalikHandlerContext onPlayerConnection(Client client) {
        int  joueurId = nextJoueurId;
        nextJoueurId = nextJoueurId++;

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
        if (msg.startsWith("deplacer") || msg.startsWith("cocher") )
        {

        }
        /*if (msg.startsWith("cocher")) {
            String[] args = msg.split(",");

            int x = Integer.parseInt(args[1]),
                    y = Integer.parseInt(args[2]);

            if(diabalik.cocher(x, y, ctx.getJoueurId())) {
                broadcast("cocher," + x + "," + y + "," + ctx.getJoueurId().ordinal());
                passTurn();
            }
        }*/
    }

    @Override
    public void onGameTurnEnding(fr.cabaf.frontend.Client client, DiabalikHandlerContext ctx) {

    }
}
