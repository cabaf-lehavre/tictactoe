package fjorde.ui;

import fjorde.*;
import fjorde.items.Jail;
import fjorde.items.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.NoSuchElementException;

public class Plateau extends JPanel {

    // TODO configure me
    public static final boolean DEBUG = Boolean.parseBoolean(System.getProperty("fjorde.debug", "true"));

    private TileSet tiles;
    private Polygon[][] tabP;
    private Map<String, Image> textures = Textures.loadTiles();

    public Plateau() {
        tiles = new TileSet(50, 50);
        tabP = new Polygon[50][50];

        // Create background polygons
        for (int i = 0; i < 50; i++) {
            int vY = i * 30;
            for (int j = 0; j < 50; j++) {
                int vX = j * 40;
                if (i % 2 != 0)
                    vX += 20; // impair X ( +20 to decay the polygon )
                int x1[] = {20 + vX, 40 + vX, 40 + vX, 20 + vX, 0 + vX, 0 + vX};
                int y1[] = {0 + vY, 10 + vY, 30 + vY, 40 + vY, 30 + vY, 10 + vY};
                tabP[i][j] = new Polygon(x1, y1, 6);

            }
        }

        // Basics tiles presents at start of the game
        Plateau.initTileSet(tiles);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }
        });
    }

    // with great powers come great responsabilities
    @SuppressWarnings("deprecation")
    public static void initTileSet(TileSet ts) {
        ts.init(9, 10, Tiles.of(TileItems.PLAIN, TileItems.SEA,
                TileItems.PLAIN, TileItems.PLAIN,
                TileItems.MOUNTAIN, TileItems.MOUNTAIN));
        ts.init(10, 11, Tiles.of(TileItems.PLAIN, TileItems.PLAIN,
                TileItems.PLAIN, TileItems.PLAIN,
                TileItems.PLAIN, TileItems.PLAIN));
        ts.init(10, 10, Tiles.of(TileItems.MOUNTAIN, TileItems.PLAIN,
                TileItems.PLAIN, TileItems.PLAIN,
                TileItems.PLAIN, TileItems.PLAIN));
    }

    private Point reduce(int x, int y) {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Polygon p = tabP[i][j];
                if (p.contains(x, y)) {
                    return new Point(i, j);
                }
            }
        }
        throw new NoSuchElementException("no polygon found on (" + x + "," + y + ")");
    }

    /**
     *
     * @param x Polygon absciss
     * @param y Polygon ordinate
     * @param tile Desired tile
     * @return {@code true} if the tile has been used, {@code false} otherwise
     */
    public boolean clic(int x, int y, Tile tile) {
        Point point = reduce(x, y);

        if (tiles.trySet(point.x, point.y, tile)) {
            repaint();
            return true;
        }
        return false;
    }

    /**
     *
     * @param x Polygon absciss
     * @param y Polygon ordinate
     * @param item Selected item
     * @return {@code false} if the item has been used, {@code false} otherwise
     */
    public boolean clic(int x, int y, PlayerItem item) {
        Point point = reduce(x, y);
        Tile tile = tiles.tryGet(point.x, point.y);

        if (tile != null && item.canPut(tile)) {
            tile.setItem(item);
            repaint();
            return true;
        }
        return false;
    }

    /**
     * Main method, which allows to "add" image to background
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point mouse = getMousePosition();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Polygon p   = tabP[i][j];
                Tile t      = tiles.tryGet(i, j);
                Rectangle b = p.getBounds();

                g.drawPolygon(p);

                if (DEBUG) {
                    g.setColor(Color.red);
                    g.drawString(i + "," + j, (int) b.getX() + 5, (int) b.getY() + 25);
                    g.setColor(Color.black);

                    if (mouse != null && p.contains(mouse)) {
                        drawNeighbours(g, i, j);
                    }
                }

                if (t != null) {
                    Image texture = textures.get(t.getSymbol());
                    g.drawImage(texture, (int) b.getX(), (int) b.getY(), null);

                    PlayerItem item = t.getItem();
                    if (item instanceof Jail) {
                        drawJailInMiddleOf(g, b);
                    } else if (item instanceof Pawn) {
                        drawPawnInMiddleOf(g, b);
                    }
                }
            }
        }
    }

    private void drawJailInMiddleOf(Graphics g, Rectangle bounds) {
        drawCircleInMiddleOf(g, bounds, 18);
    }

    private void drawPawnInMiddleOf(Graphics g, Rectangle bounds) {
        drawCircleInMiddleOf(g, bounds, 10);
    }

    private void drawCircleInMiddleOf(Graphics g, Rectangle bounds, int size) {
        g.fillOval((int) bounds.getCenterX() - size / 2, (int) bounds.getCenterY() - size / 2, size, size);
    }

    private void drawNeighbours(Graphics g, int x, int y) {
        Font oldFont = g.getFont();
        g.setFont(oldFont.deriveFont(Font.BOLD, 13));
        int[][] positions = tiles.aroundPosition(x, y);
        for (int i = 0; i < positions.length; i++) {
            int[] pos = positions[i];
            if (!tiles.inBounds(pos[0], pos[1])) {
                continue;
            }

            Polygon p = tabP[pos[0]][pos[1]];
            if (p == null) {
                continue;
            }
            Rectangle b = p.getBounds();
            g.drawString(Integer.toString(i), (int) b.getX() + 15, (int) b.getY() + 35);
        }
        g.setFont(oldFont);
    }
}