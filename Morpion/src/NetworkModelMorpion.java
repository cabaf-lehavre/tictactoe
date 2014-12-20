import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NetworkModelMorpion extends Thread implements ModeleMorpion {
    private final Client client;
    private final Etat joueur;

    private ModeleMorpionListener listener;
    private Etat gagnant;

    private final Lock endWaiter = new ReentrantLock();
    private final Lock transactionWaiter = new ReentrantLock();

    public NetworkModelMorpion(Client client, Etat joueur) {
        this.client = client;
        this.joueur = joueur;
    }

    private void onReceive(String line) {
        String[] args = line.split(",");
        
        if (args[0].equalsIgnoreCase("end")) {
            gagnant = args[1].equalsIgnoreCase("1") ? Etat.CROIX : Etat.ROND;
            endWaiter.unlock();
        }

        if (args[0].equalsIgnoreCase("set")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            Etat j = args[1].equalsIgnoreCase("1") ? Etat.CROIX : Etat.ROND;

            listener.setCase(x, y, j);
            transactionWaiter.unlock();
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

    @Override
    public void cocher(int x, int y) {
        transactionWaiter.lock();
        client.println(String.format("set,%d,%d", x, y));
        transactionWaiter.lock();
        transactionWaiter.unlock();
    }

    @Override
    public void recommencer() {

    }

    @Override
    public boolean estTerminee() {
        return gagnant != null;
    }

    @Override
    public boolean estGagnee() {
        return gagnant == joueur;
    }

    @Override
    public Etat getJoueur() {
        return joueur;
    }

    @Override
    public Etat getValeur(int x, int y) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void setListener(ModeleMorpionListener listener) {
        this.listener = listener;
    }
}
