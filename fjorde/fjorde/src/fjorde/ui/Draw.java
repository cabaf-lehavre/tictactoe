package fjorde.ui;

import fjorde.Deck;
import fjorde.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Alexandre BAPTISTE
 */
public class Draw extends JPanel implements MouseListener {

    private Deck opened = new Deck(0);
    private Deck closed = new Deck(40);

    private int selectedDeck;

    JButton previousTile;
    JButton nextTile;
    JButton openedTileButton;

    JButton drawButton;
    JButton depositButton;
    JButton closedTileButton;

    /**
     * draw a tile in the Closed Draw
     */
    public void closedDraw() {
        closed.draw(); // draw and forget
        updateSprites();
        clearDeckSelection();
    }

    /**
     * depose the top tile of Closed in Opened
     */
    public void closedDeposit(){
        opened.deposit(closed.draw()); // draw and put in open deck
        updateSprites();
        clearDeckSelection();
    }

    /**
     * Updating ( repaint ) ALL icon method,
     * preferable to do repaint in each method and call it to each others
     */
    public void updateSprites() {
        Tile cls = closed.peek();
        if (cls != null) {
            closedTileButton.setIcon(new ImageIcon(String.format("img/tiles/%s.png", cls.getSymbol())));
        } else {
            closedTileButton.setIcon(null);
        }

        Tile opn = opened.peek();
        if (opn != null) {
            openedTileButton.setIcon(new ImageIcon(String.format("img/tiles/%s.png", opn.getSymbol())));
        } else {
            openedTileButton.setIcon(null);
        }

        repaint();
    }

    /**
     * Iterate Opened deck using {@link fjorde.Deck#shiftLeft()}
     */
    public void previousOpened(){
        opened.shiftLeft();
        updateSprites();
        clearDeckSelection();
    }

    /**
     * Iterate Opened deck using {@link fjorde.Deck#shiftRight()}
     */
    public void nextOpened(){
        opened.shiftRight();
        updateSprites();
        clearDeckSelection();
    }

    /**
     * Just constructor with all UI stuff
     */
    public Draw(){
        setLayout(null);

        drawButton    = new JButton("Draw");
        depositButton = new JButton("Deposit");

        previousTile     = new JButton();
        nextTile         = new JButton();
        closedTileButton = new JButton();
        openedTileButton = new JButton();


        previousTile.setBackground(Color.WHITE);
        nextTile.setBackground(Color.WHITE);
        closedTileButton.setBackground(Color.WHITE);
        openedTileButton.setBackground(Color.WHITE);
        previousTile.setIcon(new ImageIcon("img/previousArrow.png"));
        nextTile.setIcon(new ImageIcon("img/nextArrow.jpg"));


        closedTileButton.setBounds(55,5,50,50);
        drawButton.setBounds(10,60,70,40);
        depositButton.setBounds(83,60,70,40);
        openedTileButton.setBounds(55,110,50,50);
        previousTile.setBounds(10,165,70,40);
        nextTile.setBounds(83,165,70,40);

        add(closedTileButton);
        add(drawButton);
        add(depositButton);
        add(openedTileButton);
        add(previousTile);
        add(nextTile);

        previousTile.addMouseListener(this);
        nextTile.addMouseListener(this);
        drawButton.addMouseListener(this);
        depositButton.addMouseListener(this);
        openedTileButton.addMouseListener(this);
        closedTileButton.addMouseListener(this);

        updateSprites();
    }

    /**
     * redefine actionPerformed for each button
     * @param e, the event
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if ( e.getSource() == nextTile ) {
            nextOpened();
        }

        if ( e.getSource() == previousTile ) {
            previousOpened();
        }

        if ( e.getSource() == openedTileButton ) {
            if (SwingUtilities.isRightMouseButton(e)) {
                try {
                    opened.peek().rotate();
                    updateSprites();
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(this, String.format(
                        "alexandre c un pd %s",
                            opened.peek().getSymbol()));
                }
            } else if (selectedDeck != 1) {
                selectedDeck = 1;
                openedTileButton.setBackground(Color.red);
                closedTileButton.setBackground(Color.white);
            } else {
                clearDeckSelection();
            }
        }

        if ( e.getSource() == closedTileButton ) {
            if (SwingUtilities.isRightMouseButton(e)) {
                try {
                    closed.peek().rotate();
                    updateSprites();
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(this, String.format(
                        "alexandre c un pd %s",
                            closed.peek().getSymbol()));
                }
            } else if (selectedDeck != 2) {
                selectedDeck = 2;
                openedTileButton.setBackground(Color.white);
                closedTileButton.setBackground(Color.red);
            } else {
                clearDeckSelection();
            }
        }

        if ( e.getSource() == drawButton ) {
            closedDraw();
        }

        if ( e.getSource() == depositButton ) {
            closedDeposit();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * method use to know which tile is selected and used
     * @return the selected one
     */
    public Tile getSelectedTile() {
        try {
            if (selectedDeck == 1) {
                return opened.draw();
            } else if (selectedDeck == 2) {
                return closed.draw();
            } else {
                return null;
            }
        } finally {
            clearDeckSelection();
            updateSprites();
        }
    }

    public void putTile(Tile tile) {
        opened.deposit(tile);
        updateSprites();
    }

    /**
     * If player finally doesn't want to use this tile,
     * he can re-put it again in the deck,
     * it's like a "cancel" action
     */
    public void clearDeckSelection() {
        selectedDeck = 0;
        openedTileButton.setBackground(Color.white);
        closedTileButton.setBackground(Color.white);
    }
}
