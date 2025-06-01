package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class ATM_online {
    private String username;
    private String password;
    private boolean isLoggedIn;

    public ATM_online() {}

    public ATM_online(String username, String password, boolean isLoggedIn) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ATM Online!\n\n Do you want to login?");
        String login = scanner.nextLine();
        if (login.equalsIgnoreCase("yes")) {
            login();
        } else {
            System.exit(10023);
        }


    }

    public boolean login() {
        System.out.println("Insert your username:\n");
        Scanner scanner = new Scanner(System.in);
        username = scanner.nextLine();
        System.out.println("Insert your password:\n");
        password = scanner.nextLine();

        String userN = null;
        String userPass = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("atm_online_useri.json"));
            String line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split(" ");
                userN = tokens[6];
                userPass = tokens[7];
            }

            if (username.equals(userN) && password.equals(userPass)) {
                isLoggedIn = true;
                return true;
            }


        } catch (IOException ex1) {
            System.out.println("Something went wrong");
        }

        isLoggedIn = false;
        return false;

    }


    public boolean logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
        }
        return true;
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
