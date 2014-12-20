import javax.swing.*;

public class StatusWindow extends JFrame {
    private final JLabel label = new JLabel();

    public StatusWindow() {
        add(label);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public StatusWindow(String text) {
        this();
        setText(text);
    }

    public String getText() {
        return label.getText();
    }

    public void setText(String text) {
        this.label.setText(text);
    }
}
