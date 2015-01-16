package fjorde.items;

import fjorde.TileItem;

public class Mountain extends TileItem {
    @Override
    public boolean canJail() {
        // TODO
        return false;
    }

    @Override
    public String getSymbol() {
        return "M";
    }
}
