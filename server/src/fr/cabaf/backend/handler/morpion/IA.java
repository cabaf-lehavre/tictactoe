package fr.cabaf.backend.handler.morpion;

/**
 * Created by Brieuc de Tappie on 07/01/15.
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
    public ModeleMorpion.Etat getId() {return id;}

    public void play() {
        if (ModeleMorpion.Etat.VIDE.equals(modele.getValue(1,1))) {
          aJouer(1,1);
            return;
        }
        int[] menace = estMenacer();
        if(!(menace[0]==-1 || menace[1]==-1)){
            aJouer(menace[0], menace[1]);
            return;
        }
        //Amelioration possible
        for(int i=0;i<ModeleMorpion.TAILLE;i++) {
            for(int j=0;j<ModeleMorpion.TAILLE;j++) {
                if(aJouer(i,j)) return;
            }

        }

    }
    public boolean aJouer(int x,int y) {
        lastX=x;
        lastY=y;
        boolean retour= modele.cocher(x,y,id);
        System.out.println(retour);
        return retour;
    }
    public int[] estMenacer(){
        int nbPionAdv;
        int[] caseMenacer= new int[2];
        for(int i=0;i<ModeleMorpion.TAILLE;i++) {
            nbPionAdv=0;
            caseMenacer[0]=i;
            caseMenacer[1]=-1;
            for(int j=0;j<ModeleMorpion.TAILLE;j++) {
                if(modele.getValue(i,j)!=id && modele.getValue(i,j)!=ModeleMorpion.Etat.VIDE) nbPionAdv++;
                if(modele.getValue(i,j)==ModeleMorpion.Etat.VIDE) caseMenacer[1]=j;
            }
            if(nbPionAdv==2)return caseMenacer;

        }
        for(int i=0;i<ModeleMorpion.TAILLE;i++) {
            nbPionAdv=0;
            caseMenacer[1]=i;
            caseMenacer[0]=-1;
            for(int j=0;j<ModeleMorpion.TAILLE;j++) {
                if(modele.getValue(j,i)!=id && modele.getValue(j,i)!=ModeleMorpion.Etat.VIDE) nbPionAdv++;
                if(modele.getValue(j,i)==ModeleMorpion.Etat.VIDE) caseMenacer[0]=j;
            }
            if(nbPionAdv==2)return caseMenacer;

        }
        nbPionAdv=0;
        caseMenacer[0]=-1;
        for(int i=0,j=0;i<ModeleMorpion.TAILLE;i++,j++) {
            if(modele.getValue(i,j)!=id && modele.getValue(j,i)!=ModeleMorpion.Etat.VIDE) nbPionAdv++;
            if(modele.getValue(i,j)==ModeleMorpion.Etat.VIDE){
                caseMenacer[0]=i;
                caseMenacer[1]=j;
            }
        }
        if(nbPionAdv==2)return caseMenacer;
        caseMenacer[0]=-1;
        for(int i=ModeleMorpion.TAILLE-1,j=ModeleMorpion.TAILLE-1;i>-1;i=i-1,j=j-1) {
            if(modele.getValue(i,j)!=id && modele.getValue(j,i)!=ModeleMorpion.Etat.VIDE) nbPionAdv++;
            if(modele.getValue(i,j)==ModeleMorpion.Etat.VIDE){
                caseMenacer[0]=i;
                caseMenacer[1]=j;
            }
        }
        if(nbPionAdv==2)return caseMenacer;
        caseMenacer[0]=-1;
        return caseMenacer;
    }
}
