import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


public class MorpionSwing implements ModeleMorpionListener {


	private static final Map <ModeleMorpion.Etat, ImageIcon> images
		= new HashMap<ModeleMorpion.Etat, ImageIcon>();
	static {
        images.put(ModeleMorpion.Etat.VIDE, new ImageIcon("blanc.jpg"));
		images.put(ModeleMorpion.Etat.CROIX, new ImageIcon("croix.jpg"));
		images.put(ModeleMorpion.Etat.ROND, new ImageIcon("rond.jpg"));
	}

	private ModeleMorpion modele;
	private JFrame fenetre;

	private final JButton boutonQuitter = new JButton("Q");
	private final JButton boutonNouvellePartie = new JButton("N");
	private final JLabel[][] cases = new JLabel[3][3];
	private final JLabel joueur = new JLabel();
	private final ActionListener actionRecommencer = new ActionRecommencer();
	private final ActionListener actionQuitter = new ActionQuitter();

	public MorpionSwing() {
		this(new ModeleMorpionSimple());
	}

	public MorpionSwing(ModeleMorpion modele) {
		this.modele = modele;
        this.modele.setListener(this);
		this.modele.start();

		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j] = new JLabel();
			}
		}

		this.recommencer();

		this.fenetre = new JFrame("Morpion");
		this.fenetre.setLocation(100, 200);

		//	Gestionnaire de placement
		Container contenu = this.fenetre.getContentPane();
		contenu.setLayout(new GridLayout(4,3));

		//	Positionner les composants swing
		//	- les cases
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				contenu.add(this.cases[i][j]);
			}
		}
		//	- les boutons
		contenu.add(boutonNouvellePartie);
		contenu.add(joueur);
		contenu.add(boutonQuitter);

		//	- une barre de menus
		JMenuBar jmb = new JMenuBar();
		JMenu menuJeu = new JMenu("Jeu");
		jmb.add(menuJeu);
		JMenuItem menuNouvelle = new JMenuItem("Nouvelle partie");
		JMenuItem  menuQuitter = new JMenuItem("Quitter");
		this.fenetre.setJMenuBar(jmb);
		menuJeu.add(menuNouvelle);
		menuJeu.addSeparator();
		menuJeu.add(menuQuitter);

		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boutonQuitter.addActionListener(this.actionQuitter);
		boutonNouvellePartie.addActionListener(this.actionRecommencer);
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j].addMouseListener(new ActionCliquer(i, j));
			}
		}
		menuNouvelle.addActionListener(this.actionRecommencer);
		menuQuitter.addActionListener(this.actionQuitter);

		
		this.fenetre.pack();			// redimmensionner la fenêtre
		this.fenetre.setVisible(true);	// l'afficher
	}

    @Override
    public void setCase(int x, int y, ModeleMorpion.Etat etat) {
		System.out.println("lol");
		cases[x][y].setIcon(images.get(etat));
		fenetre.repaint();
    }

	@Override
	public void endGame(ModeleMorpion.Etat gagnant) {
		if (gagnant == modele.getJoueur()) {
			new StatusWindow("Vous avez gagné \\o/");
		} else {
			new StatusWindow("Vous avez perdu :-(");
		}
		fenetre.dispose();
	}

	public void recommencer() {
		this.modele.recommencer();

		// Vider les cases
		for (int i = 0; i < this.cases.length; i++) {
			for (int j = 0; j < this.cases[i].length; j++) {
				this.cases[i][j].setIcon(images.get(this.modele.getValeur(i, j)));
			}
		}

		joueur.setIcon(images.get(modele.getJoueur()));
	}

	private class ActionQuitter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Fin...");
			System.exit(0);
		}
	}

	private class ActionRecommencer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			recommencer();
		}
	}

	private class ActionCliquer extends MouseAdapter {
		private int x;	// abscisse de la case
		private int y;	// ordonnée de la case

		public ActionCliquer(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

				modele.cocher(x, y);
				joueur.setIcon(images.get(modele.getJoueur()));

				// Fin de partie ?
				if (modele.estGagnee()) {
					JOptionPane.showMessageDialog(null, "gagne !",
							"Résultats", JOptionPane.INFORMATION_MESSAGE,
							images.get(modele.getJoueur()));
					recommencer();

				} else if (modele.estTerminee()) {
					JOptionPane.showMessageDialog(null, "Il n'y a pas de vainqueurs !",
						"Résultats", JOptionPane.INFORMATION_MESSAGE);
					recommencer();
				}
			
			}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MorpionSwing();
			}
		});
	}
}