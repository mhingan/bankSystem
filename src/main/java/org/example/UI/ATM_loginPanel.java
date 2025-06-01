package org.example.UI;

import org.example.Backend.ATM_online;
import org.example.Backend.BankAccount;

import javax.swing.*;
import java.awt.*;

public class ATM_loginPanel extends JPanel {
    private JTextField usernameField  = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton      = new JButton("Login");
    private JButton signupButton    = new JButton("Register");
    private JLabel errorLabel        = new JLabel("", SwingConstants.CENTER);
    private JFrame parentFrame;

    public ATM_loginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        add(userLabel);

        usernameField.setBounds(130, 50, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 90, 80, 25);
        add(passLabel);

        passwordField.setBounds(130, 90, 160, 25);
        add(passwordField);

        loginButton.setBounds(130, 130, 80, 25);
        add(loginButton);

        signupButton.setBounds(130, 160, 80, 25);
        add(signupButton);

        errorLabel.setBounds(50, 190, 240, 25);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        loginButton.addActionListener(e -> {
            BankAccount account = ATM_online.login(this);
            if (account != null) {
                ATM_loggedPage loggedPage = new ATM_loggedPage(parentFrame, account);
                parentFrame.setContentPane(loggedPage);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });


        signupButton.addActionListener(e -> {
           ATM_createAccount createAccount = new ATM_createAccount(parentFrame);
           parentFrame.setContentPane(createAccount);
           parentFrame.revalidate();
           parentFrame.repaint();
        });
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setErrorLabel(String message) {
        errorLabel.setText(message);
    }
}
