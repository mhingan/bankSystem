package org.example.UI;

import org.example.Backend.ATM_online;
import org.example.Backend.BankAccount;
import java.net.InetAddress;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ATM_loginPanel extends JPanel {
    private JTextField usernameField  = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton      = new JButton("Login");
    private JButton signupButton     = new JButton("Register");
    private JLabel errorLabel        = new JLabel("", SwingConstants.CENTER);
    private JFrame parentFrame;

    public ATM_loginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        errorLabel.setForeground(Color.RED);
        centerPanel.add(errorLabel, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            BankAccount account = ATM_online.login(this);
            if (account != null) {

                ATM_loggedPage loggedPage = new ATM_loggedPage(parentFrame, account);
                parentFrame.setContentPane(loggedPage);
                parentFrame.revalidate();
                parentFrame.repaint();

                JDialog loadingDialog = new JDialog(parentFrame, "Loading", false);
                loadingDialog.setUndecorated(true);
                JLabel loadingLabel = new JLabel("Please wait a moment, we are saving some info.", SwingConstants.CENTER);
                loadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
                loadingDialog.add(loadingLabel);
                loadingDialog.pack();
                loadingDialog.setLocationRelativeTo(parentFrame);

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            saveLoginInfo();
                        } catch (UnknownHostException ex) {
                            SwingUtilities.invokeLater(() -> {
                                errorLabel.setForeground(Color.RED);
                                errorLabel.setText("Could not save login info: " + ex.getMessage());
                            });
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingDialog.dispose();
                    }
                };

                worker.execute();
                loadingDialog.setVisible(true);
            }
        });


        signupButton.addActionListener(e -> {
            ATM_createAccount createAccount = new ATM_createAccount(parentFrame);
            parentFrame.setContentPane(createAccount);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    private void saveLoginInfo() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        String timestamp = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter("ATM_login.txt", true))) {
            bufferedWriter.append("Date: ")
                    .append(timestamp)
                    .append(", IP: ")
                    .append(ip)
                    .append("\n");
            bufferedWriter.close();
            System.out.println("scris cu succes");
        } catch (IOException ioEx) {
            errorLabel.setForeground(Color.RED);
            errorLabel.setText("An error occurred while saving login info");
        }
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
