package fjorde.items;

import fjorde.Player;
import fjorde.PlayerItem;
import fjorde.Tile;
import fjorde.TileItem;

/**
 * @author Florentin BENARD
 * @author Brieuc DE TAPPIE
 */

public class Jail extends PlayerItem
{
    public Jail(Player player)
    {
        super(player);
    }


    @Override
    public boolean canPut(Tile tile) {
        int i;
        for (i = 0; i < Tile.CORNERS; i++) {
            TileItem ti = tile.getCorner(i);
            if(ti instanceof Plain)
                break;
        }
        if(i==Tile.CORNERS)
            return false;
        if (tile.getItem()==null)
            return true;
        else return false;
    }
}
