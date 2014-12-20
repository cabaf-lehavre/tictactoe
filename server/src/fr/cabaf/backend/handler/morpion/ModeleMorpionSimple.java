package fr.cabaf.backend.handler.morpion;


public class ModeleMorpionSimple implements ModeleMorpion {
	private Etat[][] cases;
	private int nbCoups;
	private boolean gagnee;

	public ModeleMorpionSimple() {
		this.cases = new Etat[ModeleMorpion.TAILLE][ModeleMorpion.TAILLE];
		initialiser();
	}

	public Etat getValeur(int x, int y) {
		return this.cases[x][y];
	}

	private void initialiser() {
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j] = Etat.VIDE;
			}
		}
		this.nbCoups = 0;
		gagnee = false;
	}

	private boolean estVide(int i, int j) {
		return getValeur(i,j) == Etat.VIDE;
	}

	private void jouer(int i, int j, Etat joueur) {
		this.cases[i][j] = joueur;
		this.nbCoups++;

		gagnee = gagnee ||
				((cases[i][0] == cases[i][1]	// ligne pleine
						&& cases[i][1] == cases[i][2])
						|| (cases[0][j] == cases[1][j]	// colonne pleine
						&& cases[1][j] == cases[2][j])
						|| (i == j	// première diagonale pleine
						&& cases[0][0] == cases[1][1]
						&& cases[1][1] == cases[2][2])
						|| (i + j == 2	// deuxième diagonale pleine
						&& cases[0][2] == cases[1][1]
						&& cases[1][1] == cases[2][0]));
	}

	public boolean estTerminee() {
		return estGagnee() || this.nbCoups >= ModeleMorpion.TAILLE * ModeleMorpion.TAILLE;
	}

	public boolean estGagnee() {
		return gagnee;
	}

	public void cocher(int x, int y, Etat joueur) {
		if (this.estTerminee() || !this.estVide(x, y)) {
			return;
		}

		this.jouer(x, y, joueur);
	}

}