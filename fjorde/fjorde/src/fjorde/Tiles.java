package fjorde;

import java.io.File;
import java.util.*;

/**
 * Tiles contains utilities related to {@link fjorde.Tile}.
 * @author Antoine CHAUVIN
 */
public final class Tiles {
    private Tiles() {}

    private final static List<String> SYMBOLS;

    static {
        File baseDir = Textures.getTilesBaseDir();
        File[] children = baseDir.listFiles();
        if (children == null) {
            throw new Error("COULDN'T FIND IMAGE DIRECTORY");
        }

        List<String> symbols = new ArrayList<String>();
        for (File child : children) {
            if (!child.getName().endsWith("png")) {
                continue;
            }
            symbols.add(FileUtils.stripExt(child.getName()));
        }

        SYMBOLS = Collections.unmodifiableList(symbols);
    }

    /**
     * Randomly generate a valid tile symbol
     * @param random a non-null random generator
     * @return a non-null string representing
     */
    public static String sampleSymbol(Random random) {
        int i = random.nextInt(SYMBOLS.size());
        return SYMBOLS.get(i);
    }

    /**
     * Generate a tile by a given symbol
     * @param symbol a non-null string representing the symbol
     * @return a non-null tile
     */
    public static Tile fromSymbol(String symbol) {
        TileItem[] corners = new TileItem[symbol.length()];
        for (int i = 0; i < symbol.length(); i++) {
            char c = symbol.charAt(i);
            corners[i] = TileItems.create(c);
        }

        return new Tile(corners);
    }

    /**
     * Determine whether or not a symbol is valid
     * @param symbol a non-null string representing the symbol
     * @return a boolean
     */
    public static boolean isValidSymbol(String symbol) {
        return SYMBOLS.contains(symbol);
    }

    /**
     * Build a new symbol given an array of {@link fjorde.TileItem}
     * @param corners a non-null array
     * @return a non-null string representing a symbol
     */
    public static String newSymbol(TileItem[] corners) {
        StringBuilder res = new StringBuilder();

        for (TileItem corner : corners) {
            res.append(corner.getSymbol());
        }

        return res.toString();
    }

    public static boolean isValidCorners(TileItem[] corners) {
        return isValidSymbol(newSymbol(corners));
    }

    /**
     * Create a tile without neighbours
     * @param corners a non-null array of non-null items
     * @return a non-null tile
     */
    public static Tile of(TileItem... corners) {
        if (corners.length != Tile.CORNERS) {
            throw new IllegalArgumentException();
        }
        return new Tile(corners, new Tile[Tile.CORNERS]);
    }

    /**
     * Create a tile without neighbours using {@link fjorde.TileItems#create(int)}
     * @param indexes a non-null array of integer
     * @return a non-null tile
     */
    public static Tile of(int... indexes) {
        TileItem[] corners = new TileItem[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            corners[i] = TileItems.create(indexes[i]);
        }

        return of(corners);
    }

    /**
     * Randomly create a tile
     * @param random a non-null random generator
     * @return a non-null tile
     */
    public static Tile sample(Random random) {
        return fromSymbol(sampleSymbol(random));
    }

    /**
     * Randomly create a list of tiles
     * @param n the size of the resulting list
     * @param random a non-null random generator
     * @return a non-null list of non-null tiles
     */
    public static List<Tile> samples(int n, Random random) {
        List<Tile> samples = new LinkedList <Tile>();
        for (int i = 0; i < n; i++) {
            samples.add(sample(random));
        }
        return samples;
    }

    /**
     * Randomly create a list of tiles
     * @param n the size of the resulting list
     * @return a non-null list of non-null tiles
     */
    public static List<Tile> samples(int n) {
        return samples(n, new Random(System.nanoTime()));
    }
}
