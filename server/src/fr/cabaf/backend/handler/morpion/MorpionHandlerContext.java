package fr.cabaf.backend.handler.morpion;

import fr.cabaf.backend.handler.BaseGameContext;

public class MorpionHandlerContext extends BaseGameContext {
    private ModeleMorpion.Etat joueurId;

    public MorpionHandlerContext() {
    }

    public MorpionHandlerContext(ModeleMorpion.Etat joueurId) {
        this.joueurId = joueurId;
    }

    public ModeleMorpion.Etat getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(ModeleMorpion.Etat joueurId) {
        this.joueurId = joueurId;
    }
}
