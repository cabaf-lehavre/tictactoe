package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.frontend.Client;

/**
 * Created by Brieuc de Tappie on 10/01/2015.
 */
public interface ModeleDiabalikHandler {
    void onPlayerReceive(Client client, DiabalikHandlerContext ctx, String msg);
    void onGameTurnEnding(Client client, DiabalikHandlerContext ctx);
}
