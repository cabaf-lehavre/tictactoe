package fr.cabaf.backend.handler.diabalik;

/**
 * Created by db131357 on 07/01/15.
 */
public class Case {

        private int proprietaire; //0: rien, 1:J1 2:J2
        private boolean /*estSupport=false,*/ estBalle=false;
        public Case(){proprietaire=0;}
        public void setProprietaire(int pro) {proprietaire=pro;}
        //public void setSupport(boolean bool) {estSupport=bool ;}
        public void setBalle(boolean bool)  {estBalle=bool   ;}
        public String toString(){
            if(estBalle) return "x";
            else if(proprietaire!=0) return "o";
            else return "c";
        }
        public int getProprietaire(){return proprietaire;}
        //public boolean getSupport() {return estSupport;}
        public boolean getBalle()   {return estBalle;}
}
