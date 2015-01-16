package fjorde;

import fjorde.items.Mountain;
import fjorde.items.Plain;
import fjorde.items.Sea;

import java.util.Random;

/**
 * TileItems provide many utilities related to {@link fjorde.TileItem}.
 * @author Antoine CHAUVIN
 */
public final class TileItems {
    private TileItems() {}

    public static final int SEA = 1, PLAIN = 2, MOUNTAIN = 3;

    public static Sea createSea() {
        return new Sea();
    }

    public static Plain createPlain() {
        return new Plain();
    }

    public static Mountain createMountain() {
        return new Mountain();
    }

    /**
     * Create an item using an index
     * @param n an integer ranged between 1 and 3
     * @return a non-null item
     * @throws java.lang.IllegalArgumentException if given a invalid index
     */
    public static TileItem create(int n) {
        switch (n) {
            case SEA:      return createSea();
            case PLAIN:    return createPlain();
            case MOUNTAIN: return createMountain();

            default:
                throw new IllegalArgumentException(String.format(
                    "%d is an invalid TileItem identifier",
                        n));
        }
    }
    public static TileItem create(char c) {
        switch (c) {
            case 'M': return createMountain();
            case 'S': return createSea();
            case 'P': return createPlain();
            default: throw new Error("UNEXPECTED TILE_ITEM "+c);
        }
    }

    /**
     * Randomly create an item
     * @param random a non-null random generator
     * @return a non-null item
     */
    public static TileItem sample(Random random) {
        return create(random.nextInt(3) + 1);
    }

    /**
     * Randomly create multiple items
     * @param n an integer representing the result's array length
     * @param random a non-null random generator
     * @return a non-null array of non-null item
     */
    public static TileItem[] samples(int n, Random random) {
        TileItem[] items = new TileItem[6];
        for (int i = 0; i < n; i++) {
            items[i] = TileItems.sample(random);
        }
        return items;
    }

    /**
     * Randomly create multiple items
     * @param n an integer representing the result's array length
     * @return a non-null array of non-null item
     */
    public static TileItem[] samples(int n) {
        return samples(n, new Random(System.nanoTime()));
    }
}
