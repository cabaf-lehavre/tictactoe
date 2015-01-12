import java.io.BufferedReader;
import java.io.IOException;

public class Lobby {

    public static void main(String[] args) {
        try {
            final Client client = connect();

            int id = initGame(client);
            if (id == -1) {
                throw Console.quit("something went wrong, game started before I got my ID");
            }

            int winner = playGame(client, id);

            if (winner == id) {
                Console.println("You won :-)");
            } else {
                Console.println("You lose :-(");
            }
        } catch (IOException e) {
            throw Console.quit(e.getLocalizedMessage());
        }
    }

    public static Client connect() throws IOException {
        final String address = Console.readString("Entrez l'IP du serveur");
        final int port = Console.readInt("Entrez le port du serveur");

        final Client client;
        try {
            client = new Client(address, port);
        } catch (IOException e) {
            throw Console.quit(e.getLocalizedMessage());
        }
        return client;
    }

    public static int initGame(Client client) throws IOException {
        final BufferedReader reader = client.createBufferedReader();

        int id = -1;

        String line;
        while ((line = reader.readLine()) != null) {
            Console.println("RECV " + line);
            if (line.startsWith("joueur_id")) {
                id = Integer.parseInt(line.substring("joueur_id".length() + 1));
                client.println("enter");
            } else if (line.equalsIgnoreCase("start_game")) {
                break;
            }
        }
        return id;
    }

    public static int playGame(Client client, int id) throws IOException {
        final DiabalikModele diabalik = new DiabalikModele();
        final BufferedReader reader = client.createBufferedReader();

        int winner = -1;

        String line;
        String[] args;
        while ((line = reader.readLine()) != null) {
            Console.println("RECV " + line);

            if (line.startsWith("deplacer")) {
                args = line.split(",");

                int x = Integer.parseInt(args[1]),
                    y = Integer.parseInt(args[2]),
                    xD = Integer.parseInt(args[3]),
                    yD = Integer.parseInt(args[4]),
                    prop = Integer.parseInt(args[5]);

                diabalik.deplacer(x, y, xD, yD, prop);
            } else if (line.startsWith("passe")) {
                args = line.split(",");

                int x = Integer.parseInt(args[1]),
                    y = Integer.parseInt(args[2]),
                    xD = Integer.parseInt(args[3]),
                    yD = Integer.parseInt(args[4]),
                    prop = Integer.parseInt(args[5]);

                diabalik.passe(x, y, xD, yD, prop);
            } else if (line.startsWith("start_turn")) {
                int playing = Integer.parseInt(line.substring("start_turn".length() + 1));
                if (playing == id) {
                    play(client);
                }
            } else if (line.startsWith("end_game")) {
                winner = Integer.parseInt(line.substring("end_game".length() + 1));
                break; // avoid printing game
            }

            Console.println(diabalik.toString());
        }

        return winner;
    }

    public static void play(Client client) {
        char action =
            Console.readCharacter(
                "Quel action souhaite-tu faire?\n" +
                "\t[D]eplacer\n" +
                "\t[P]asser\n");

        int x = Console.readInt("x="),
            y = Console.readInt("y="),
            xD = Console.readInt("xD="),
            yD = Console.readInt("yD=");

        switch (action) {
            case 'D':
                client.println(String.format("deplacer,%d,%d,%d,%d", x, y, xD, yD));
                break;

            case 'P':
                client.println(String.format("passer,%d,%d,%d,%d", x, y, xD, yD));
                break;
        }
    }
}
