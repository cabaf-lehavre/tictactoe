package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.handler.BaseGameContext;

/**
 * Created by Brieuc de Tappie on 10/01/2015.
 */
public class DiabalikHandlerContext extends BaseGameContext {
    private int joueurId;

    public DiabalikHandlerContext() {
    }

    public DiabalikHandlerContext(int joueurId) {
        this.joueurId = joueurId;
    }

    public int getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(int joueurId) {
        this.joueurId = joueurId;
    }
}
