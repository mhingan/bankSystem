package org.example.Backend;

import com.google.gson.Gson;
import org.example.UI.ATM_loginPanel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ATM_online {
    private static String username;
    private static String password;
    private static boolean isLoggedIn;

    public ATM_online() {
    }

    public ATM_online(String username, String password, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }


    public static BankAccount login(ATM_loginPanel loginPanel) {
        String inputUsername = loginPanel.getUsername();
        String inputPassword = loginPanel.getPassword();


        Gson gson = GsonFactory.createGson();
        File jsonFile = new File("atm_online_useri.json");

        if (!jsonFile.exists()) {
            loginPanel.setErrorLabel("Baza de date lipsește.");
            return null;
        }

        List<BankAccount> accounts;
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            // 1) Citim un array de conturi, nu un singur obiect
            Type listType = new com.google.gson.reflect.TypeToken<List<BankAccount>>() {}.getType();
            accounts = gson.fromJson(reader, listType);
            if (accounts == null) {
                loginPanel.setErrorLabel("Nu există niciun cont.");
                return null;
            }
        } catch (Exception ex) {
            loginPanel.setErrorLabel("Eroare la citirea bazei de date.");
            return null;
        }

        // 2) Căutăm contul cu username+password în fiecare obiect BankAccount
        for (BankAccount account : accounts) {
            User u = account.getUser();
            if (u != null) {
                if (u.getUsername().equals(inputUsername) && u.getPassword().equals(inputPassword)) {
                    System.out.println("Debug: login reușit");
                    return account;
                }
            }
        }

        loginPanel.setErrorLabel("Username or password are incorrect.");
        return null;
    }



    public boolean createAccount(BankAccount account) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("atm_online_useri.json"))) {
            Gson gson = GsonFactory.createGson();
            bw.write(gson.toJson(account)); // scriem obiectul ca JSON
            return true;
        } catch (IOException ex) {
            System.out.println("Eroare la scrierea în fișier: " + ex.getMessage());
            return false;
        }
    }


}
