/**
 * Created by Cyril Alves on 09/01/2015.
 */
public interface ModeleDiabalik {


    int TAILLE = 7;

    public boolean deplacer(int x,int y,int xD, int yD,int proprietaire);

    public boolean passe(int x,int y, int xD,int yD, int propritetaire);
    public boolean aGagne(int proprietaire);

}