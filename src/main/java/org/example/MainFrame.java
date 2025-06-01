package org.example;

import org.example.UI.ATM_loginPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("ATM Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);

        ATM_loginPanel loginPanel = new ATM_loginPanel(this);
        setContentPane(loginPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
