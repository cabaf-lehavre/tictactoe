package fjorde.ui;
import fjorde.Bag;
import fjorde.Player;
import javax.swing.*;
import java.awt.*;

/**
 * @author Alexandre BAPTISTE
 */
public class Scoreboard extends JPanel{

    JLabel playerOneScore = new JLabel();
    JLabel playerTwoScore = new JLabel();
    JButton playerOne = new JButton();
    JButton playerTwo = new JButton();

    public Scoreboard(Player player_one, Player player_two) {

        setLayout(null);
        playerOneScore.setText(player_one.getName() + ": " + player_one.getScore());
        playerTwoScore.setText(player_two.getName() + ": " + player_two.getScore());

        playerOne.setBackground(Color.WHITE);
        playerOne.setIcon( new ImageIcon("img/barbare1.jpg"));

        playerTwo.setBackground(Color.WHITE);
        playerTwo.setIcon( new ImageIcon("img/barbare2.png"));

        playerOne.setBounds(10,50,50,63);
        playerTwo.setBounds(10,130,64,50);
        playerOneScore.setBounds(80,55,100,100);
        playerTwoScore.setBounds(80,120,100,100);
        add(playerOne);
        add(playerTwo);
        add(playerOneScore);
        add(playerTwoScore);
    }


}
