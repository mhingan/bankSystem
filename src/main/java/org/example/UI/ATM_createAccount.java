package org.example.UI;

import com.google.gson.Gson;
import org.example.Backend.BankAccount;
import org.example.Backend.GsonFactory;
import org.example.Backend.User;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ATM_createAccount extends JPanel {

    private JTextField CNP = new JTextField(15);
    private JTextField firstName = new JTextField(15);
    private JTextField lastName = new JTextField(15);
    private JTextField email = new JTextField(15);
    private JTextField phone = new JTextField(15);
    private JTextField address = new JTextField(15);
    private JTextField username = new JTextField(15);
    private JPasswordField password = new JPasswordField(15);
    private JLabel errorLabel = new JLabel(" ");

    private JButton createAccount = new JButton("Create Account");
    private JButton loginButton = new JButton("Login");

    private JFrame parentFrame;

    public ATM_createAccount(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    public void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets labelInsets = new Insets(8, 8, 2, 4);
        Insets fieldInsets = new Insets(8, 2, 2, 8);
        Insets buttonInsets = new Insets(16, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = labelInsets;
        add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(firstName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(lastName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("CNP:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(CNP, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(address, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.insets = labelInsets;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.insets = fieldInsets;
        add(password, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.add(createAccount);
        buttonPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = buttonInsets;
        add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 8, 8);
        errorLabel.setForeground(Color.RED);
        add(errorLabel, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            ATM_loginPanel ATM_loginPanel = new ATM_loginPanel(parentFrame);
            parentFrame.setContentPane(ATM_loginPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        createAccount.addActionListener((ActionEvent e) -> {
            String cnp       = CNP.getText().trim();
            String fName     = firstName.getText().trim();
            String lName     = lastName.getText().trim();
            String mail      = email.getText().trim();
            String tel       = phone.getText().trim();
            String addr      = address.getText().trim();
            String usern     = username.getText().trim();
            String pass      = new String(password.getPassword());

            if (cnp.isEmpty() || fName.isEmpty() || lName.isEmpty()
                    || mail.isEmpty() || tel.isEmpty() || addr.isEmpty()
                    || usern.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                        ATM_createAccount.this,
                        "EMPTY FIELDS FOUND!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            User user = new User(cnp, fName, lName, mail, tel, addr, usern, pass);

            long generatedNumber = System.currentTimeMillis() % 1_000_000;
            LocalDate expire = LocalDate.now().plusYears(3);

            BankAccount account = new BankAccount(generatedNumber, user, 0.0, expire);

            saveAccountAsJson(account);

            JOptionPane.showMessageDialog(
                    ATM_createAccount.this,
                    "Account created successfully. Acc. no.: " + generatedNumber,
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE
            );

            CNP.setText("");
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            phone.setText("");
            address.setText("");
            username.setText("");
            password.setText("");
        });
    }

    private void saveAccountAsJson(BankAccount newAccount) {
        try {
            Gson gson = GsonFactory.createGson();
            String filePath = "atm_online_useri.json";
            List<BankAccount> list;

            if (Files.exists(Paths.get(filePath))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    Type listType = new com.google.gson.reflect.TypeToken<List<BankAccount>>() {}.getType();
                    list = gson.fromJson(reader, listType);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                }
            } else {
                list = new ArrayList<>();
            }

            list.add(newAccount);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                gson.toJson(list, writer);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    ATM_createAccount.this,
                    "Error at saving account:\n" + ex.getMessage(),
                    "Error I/O",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
