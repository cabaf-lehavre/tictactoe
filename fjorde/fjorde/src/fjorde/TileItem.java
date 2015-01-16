package fjorde;

/**
 * A TileItem represent a bit of a {@link fjorde.Tile}.
 * @author Antoine CHAUVIN
 */
public abstract class TileItem {
    public abstract boolean canJail();

    public abstract String getSymbol();
}
