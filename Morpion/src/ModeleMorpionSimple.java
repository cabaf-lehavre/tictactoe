



public class ModeleMorpionSimple implements ModeleMorpion {

	private Etat[][] cases;
	private Etat joueur;
	private int nbCoups;
	private boolean gagnee;

	public ModeleMorpionSimple() {
		this.cases = new Etat[ModeleMorpion.TAILLE][ModeleMorpion.TAILLE];
		initialiser();
	}

	@Override
	public void start() {

	}

	public boolean estTerminee() {
		return estGagnee() || this.nbCoups >= ModeleMorpion.TAILLE * ModeleMorpion.TAILLE;
	}

	public boolean estGagnee() {
		return gagnee;
	}

	private boolean estVide(int i, int j) {
		return getValeur(i,j) == Etat.VIDE;
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

		this.joueur = Etat.CROIX;
	}

	public Etat getJoueur() {
		return this.joueur;
	}

	private void changer() {
		if (this.joueur == Etat.CROIX) {
			this.joueur = Etat.ROND;
		} else {
			this.joueur = Etat.CROIX;
		}
	}

	private void jouer(int i, int j) {
		this.cases[i][j] = this.joueur;
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

	public void quitter() {
	}

	public void recommencer() {
		this.initialiser();
	}

	public void cocher(int x, int y) {
		if (!this.estTerminee()) {
			if (this.estVide(x, y)) {
				this.jouer(x, y);
				if (! this.estTerminee()) {
					this.changer();
				}
			}
		}
	}

    @Override
    public void setListener(ModeleMorpionListener listener) {

    }
}