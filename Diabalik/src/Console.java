import java.util.Scanner;

/**
 * @author Antoine CHAUVIN INFOB1
 */
public class Console {
    public static void print(String msg) {
        System.out.print(msg);
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static String readString(String msg) {
        Scanner scanner = new Scanner(System.in);
        print(msg);
        return scanner.nextLine();
    }

    public static int readInt(String msg) {
        String str = readString(msg);
        return Integer.parseInt(str, 10);
    }

    public static char readCharacter(String msg) {
        String str = readString(msg);
        return str.charAt(0);
    }

    public static RuntimeException quit(String msg) {
        System.err.println(msg);
        System.exit(1);
        return null;
    }
}
