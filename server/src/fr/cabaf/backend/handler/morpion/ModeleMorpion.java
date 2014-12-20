package fr.cabaf.backend.handler.morpion;


public interface ModeleMorpion {


	enum Etat {
		VIDE, CROIX, ROND;

		public Etat next() {
			if (this == CROIX) return ROND;
			throw new IllegalStateException();
		}

		public Etat reset() {
			return CROIX;
		}
	}

	int TAILLE = 3;

	public void cocher(int x, int y, Etat joueur);

	public boolean estTerminee();

	public boolean estGagnee();

}