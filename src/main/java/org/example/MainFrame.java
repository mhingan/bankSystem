package org.example.UI;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("ATM Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Adăugăm panoul de login
        ATM_loginPanel loginPanel = new ATM_loginPanel(this);
        setContentPane(loginPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Pornim GUI-ul în thread-ul EDT
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
