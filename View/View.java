package View;

import Controller.*;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame {
    public View(Controller controller) {
        setTitle("MVC Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to the MVC Example");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Click Me");
        button.addActionListener(e -> controller.handleButtonClick());
        add(button, BorderLayout.SOUTH);

        setVisible(true);
    }
}