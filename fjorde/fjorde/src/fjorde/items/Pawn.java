package fjorde.items;

import fjorde.Player;
import fjorde.PlayerItem;
import fjorde.Tile;

/**
 * @author Florentin BENARD
 * @author Brieuc DE TAPPIE
 */
public class Pawn extends PlayerItem
{
    public Pawn(Player player)
    {
        super(player);
    }

    @Override
    public boolean canPut(Tile tile) {
        Tile neighbour;

        if (tile.getItem() instanceof Jail || tile.getItem() instanceof Pawn) return false;

        for (int i = 0; i < Tile.CORNERS ; i++) {
            neighbour = tile.getNeighbour(i);

            if (neighbour == null || neighbour.getItem() == null || neighbour.getItem().getOwner() == null) {
                continue;
            }

            if (!(neighbour.getItem() instanceof Jail) && (!(neighbour.getItem() instanceof Pawn) || !neighbour.getItem().getOwner().equals(getOwner()))) {
                continue;
            }

            if (tile.getCorner(i) instanceof Plain || tile.getCorner((i + 1) % Tile.CORNERS) instanceof Plain) {
                return true;
            }
        }

        return false;
    }
}
