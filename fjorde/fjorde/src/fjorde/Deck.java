package fjorde;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexandre BAPTISTE
 * @author Antoine CHAUVIN
 */
public class Deck {

    private List<Tile> remaining;

    public Deck(List<Tile> remaining) {
        this.remaining = new LinkedList<Tile>(remaining);
        Collections.shuffle(this.remaining);
    }

    public Deck(int size) {
        // no need to shuffle a list of random tiles
        this.remaining = Tiles.samples(size);
    }

    // Drawn last Tile in the deck
    public Tile draw() {
        // return nothing if there is no tile left
        if (remaining.isEmpty()) {
            return null;
        }
        return remaining.remove(0);
    }

    public Tile peek() {
        if (remaining.isEmpty()) {
            return null;
        }
        return remaining.get(0);
    }

    public void deposit(Tile tile){
        if (tile == null) {
            return;
        }
        remaining.add(0, tile);
    }

    public void shiftRight() {
        if (remaining.isEmpty()) {
            return;
        }
        remaining.add(remaining.remove(0));
    }

    public void shiftLeft() {
        if (remaining.isEmpty()) {
            return;
        }
        remaining.add(0, remaining.remove(remaining.size() - 1));
    }

    private List<Tile> draw(int nbTile) {
        List<Tile> res = new LinkedList<Tile>();
        for (int i = 0; i < nbTile; i++) {
            res.add(draw());
        }
        return res;
    }

    public Tile getTile(int i) {
        return remaining.get(i);
    }

    public int getSize() { return remaining.size(); }
}

