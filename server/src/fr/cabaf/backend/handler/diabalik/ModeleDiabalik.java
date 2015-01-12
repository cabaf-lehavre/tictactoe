package fr.cabaf.backend.handler.diabalik;

/**
 * Created by db131357 on 06/01/15.
 */
public interface ModeleDiabalik {


    int TAILLE = 7;
    public boolean estTerminee();
    public boolean deplacer(int x,int y,int xD, int yD,int proprietaire);

    public boolean passe(int x,int y, int xD,int yD, int propritetaire);
    public boolean aGagne(int proprietaire);


}

