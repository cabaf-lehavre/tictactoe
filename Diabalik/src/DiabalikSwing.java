/**
 * Created by Cyril Alves on 09/01/2015.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;



public class DiabalikSwing {

    private ModeleDiabalikSimple modele;
    private JFrame fenetre;

    private final JLabel[][] cases = new JLabel[7][7];

    public DiabalikSwing() {
        this(new ModeleDiabalikSimple());
    }

    public DiabalikSwing(NetworkModelDiabalik networkModelDiabalik) {
    }

    public DiabalikSwing(ModeleDiabalikSimple modele) {

        this.modele = modele;

        for (int i = 0; i < this.cases.length; i++) {
            for (int j = 0; j < this.cases[i].length; j++) {
                this.cases[i][j] = new JLabel();
            }
        }

        this.fenetre = new JFrame("Diaballik");
        this.fenetre.setLocation(100, 200);

        Container contenu = this.fenetre.getContentPane();
        contenu.setLayout(new GridLayout(7,7));

        //	Positionner les composants swing
        //	- les cases
        for (int i = 0; i < this.cases.length; i++) {
            for (int j = 0; j < this.cases[i].length; j++) {
                contenu.add(this.cases[i][j]);
            }
        }

        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.fenetre.pack();			// redimmensionner la fenêtre
        this.fenetre.setVisible(true);	// l'afficher
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiabalikSwing();
            }
        });
    }
}


