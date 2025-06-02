package org.example.UI;

import org.example.Backend.BankAccount;
import org.example.Backend.User;

import javax.swing.*;
import java.awt.*;

public class ATM_loggedPage extends JPanel {
    private JButton userInfoButton    = new JButton("MyInfo");
    private JButton accountInfoButton = new JButton("Account info");
    private JButton logoutButton      = new JButton("Logout");

    private BankAccount account;

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
                // Validation: all fields must be non-empty
                if (firstNameField.getText().trim().isEmpty()
                        || lastNameField.getText().trim().isEmpty()
                        || emailField.getText().trim().isEmpty()
                        || phoneField.getText().trim().isEmpty()
                        || addressField.getText().trim().isEmpty()) {
                    errorLabel.setText("Please fill in all fields.");
                    errorLabel.setForeground(Color.RED);
                    return;
                }

                // If validation passes, save and show success
                u.setfName(firstNameField.getText());
                u.setlName(lastNameField.getText());
                u.setEmail(emailField.getText());
                u.setPhone(phoneField.getText());
                u.setAddress(addressField.getText());

                errorLabel.setText("Saved successfully.");
                errorLabel.setForeground(Color.GREEN);

                // Delay closing so user sees the green message
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
    }
}
