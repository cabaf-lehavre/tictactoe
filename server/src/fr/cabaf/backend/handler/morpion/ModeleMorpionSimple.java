package fr.cabaf.backend.handler.morpion;


public class ModeleMorpionSimple implements ModeleMorpion {
	private Etat[][] cases;
	private int nbCoups;
	private Etat gagnant; // nullable

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
	}

	private boolean estVide(int i, int j) {
		return getValeur(i,j) == Etat.VIDE;
	}

	private void jouer(int x, int y, Etat joueur) {
		this.cases[x][y] = joueur;
		this.nbCoups++;

		Etat gagnant = null;
		for (int i = 0; i < 2; i++) {
			gagnant = gagnantSurLigne(i);
			if (gagnant != null) {
				break;
			}

			gagnant = gagnantSurColonne(i);
			if (gagnant != null) {
				break;
			}
		}

		if (gagnant == null) {
			gagnant = gagnantSurDiagonale(true);
		}
		if (gagnant == null) {
			gagnant = gagnantSurDiagonale(false);
		}

		this.gagnant = gagnant;
	}

	private Etat gagnantSurLigne(int i) {
		if (cases[i][0] == cases[i][1] && cases[i][1] == cases[i][2] && cases[i][0] != Etat.VIDE) {
			return cases[i][0];
		}
		return null;
	}

	private Etat gagnantSurColonne(int j) {
		if (cases[0][j] == cases[1][j] && cases[1][j] == cases[2][j] && cases[0][j] != Etat.VIDE) {
			return cases[0][j];
		}
		return null;
	}

	private Etat gagnantSurDiagonale(boolean descendante) {
		if (descendante && cases[0][0] == cases[1][1] && cases[1][1] == cases[2][2] && cases[1][1] != Etat.VIDE) {
			return cases[1][1];
		}
		if (!descendante && cases[2][0] == cases[1][1] && cases[1][1] == cases[0][2] && cases[1][1] != Etat.VIDE) {
			return cases[1][1];
		}
		return null;
	}

	public boolean estTerminee() {
		return gagnant != null || this.nbCoups >= ModeleMorpion.TAILLE * ModeleMorpion.TAILLE;
	}

	@Override
	public Etat getGagnant() {
		if (gagnant == null) {
			throw new IllegalStateException("there is no or never be a winner");
		}
		return gagnant;
	}

	public void cocher(int x, int y, Etat joueur) {
		if (this.estTerminee() || !this.estVide(x, y)) {
			return;
		}

		this.jouer(x, y, joueur);
	}

}