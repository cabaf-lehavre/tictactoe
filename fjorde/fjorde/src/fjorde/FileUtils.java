package fjorde;

/**
 * @author Antoine CHAUVIN INFOB1
 */
public final class FileUtils {
    private FileUtils() {}

    /**
     * Strip the extension of a file name
     * @param filename a non-null string representing the file name
     * @return a non-null string representing the stripped file name
     */
    public static String stripExt(String filename) {
        int lo = filename.lastIndexOf('/');
        if (lo < 0) {
            lo = 0;
        }

        int hi = filename.lastIndexOf('.');
        if (hi < lo) {
            hi = filename.length() + 1;
        }

        return filename.substring(lo, hi);
    }
}
