package org.example;

import java.time.LocalDate;

public class BankAccount {
    private long number;
    private User user;
    private double balance;
    private LocalDate expireDate;


    public BankAccount(long number, User user, double balance, LocalDate expireDate) {
        this.number = number;
        this.user = user;
        this.balance = balance;
        this.expireDate = expireDate;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        if(number < 100000000 || number > 1999999999){
            throw new IllegalArgumentException("Number must be between 100000000 and 1999999999");
        }
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if(balance < 0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        if(expireDate == null || expireDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Expire date error: " + expireDate);
        }
        this.expireDate = expireDate;
    }

    public void deposit(double amount){
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.balance += amount;
    }

    public void withdraw(double amount){
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(amount > balance){
            throw new IllegalArgumentException("Amount cannot be greater than balance");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "Number: " + number + "\nUser: " + user + "\nBalance: " + balance + "\nExpire Date: " + expireDate;
    }
}
