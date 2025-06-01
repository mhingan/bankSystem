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


public class ATM_createAccount extends JPanel {

    private JTextField CNP = new JTextField();
    private JTextField firstName = new JTextField();
    private JTextField lastName = new JTextField();
    private JTextField email = new JTextField();
    private JTextField phone = new JTextField();
    private JTextField address = new JTextField();
    private JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JLabel errorLabel = new JLabel();

    private JButton createAccount = new JButton("Create Account");
    private JButton loginButton = new JButton("Login");

    private JFrame parentFrame;

    public ATM_createAccount(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }


    public void initComponents() {
        this.setLayout(null);

        // First Name
        JLabel fNameLabel = new JLabel("First Name:");
        fNameLabel.setBounds(10, 10, 100, 20);
        this.add(fNameLabel);
        firstName.setBounds(120, 10, 150, 20);
        this.add(firstName);

        // Last Name
        JLabel lNameLabel = new JLabel("Last Name:");
        lNameLabel.setBounds(10, 40, 100, 20);
        this.add(lNameLabel);
        lastName.setBounds(120, 40, 150, 20);
        this.add(lastName);

        // CNP
        JLabel cnpLabel = new JLabel("CNP:");
        cnpLabel.setBounds(10, 70, 100, 20);
        this.add(cnpLabel);
        CNP.setBounds(120, 70, 150, 20);
        this.add(CNP);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 100, 100, 20);
        this.add(emailLabel);
        email.setBounds(120, 100, 150, 20);
        this.add(email);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 130, 100, 20);
        this.add(phoneLabel);
        phone.setBounds(120, 130, 150, 20);
        this.add(phone);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 160, 100, 20);
        this.add(addressLabel);
        address.setBounds(120, 160, 150, 20);
        this.add(address);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 190, 100, 20);
        this.add(usernameLabel);
        username.setBounds(120, 190, 150, 20);
        this.add(username);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 220, 100, 20);
        this.add(passwordLabel);
        password.setBounds(120, 220, 150, 20);
        this.add(password);


        //button create account
        createAccount.setBounds(120, 250, 120, 30);
        this.add(createAccount);

        //button create account
        loginButton.setBounds(120, 290, 80, 30);
        this.add(loginButton);

        loginButton.addActionListener(e -> {
            ATM_loginPanel ATM_loginPanel = new ATM_loginPanel(parentFrame);
            parentFrame.setContentPane(ATM_loginPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        });


        //error label
        errorLabel.setBounds(10, 290, 100, 40);
        this.add(errorLabel);


        createAccount.addActionListener(e -> {

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
                    "Cont creat cu succes! Număr cont: " + generatedNumber,
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
                    "Eroare la salvarea contului:\n" + ex.getMessage(),
                    "Eroare I/O",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



}
