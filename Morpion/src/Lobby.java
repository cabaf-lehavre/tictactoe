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

        Container ctnr = getContentPane();
        ctnr.setLayout(new GridLayout(3, 2));

        ctnr.add(new JLabel("Adresse"));
        ctnr.add(address);
        ctnr.add(new JLabel("Port"));

        ctnr.add(connect);

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

            BufferedReader reader = client.createBufferedReader();
            ModeleMorpion.Etat joueur = ModeleMorpion.Etat.valueOf(reader.readLine());

            NetworkModelMorpion modele = new NetworkModelMorpion(client, joueur);

            w.dispose();
            new MorpionSwing(modele);
        } catch (IOException e) {
            w.setText(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        new Lobby().display();
    }
}
