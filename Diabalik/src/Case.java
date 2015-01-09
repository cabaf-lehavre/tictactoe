/**
 * Created by Cyril Alves on 09/01/2015.
 */
public class Case {

        private int proprietaire=0; //0: rien, 1:J1 2:J2
        private boolean estBalle=false;

        public Case(){}

        public void setProprietaire(int pro) {proprietaire=pro;}

        public void setBalle(boolean bool)  {estBalle=bool   ;}

        public String toString(){
            if(estBalle) return "x";
            else if(proprietaire!=0) return "o";
            else return "c";
        }
        public int getProprietaire(){return proprietaire;}

        public boolean getBalle()   {return estBalle;}
}
