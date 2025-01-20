import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFirstGui extends JFrame {

    public MyFirstGui() {
        setSize(300, 200);
        setTitle("My First GUI");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Creates friendly mid-level container and glue it on
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);  // absolute positioning
        getContentPane().add(mainPanel);

        JButton helloButton = new JButton("Hello");
        helloButton.setBounds(25, 25, 80, 30);
        mainPanel.add(helloButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(125, 25, 80, 30);
        mainPanel.add(clearButton);

        JTextField msgField = new JTextField();
        msgField.setBounds(25, 100, 225, 30);
        mainPanel.add(msgField);

        helloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgField.setText("Hello world");
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgField.setText("");
            }
        });

        // do this last
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyFirstGui();
    }
}
