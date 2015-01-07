package fr.cabaf.backend.handler.morpion;

import fr.cabaf.backend.Client;
import fr.cabaf.backend.ClientHandler;

import java.util.Random;

/**
 * @author Brieuc DE TAPPIE
 */
public class IAMorpion implements ClientHandler {
    private Client client;
    private ModeleMorpion.Etat clientId;
    private ModeleMorpion morpion = new ModeleMorpionSimple();
    private IA ia;
    private Random random = new Random(System.nanoTime());
    private ModeleMorpion.Etat currentId;

    @Override
    public void onConnect(Client client) {
        if (this.client != null) {
            client.sendLine("already_running");
            return;
        }

        this.client = client;

        boolean premier = random.nextBoolean();
        this.currentId = ModeleMorpion.Etat.CROIX;
        this.clientId = premier ? ModeleMorpion.Etat.CROIX : ModeleMorpion.Etat.ROND;
        ia = new IA(morpion, !premier ? ModeleMorpion.Etat.CROIX : ModeleMorpion.Etat.ROND);
        client.sendLine("joueur_id," + clientId.ordinal());
        client.sendLine("start_game");

        passTurn();
    }

    @Override
    public void onDisconnect(Client client) {

    }

    @Override
    public void onReceive(Client client, String str) {
        System.out.println("RECV " + str);

        if (currentId != clientId) {
            return;
        }

        if (str.startsWith("cocher")) {
            // client plays

            String[] args = str.split(",");

            int x = Integer.parseInt(args[1]),
                y = Integer.parseInt(args[2]);

            if (morpion.cocher(x, y, clientId)) {
                client.sendLine("cocher," + x + "," + y + "," + clientId.ordinal());
                passTurn();
            }
        }
    }

    @Override
    public void onException(Exception e) {

    }

    private void passTurn() {
        client.sendLine("start_turn," + currentId.ordinal());

        if (currentId == clientId) {
            // client plays
        } else {
            ia.play();
            client.sendLine("cocher,,,");
        }

        if (morpion.estTerminee()) {
            client.sendLine("end_game,<<gagnant>>");
            client.close();

            cleanup();
        }
    }

    private void cleanup() {
        this.client = null;
    }
}
