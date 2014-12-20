import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

public class Lobby extends JFrame implements ActionListener {
    private final JTextField address = new JTextField();
    private final JTextField port = new JTextField();
    private final JButton connect = new JButton();


    public void display() {
        connect.addActionListener(this);
        connect.setText("Se Connecter");

        address.setText("localhost");
        port.setText("5555");

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Adresse"));
        panel.add(address);
        panel.add(new JLabel("Port"));
        panel.add(port);

        Container ctnr = getContentPane();
        ctnr.setLayout(new FlowLayout(FlowLayout.CENTER));
        ctnr.add(panel);
        ctnr.add(connect);

        setTitle("Morpion");
        setSize(200, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() != connect) {
            return;
        }

        String address = this.address.getText();
        int port = Integer.parseInt(this.port.getText());

        StatusWindow w = new StatusWindow("Connection...");

        try {
            Client client = new Client(address, port);
            ModeleMorpion.Etat joueur;

            BufferedReader reader = client.createBufferedReader();
            while (true) {
                String str = reader.readLine();

                if (str.startsWith("joueur_id")) {
                    int index = Integer.parseInt(str.substring("joueur_id".length() + 1));
                    joueur = ModeleMorpion.Etat.values()[index];
                    break;
                }
                // TODO couldnt join the game
            }

            w.dispose();
            new MorpionSwing(new NetworkModelMorpion(client, joueur));
        } catch (IOException e) {
            w.setText(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        new Lobby().display();
    }
}
