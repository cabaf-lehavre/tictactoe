package fjorde;

import fjorde.items.Jail;
import fjorde.items.Pawn;

/**
 * @author Alexandre BAPTISTE
 * @author Antoine CHAUVIN
 */
public class Bag {
    private final Player player;
    private int remainingJails;
    private int remainingPawns;

    /**
     *
     * @param player, bag's owner
     * @param remainingJails current number of jails
     * @param remainingPawns current number of pawns
     */
    public Bag( Player player, int remainingJails, int remainingPawns ){
        this.player = player;
        this.remainingJails = remainingJails;
        this.remainingPawns = remainingPawns;

    }

    public int getRemainingJails(){
        return remainingJails;
    }

    public int getRemainingPawns() {
        return remainingPawns;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * "drawing" the pawn, decrease current
     * @return the player Pawn
     */
    public Pawn getPawn() {
        if (remainingPawns <= 0) {
            return null;
        }
        remainingPawns --;
        return new Pawn(player);
    }

    /**
     * "drawing" the jail, decrease current
     * @return the player jail
     */
    public Jail getJail() {
        if (remainingJails <= 0) {
            return null;
        }
        remainingJails --;
        return new Jail(player);
    }

    public void put(PlayerItem item) {
        if (item instanceof Pawn) {
            remainingPawns++;
        } else if (item instanceof Jail) {
            remainingJails++;
        }
    }
}
