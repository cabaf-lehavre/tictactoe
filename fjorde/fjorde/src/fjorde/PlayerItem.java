package fjorde;

public abstract class PlayerItem
{
    private Player owner;

    public PlayerItem(Player owner)
    {
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public abstract boolean canPut(Tile tile);

    @Override
    public String toString()
    {
        return "PlayerItem{" +
                "owner=" + owner +
                '}';
    }
}