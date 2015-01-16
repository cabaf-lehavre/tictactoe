package fjorde.ui;

import fjorde.Bag;
import fjorde.Player;
import fjorde.PlayerItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexandre BAPTISTE
 */

public class JailsPawn extends JPanel implements ActionListener{

    JButton jailButton;
    JButton pawnButton;
    JLabel remainingJails;
    JLabel remainingPaws;
    Player player;
    Bag bag;
    int buttonSelection;


    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == jailButton ) {
            if (buttonSelection != 1) {
                buttonSelection = 1;
                jailButton.setBackground(Color.red);
                pawnButton.setBackground(Color.white);
            } else {
                clearSelection();
            }
        }

        if ( e.getSource() == pawnButton ) {
            if (buttonSelection != 2) {
                buttonSelection = 2;
                pawnButton.setBackground(Color.red);
                jailButton.setBackground(Color.white);
            } else {
                clearSelection();
            }
        }
    }

    /**
     * the only one UI parts which has a GridLayout,
     * still adding buttons and stuff
     */
    public JailsPawn(Player player) {
        this.player = player;
        this.bag = new Bag(player, 4, 20);

        setLayout(new GridLayout(2,1));

        jailButton = new JButton();
        pawnButton = new JButton();
        remainingJails = new JLabel();
        remainingPaws = new JLabel();
        jailButton.setBackground(Color.WHITE);
        pawnButton.setBackground(Color.WHITE);
        jailButton.setIcon(new ImageIcon("img/jail.png"));
        pawnButton.setIcon(new ImageIcon("img/pawn.png"));

        updateCounters();

        add(jailButton);
        add(remainingJails);
        add(pawnButton);
        add(remainingPaws);

        jailButton.addActionListener(this);
        pawnButton.addActionListener(this);
    }

    /**
     * just the number of remaining jail/paws
     */
    private void updateCounters() {
        remainingJails.setText("       x    " + bag.getRemainingJails());
        remainingPaws.setText("        x   " + bag.getRemainingPawns());
    }

    private void clearSelection() {
        buttonSelection = 0;
        jailButton.setBackground(Color.white);
        pawnButton.setBackground(Color.white);
    }

    /**
     * just to know which one of jail/pawn has been selected
     * @return jail/pawn
     */
    public PlayerItem getSelectedItem() {
        try {
            switch (buttonSelection) {
                case 1:
                    return bag.getJail();
                case 2:
                    return bag.getPawn();
                default:
                    return null;
            }
        } finally {
            clearSelection();
            updateCounters();
        }
    }

    public void putItem(PlayerItem item) {
        bag.put(item);
        updateCounters();
    }

}
