


public interface ModeleMorpion {


	enum Etat {
        VIDE, CROIX, ROND;

        public Etat backwards() {
            if (CROIX == this) return ROND;
            if (ROND == this) return CROIX;
            throw new IllegalStateException("VIDE n'a pas de backwards");
        }
    }

	int TAILLE = 3;

    /**
     * Notify from owner that the game must initialize.
     */
    void start();

	public void cocher(int x, int y); 

	public void recommencer();

	public boolean estTerminee();

	public boolean estGagnee();

	public Etat getJoueur();

	public Etat getValeur(int x, int y);

    public void setListener(ModeleMorpionListener listener);

}