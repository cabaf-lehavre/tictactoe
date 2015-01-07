package fr.cabaf.backend.handler.morpion;

/**
 * Created by db131357 on 07/01/15.
 */
public class IA {
    private final ModeleMorpion modele;
    private final ModeleMorpion.Etat id;
    private int lastX, lastY;

    public IA(ModeleMorpion modele, ModeleMorpion.Etat id) {
        this.modele = modele;
        this.id = id;
    }

    public int getLastPlayedX() {return  lastX;}
    public int getLastPlayedY() {return  lastY;}

    public void play() {


    }
}
