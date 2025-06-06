package org.example.UI;

import com.google.gson.Gson;
import org.example.Backend.BankAccount;
import org.example.Backend.GsonFactory;
import org.example.Backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ATM_loggedPage extends JPanel {
    private JButton userInfoButton    = new JButton("MyInfo");
    private JButton accountInfoButton = new JButton("Account info");
    private JButton logoutButton      = new JButton("Logout");
    private JButton historyButton      = new JButton("History");

    private static BankAccount account;

    public ATM_loggedPage(JFrame parentFrame, BankAccount account) {
        this.account = account;
        User u = account.getUser();

        setLayout(null);

        JPanel panelHomePage = new JPanel();
        panelHomePage.setLayout(null);
        panelHomePage.setBounds(10, 10, 360, 120);
        panelHomePage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(panelHomePage);


        JLabel usernameLabel = new JLabel("Username: " + u.getUsername());
        usernameLabel.setBounds(10, 10, 340, 20);
        panelHomePage.add(usernameLabel);

        JLabel accountNumberLabel = new JLabel("Account Number: " + account.getNumber());
        accountNumberLabel.setBounds(10, 40, 340, 20);
        panelHomePage.add(accountNumberLabel);

        JLabel balanceLabel = new JLabel("Balance: " + account.getBalance() + " RON");
        balanceLabel.setBounds(10, 70, 340, 20);
        panelHomePage.add(balanceLabel);

        logoutButton.setBounds(10, 140, 80, 30);
        add(logoutButton);

        userInfoButton.setBounds(10, 180, 100, 30);
        add(userInfoButton);

        accountInfoButton.setBounds(120, 180, 120, 30);
        add(accountInfoButton);


        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                ATM_loginPanel loginPanel = new ATM_loginPanel(parentFrame);
                parentFrame.setContentPane(loginPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        userInfoButton.addActionListener(e -> {
            JDialog dialog = new JDialog(parentFrame, "Personal info", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setSize(350, 260);
            dialog.setLayout(null);

            // First name
            JTextField firstNameField = new JTextField(u.getfName());
            firstNameField.setBounds(10, 10, 200, 20);
            firstNameField.setEditable(false);
            dialog.add(firstNameField);

            // Last name
            JTextField lastNameField = new JTextField(u.getlName());
            lastNameField.setBounds(10, 40, 200, 20);
            lastNameField.setEditable(false);
            dialog.add(lastNameField);

            // Email
            JTextField emailField = new JTextField(u.getEmail());
            emailField.setBounds(10, 70, 200, 20);
            emailField.setEditable(false);
            dialog.add(emailField);

            // Phone
            JTextField phoneField = new JTextField(u.getPhone());
            phoneField.setBounds(10, 100, 200, 20);
            phoneField.setEditable(false);
            dialog.add(phoneField);

            // Address
            JTextField addressField = new JTextField(u.getAddress());
            addressField.setBounds(10, 130, 200, 20);
            addressField.setEditable(false);
            dialog.add(addressField);

            // Error/Success label
            JLabel errorLabel = new JLabel("");
            errorLabel.setBounds(10, 190, 320, 20);
            dialog.add(errorLabel);

            // Single Edit button
            JButton editButton = new JButton("Edit");
            editButton.setBounds(10, 160, 80, 20);
            editButton.addActionListener(ev -> {
                firstNameField.setEditable(true);
                lastNameField.setEditable(true);
                emailField.setEditable(true);
                phoneField.setEditable(true);
                addressField.setEditable(true);
                errorLabel.setText("");
            });
            dialog.add(editButton);

            // Save button
            JButton saveButton = new JButton("Save");
            saveButton.setBounds(120, 160, 80, 20);
            dialog.add(saveButton);

            saveButton.addActionListener(ev -> {
                if (firstNameField.getText().trim().isEmpty()
                        || lastNameField.getText().trim().isEmpty()
                        || emailField.getText().trim().isEmpty()
                        || phoneField.getText().trim().isEmpty()
                        || addressField.getText().trim().isEmpty()) {
                    errorLabel.setText("Please fill in all fields.");
                    errorLabel.setForeground(Color.RED);
                    return;
                }

                u.setfName(firstNameField.getText());
                u.setlName(lastNameField.getText());
                u.setEmail(emailField.getText());
                u.setPhone(phoneField.getText());
                u.setAddress(addressField.getText());

                errorLabel.setText("Saved successfully.");
                errorLabel.setForeground(Color.GREEN);

                new javax.swing.Timer(800, evt -> {
                    ((javax.swing.Timer) evt.getSource()).stop();
                    dialog.dispose();
                }).start();
            });

            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        });




        accountInfoButton.addActionListener(e -> {
            JDialog dialog = new JDialog(parentFrame, "Account info", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setSize(300, 150);
            dialog.setLayout(null);

            JLabel balanceInfoLabel = new JLabel("Balance: " + account.getBalance() + " RON");
            balanceInfoLabel.setBounds(10, 10, 280, 20);
            dialog.add(balanceInfoLabel);

            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        });

        JTextArea transactionArea = new JTextArea();

        historyButton.setBounds(250, 180, 100, 30);
        add(historyButton);


        historyButton.addActionListener(e -> {
            removeAll();
            repaint();
            setLayout(null);

            JButton backButton = new JButton("Back");
            backButton.setBounds(10, 10, 80, 30);
            add(backButton);

            JLabel loginLabel = new JLabel("Login History:");
            loginLabel.setBounds(20, 50, 150, 20);
            add(loginLabel);

            JTextArea loginArea = new JTextArea();
            loginArea.setEditable(false);

            try {
                java.util.List<String> loginLines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("ATM_login.txt"));
                StringBuilder loginText = new StringBuilder();
                for (String line : loginLines) {
                    loginText.append(line).append("\n");
                }
                loginArea.setText(loginText.toString());
            } catch (Exception ex) {
                loginArea.setText("Could not load login history.");
            }

            JScrollPane loginScroll = new JScrollPane(loginArea);
            loginScroll.setBounds(20, 70, 340, 100);
            add(loginScroll);

            JLabel transactionLabel = new JLabel("Transaction History:");
            transactionLabel.setBounds(20, 180, 200, 20);
            add(transactionLabel);


            transactionArea.setEditable(false);

            try {
                java.util.List<String> transactionLines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("ATM_history_transactions.txt"));
                StringBuilder transactionText = new StringBuilder();
                for (String line : transactionLines) {
                    transactionText.append(line).append("\n");
                }
                transactionArea.setText(transactionText.toString());
            } catch (Exception ex) {
                transactionArea.setText("Could not load transaction history.");
            }

            JScrollPane transactionScroll = new JScrollPane(transactionArea);
            transactionScroll.setBounds(20, 200, 340, 100);
            add(transactionScroll);

            backButton.addActionListener(ev -> {
                parentFrame.setContentPane(new ATM_loggedPage(parentFrame, account));
                parentFrame.revalidate();
                parentFrame.repaint();
            });

            revalidate();
            repaint();
        });



        JPanel transferPanel = new JPanel();
        transferPanel.setLayout(null);
        transferPanel.setBorder(BorderFactory.createTitledBorder("New Transfer"));
        transferPanel.setBounds(20, 310, 340, 130);
        add(transferPanel);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(10, 20, 30, 20);
        transferPanel.add(toLabel);

        JTextField toField = new JTextField();
        toField.setBounds(50, 20, 270, 20);
        transferPanel.add(toField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 50, 60, 20);
        transferPanel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(70, 50, 250, 20);
        transferPanel.add(amountField);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(10, 105, 320, 20);
        statusLabel.setForeground(Color.RED);
        transferPanel.add(statusLabel);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(110, 80, 120, 20);
        transferPanel.add(transferButton);

        transferButton.addActionListener(ev -> {
            String to = toField.getText().trim();
            String amountText = amountField.getText().trim();

            if (to.isEmpty() || amountText.isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                statusLabel.setText("Invalid amount.");
                return;
            }

            if (amount <= 0) {
                statusLabel.setText("Amount must be positive.");
                return;
            }

            if (account.getBalance() < amount) {
                statusLabel.setText("Insufficient balance.");
                return;
            }

            account.setBalance(account.getBalance() - amount);
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("Transfer successful.");
            updateAccountBalance();


            try {
                java.nio.file.Files.write(
                        java.nio.file.Paths.get("ATM_history_transactions.txt"),
                        (u.getUsername() + " transferred " + amount + " RON to " + to + "\n").getBytes(),
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.APPEND
                );
            } catch (Exception ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Transfer saved, but failed to log.");
            }

            try {
                java.util.List<String> transactionLines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("ATM_history_transactions.txt"));
                StringBuilder transactionText = new StringBuilder();
                for (String line : transactionLines) {
                    transactionText.append(line).append("\n");
                }
                transactionArea.setText(transactionText.toString());
            } catch (Exception ex) {
                transactionArea.setText("Could not reload transaction history.");
            }
        });





    }


    private void updateAccountBalance() {
        try {
            Gson gson = GsonFactory.createGson();
            String filePath = "atm_online_useri.json";
            java.util.List<BankAccount> list;

            if (Files.exists(Paths.get(filePath))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    Type listType = new com.google.gson.reflect.TypeToken<java.util.List<BankAccount>>() {}.getType();
                    list = gson.fromJson(reader, listType);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                }
            } else {
                list = new ArrayList<>();
            }

            for (BankAccount acc : list) {
                if (acc.getNumber() == account.getNumber()) {
                    acc.setBalance(account.getBalance());
                    break;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                gson.toJson(list, writer);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating account:\n" + ex.getMessage(),
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }






}
