import java.io.BufferedReader;
import java.io.IOException;

public class NetworkModelDiabalik extends Thread {
    private final Client client;
    private final int joueur;

    private ModeleDiabalikSimple listener;
    private int gagnant;

    public NetworkModelDiabalik(Client client, int joueur) {
        this.client = client;
        this.joueur = joueur;
    }

    private void onReceive(String line) {
        System.out.println("RECV " + line);

        String[] args = line.split(",");

        if (args[0].equalsIgnoreCase("passe")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int xD = Integer.parseInt(args[3]);
            int yD = Integer.parseInt(args[4]);
            int j = Integer.parseInt(args[5]);

            listener.passe(x, y,yD,xD,j);
        }

        if (args[0].equalsIgnoreCase("deplacement")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int xD = Integer.parseInt(args[3]);
            int yD = Integer.parseInt(args[4]);
            int j = Integer.parseInt(args[5]);

            listener.deplacer(x,y,yD,xD,j);
        }

        if (args[0].equalsIgnoreCase("end_tour")) {
            int j = Integer.parseInt(args[1]);

            listener.changerTour(j);
        }

        if (args[0].equalsIgnoreCase("end_game")) {
            int j = Integer.parseInt(args[1]);

            listener.aGagne(j);
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = client.createBufferedReader();
            String line;
            while ((line = reader.readLine()) != null) {
                onReceive(line);
            }
        } catch (IOException e) {
            throw new Error(e);
        }
    }


    public void envPasse(int x, int y,int xD,int yD) {
        client.println("passe," + x + "," + y+","+xD+","+yD);
    }

    public void envDeplacer(int x, int y,int xD,int yD) {
        client.println("deplacement," + x + "," + y+","+xD+","+yD);
    }


    public void recommencer() {

    }


    public boolean estTerminee() {
        return gagnant !=0;
    }


    public boolean estGagnee() {
        return gagnant == joueur;
    }


    public int getJoueur() {
        return joueur;
    }


    public int getValeur(int x, int y) {
        return 0;
    }


    public void setListener(ModeleDiabalikSimple listener) {
        this.listener = listener;
    }
}
