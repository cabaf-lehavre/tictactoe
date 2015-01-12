package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.Client;

/**
 * Created by db131357 on 06/01/15.
 */
public interface ModeleDiabalik {


    int TAILLE = 7;
    boolean deplacer(int x,int y,int xD, int yD,int proprietaire);

    boolean passe(int x,int y, int xD,int yD, int propritetaire);
    boolean aGagne(int proprietaire);



    boolean estTerminee();
}

