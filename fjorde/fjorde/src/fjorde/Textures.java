package fjorde;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Textures {
    private Textures() {}

    /**
     * Get the default base directory containing tile textures
     * @return a non-null file
     */
    public static File getTilesBaseDir() {
        String path = System.getProperty("fjorde.img.tiles", "img/tiles/");
        File file = new File(path);
        if (!file.exists()) {
            throw new Error("DIRECTORY CONTAINING TILE TEXTURES "+path+" DOESNT EXIST, PLEASE USE -Dfjorde.img.tiles PROPERTY");
        }
        return file;
    }

    /**
     * Load all textures present in a directory
     * @param baseDir a non-null file
     * @return a non-null map from image name to non-null texture
     */
    public static Map<String, Image> load(File baseDir) {
        File[] children = baseDir.listFiles();
        if (children == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Image> textures = new HashMap<String, Image>();

        for (File child : children) {
            if (child.isDirectory() || !child.getName().endsWith("png")) {
                continue;
            }

            try {
                BufferedImage img = ImageIO.read(child);
                String name = FileUtils.stripExt(child.getName());
                textures.put(name, img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return textures;
    }

    /**
     * Load all textures present in a directory
     * @param baseDir a non-null string representing the base directory
     * @return a non-null map from image name to non-null texture
     */
    public static Map<String, Image> load(String baseDir) {
        return load(new File(baseDir));
    }

    /**
     * Load all tile textures
     * @return a non-null map from tile symbol to non-null texture
     */
    public static Map<String, Image> loadTiles() {
        return load(getTilesBaseDir());
    }

}
