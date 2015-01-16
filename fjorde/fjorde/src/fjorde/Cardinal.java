package fjorde;

/**
 * Cardinal represent the eight cardinal points.
 * @author Antoine CHAUVIN
 */
public enum Cardinal {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
    ;

    public static int neighbourIndexOpposite(int i) {
        return i < 3 ? i + 3 : i - 3;
    }
}
