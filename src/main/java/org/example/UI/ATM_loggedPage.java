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

        // PANEL HOME (dreptunghi în care afișăm username, număr cont și sumă)
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
            dialog.setSize(300, 200);
            dialog.setLayout(null);

            JLabel firstNameLabel = new JLabel("First name: " + u.getfName());
            firstNameLabel.setBounds(10, 10, 280, 20);
            dialog.add(firstNameLabel);

            JLabel lastNameLabel = new JLabel("Last name: " + u.getlName());
            lastNameLabel.setBounds(10, 40, 280, 20);
            dialog.add(lastNameLabel);

            JLabel emailLabel = new JLabel("Email: " + u.getEmail());
            emailLabel.setBounds(10, 70, 280, 20);
            dialog.add(emailLabel);

            JLabel phoneLabel = new JLabel("Phone: " + u.getPhone());
            phoneLabel.setBounds(10, 100, 280, 20);
            dialog.add(phoneLabel);

            JLabel addressLabel = new JLabel("Address: " + u.getAddress());
            addressLabel.setBounds(10, 130, 280, 20);
            dialog.add(addressLabel);

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
